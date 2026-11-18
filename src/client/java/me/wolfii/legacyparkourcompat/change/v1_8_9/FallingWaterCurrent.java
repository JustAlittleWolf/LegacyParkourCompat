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
import java.util.function.Function;

/**
 * 1.8 through 1.8.9 Ornithe Feather: World.applyLiquidDrag visits the integral
 * upper endpoint. LiquidBlock.getFlow maps falling depth to zero, samples the
 * lower west source through air, then applies the dry oak-fence face cue above
 * that side. Vec3d.normalize supplies the two normalization steps around the
 * (0, -6, 0) addition. Stable 1.9 World.applyLiquidDrag excludes this cell.
 * In 26.2 unobfuscated, EntityFluidInteraction.update has its own box and
 * contextual tracker. The legacy water scan supplies this projected current.
 */
@MovementChange(emulates = ParkourVersion.V1_8_9)
public final class FallingWaterCurrent implements FallingWaterCurrentHook {
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
                if (above != Blocks.OAK_FENCE.defaultBlockState() || below != Blocks.WATER.defaultBlockState()) {
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
