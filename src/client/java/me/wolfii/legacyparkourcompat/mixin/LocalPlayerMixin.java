package me.wolfii.legacyparkourcompat.mixin;

import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.hook.AutoJumpBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.SprintingBehavior;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LocalPlayer.class)
public abstract class LocalPlayerMixin {
    @Redirect(
        method = "updateAutoJump(FF)V",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/util/Mth;invSqrt(F)F", ordinal = 0)
    )
    private float legacyparkourcompat$autoJumpInverseSqrt(float value) {
        float vanilla = org.joml.Math.invsqrt(value);
        Player player = (Player) (Object) this;
        return MovementRuntime.find(AutoJumpBehavior.class, player)
            .map(behavior -> behavior.inverseSqrt(player, value, vanilla))
            .orElse(vanilla);
    }

    @Redirect(
        method = "canStartSprinting()Z",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;isFallFlying()Z", ordinal = 0)
    )
    private boolean legacyparkourcompat$fallFlyingSprintGate(LocalPlayer localPlayer) {
        boolean vanilla = localPlayer.isFallFlying();
        Player player = localPlayer;
        return MovementRuntime.find(SprintingBehavior.class, player)
            .map(behavior -> behavior.fallFlyingForSprintGate(player, vanilla))
            .orElse(vanilla);
    }

    @Inject(method = "vehicleCanSprint(Lnet/minecraft/world/entity/Entity;)Z", at = @At("RETURN"), cancellable = true)
    private void legacyparkourcompat$historicalVehicleSprint(Entity vehicle, CallbackInfoReturnable<Boolean> cir) {
        Player player = (Player) (Object) this;
        MovementRuntime.find(SprintingBehavior.class, player).ifPresent(behavior ->
            cir.setReturnValue(behavior.vehicleCanSprint(player, vehicle, cir.getReturnValue()))
        );
    }
}
