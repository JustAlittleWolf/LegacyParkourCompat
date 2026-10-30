package me.wolfii.legacyparkourcompat.change.v1_15_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.jumpboost.JumpBoostEffectReceiverHook;
import net.minecraft.world.entity.player.Player;

import java.util.Map;
import java.util.OptionalInt;
import java.util.WeakHashMap;

/**
 * Mirrors 1.15.2 Mojmap MobEffectInstance.update/tick and LivingEntity.tickEffects hidden fallback
 * for movement reads only. Current packet interception uses 26.2 unobfuscated
 * ClientPacketListener.handleUpdateMobEffect/handleRemoveMobEffect; jump output is read at
 * LivingEntity.getJumpBoostPower.
 */
@MovementChange(emulates = ParkourVersion.V1_15_2)
public final class HiddenJumpBoostEffectReceiver implements JumpBoostEffectReceiverHook {
    private final Map<Player, State> effects = new WeakHashMap<>();

    public HiddenJumpBoostEffectReceiver() {
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
        } else {
            current.update(amplifier, duration);
        }
    }

    @Override
    public void receiveRemoval(Player player) {
        this.effects.remove(player);
    }

    @Override
    public void tick(Player player) {
        State state = this.effects.get(player);
        if (state != null && !state.nativeSession && state.effect != null) {
            state.effect.tick();
        }
    }

    @Override
    public OptionalInt amplifier(Player player) {
        State state = this.effects.get(player);
        Effect effect = state == null || state.nativeSession ? null : state.effect;
        return effect == null ? OptionalInt.empty() : OptionalInt.of(effect.amplifier);
    }

    private static final class State {
        private Effect effect;
        private boolean nativeSession;
    }

    private static final class Effect {
        private int amplifier;
        private int duration;
        private Effect hidden;

        private Effect(int amplifier, int duration) {
            this.amplifier = amplifier;
            this.duration = duration;
        }

        private Effect copy() {
            return new Effect(this.amplifier, this.duration);
        }

        private void update(int incomingAmplifier, int incomingDuration) {
            if (incomingAmplifier > this.amplifier) {
                if (incomingDuration < this.duration) {
                    Effect previousHidden = this.hidden;
                    this.hidden = this.copy();
                    this.hidden.hidden = previousHidden;
                }
                this.amplifier = incomingAmplifier;
                this.duration = incomingDuration;
            } else if (incomingDuration > this.duration) {
                if (incomingAmplifier == this.amplifier) {
                    this.duration = incomingDuration;
                } else if (this.hidden == null) {
                    this.hidden = new Effect(incomingAmplifier, incomingDuration);
                } else {
                    this.hidden.update(incomingAmplifier, incomingDuration);
                }
            }
        }

        private void tick() {
            if (this.duration > 0) {
                if (this.hidden != null) {
                    this.hidden.tickDownDuration();
                }
                this.duration--;
                if (this.duration == 0 && this.hidden != null) {
                    Effect restored = this.hidden;
                    this.amplifier = restored.amplifier;
                    this.duration = restored.duration;
                    this.hidden = restored.hidden;
                }
            }
        }

        private void tickDownDuration() {
            if (this.hidden != null) {
                this.hidden.tickDownDuration();
            }
            this.duration--;
        }
    }
}
