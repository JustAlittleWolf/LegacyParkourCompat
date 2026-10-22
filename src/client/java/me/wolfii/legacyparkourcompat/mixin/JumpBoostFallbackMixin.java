package me.wolfii.legacyparkourcompat.mixin;

import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.hook.player.JumpBoostFallbackStateHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.player.JumpBoostResponseHook;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
abstract class JumpBoostFallbackMixin {
    @Unique
    private int legacy$jumpBoostPreviousAmplifier = Integer.MIN_VALUE;

    @Unique
    private int legacy$jumpBoostPreviousDuration = Integer.MIN_VALUE;

    @Inject(method = "addEffect(Lnet/minecraft/world/effect/MobEffectInstance;Lnet/minecraft/world/entity/Entity;)Z", at = @At("HEAD"))
    private void legacy$captureJumpBoostBeforeUpdate(
        MobEffectInstance effect,
        Entity source,
        CallbackInfoReturnable<Boolean> callback
    ) {
        if ((Object)this instanceof LocalPlayer player && MovementRuntime.appliesTo(player)
            && effect.is(MobEffects.JUMP_BOOST)) {
            MobEffectInstance current = player.getEffect(MobEffects.JUMP_BOOST);
            this.legacy$jumpBoostPreviousAmplifier = current == null ? Integer.MIN_VALUE : current.getAmplifier();
            this.legacy$jumpBoostPreviousDuration = current == null ? Integer.MIN_VALUE : current.getDuration();
        }
    }

    @Inject(method = "addEffect(Lnet/minecraft/world/effect/MobEffectInstance;Lnet/minecraft/world/entity/Entity;)Z", at = @At("RETURN"))
    private void legacy$recordJumpBoostUpdate(
        MobEffectInstance effect,
        Entity source,
        CallbackInfoReturnable<Boolean> callback
    ) {
        if ((Object)this instanceof LocalPlayer player && MovementRuntime.appliesTo(player)
            && effect.is(MobEffects.JUMP_BOOST)) {
            MobEffectInstance current = player.getEffect(MobEffects.JUMP_BOOST);
            int currentAmplifier = current == null ? Integer.MIN_VALUE : current.getAmplifier();
            int currentDuration = current == null ? Integer.MIN_VALUE : current.getDuration();
            MovementRuntime.find(JumpBoostFallbackStateHook.class, player).ifPresent(hook ->
                hook.effectUpdated(
                    player,
                    this.legacy$jumpBoostPreviousAmplifier,
                    this.legacy$jumpBoostPreviousDuration,
                    currentAmplifier,
                    currentDuration,
                    callback.getReturnValueZ()
                ));
            this.legacy$jumpBoostPreviousAmplifier = Integer.MIN_VALUE;
            this.legacy$jumpBoostPreviousDuration = Integer.MIN_VALUE;
        }
    }

    @Inject(method = "tickEffects", at = @At("HEAD"))
    private void legacy$captureJumpBoostBeforeTick(CallbackInfo callback) {
        if ((Object)this instanceof LocalPlayer player && MovementRuntime.appliesTo(player)) {
            int amplifier = this.legacy$jumpBoostAmplifier(player);
            MovementRuntime.find(JumpBoostFallbackStateHook.class, player)
                .ifPresent(hook -> hook.beforeEffectTick(player, amplifier));
        }
    }

    @Inject(method = "tickEffects", at = @At("TAIL"))
    private void legacy$captureJumpBoostAfterTick(CallbackInfo callback) {
        if ((Object)this instanceof LocalPlayer player && MovementRuntime.appliesTo(player)) {
            int amplifier = this.legacy$jumpBoostAmplifier(player);
            MovementRuntime.find(JumpBoostFallbackStateHook.class, player)
                .ifPresent(hook -> hook.afterEffectTick(player, amplifier));
        }
    }

    @Inject(method = "getJumpBoostPower", at = @At("RETURN"), cancellable = true)
    private void legacy$ignoreRestoredJumpBoost(CallbackInfoReturnable<Float> callback) {
        if ((Object)this instanceof LocalPlayer player && MovementRuntime.appliesTo(player)
            && MovementRuntime.find(JumpBoostResponseHook.class, player)
                .map(hook -> hook.suppressJumpBoost(player)).orElse(false)) {
            callback.setReturnValue(0.0F);
        }
    }

    @Unique
    private int legacy$jumpBoostAmplifier(LocalPlayer player) {
        MobEffectInstance effect = player.getEffect(MobEffects.JUMP_BOOST);
        return effect == null ? Integer.MIN_VALUE : effect.getAmplifier();
    }
}
