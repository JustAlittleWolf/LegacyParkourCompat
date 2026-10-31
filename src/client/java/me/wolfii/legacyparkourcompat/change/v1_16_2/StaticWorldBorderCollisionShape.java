package me.wolfii.legacyparkourcompat.change.v1_16_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.worldborder.StaticWorldBorderCollisionShapeHook;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Optional;

/**
 * Restores the 1.16 static extent's asymmetric endpoint clamps. 1.16.5 Mojmap
 * WorldBorder.StaticBorderExtent.updateBox floors the resulting minima, ceils
 * maxima, and Shapes.box sorts reversed coordinates through AABB construction;
 * 1.17.1 Mojmap WorldBorder.StaticBorderExtent.updateBox clamps each endpoint
 * independently, removing the accepted reversed-inner-box case.
 */
@MovementChange(emulates = ParkourVersion.V1_16_2)
public final class StaticWorldBorderCollisionShape implements StaticWorldBorderCollisionShapeHook {
    @Override
    public Optional<VoxelShape> collisionShape(double centerX, double centerZ, double size, int absoluteMaxSize) {
        double minX = Math.max(centerX - size / 2.0, -absoluteMaxSize);
        double minZ = Math.max(centerZ - size / 2.0, -absoluteMaxSize);
        double maxX = Math.min(centerX + size / 2.0, absoluteMaxSize);
        double maxZ = Math.min(centerZ + size / 2.0, absoluteMaxSize);

        double floorMinX = Math.floor(minX);
        double floorMinZ = Math.floor(minZ);
        double ceilMaxX = Math.ceil(maxX);
        double ceilMaxZ = Math.ceil(maxZ);
        AABB sortedInterior = new AABB(
            Math.min(floorMinX, ceilMaxX), Double.NEGATIVE_INFINITY, Math.min(floorMinZ, ceilMaxZ),
            Math.max(floorMinX, ceilMaxX), Double.POSITIVE_INFINITY, Math.max(floorMinZ, ceilMaxZ)
        );
        return Optional.of(Shapes.join(Shapes.INFINITY, Shapes.create(sortedInterior), BooleanOp.ONLY_FIRST));
    }
}
