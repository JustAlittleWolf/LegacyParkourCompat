package me.wolfii.legacyparkourcompat.mechanic.hook.collision;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;

/** Admission of the resolved movement's position/box and contact-segment write. */
@MechanicType("player.movement.position_write")
public interface MovementPositionWriteHook extends VersionedMechanic {
    boolean shouldWrite(ParkourVersion selected, double movementLengthSquared);
}
