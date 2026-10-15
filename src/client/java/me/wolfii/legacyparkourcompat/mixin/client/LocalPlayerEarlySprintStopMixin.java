package me.wolfii.legacyparkourcompat.mixin.client;

import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.hook.sprint.EarlySprintStopHook;
import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LocalPlayer.class)
public abstract class LocalPlayerEarlySprintStopMixin {
    @Inject(method = "aiStep", at = @At(
        value = "INVOKE",
        target = "Lnet/minecraft/client/tutorial/Tutorial;onInput(Lnet/minecraft/client/player/ClientInput;)V",
        shift = At.Shift.AFTER
    ))
    private void legacyParkourCompat$afterTutorialInput(CallbackInfo ci) {
        LocalPlayer player = (LocalPlayer) (Object) this;
        MovementRuntime.find(EarlySprintStopHook.class, player)
            .ifPresent(hook -> hook.afterTutorialInput(player));
    }
}
