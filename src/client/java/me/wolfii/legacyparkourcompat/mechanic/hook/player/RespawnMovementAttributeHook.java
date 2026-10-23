package me.wolfii.legacyparkourcompat.mechanic.hook.player;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.client.player.LocalPlayer;

/** Controls the movement-speed attribute response after local-player respawn. */
@MechanicType("player.respawn_movement_attributes")
public interface RespawnMovementAttributeHook extends VersionedMechanic {
    void afterRespawn(LocalPlayer replacementPlayer);
}
