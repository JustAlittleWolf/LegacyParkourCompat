package me.wolfii.legacyparkourcompat.mechanic.collision;

import me.wolfii.legacyparkourcompat.mixin.collision.ArrayVoxelShapeInvoker;
import me.wolfii.legacyparkourcompat.mixin.collision.VoxelShapeAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.DiscreteVoxelShape;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

/** Builds the one-cell array encoding while retaining the supplied raw endpoints. */
public final class RawArrayVoxelShape {
    private RawArrayVoxelShape() {
    }

    public static VoxelShape oneCell(AABB bounds) {
        DiscreteVoxelShape fullCell = ((VoxelShapeAccessor)(Object)Shapes.block()).legacyparkourcompat$discreteShape();
        return (VoxelShape)(Object)ArrayVoxelShapeInvoker.legacyparkourcompat$create(
            fullCell,
            new double[]{bounds.minX, bounds.maxX},
            new double[]{bounds.minY, bounds.maxY},
            new double[]{bounds.minZ, bounds.maxZ}
        );
    }
}
