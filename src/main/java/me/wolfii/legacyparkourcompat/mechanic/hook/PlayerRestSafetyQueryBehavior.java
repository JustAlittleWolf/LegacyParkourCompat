package me.wolfii.legacyparkourcompat.mechanic.hook;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;

/** Historical control flow for whether the player-rest safety query runs. */
@FunctionalInterface
@MechanicType("player.rest-safety-query")
public interface PlayerRestSafetyQueryBehavior extends VersionedMechanic {
    boolean skipSafetyQuery(boolean vanilla);
}
