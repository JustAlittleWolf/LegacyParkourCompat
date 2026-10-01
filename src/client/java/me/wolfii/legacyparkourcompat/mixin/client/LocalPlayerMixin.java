package me.wolfii.legacyparkourcompat.mixin.client;

import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.hook.AutoJumpBehavior;
import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LocalPlayer.class)
public abstract class LocalPlayerMixin {
    @Inject(method = "isAutoJumpEnabled", at = @At("RETURN"), cancellable = true)
    private void legacyparkourcompat$applyAutoJumpBehavior(CallbackInfoReturnable<Boolean> cir) {
        LocalPlayer player = (LocalPlayer)(Object)this;
        MovementRuntime.find(AutoJumpBehavior.class, player).ifPresent(behavior ->
            cir.setReturnValue(behavior.isAutoJumpEnabled(player, cir.getReturnValue()))
        );
    }
}
