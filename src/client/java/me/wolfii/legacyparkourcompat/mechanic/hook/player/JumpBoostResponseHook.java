package me.wolfii.legacyparkourcompat.mechanic.hook.player;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.client.player.LocalPlayer;

/** Resolves Jump Boost presence for the local player's ground-jump response. */
@MechanicType("player.jump_boost_response")
public interface JumpBoostResponseHook extends VersionedMechanic {
    boolean suppressJumpBoost(LocalPlayer player);
}
