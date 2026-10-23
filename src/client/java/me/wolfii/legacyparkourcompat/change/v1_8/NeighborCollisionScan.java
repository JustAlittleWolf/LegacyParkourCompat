package me.wolfii.legacyparkourcompat.change.v1_8;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.collision.BlockCollisionScan;
import me.wolfii.legacyparkourcompat.mechanic.hook.collision.BlockCollisionScanHook;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.AABB;

/**
 * 1.8.9 and 1.9, ornithe-feather: net.minecraft.world.World.getCollisions(Entity, Box).
 * The two source methods define the bounds and pruning below.
 */
@MovementChange(emulates = ParkourVersion.V1_8)
public final class NeighborCollisionScan implements BlockCollisionScanHook {
    @Override
    public BlockCollisionScan scan(AABB box) {
        // 1.8.9 World.java getCollisions(Entity, Box) scans X/Z to floor(max + 1) exclusive,
        // and Y from floor(min) - 1 to floor(max + 1) exclusive; it prunes no cells.
        return new BlockCollisionScan(
            Mth.floor(box.minX), Mth.floor(box.maxX + 1.0),
            Mth.floor(box.minY) - 1, Mth.floor(box.maxY + 1.0),
            Mth.floor(box.minZ), Mth.floor(box.maxZ + 1.0),
            false, false
        );
    }
}
