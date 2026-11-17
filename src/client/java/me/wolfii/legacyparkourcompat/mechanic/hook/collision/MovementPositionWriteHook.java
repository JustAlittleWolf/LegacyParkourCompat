package me.wolfii.legacyparkourcompat.mechanic.hook.collision;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.phys.Vec3;

/** Admission of the resolved movement's position/box and contact-segment write. */
@MechanicType("player.movement.position_write")
public interface MovementPositionWriteHook extends VersionedMechanic {
    default boolean alwaysEnterWriteBranch() {
        return false;
    }

    boolean shouldWrite(ParkourVersion selected, double movementLengthSquared);

    /**
     * Admission when both the requested movement and collision result are available.
     * Existing providers retain their resolved-length rule unless they override this form.
     */
    default boolean shouldWrite(ParkourVersion selected, Vec3 requestedMovement, Vec3 resolvedMovement) {
        return shouldWrite(selected, resolvedMovement.lengthSqr());
    }
}
