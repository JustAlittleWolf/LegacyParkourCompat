package me.wolfii.legacyparkourcompat.mixin;


@Mixin(Player.class)

import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.VanillaCall;
import me.wolfii.legacyparkourcompat.mechanic.hook.FlightFallDistanceBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.SwimmingBehavior;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

abstract class PlayerMixin {
    @Redirect(
        method = "aiStep",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;resetFallDistance()V")
    )
    private void legacyParkourCompat$resetFallDistanceBeforeMovement(Entity entity) {
        Player player = (Player) (Object) this;
        var behavior = MovementRuntime.find(FlightFallDistanceBehavior.class, player);
        if (behavior.isEmpty() || !player.getAbilities().flying || player.isPassenger()) {
            entity.resetFallDistance();
        } else {
            behavior.orElseThrow().beforeMovement(player, entity::resetFallDistance);
        }
    }



    @Inject(method = "isSwimming", at = @At("RETURN"), cancellable = true)
    private void legacyparkourcompat$swimmingState(CallbackInfoReturnable<Boolean> cir) {
        Player player = (Player)(Object)this;
        cir.setReturnValue(MovementRuntime.find(SwimmingBehavior.class, player)
            .map(behavior -> behavior.isSwimming(player, cir.getReturnValue()))
            .orElse(cir.getReturnValue()));
    }

}
