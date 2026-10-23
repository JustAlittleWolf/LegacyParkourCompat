package me.wolfii.legacyparkourcompat.change.v1_8;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.collision.BlockCollisionScan;
import me.wolfii.legacyparkourcompat.mechanic.hook.collision.BlockCollisionScanHook;
import net.minecraft.world.phys.AABB;

import java.util.Optional;

/**
 * The accepted scan evidence begins at 1.8.9. Keep 1.8 through 1.8.8 on
 * Minecraft's native block-collision cursor until those releases are reviewed.
 */
@MovementChange(emulates = ParkourVersion.V1_8)
public final class NeighborCollisionScanNativeFence implements BlockCollisionScanHook {
    public NeighborCollisionScanNativeFence() {
    }

    @Override
    public Optional<BlockCollisionScan> scan(AABB box) {
        return Optional.empty();
    }
}
