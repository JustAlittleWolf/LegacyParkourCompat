package me.wolfii.legacyparkourcompat.mechanic.water;

import me.wolfii.legacyparkourcompat.mechanic.hook.water.WaterBodyContactAreaAdmissionHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.water.WaterBodyContactHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.water.WaterBodyContactScanBoundHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.water.WaterCurrentHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.world.FlowScanAreaAdmissionHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.world.WaterFlowScanBoundHook;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.vehicle.boat.AbstractBoat;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import java.util.Optional;
import java.util.OptionalInt;

/**
 * One shared water-only traversal; it never writes native trackers or velocity.
 * Contact and flow use separate query domains, bounds and area admission. The
 * combined 26.2 tracker remains responsible for eyes, depth, lava and modern
 * features. Unsupported probes/flow providers cause a native fallback.
 * Resolved historical flow uses its own old-height cell bounds.
 */
public final class WaterScan {
    private WaterScan() { }

    public record Result(boolean contact, Vec3 impulse, boolean replacesContact, boolean replacesCurrent) { }
    private record Bounds(int x0, int y0, int z0, int x1, int y1, int z1) { }

    public static boolean supportsFeatures(Player player) {
        // Preserve both the cached LocalPlayer predicate used by updateSwimming
        // and the fresh native eye tracker when selecting this refresh. The
        // result stays frozen until the next refresh; consumers must not recheck
        // mutable sprint/input state after current has already been applied.
        return !player.isSwimming() && !player.isVisuallySwimming()
            && !(player.isSprinting() && (player.isUnderWater() || player.isEyeInFluid(FluidTags.WATER)))
            && !player.hasEffect(MobEffects.SLOW_FALLING) && !player.hasEffect(MobEffects.DOLPHINS_GRACE);
    }

