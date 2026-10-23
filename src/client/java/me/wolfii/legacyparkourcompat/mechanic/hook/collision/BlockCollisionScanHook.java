package me.wolfii.legacyparkourcompat.mechanic.hook.collision;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.phys.AABB;

@MechanicType("collision.block-scan")
public interface BlockCollisionScanHook extends VersionedMechanic {
    BlockCollisionScan scan(AABB sweptBox);
}
