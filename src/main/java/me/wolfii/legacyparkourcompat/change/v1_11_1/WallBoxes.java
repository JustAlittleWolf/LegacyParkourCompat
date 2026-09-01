package me.wolfii.legacyparkourcompat.change.v1_11_1;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WallSide;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

/**
 * 1.8–1.12 wall AABBs from {@code WallBlock} (1.12.2 {@code field_12831} /
 * {@code field_12830}). Connections come from the current block state; we do
 * not recompute fence-like connections.
 */
final class WallBoxes {
    private static final VoxelShape[] OUTLINE = {
        Shapes.box(0.25, 0.0, 0.25, 0.75, 1.0, 0.75),
        Shapes.box(0.25, 0.0, 0.25, 0.75, 1.0, 1.0),
        Shapes.box(0.0, 0.0, 0.25, 0.75, 1.0, 0.75),
        Shapes.box(0.0, 0.0, 0.25, 0.75, 1.0, 1.0),
        Shapes.box(0.25, 0.0, 0.0, 0.75, 1.0, 0.75),
        Shapes.box(0.3125, 0.0, 0.0, 0.6875, 0.875, 1.0),
        Shapes.box(0.0, 0.0, 0.0, 0.75, 1.0, 0.75),
        Shapes.box(0.0, 0.0, 0.0, 0.75, 1.0, 1.0),
        Shapes.box(0.25, 0.0, 0.25, 1.0, 1.0, 0.75),
        Shapes.box(0.25, 0.0, 0.25, 1.0, 1.0, 1.0),
        Shapes.box(0.0, 0.0, 0.3125, 1.0, 0.875, 0.6875),
        Shapes.box(0.0, 0.0, 0.25, 1.0, 1.0, 1.0),
        Shapes.box(0.25, 0.0, 0.0, 1.0, 1.0, 0.75),
        Shapes.box(0.25, 0.0, 0.0, 1.0, 1.0, 1.0),
        Shapes.box(0.0, 0.0, 0.0, 1.0, 1.0, 0.75),
        Shapes.box(0.0, 0.0, 0.0, 1.0, 1.0, 1.0)
    };
    private static final VoxelShape[] TALL = {
        Shapes.box(0.25, 0.0, 0.25, 0.75, 1.5, 0.75),
        Shapes.box(0.25, 0.0, 0.25, 0.75, 1.5, 1.0),
        Shapes.box(0.0, 0.0, 0.25, 0.75, 1.5, 0.75),
        Shapes.box(0.0, 0.0, 0.25, 0.75, 1.5, 1.0),
        Shapes.box(0.25, 0.0, 0.0, 0.75, 1.5, 0.75),
        Shapes.box(0.3125, 0.0, 0.0, 0.6875, 1.5, 1.0),
        Shapes.box(0.0, 0.0, 0.0, 0.75, 1.5, 0.75),
        Shapes.box(0.0, 0.0, 0.0, 0.75, 1.5, 1.0),
        Shapes.box(0.25, 0.0, 0.25, 1.0, 1.5, 0.75),
        Shapes.box(0.25, 0.0, 0.25, 1.0, 1.5, 1.0),
        Shapes.box(0.0, 0.0, 0.3125, 1.0, 1.5, 0.6875),
        Shapes.box(0.0, 0.0, 0.25, 1.0, 1.5, 1.0),
        Shapes.box(0.25, 0.0, 0.0, 1.0, 1.5, 0.75),
        Shapes.box(0.25, 0.0, 0.0, 1.0, 1.5, 1.0),
        Shapes.box(0.0, 0.0, 0.0, 1.0, 1.5, 0.75),
        Shapes.box(0.0, 0.0, 0.0, 1.0, 1.5, 1.0)
    };

    private WallBoxes() {
    }

    static VoxelShape outline(BlockState state) {
        return OUTLINE[index(state)];
    }

    static VoxelShape tallCollision(BlockState state) {
        return TALL[index(state)];
    }

    private static int index(BlockState state) {
        int i = 0;
        if (connected(state, Direction.NORTH)) {
            i |= 1 << Direction.NORTH.get2DDataValue();
        }
        if (connected(state, Direction.EAST)) {
            i |= 1 << Direction.EAST.get2DDataValue();
        }
        if (connected(state, Direction.SOUTH)) {
            i |= 1 << Direction.SOUTH.get2DDataValue();
        }
        if (connected(state, Direction.WEST)) {
            i |= 1 << Direction.WEST.get2DDataValue();
        }
        return i;
    }

    private static boolean connected(BlockState state, Direction direction) {
        return state.getValue(WallBlock.PROPERTY_BY_DIRECTION.get(direction)) != WallSide.NONE;
    }
}
