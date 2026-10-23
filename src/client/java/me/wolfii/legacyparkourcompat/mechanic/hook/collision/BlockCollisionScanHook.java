package me.wolfii.legacyparkourcompat.mechanic.hook.collision;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.phys.AABB;

import java.util.Optional;

@MechanicType("collision.block-scan")
public interface BlockCollisionScanHook extends VersionedMechanic {
    /** Empty keeps the native cursor and pruning unchanged for this profile. */
    Optional<BlockCollisionScan> scan(AABB sweptBox);
}
