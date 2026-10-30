package me.wolfii.legacyparkourcompat.change.v1_15;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.jumpboost.JumpBoostEffectReceiverHook;
import net.minecraft.world.entity.player.Player;

import java.util.Map;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.WeakHashMap;

/**
 * Mirrors the pre-hidden-effect Jump Boost receiver: a lower amplifier is ignored, and
 * client expiry keeps the active entry until a matching REMOVE is received.
 * Historical evidence: 1.14–1.14.3 Yarn StatusEffectInstance.upgrade/update and
 * ClientPlayNetworkHandler.onEntityPotionEffect/onRemoveEntityEffect; 1.14.4 Mojmap MobEffectInstance.update/tick,
 * ClientPacketListener.handleUpdateMobEffect/handleRemoveMobEffect and LivingEntity.tickEffects;
 * 1.15.1 Mojmap MobEffectInstance.update/tick has the same receiver rules. Current interception
 * and movement read use 26.2 unobfuscated ClientPacketListener.handleUpdateMobEffect/
 * handleRemoveMobEffect and LivingEntity.tickEffects/getJumpBoostPower.
 */
@MovementChange(emulates = ParkourVersion.V1_15)
public final class LegacyJumpBoostEffectReceiver implements JumpBoostEffectReceiverHook {
    private final Map<Player, State> effects = new WeakHashMap<>();

    public LegacyJumpBoostEffectReceiver() {
    }

    @Override
    public void receiveUpdate(Player player, int amplifier, int duration) {
        State state = this.effects.computeIfAbsent(player, ignored -> new State());
        if (duration == -1) {
            // 1.19.4 Mojmap MobEffectInstance introduced INFINITE_DURATION (-1).
            // Keep that modern effect session on the native receiver until REMOVE.
            state.nativeSession = true;
            state.effect = null;
            return;
        }
        if (state.nativeSession) {
            return;
        }
        Effect current = state.effect;
        if (current == null) {
            state.effect = new Effect(amplifier, duration);
        } else if (amplifier > current.amplifier) {
            current.amplifier = amplifier;
            current.duration = duration;
        } else if (amplifier == current.amplifier && current.duration < duration) {
            current.duration = duration;
        }
    }

    @Override
    public void receiveRemoval(Player player) {
        this.effects.remove(player);
    }

    @Override
    public void tick(Player player) {
        State state = this.effects.get(player);
        if (state != null && !state.nativeSession && state.effect != null && state.effect.duration > 0) {
            state.effect.duration--;
        }
    }

    @Override
    public OptionalInt amplifier(Player player) {
        State state = this.effects.get(player);
        Effect effect = state == null || state.nativeSession ? null : state.effect;
        return effect == null ? OptionalInt.empty() : OptionalInt.of(effect.amplifier);
    }

    @Override
    public Optional<Boolean> hasJumpBoost(Player player) {
        State state = this.effects.get(player);
        return state == null || state.nativeSession ? Optional.empty() : Optional.of(state.effect != null);
    }

    private static final class State {
        private Effect effect;
        private boolean nativeSession;
    }

    private static final class Effect {
        private int amplifier;
        private int duration;

        private Effect(int amplifier, int duration) {
            this.amplifier = amplifier;
            this.duration = duration;
        }
    }
}
