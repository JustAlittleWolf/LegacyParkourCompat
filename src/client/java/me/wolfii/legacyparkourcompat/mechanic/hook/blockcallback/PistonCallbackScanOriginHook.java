package me.wolfii.legacyparkourcompat.mechanic.hook.blockcallback;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

/** Selects the explicit piston callback scan's starting position. */
@MechanicType("block_callback.piston_scan_origin")
public interface PistonCallbackScanOriginHook extends VersionedMechanic {
    Vec3 scanOrigin(Entity entity, Vec3 currentOrigin);
}
