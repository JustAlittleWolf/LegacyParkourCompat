package me.wolfii.legacyparkourcompat.mechanic.collision;

import me.wolfii.legacyparkourcompat.mechanic.hook.shape.CollisionQueryShapeHook;
import net.minecraft.world.phys.shapes.BitSetDiscreteVoxelShape;
import net.minecraft.world.phys.shapes.CubeVoxelShape;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

/** Materializes a historical query encoding with the current vanilla shape classes. */
public final class CollisionQueryShapeMaterializer {
    private CollisionQueryShapeMaterializer() {
    }

    public static VoxelShape materialize(CollisionQueryShapeHook.Encoding encoding) {
        return switch (encoding.kind()) {
            case EMPTY -> Shapes.empty();
            case FULL_CUBE -> Shapes.block();
            case RAW -> RawArrayVoxelShape.oneCell(new net.minecraft.world.phys.AABB(
                encoding.x().min(), encoding.y().min(), encoding.z().min(),
                encoding.x().max(), encoding.y().max(), encoding.z().max()));
            case DISCRETE -> new CubeVoxelShape(BitSetDiscreteVoxelShape.withFilledBounds(
                size(encoding.x()), size(encoding.y()), size(encoding.z()),
                encoding.x().minCell(), encoding.y().minCell(), encoding.z().minCell(),
                encoding.x().maxCell(), encoding.y().maxCell(), encoding.z().maxCell()));
        };
    }

    private static int size(CollisionQueryShapeHook.Axis axis) {
        return 1 << axis.bits();
    }
}
