package me.wolfii.legacyparkourcompat.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.hook.FlightActivationJumpBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.PassengerCrouchBehavior;
import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LocalPlayer.class)
abstract class LocalPlayerInputMixin {
    @WrapOperation(
        method = "aiStep",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/player/LocalPlayer;jumpFromGround()V"
        )
    )
    private void legacyparkourcompat$flightActivationJump(LocalPlayer player, Operation<Void> vanilla) {
        MovementRuntime.find(FlightActivationJumpBehavior.class, player)
            .ifPresentOrElse(
                behavior -> behavior.onFlightActivated(player, () -> vanilla.call(player)),
                () -> vanilla.call(player)
            );
    }

    @ModifyExpressionValue(
        method = "aiStep",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/player/LocalPlayer;isPassenger()Z",
            ordinal = 0
        )
    )
    private boolean legacyparkourcompat$passengerBlocksCrouching(boolean vanilla) {
        LocalPlayer player = (LocalPlayer)(Object)this;
        return MovementRuntime.find(PassengerCrouchBehavior.class, player)
            .map(behavior -> behavior.blocksCrouching(player, vanilla))
            .orElse(vanilla);
    }
}