    public static Optional<Result> scan(Player player, Optional<WaterBodyContactHook> body,
                                        Optional<WaterBodyContactScanBoundHook> contactBound,
                                        Optional<WaterBodyContactAreaAdmissionHook> contactAdmission,
                                        Optional<WaterCurrentHook> current,
                                        Optional<WaterFlowScanBoundHook> flowBound,
                                        Optional<FlowScanAreaAdmissionHook> flowAdmission) {
        Optional<AABB> selected = body.flatMap(hook -> hook.probe(player.getBoundingBox()));
        boolean replacesContact = selected.isPresent();
        AABB historicalFlowBox = flowBound.isPresent() ? legacyFlowQuery(player.getBoundingBox()) : null;
        boolean hasHistoricalFlowBound = historicalFlowBox != null
            && hasHistoricalUpperBound(flowBound.get(), historicalFlowBox);
        Bounds historicalFlowBounds = hasHistoricalFlowBound
            ? bounds(historicalFlowBox, flowBound) : null;
        boolean historicalFlowAdmitted = historicalFlowBounds != null && flowAdmission.map(hook -> hook.admits(
            historicalFlowBounds.x0, historicalFlowBounds.y0, historicalFlowBounds.z0,
            historicalFlowBounds.x1, historicalFlowBounds.y1, historicalFlowBounds.z1
        )).orElse(false);
        boolean historicalFlowResolved = historicalFlowBounds != null && flowAdmission.isPresent();
        boolean historicalFlowRejectedByArea = historicalFlowResolved && !historicalFlowAdmitted;
        AABB flowBox = historicalFlowResolved ? historicalFlowBox : selected.orElse(null);
        Bounds flowBounds = historicalFlowResolved ? historicalFlowBounds
            : flowBox == null ? null : bounds(flowBox, flowBound);
        boolean currentResolved = current.filter(WaterCurrentHook::replacesNativeCurrent).isPresent()
            && (historicalFlowResolved || (!hasHistoricalFlowBound && selected.isPresent()));
        boolean replacesCurrent = currentResolved;
        if ((!replacesContact && !replacesCurrent) || !supportsFeatures(player)) return Optional.empty();
        if (player.getVehicle() instanceof AbstractBoat) {
            return Optional.of(new Result(false, Vec3.ZERO, replacesContact, replacesCurrent));
        }
        Bounds contactBounds = selected.map(box -> contactBounds(box, contactBound)).orElse(null);
        boolean contactAdmitted = contactBounds != null && contactAdmission.map(hook -> hook.admits(
            contactBounds.x0, contactBounds.y0, contactBounds.z0,
            contactBounds.x1, contactBounds.y1, contactBounds.z1
        )).orElse(true);
        boolean scanContact = contactBounds != null && contactAdmitted;
        boolean scanFlow = replacesCurrent && !historicalFlowRejectedByArea && flowBounds != null;
        if (!scanContact && !scanFlow) {
            // A rejected historical contact area reports false presence while
            // leaving any independently resolved flow result in its own domain.
            return Optional.of(new Result(false, Vec3.ZERO, replacesContact, replacesCurrent));
        }
        WaterBodyContactHook contactHook = body.orElse(null);
        WaterCurrentHook currentHook = current.orElse(null);
        Level level = player.level();
        AABB nativeBox = player.getFluidInteractionBox();
        if (nativeBox == null) return Optional.empty();
        Bounds nativeBounds = bounds(nativeBox, Optional.empty());
        Bounds scannedContactBounds = scanContact ? contactBounds : null;
        Bounds scannedFlowBounds = scanFlow ? flowBounds : null;
        int scanX0 = Math.min(nativeBounds.x0, Math.min(
            scannedContactBounds == null ? Integer.MAX_VALUE : scannedContactBounds.x0,
            scannedFlowBounds == null ? Integer.MAX_VALUE : scannedFlowBounds.x0));
        int scanY0 = Math.min(nativeBounds.y0, Math.min(
            scannedContactBounds == null ? Integer.MAX_VALUE : scannedContactBounds.y0,
            scannedFlowBounds == null ? Integer.MAX_VALUE : scannedFlowBounds.y0));
        int scanZ0 = Math.min(nativeBounds.z0, Math.min(
            scannedContactBounds == null ? Integer.MAX_VALUE : scannedContactBounds.z0,
            scannedFlowBounds == null ? Integer.MAX_VALUE : scannedFlowBounds.z0));
        int scanX1 = Math.max(nativeBounds.x1, Math.max(
            scannedContactBounds == null ? Integer.MIN_VALUE : scannedContactBounds.x1,
            scannedFlowBounds == null ? Integer.MIN_VALUE : scannedFlowBounds.x1));
        int scanY1 = Math.max(nativeBounds.y1, Math.max(
            scannedContactBounds == null ? Integer.MIN_VALUE : scannedContactBounds.y1,
            scannedFlowBounds == null ? Integer.MIN_VALUE : scannedFlowBounds.y1));
        int scanZ1 = Math.max(nativeBounds.z1, Math.max(
            scannedContactBounds == null ? Integer.MIN_VALUE : scannedContactBounds.z1,
            scannedFlowBounds == null ? Integer.MIN_VALUE : scannedFlowBounds.z1));
        if (!currentResolved && (scanY0 < 0 || scanY1 > 256)) return Optional.empty();
        int flowScanY0 = historicalFlowResolved ? Math.max(flowBounds.y0, 0)
            : flowBounds == null ? 0 : flowBounds.y0;
        int flowScanY1 = historicalFlowResolved ? Math.min(flowBounds.y1, 256)
            : flowBounds == null ? 0 : flowBounds.y1;
        boolean contactFound = false;
        Vec3 total = Vec3.ZERO;
        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
        // One union traversal feeds the independent contact and flow domains.
        for (int x = scanX0; x < scanX1; x++) {
            for (int y = scanY0; y < scanY1; y++) {
                for (int z = scanZ0; z < scanZ1; z++) {
                    pos.set(x, y, z);
                    BlockState state = level.getBlockState(pos);
                    if (state.getFluidState().is(FluidTags.WATER)) {
                        if (!(state.getBlock() instanceof LiquidBlock) || state.getValue(LiquidBlock.LEVEL) >= 8) {
                            return Optional.empty();
                        }
                        int liquidLevel = state.getValue(LiquidBlock.LEVEL);
                        boolean inContact = scannedContactBounds != null
                            && x >= scannedContactBounds.x0 && x < scannedContactBounds.x1
                            && y >= scannedContactBounds.y0 && y < scannedContactBounds.y1
                            && z >= scannedContactBounds.z0 && z < scannedContactBounds.z1
                            && contactHook.includes(contactBounds.y1, y, liquidLevel);
                        if (inContact) contactFound = true;
                        boolean inFlow = replacesCurrent && !historicalFlowRejectedByArea && flowBounds != null
                            && x >= flowBounds.x0 && x < flowBounds.x1
                            && y >= flowScanY0 && y < flowScanY1
                            && z >= flowBounds.z0 && z < flowBounds.z1
                            && includesFlow(flowBounds.y1, y, liquidLevel);
                        if (inFlow) {
                            Optional<Vec3> flow = currentHook.flow(level, pos, state);
                            if (flow.isEmpty()) return Optional.empty();
                            total = currentHook.accumulate(total, flow.get());
                        }
                    }
                }
            }
        }
        Vec3 impulse = replacesCurrent ? currentHook.impulse(total, player.getAbilities().flying) : Vec3.ZERO;
        return Optional.of(new Result(contactFound, impulse, replacesContact, replacesCurrent));
    }

