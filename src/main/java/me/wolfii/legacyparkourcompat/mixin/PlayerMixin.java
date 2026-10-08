package me.wolfii.legacyparkourcompat.mixin;

import me.wolfii.legacyparkourcompat.mechanic.AirSpeedState;
import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.hook.AirSpeedBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.AirSpeedUpdateBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.FlightFallDistanceBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.PlayerPoseBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.SneakEdgeBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.SneakEdgeDistanceBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.SneakEdgeProbeBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.SwimmingBehavior;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
abstract class PlayerMixin implements AirSpeedState {

    @Inject(method = "updatePlayerPose()V", at = @At("HEAD"), cancellable = true)
    private void legacyparkourcompat$historicalPoseUpdate(CallbackInfo callback) {
        Player player = (Player) (Object) this;
        if (MovementRuntime.find(PlayerPoseBehavior.class, player)
            .map(behavior -> behavior.updatePose(player))
            .orElse(false)) {
            callback.cancel();
        }
    }

    @Inject(method = "canFallAtLeast(DDD)Z", at = @At("HEAD"), cancellable = true)
    private void legacyparkourcompat$historicalSupportProbe(
        double deltaX,
        double deltaZ,
        double minHeight,
        CallbackInfoReturnable<Boolean> callback
    ) {
        Player player = (Player) (Object) this;
        MovementRuntime.find(SneakEdgeProbeBehavior.class, player)
            .ifPresent(behavior -> callback.setReturnValue(
                behavior.canFallAtLeast(player, deltaX, deltaZ, minHeight)));
    }

    @Redirect(
        method = "aiStep",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;resetFallDistance()V")
    )
    private void legacyparkourcompat$resetFallDistanceBeforeMovement(Player entity) {
        Player player = (Player) (Object) this;
        MovementRuntime.find(FlightFallDistanceBehavior.class, player)
            .ifPresentOrElse(
                behavior -> behavior.beforeMovement(player, entity::resetFallDistance),
                entity::resetFallDistance
            );
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
        MovementRuntime.find(AirSpeedUpdateBehavior.class, player)
            .ifPresent(behavior -> this.legacyparkourcompat$storedAirSpeed = behavior.afterAiStep(player));
    }

    @Inject(method = "getFlyingSpeed()F", at = @At("RETURN"), cancellable = true)
    private void legacyparkourcompat$useStoredAirSpeed(CallbackInfoReturnable<Float> cir) {
        Player player = (Player) (Object) this;
        MovementRuntime.find(AirSpeedBehavior.class, player).ifPresent(behavior ->
            cir.setReturnValue(behavior.speed(player, this.legacyparkourcompat$storedAirSpeed, cir.getReturnValue()))
        );
    }

    @Inject(method = "maybeBackOffFromEdge", at = @At("RETURN"), cancellable = true)
    private void legacyparkourcompat$maybeBackOffFromEdge(
        Vec3 movement,
        MoverType moverType,
        CallbackInfoReturnable<Vec3> callback
    ) {
        Player player = (Player) (Object) this;
        MovementRuntime.find(SneakEdgeBehavior.class, player).ifPresent(behavior -> {
            boolean stayingOnGroundSurface = (moverType == MoverType.SELF || moverType == MoverType.PLAYER)
                && player.onGround()
                && ((PlayerMovementAccessor) player).legacyparkourcompat$isStayingOnGroundSurface();
            callback.setReturnValue(behavior.maybeBackOffFromEdge(
                player,
                movement,
                moverType,
                stayingOnGroundSurface,
                MovementRuntime.find(SneakEdgeDistanceBehavior.class, player)
                    .map(distance -> distance.edgeFallDistance(player, player.maxUpStep()))
                    .orElse(player.maxUpStep())
            ));
        });
    }

    @Redirect(
        method = "maybeBackOffFromEdge(Lnet/minecraft/world/phys/Vec3;Lnet/minecraft/world/entity/MoverType;)Lnet/minecraft/world/phys/Vec3;",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;maxUpStep()F")
    )
    private float legacyparkourcompat$edgeProbeDistance(Player player) {
        float vanillaDistance = player.maxUpStep();
        return MovementRuntime.find(SneakEdgeDistanceBehavior.class, player)
            .map(behavior -> behavior.edgeFallDistance(player, vanillaDistance))
            .orElse(vanillaDistance);
    }
}
