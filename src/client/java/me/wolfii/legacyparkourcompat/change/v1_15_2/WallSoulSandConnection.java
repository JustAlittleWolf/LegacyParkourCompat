package me.wolfii.legacyparkourcompat.change.v1_15_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.wall.WallSoulSandConnectionHook;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.SoulSandBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WallSide;

/**
 * 1.15.2 Mojmap: WallBlock.connectsTo reads Block.isFaceSturdy, which tests the
 * 14/16 collision shape from SoulSandBlock.getCollisionShape. WallBlock.getStateForPlacement
 * and updateShape raise the post unless the wall is straight with air above.
 * 1.16 Mojmap: SoulSandBlock.getBlockSupportShape supplies full side support.
 * WallBlock.connectsTo adds the arm and WallBlock.getCollisionShape uses the resulting state.
 */
@MovementChange(emulates = ParkourVersion.V1_15_2)
public final class WallSoulSandConnection implements WallSoulSandConnectionHook {
    public WallSoulSandConnection() {
    }

    @Override
    public BlockState collisionState(BlockState state, BlockGetter level, BlockPos pos, ParkourVersion selected) {
        if (selected != ParkourVersion.V1_15_2) {
            return state;
        }

        BlockState shapeState = state;
        for (Direction direction : Direction.Plane.HORIZONTAL) {
            var side = WallBlock.PROPERTY_BY_DIRECTION.get(direction);
            if (state.getValue(side) != WallSide.NONE
                && level.getBlockState(pos.relative(direction)).getBlock() instanceof SoulSandBlock) {
                shapeState = shapeState.setValue(side, WallSide.NONE);
            }
        }
        if (shapeState == state) {
            return state;
        }

        boolean north = shapeState.getValue(WallBlock.NORTH) != WallSide.NONE;
        boolean east = shapeState.getValue(WallBlock.EAST) != WallSide.NONE;
        boolean south = shapeState.getValue(WallBlock.SOUTH) != WallSide.NONE;
        boolean west = shapeState.getValue(WallBlock.WEST) != WallSide.NONE;
        boolean straight = north && south && !east && !west || east && west && !north && !south;
        return shapeState.setValue(WallBlock.UP, !straight || !level.getBlockState(pos.above()).isAir());
    }
}
