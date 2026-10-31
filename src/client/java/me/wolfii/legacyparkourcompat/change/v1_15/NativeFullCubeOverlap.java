package me.wolfii.legacyparkourcompat.change.v1_15;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.collision.FullCubeOverlapHook;
import net.minecraft.world.phys.AABB;

/**
 * Retains the strict full-cube AABB predicate for selections before 1.15.2,
 * including 1.15 and 1.15.1. Mojmap 1.16 and 1.16.5
 * CollisionSpliterator.collisionCheck use AABB.intersects for the
 * Shapes.block() branch; 1.15.2 CollisionGetter.getBlockCollisions instead
 * admits full cubes through its query voxel shape.
 */
@MovementChange(emulates = ParkourVersion.V1_15)
public final class NativeFullCubeOverlap implements FullCubeOverlapHook {
    @Override
    public boolean overlaps(AABB queryBox, int blockX, int blockY, int blockZ) {
        return queryBox.intersects(blockX, blockY, blockZ,
            blockX + 1.0, blockY + 1.0, blockZ + 1.0);
    }
}
