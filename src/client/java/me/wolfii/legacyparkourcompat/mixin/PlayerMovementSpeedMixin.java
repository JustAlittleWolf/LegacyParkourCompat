package me.wolfii.legacyparkourcompat.mixin;

import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
abstract class PlayerMovementSpeedMixin {
    @Inject(method = "getSpeed", at = @At("HEAD"), cancellable = true)
    private void legacyparkourcompat$historicalSpeed(CallbackInfoReturnable<Float> callback) {
        MovementRuntime.playerMovementSpeed((Player)(Object)this).ifPresent(callback::setReturnValue);
    }
}
