package me.wolfii.legacyparkourcompat.mechanic.hook;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;

/** Hurt check that gates fast natural regeneration. */
@FunctionalInterface
@MechanicType("player.regeneration.fast")
public interface FastRegenerationBehavior extends VersionedMechanic {
    boolean isHurtForFastRegeneration(boolean vanilla);
}
