package me.wolfii.legacyparkourcompat.change.common;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WallSide;
import net.minecraft.world.phys.shapes.VoxelShape;

/** Shared 1.12-era horizontal wall boxes indexed NORTH/EAST/SOUTH/WEST. */
public final class WallCollisionGeometry {
    private static final VoxelShape[] SHAPES = {
        box(4, 4, 12, 12), box(4, 4, 12, 16),
        box(0, 4, 12, 12), box(0, 4, 12, 16),
        box(4, 0, 12, 12), box(5, 0, 11, 16),
        box(0, 0, 12, 12), box(0, 0, 12, 16),
        box(4, 4, 16, 12), box(4, 4, 16, 16),
        box(0, 5, 16, 11), box(0, 4, 16, 16),
        box(4, 0, 16, 12), box(4, 0, 16, 16),
        box(0, 0, 16, 12), box(0, 0, 16, 16)
    };

    private WallCollisionGeometry() {
    }

    public static VoxelShape shape(int mask) {
        return SHAPES[mask];
    }

    public static int mask(BlockState state) {
        int mask = 0;
        if (state.getValue(WallBlock.NORTH) != WallSide.NONE) mask |= 1 << 2;
        if (state.getValue(WallBlock.EAST) != WallSide.NONE) mask |= 1 << 3;
        if (state.getValue(WallBlock.SOUTH) != WallSide.NONE) mask |= 1;
        if (state.getValue(WallBlock.WEST) != WallSide.NONE) mask |= 1 << 1;
        return mask;
    }

    private static VoxelShape box(int minX, int minZ, int maxX, int maxZ) {
        return Block.box(minX, 0, minZ, maxX, 24, maxZ);
    }
}
