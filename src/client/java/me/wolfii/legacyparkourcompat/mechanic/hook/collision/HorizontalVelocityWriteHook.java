package me.wolfii.legacyparkourcompat.mechanic.hook.collision;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.phys.Vec3;
import java.util.Optional;

/** Combines the two independently computed horizontal velocity-clear decisions. */
@MechanicType("collision.horizontal_velocity_write")
public interface HorizontalVelocityWriteHook extends VersionedMechanic {
    Optional<Vec3> clear(Vec3 capturedVelocity, boolean clearX, boolean clearZ);
}
