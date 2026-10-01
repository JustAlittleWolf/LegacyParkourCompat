package me.wolfii.legacyparkourcompat.mixin;


@Mixin(Player.class)

import me.wolfii.legacyparkourcompat.mechanic.AirSpeedState;
import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.VanillaCall;
import me.wolfii.legacyparkourcompat.mechanic.hook.AirSpeedBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.FlightFallDistanceBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.SwimmingBehavior;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
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



    @Unique
    private float legacyparkourcompat$storedAirSpeed = 0.02F;

    @Override
    public float lpc$storedAirSpeed() {
        return this.legacyparkourcompat$storedAirSpeed;
    }

    @Inject(
        method = "aiStep()V",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Avatar;aiStep()V", shift = At.Shift.AFTER)
    )
    private void legacyparkourcompat$storeAirSpeedAfterTravel(CallbackInfo ci) {
        Player player = (Player) (Object) this;
        MovementRuntime.find(AirSpeedBehavior.class, player)
            .ifPresent(behavior -> this.legacyparkourcompat$storedAirSpeed = behavior.afterAiStep(player));
    }

    @Inject(method = "getFlyingSpeed()F", at = @At("RETURN"), cancellable = true)
    private void legacyparkourcompat$useStoredAirSpeed(CallbackInfoReturnable<Float> cir) {
        Player player = (Player) (Object) this;
        MovementRuntime.find(AirSpeedBehavior.class, player).ifPresent(behavior ->
            cir.setReturnValue(behavior.speed(player, this.legacyparkourcompat$storedAirSpeed, cir.getReturnValue()))
        );
    }

}
