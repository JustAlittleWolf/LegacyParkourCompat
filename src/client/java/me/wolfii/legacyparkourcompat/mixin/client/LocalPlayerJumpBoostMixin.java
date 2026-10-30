package me.wolfii.legacyparkourcompat.mixin.client;

import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.hook.jumpboost.JumpBoostEffectReceiverHook;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.OptionalInt;

/**
 * Historical direct read: 1.14.4 Mojmap and 1.15.2 Mojmap LocalPlayer.updateAutoJump
 * gate on hasEffect(JUMP) and read MobEffectInstance.getAmplifier in the jump-height
 * formula. Current 26.2 unobfuscated LocalPlayer.updateAutoJump has the same direct
 * amplifier read after hasEffect(JUMP_BOOST); this redirects only that read.
 */
@Mixin(LocalPlayer.class)
abstract class LocalPlayerJumpBoostMixin {
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
