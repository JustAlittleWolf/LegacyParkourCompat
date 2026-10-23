package me.wolfii.legacyparkourcompat.mechanic.water;

import me.wolfii.legacyparkourcompat.mechanic.hook.water.WaterBodyContactHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.water.WaterCurrentHook;
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

/**
 * One shared water-only traversal; it never writes native trackers or velocity.
 * The combined 26.2 tracker remains responsible for eyes, depth, lava and modern
 * features. Unsupported probes/flow providers cause an atomic native fallback.
 * Client chunk checks intentionally permit empty chunks: 1.12-family Feather
 * World.isAreaLoaded(..., true) dispatches to ClientWorld.isChunkLoadedAt,
 * which returns true for allowEmpty. Extended modern world-height contexts
 * remain native instead of interpreting modern-only water as historical cells.
 */
public final class WaterScan {
    private WaterScan() { }

    public record Result(boolean contact, Vec3 impulse, boolean replacesCurrent) { }

    public static boolean supportsFeatures(Player player) {
        // Preserve both the cached LocalPlayer predicate used by updateSwimming
        // and the fresh native eye tracker when selecting this refresh. The
        // result stays frozen until the next refresh; consumers must not recheck
        // mutable sprint/input state after current has already been applied.
        return !player.isSwimming() && !player.isVisuallySwimming()
            && !(player.isSprinting() && (player.isUnderWater() || player.isEyeInFluid(FluidTags.WATER)))
            && !player.hasEffect(MobEffects.SLOW_FALLING) && !player.hasEffect(MobEffects.DOLPHINS_GRACE);
    }

    public static Optional<Result> scan(Player player, WaterBodyContactHook body, WaterCurrentHook current) {
        Optional<AABB> selected = body.probe(player.getBoundingBox());
        if (selected.isEmpty() || !supportsFeatures(player)) return Optional.empty();
        if (player.getVehicle() instanceof AbstractBoat) {
            return Optional.of(new Result(false, Vec3.ZERO, current.replacesNativeCurrent()));
        }
        AABB box = selected.get();
        int x0 = Mth.floor(box.minX), x1 = Mth.ceil(box.maxX);
        int y0 = Mth.floor(box.minY), y1 = Mth.ceil(box.maxY);
        int z0 = Mth.floor(box.minZ), z1 = Mth.ceil(box.maxZ);
        Level level = player.level();
        AABB nativeBox = player.getFluidInteractionBox();
        if (nativeBox == null) return Optional.empty();
        int scanX0 = Math.min(x0, Mth.floor(nativeBox.minX));
        int scanY0 = Math.min(y0, Mth.floor(nativeBox.minY));
        int scanZ0 = Math.min(z0, Mth.floor(nativeBox.minZ));
        int scanX1 = Math.max(x1, Mth.ceil(nativeBox.maxX));
        int scanY1 = Math.max(y1, Mth.ceil(nativeBox.maxY));
        int scanZ1 = Math.max(z1, Mth.ceil(nativeBox.maxZ));
        if (scanY0 < 0 || scanY1 > 256) return Optional.empty();
        boolean contact = false;
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
                        if (x >= x0 && x < x1 && y >= y0 && y < y1 && z >= z0 && z < z1
                                && body.includes(y1, y, state.getValue(LiquidBlock.LEVEL))) {
                            contact = true;
                            if (current.replacesNativeCurrent()) {
                                Optional<Vec3> flow = current.flow(level, pos, state);
                                if (flow.isEmpty()) return Optional.empty();
                                total = current.accumulate(total, flow.get());
                            }
                        }
                    }
                }
            }
        }
        return Optional.of(new Result(contact, current.impulse(total, player.getAbilities().flying),
            current.replacesNativeCurrent()));
    }

}
