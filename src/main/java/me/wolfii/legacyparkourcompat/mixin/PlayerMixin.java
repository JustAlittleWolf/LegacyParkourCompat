package me.wolfii.legacyparkourcompat.mixin;

import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.hook.SwimmingBehavior;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
abstract class PlayerMixin {
    @Inject(method = "isSwimming", at = @At("RETURN"), cancellable = true)
    private void legacyparkourcompat$swimmingState(CallbackInfoReturnable<Boolean> cir) {
        Player player = (Player)(Object)this;
        cir.setReturnValue(MovementRuntime.find(SwimmingBehavior.class, player)
            .map(behavior -> behavior.isSwimming(player, cir.getReturnValue()))
            .orElse(cir.getReturnValue()));
    }
}
