package me.wolfii.legacyparkourcompat.change.v1_12;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.water.WaterCurrentHook;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import java.util.Optional;

/**
 * 1.12/1.12.1/1.12.2 Feather: World.applyLiquidDrag, LiquidBlock.getFlow,
 * PlayerEntity.hasLiquidCollision, Vec3d.normalize/length and MathHelper.sqrt(double).
 * Replaced in 1.13 Feather: Entity.m_69693160 (player averaging/depth weighting);
 * 26.2 unobfuscated: EntityFluidInteraction.Tracker.applyCurrentTo.
 * Normalize the sum even for players; float-rounded sqrt is historical behavior.
 * Supported ordinary nonfalling water flow is supplied by the shared scan.
 */
@MovementChange(emulates = ParkourVersion.V1_12)
public final class WaterCurrent implements WaterCurrentHook {
    public boolean replacesNativeCurrent() { return true; }
    public Vec3 accumulate(Vec3 total, Vec3 flow) { return total.add(flow); }
    public Vec3 impulse(Vec3 total, boolean flying) {
        if (length(total) > 0.0 && !flying) {
            Vec3 normalized = normalize(total);
            return new Vec3(normalized.x * 0.014, normalized.y * 0.014, normalized.z * 0.014);
        }
        return Vec3.ZERO;
    }

    /**
     * LiquidBlock.getFlow/getLiquidDepth, ordinary nonfalling branch only.
     * Falling centers need a historical wall-face provider and stay native.
     * The blocking-material provider is bounded to ordinary air and these
     * full solids (1.12-family Block bootstrap and Material.blocksMovement).
     */
    public Optional<Vec3> flow(Level level, BlockPos pos, BlockState state) {
        double x = 0.0, y = 0.0, z = 0.0;
        int depth = state.getValue(LiquidBlock.LEVEL);
        for (Direction direction : Direction.Plane.HORIZONTAL) {
            BlockPos adjacent = pos.relative(direction);
            BlockState neighbor = level.getBlockState(adjacent);
            if (!supported(neighbor)) return Optional.empty();
            int other = depth(neighbor);
            if (other < 0) {
                if (neighbor.isAir()) {
                    BlockState below = level.getBlockState(adjacent.below());
                    if (!supported(below)) return Optional.empty();
                    other = depth(below);
                    if (other >= 0) {
                        int difference = other - (depth - 8);
                        x += direction.getStepX() * difference;
                        y += direction.getStepY() * difference;
                        z += direction.getStepZ() * difference;
                    }
                }
            } else {
                int difference = other - depth;
                x += direction.getStepX() * difference;
                y += direction.getStepY() * difference;
                z += direction.getStepZ() * difference;
            }
        }
        return Optional.of(normalize(new Vec3(x, y, z)));
    }

    private static boolean supported(BlockState state) {
        if (state.getFluidState().is(FluidTags.WATER)) {
            return state.getBlock() instanceof LiquidBlock && state.getValue(LiquidBlock.LEVEL) < 8;
        }
        return state.isAir() || state.is(Blocks.STONE) || state.is(Blocks.COBBLESTONE)
            || state.is(Blocks.DIRT) || state.is(Blocks.BEDROCK);
    }

    private static int depth(BlockState state) {
        return state.getBlock() instanceof LiquidBlock && state.getFluidState().is(FluidTags.WATER)
            ? state.getValue(LiquidBlock.LEVEL) : -1;
    }

    private static double length(Vec3 vector) {
        return (float)Math.sqrt(vector.x * vector.x + vector.y * vector.y + vector.z * vector.z);
    }

    private static Vec3 normalize(Vec3 vector) {
        double length = length(vector);
        return length < 1.0E-4 ? Vec3.ZERO : new Vec3(vector.x / length, vector.y / length, vector.z / length);
    }
}
