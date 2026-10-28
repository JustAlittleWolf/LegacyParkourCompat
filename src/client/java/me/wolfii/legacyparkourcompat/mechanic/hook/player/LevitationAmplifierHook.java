package me.wolfii.legacyparkourcompat.mechanic.hook.player;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;

/** Selects the received effect amplifier used by the player's levitation movement response. */
@MechanicType("player.levitation.amplifier")
public interface LevitationAmplifierHook extends VersionedMechanic {
    /** Returns the amplifier movement should use; the caller retains vanilla when unresolved. */
    int movementAmplifier(int receivedAmplifier);
}
