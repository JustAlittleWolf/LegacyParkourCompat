package me.wolfii.legacyparkourcompat.change.v1_9;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.collision.BlockCollisionScan;
import me.wolfii.legacyparkourcompat.mechanic.hook.collision.BlockCollisionScanHook;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.AABB;

/**
 * 1.8.9 and 1.9, ornithe-feather: net.minecraft.world.World.getCollisions(Entity, Box).
 * The two source methods define the bounds and pruning below; 1.9.1 through 1.9.4 retain the 1.9 scan.
 */
@MovementChange(emulates = ParkourVersion.V1_9)
public final class NeighborCollisionScan implements BlockCollisionScanHook {
    @Override
    public BlockCollisionScan scan(AABB box) {
        // 1.9 World.java getCollisions(Entity, Box) expands every bound by one cell,
        // prunes horizontal corner columns, then prunes vertical end cells on horizontal edge columns.
        return new BlockCollisionScan(
            Mth.floor(box.minX) - 1, Mth.ceil(box.maxX) + 1,
            Mth.floor(box.minY) - 1, Mth.ceil(box.maxY) + 1,
            Mth.floor(box.minZ) - 1, Mth.ceil(box.maxZ) + 1,
            true, true
        );
    }
}
