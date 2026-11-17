package me.wolfii.legacyparkourcompat.change.v1_12;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.block.FenceCollisionStateHook;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.CrossCollisionBlock;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;
import java.util.Optional;

/**
 * Applies the accepted 1.12 directional fence-neighbor cases during collision queries.
 *
 * <p>In Ornithe Feather 1.11.2, {@code FenceBlock.shouldConnectTo(WorldView, BlockPos)}
 * accepted any fence gate. Ornithe Feather 1.12 instead queries the opposite neighbor
 * face in {@code FenceBlock.shouldConnectTo(WorldView, BlockPos, Direction)}. Its
 * {@code FenceGateBlock.getFaceShape(WorldView, BlockState, BlockPos, Direction)}
 * returns {@code MIDDLE_POLE} only when the gate facing axis matches the clockwise-Y
 * axis of that queried face. The 1.12 {@code StairsBlock.getFaceShape(...)} also makes
 * the straight-stair face result directional. Only these source-supported cases are
 * projected here; other neighbors retain the native query bits.
 */
@MovementChange(emulates = ParkourVersion.V1_12)
public final class FenceCollisionState implements FenceCollisionStateHook {
    private static final List<Direction> HORIZONTAL_DIRECTIONS = List.of(
        Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST
    );

    public FenceCollisionState() {
    }

    @Override
    public Optional<BlockState> collisionState(BlockState state, BlockGetter level, BlockPos pos) {
        if (!(state.getBlock() instanceof FenceBlock)) {
            return Optional.empty();
        }

        BlockState result = state;
        boolean acceptedCase = false;

        for (Direction direction : HORIZONTAL_DIRECTIONS) {
            BlockPos neighborPos = pos.relative(direction);
            BlockState neighbor = level.getBlockState(neighborPos);
            boolean connects;

            if (neighbor.getBlock() instanceof FenceGateBlock) {
                Direction queriedFace = direction.getOpposite();
                Direction gateFacing = neighbor.getValue(HorizontalDirectionalBlock.FACING);
                connects = gateFacing.getAxis() == queriedFace.getClockWise().getAxis();
                acceptedCase = true;
            } else if (neighbor.getBlock() instanceof StairBlock) {
                Direction queriedFace = direction.getOpposite();
                Direction stairFacing = neighbor.getValue(StairBlock.FACING);
                if (!hasStraightResolvedShape(neighbor, level, neighborPos)) {
                    // Inner and outer stair face cases are outside this accepted bounded case.
                    return Optional.empty();
                }
                connects = stairFacing == queriedFace;
                acceptedCase = true;
            } else {
                continue;
            }

            result = setConnection(result, direction, connects);
        }

        if (!acceptedCase || result.equals(state)) {
            return Optional.empty();
        }
        return Optional.of(result);
    }

    /**
     * Mirrors the 1.12 {@code StairsBlock.getStairShape(BlockState, WorldView, BlockPos)}
     * inputs needed to establish the accepted straight-face case. The source resolves
     * the stair's virtual shape from the front and back neighbors before classifying a
     * face; reading the stored SHAPE property alone would miss that query-time gate.
     */
    private static boolean hasStraightResolvedShape(BlockState state, BlockGetter level, BlockPos pos) {
        Direction facing = state.getValue(StairBlock.FACING);
        BlockState front = level.getBlockState(pos.relative(facing));
        if (isSameHalfStair(state, front)) {
            Direction frontFacing = front.getValue(StairBlock.FACING);
            if (frontFacing.getAxis() != facing.getAxis()
                && hasNoSameFacingSameHalfStair(state, level, pos, frontFacing.getOpposite())) {
                return false;
            }
        }

        BlockState back = level.getBlockState(pos.relative(facing.getOpposite()));
        if (isSameHalfStair(state, back)) {
            Direction backFacing = back.getValue(StairBlock.FACING);
            if (backFacing.getAxis() != facing.getAxis()
                && hasNoSameFacingSameHalfStair(state, level, pos, backFacing)) {
                return false;
            }
        }
        return true;
    }

    private static boolean isSameHalfStair(BlockState state, BlockState neighbor) {
        return neighbor.getBlock() instanceof StairBlock
            && state.getValue(StairBlock.HALF).equals(neighbor.getValue(StairBlock.HALF));
    }

    private static boolean hasNoSameFacingSameHalfStair(
        BlockState state, BlockGetter level, BlockPos pos, Direction direction
    ) {
        BlockState neighbor = level.getBlockState(pos.relative(direction));
        return !(neighbor.getBlock() instanceof StairBlock)
            || neighbor.getValue(StairBlock.FACING) != state.getValue(StairBlock.FACING)
            || !neighbor.getValue(StairBlock.HALF).equals(state.getValue(StairBlock.HALF));
    }

    private static BlockState setConnection(BlockState state, Direction direction, boolean connects) {
        return switch (direction) {
            case NORTH -> state.setValue(CrossCollisionBlock.NORTH, connects);
            case EAST -> state.setValue(CrossCollisionBlock.EAST, connects);
            case SOUTH -> state.setValue(CrossCollisionBlock.SOUTH, connects);
            case WEST -> state.setValue(CrossCollisionBlock.WEST, connects);
            default -> throw new IllegalArgumentException("Fence connection direction must be horizontal: " + direction);
        };
    }
}
