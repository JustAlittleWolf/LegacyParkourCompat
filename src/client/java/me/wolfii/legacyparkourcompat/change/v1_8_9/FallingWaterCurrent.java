package me.wolfii.legacyparkourcompat.change.v1_8_9;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.water.FallingWaterCurrentHook;
import me.wolfii.legacyparkourcompat.mechanic.water.LegacyWaterFlowMath;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

/**
 * 1.8 through 1.8.9 Ornithe Feather: World.applyLiquidDrag visits the integral
 * upper endpoint. LiquidBlock.getFlow maps falling depth to zero, samples the
 * lower west source through air, then tests the dry wooden fence above that
 * side. In these exact releases Block registers the six wood fences with
 * Material.WOOD and Block.isFaceSolid checks its solidity. Vec3d.normalize
 * supplies the two normalization steps around the
 * (0, -6, 0) addition. Stable 1.9 World.applyLiquidDrag excludes this cell.
 * In 26.2 unobfuscated, EntityFluidInteraction.update has its own box and
 * contextual tracker. The legacy water scan supplies this projected current.
 */
@MovementChange(emulates = ParkourVersion.V1_8_9)
public final class FallingWaterCurrent implements FallingWaterCurrentHook {
    private static final Set<BlockState> FENCE_CUES = Set.of(
        Blocks.OAK_FENCE.defaultBlockState(), Blocks.SPRUCE_FENCE.defaultBlockState(),
        Blocks.BIRCH_FENCE.defaultBlockState(), Blocks.JUNGLE_FENCE.defaultBlockState(),
        Blocks.ACACIA_FENCE.defaultBlockState(), Blocks.DARK_OAK_FENCE.defaultBlockState()
    );

    @Override
    public Optional<Sample> sample(Level level, BlockPos pos, BlockState state,
                                   Function<BlockState, Optional<Vec3>> horizontalFlow) {
        if (!state.is(Blocks.WATER) || state.getValue(LiquidBlock.LEVEL) != 8
            || pos.getY() < 1 || pos.getY() > 254) {
            return Optional.empty();
        }

        BlockPos companion = pos.west().below();
        for (Direction side : Direction.Plane.HORIZONTAL) {
            BlockPos adjacent = pos.relative(side);
            BlockPos companionSide = companion.relative(side);
            if (!level.hasChunkAt(adjacent) || !level.hasChunkAt(companionSide)
                || !level.getBlockState(adjacent).isAir()) {
                return Optional.empty();
            }
            BlockState above = level.getBlockState(adjacent.above());
            BlockState below = level.getBlockState(adjacent.below());
            if (side == Direction.WEST) {
                if (!FENCE_CUES.contains(above) || below != Blocks.WATER.defaultBlockState()) {
                    return Optional.empty();
                }
            } else if (!above.isAir() || !below.isAir()) {
                return Optional.empty();
            }
        }

        // The old falling bit changes the wall response while center depth stays zero.
        return horizontalFlow.apply(state.setValue(LiquidBlock.LEVEL, 0))
            .map(flow -> new Sample(LegacyWaterFlowMath.normalize(flow.add(0.0, -6.0, 0.0)), companion));
    }
}
