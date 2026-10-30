package me.wolfii.legacyparkourcompat.mixin.client;

import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.hook.jumpboost.JumpBoostEffectReceiverHook;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Optional;
import java.util.OptionalInt;

/**
 * Historical direct reads: 1.14.4 Mojmap and 1.15.2 Mojmap LocalPlayer.updateAutoJump
 * gate on hasEffect(JUMP) and read MobEffectInstance.getAmplifier in the jump-height
 * formula. Current 26.2 unobfuscated LocalPlayer.updateAutoJump reads hasEffect(JUMP_BOOST)
 * and then the amplifier; both Jump Boost reads use the same selected receiver state.
 */
@Mixin(LocalPlayer.class)
abstract class LocalPlayerJumpBoostMixin {
    @Redirect(
        method = "updateAutoJump(FF)V",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/entity/LivingEntity;hasEffect(Lnet/minecraft/world/effect/MobEffect;)Z"
        )
    )
    private boolean legacyparkourcompat$readJumpBoostPresence(LivingEntity entity, MobEffect effect) {
        LocalPlayer player = (LocalPlayer)(Object)this;
        if (entity == player && effect.equals(MobEffects.JUMP_BOOST)) {
            Optional<Boolean> receiverPresence = MovementRuntime.find(JumpBoostEffectReceiverHook.class, player)
                .flatMap(receiver -> receiver.hasJumpBoost(player));
            if (receiverPresence.isPresent()) {
                return receiverPresence.get();
            }
        }
        return entity.hasEffect(effect);
    }

    @Redirect(
        method = "updateAutoJump(FF)V",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/effect/MobEffectInstance;getAmplifier()I"
        )
    )
    private int legacyparkourcompat$readJumpBoostAmplifier(MobEffectInstance effect) {
        LocalPlayer player = (LocalPlayer)(Object)this;
        OptionalInt amplifier = MovementRuntime.find(JumpBoostEffectReceiverHook.class, player)
            .map(receiver -> receiver.amplifier(player))
            .orElseGet(OptionalInt::empty);
        return amplifier.isPresent() ? amplifier.getAsInt() : effect.getAmplifier();
    }
}
