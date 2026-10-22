package me.wolfii.legacyparkourcompat.mechanic.hook.player;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.client.player.LocalPlayer;

/** Tracks the local Jump Boost fallback transition for versions without hidden effects. */
@MechanicType("player.jump_boost_fallback_state")
public interface JumpBoostFallbackStateHook extends VersionedMechanic {
    void beforeEffectTick(LocalPlayer player, int amplifier);

    void afterEffectTick(LocalPlayer player, int amplifier);

    void effectUpdated(
        LocalPlayer player,
        int previousAmplifier,
        int previousDuration,
        int currentAmplifier,
        int currentDuration,
        boolean accepted
    );
}
