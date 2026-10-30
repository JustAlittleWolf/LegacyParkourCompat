package me.wolfii.legacyparkourcompat.mixin.client;

import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.hook.jumpboost.JumpBoostEffectReceiverHook;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/** Ticks the movement-only receiver and substitutes only the Jump Boost power term. */
@Mixin(LivingEntity.class)
abstract class LivingEntityJumpBoostMixin {
    @Inject(method = "tickEffects", at = @At("TAIL"))
    private void legacyparkourcompat$tickJumpBoostReceiver(CallbackInfo callbackInfo) {
        if ((Object)this instanceof Player player && player == Minecraft.getInstance().player) {
            MovementRuntime.find(JumpBoostEffectReceiverHook.class, player).ifPresent(receiver -> receiver.tick(player));
        }
    }

    @Inject(method = "getJumpBoostPower", at = @At("RETURN"), cancellable = true)
    private void legacyparkourcompat$readJumpBoostAmplifier(CallbackInfoReturnable<Float> callbackInfo) {
        if ((Object)this instanceof Player player && player == Minecraft.getInstance().player) {
            MovementRuntime.find(JumpBoostEffectReceiverHook.class, player).ifPresent(receiver ->
                receiver.amplifier(player).ifPresent(amplifier -> callbackInfo.setReturnValue(0.1F * (amplifier + 1)))
            );
        }
    }
}
