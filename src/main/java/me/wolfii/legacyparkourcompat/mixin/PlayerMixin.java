package me.wolfii.legacyparkourcompat.mixin;

import me.wolfii.legacyparkourcompat.mechanic.AirSpeedState;
import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.hook.AirSpeedBehavior;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public abstract class PlayerMixin implements AirSpeedState {
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
