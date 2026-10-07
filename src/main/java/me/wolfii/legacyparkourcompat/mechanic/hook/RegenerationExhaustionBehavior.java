package me.wolfii.legacyparkourcompat.mechanic.hook;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;

/** Exhaustion charged for slow natural regeneration. */
@FunctionalInterface
@MechanicType("player.regeneration.exhaustion")
public interface RegenerationExhaustionBehavior extends VersionedMechanic {
    float slowRegenerationExhaustion(float vanilla);
}
