package me.wolfii.legacyparkourcompat.mechanic.hook.player;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;

/** Resolves the shared movement flags copied between local players on respawn. */
@MechanicType("player.respawn_movement_flags")
public interface RespawnMovementFlagsHook extends VersionedMechanic {
    byte restore(byte oldSharedFlags, byte newSharedFlags);
}
