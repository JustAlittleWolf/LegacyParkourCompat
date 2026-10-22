package me.wolfii.legacyparkourcompat.change.v1_15;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.player.JumpBoostFallbackStateHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.player.JumpBoostResponseHook;
import net.minecraft.client.player.LocalPlayer;

import java.util.Map;
import java.util.WeakHashMap;

/**
 * 1.15 and 1.15.1 Mojmap net/minecraft/world/effect/MobEffectInstance.java,
 * update and tick, have no hidden-effect fallback. 1.15.2 adds that chain and
 * restores it from tick; net/minecraft/world/entity/LivingEntity.java,
 * tickEffects and jumpFromGround, run the lifecycle on the client and read the
 * restored active amplifier. 1.15.2 net/minecraft/client/multiplayer/
 * ClientPacketListener.java, handleUpdateMobEffect, feeds updates through
 * addEffect. Suppression is local-player jump response only, and an accepted
 * update clears it only when the primary active amplifier or duration changes.
 */
@MovementChange(emulates = ParkourVersion.V1_15)
public final class JumpBoostHiddenFallback implements JumpBoostFallbackStateHook, JumpBoostResponseHook {
    private static final int NO_EFFECT = Integer.MIN_VALUE;

    private final Map<LocalPlayer, State> states = new WeakHashMap<>();

    @Override
    public void beforeEffectTick(LocalPlayer player, int amplifier) {
        State state = this.states.computeIfAbsent(player, ignored -> new State());
        state.amplifierBeforeTick = amplifier;
        state.revisionBeforeTick = state.revision;
        state.hasPendingTick = true;
    }

    @Override
    public void afterEffectTick(LocalPlayer player, int amplifier) {
        State state = this.states.get(player);
        if (state == null || !state.hasPendingTick) {
            return;
        }

        int before = state.amplifierBeforeTick;
        state.hasPendingTick = false;
        if (state.revision != state.revisionBeforeTick) {
            return;
        }

        if (before != NO_EFFECT && amplifier != NO_EFFECT && amplifier < before) {
            state.restoredFallback = true;
        } else if (before == NO_EFFECT || amplifier == NO_EFFECT || amplifier > before) {
            state.restoredFallback = false;
        }
    }

    @Override
    public void effectUpdated(
        LocalPlayer player,
        int previousAmplifier,
        int previousDuration,
        int currentAmplifier,
        int currentDuration,
        boolean accepted
    ) {
        if (!accepted || currentAmplifier == NO_EFFECT) {
            return;
        }

        boolean activeEffectChanged = previousAmplifier == NO_EFFECT
            || currentAmplifier > previousAmplifier
            || currentAmplifier == previousAmplifier && currentDuration > previousDuration;
        if (activeEffectChanged) {
            State state = this.states.computeIfAbsent(player, ignored -> new State());
            state.restoredFallback = false;
            state.revision++;
        }
    }

    @Override
    public boolean suppressJumpBoost(LocalPlayer player) {
        State state = this.states.get(player);
        return state != null && state.restoredFallback;
    }

    private static final class State {
        private int amplifierBeforeTick = NO_EFFECT;
        private int revisionBeforeTick;
        private int revision;
        private boolean hasPendingTick;
        private boolean restoredFallback;
    }
}
