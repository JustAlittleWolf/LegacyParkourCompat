package me.wolfii.legacyparkourcompat.mechanic.hook.state;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.player.Player;

/** Resolves the current eye height for player movement queries. */
@MechanicType("player.movement_eye_height")
public interface PlayerMovementEyeHeightHook extends VersionedMechanic {
    float eyeHeight(Player player, float vanilla);
}
