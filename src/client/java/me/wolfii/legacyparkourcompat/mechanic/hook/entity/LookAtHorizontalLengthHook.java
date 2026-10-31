package me.wolfii.legacyparkourcompat.mechanic.hook.entity;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;

/** Supplies the horizontal length intermediate used by Entity.lookAt pitch calculation. */
@MechanicType("entity.look_at.horizontal_length")
public interface LookAtHorizontalLengthHook extends VersionedMechanic {
    double horizontalLength(double squaredHorizontalDistance);
}