    private static Bounds bounds(AABB box, Optional<WaterFlowScanBoundHook> flowBound) {
        return new Bounds(Mth.floor(box.minX), Mth.floor(box.minY), Mth.floor(box.minZ),
            exclusiveUpperBound(flowBound, box.maxX), exclusiveUpperBound(flowBound, box.maxY),
            exclusiveUpperBound(flowBound, box.maxZ));
    }

    private static Bounds contactBounds(AABB box, Optional<WaterBodyContactScanBoundHook> contactBound) {
        return new Bounds(Mth.floor(box.minX), Mth.floor(box.minY), Mth.floor(box.minZ),
            contactExclusiveUpperBound(contactBound, box.maxX),
            contactExclusiveUpperBound(contactBound, box.maxY),
            contactExclusiveUpperBound(contactBound, box.maxZ));
    }

    private static int contactExclusiveUpperBound(Optional<WaterBodyContactScanBoundHook> hook,
                                                  double maximum) {
        if (hook.isPresent()) {
            OptionalInt historical = hook.get().exclusiveUpperBound(maximum);
            if (historical.isPresent()) return historical.getAsInt();
        }
        return Mth.ceil(maximum);
    }

    private static int exclusiveUpperBound(Optional<WaterFlowScanBoundHook> hook, double maximum) {
        if (hook.isPresent()) {
            OptionalInt historical = hook.get().exclusiveUpperBound(maximum);
            if (historical.isPresent()) return historical.getAsInt();
        }
        return Mth.ceil(maximum);
    }

    private static boolean hasHistoricalUpperBound(WaterFlowScanBoundHook hook, AABB box) {
        return hook.exclusiveUpperBound(box.maxX).isPresent()
            || hook.exclusiveUpperBound(box.maxY).isPresent()
            || hook.exclusiveUpperBound(box.maxZ).isPresent();
    }

    private static AABB legacyFlowQuery(AABB body) {
        // 1.8.9 Entity.checkWaterCollisions passes this exact transformed shape
        // to World.applyLiquidDrag; retain the source float literal conversion.
        return new AABB(body.minX, body.minY - -0.4F, body.minZ,
            body.maxX, body.maxY + -0.4F, body.maxZ).deflate(0.001);
    }

    private static boolean includesFlow(int exclusiveMaxY, int cellY, int liquidLevel) {
        // 1.8.9 LiquidBlock.getHeightLoss and World.applyLiquidDrag's l >= d gate.
        int level = liquidLevel >= 8 ? 0 : liquidLevel;
        double surface = cellY + 1 - (level + 1) / 9.0F;
        return exclusiveMaxY >= surface;
    }

}
