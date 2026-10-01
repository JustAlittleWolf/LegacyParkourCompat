package me.wolfii.legacyparkourcompat.mixin;

import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.hook.SprintCollisionBehavior;
import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LocalPlayer.class)
public abstract class LocalPlayerSprintCollisionMixin {
    @Inject(method = "shouldStopRunSprinting()Z", at = @At("RETURN"), cancellable = true)
    private void legacyparkourcompat$restoreSprintStopOnCollision(CallbackInfoReturnable<Boolean> cir) {
        LocalPlayer player = (LocalPlayer) (Object) this;
        MovementRuntime.find(SprintCollisionBehavior.class, player)
            .ifPresent(behavior -> cir.setReturnValue(
                behavior.shouldStopRunSprinting(player, cir.getReturnValue())
            ));
    }
}
