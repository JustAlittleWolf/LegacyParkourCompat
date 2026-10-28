package me.wolfii.legacyparkourcompat.mechanic.water;

import me.wolfii.legacyparkourcompat.mechanic.hook.water.WaterBodyContactHook;
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
 * Contact and flow can use separate query domains. The combined 26.2 tracker
 * remains responsible for eyes, depth, lava and modern features. Unsupported
 * probes/flow providers cause a native fallback. Extended modern world-height
 * contexts remain native instead of interpreting modern-only water as historical cells.
 */
public final class WaterScan {
    private WaterScan() { }

    public record Result(boolean contact, Vec3 impulse, boolean replacesContact, boolean replacesCurrent) { }

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
                                        Optional<WaterCurrentHook> current,
                                        Optional<WaterFlowScanBoundHook> flowBound,
                                        Optional<FlowScanAreaAdmissionHook> flowAdmission) {
        Optional<AABB> selected = body.flatMap(hook -> hook.probe(player.getBoundingBox()));
        boolean replacesContact = selected.isPresent();
        AABB flowOnlyBox = selected.isEmpty() && flowBound.isPresent()
            ? legacyFlowQuery(player.getBoundingBox()) : null;
        boolean hasFlowOnlyBound = flowOnlyBox != null && hasHistoricalUpperBound(flowBound.get(), flowOnlyBox);
        int flowOnlyX0 = flowOnlyBox == null ? 0 : Mth.floor(flowOnlyBox.minX);
        int flowOnlyY0 = flowOnlyBox == null ? 0 : Mth.floor(flowOnlyBox.minY);
        int flowOnlyZ0 = flowOnlyBox == null ? 0 : Mth.floor(flowOnlyBox.minZ);
        int flowOnlyX1 = flowOnlyBox == null ? 0 : exclusiveUpperBound(flowBound, flowOnlyBox.maxX);
        int flowOnlyY1 = flowOnlyBox == null ? 0 : exclusiveUpperBound(flowBound, flowOnlyBox.maxY);
        int flowOnlyZ1 = flowOnlyBox == null ? 0 : exclusiveUpperBound(flowBound, flowOnlyBox.maxZ);
        boolean flowOnlyAdmitted = hasFlowOnlyBound && flowAdmission.map(hook -> hook.admits(
            flowOnlyX0, flowOnlyY0, flowOnlyZ0, flowOnlyX1, flowOnlyY1, flowOnlyZ1
        )).orElse(false);
        boolean flowOnlyRejectedByHistoricalArea = hasFlowOnlyBound
            && flowAdmission.isPresent() && !flowOnlyAdmitted;
        boolean replacesCurrent = current.filter(WaterCurrentHook::replacesNativeCurrent).isPresent()
            && (selected.isPresent() || flowOnlyAdmitted || flowOnlyRejectedByHistoricalArea);
        if ((!replacesContact && !replacesCurrent) || !supportsFeatures(player)) return Optional.empty();
        if (player.getVehicle() instanceof AbstractBoat) {
            return Optional.of(new Result(false, Vec3.ZERO, replacesContact, replacesCurrent));
        }
        if (flowOnlyRejectedByHistoricalArea) {
            // The 1.8.9 World.applyLiquidDrag returns before scanning or applying
            // current when its Y-overlap admission fails; keep native contact separate.
            return Optional.of(new Result(false, Vec3.ZERO, false, true));
        }
        AABB contactBox = selected.orElse(null);
        AABB flowBox = selected.orElse(flowOnlyBox);
        WaterBodyContactHook contactHook = body.orElse(null);
        WaterCurrentHook currentHook = current.orElse(null);
        int x0 = contactBox == null ? 0 : Mth.floor(contactBox.minX);
        int x1 = contactBox == null ? 0 : Mth.ceil(contactBox.maxX);
        int y0 = contactBox == null ? 0 : Mth.floor(contactBox.minY);
        int y1 = contactBox == null ? 0 : Mth.ceil(contactBox.maxY);
        int z0 = contactBox == null ? 0 : Mth.floor(contactBox.minZ);
        int z1 = contactBox == null ? 0 : Mth.ceil(contactBox.maxZ);
        int flowX0 = flowOnlyBox != null ? flowOnlyX0 : flowBox == null ? 0 : Mth.floor(flowBox.minX);
        int flowY0 = flowOnlyBox != null ? flowOnlyY0 : flowBox == null ? 0 : Mth.floor(flowBox.minY);
        int flowZ0 = flowOnlyBox != null ? flowOnlyZ0 : flowBox == null ? 0 : Mth.floor(flowBox.minZ);
        int flowX1 = flowOnlyBox != null ? flowOnlyX1
            : flowBox == null ? 0 : exclusiveUpperBound(flowBound, flowBox.maxX);
        int flowY1 = flowOnlyBox != null ? flowOnlyY1
            : flowBox == null ? 0 : exclusiveUpperBound(flowBound, flowBox.maxY);
        int flowZ1 = flowOnlyBox != null ? flowOnlyZ1
            : flowBox == null ? 0 : exclusiveUpperBound(flowBound, flowBox.maxZ);
        Level level = player.level();
        AABB nativeBox = player.getFluidInteractionBox();
        if (nativeBox == null) return Optional.empty();
        int scanX0 = Math.min(Math.min(x0, flowX0), Mth.floor(nativeBox.minX));
        int scanY0 = Math.min(Math.min(y0, flowY0), Mth.floor(nativeBox.minY));
        int scanZ0 = Math.min(Math.min(z0, flowZ0), Mth.floor(nativeBox.minZ));
        int scanX1 = Math.max(Math.max(x1, flowX1), Mth.ceil(nativeBox.maxX));
        int scanY1 = Math.max(Math.max(y1, flowY1), Mth.ceil(nativeBox.maxY));
        int scanZ1 = Math.max(Math.max(z1, flowZ1), Mth.ceil(nativeBox.maxZ));
        if (flowOnlyAdmitted) {
            // 1.8.9 World.getBlockState returns air outside [0, 256); the old
            // admission only requires vertical overlap, so clip those reads.
            scanY0 = Math.max(scanY0, 0);
            scanY1 = Math.min(scanY1, 256);
        } else if (scanY0 < 0 || scanY1 > 256) {
            return Optional.empty();
        }
        boolean contactFound = false;
        Vec3 total = Vec3.ZERO;
        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
        // Inspect the union so modern-only fluid outside the shorter historical
        // probe also causes fallback. Historical accepted-cell order is unchanged.
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
                        boolean inContact = contactBox != null
                            && x >= x0 && x < x1 && y >= y0 && y < y1 && z >= z0 && z < z1
                            && contactHook.includes(y1, y, liquidLevel);
                        if (inContact) {
                            contactFound = true;
                        }
                        boolean inFlow = replacesCurrent && flowBox != null
                            && x >= flowX0 && x < flowX1 && y >= flowY0 && y < flowY1 && z >= flowZ0 && z < flowZ1
                            && includesFlow(flowY1, y, liquidLevel);
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
