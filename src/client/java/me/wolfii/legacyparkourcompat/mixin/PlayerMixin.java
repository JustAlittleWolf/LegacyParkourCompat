package me.wolfii.legacyparkourcompat.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import me.wolfii.legacyparkourcompat.mechanic.AirSpeedState;
import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.hook.travel.AirSpeedHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.travel.AirSpeedUpdateHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.state.FlightFallDistanceHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.state.PlayerPoseHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.state.PoseClearanceHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.state.SleepGlidePosePriorityHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.collision.SneakEdgeHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.collision.SneakEdgeCollisionQueryHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.collision.SneakEdgeDistanceHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.collision.SneakEdgeProbeHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.travel.SwimmingPitchHook;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
abstract class PlayerMixin implements AirSpeedState {
    @ModifyArg(
        method = "canPlayerFitWithinBlocksAndEntitiesWhen(Lnet/minecraft/world/entity/Pose;)Z",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/phys/AABB;deflate(D)Lnet/minecraft/world/phys/AABB;"
        ),
        index = 0
    )
    private double legacyparkourcompat$historicalPoseClearanceEpsilon(
        double epsilon,
        @Local(argsOnly = true) Pose pose
    ) {
        Player player = (Player)(Object)this;
        if (player.getScale() != 1.0F) {
            return epsilon;
        }
        return MovementRuntime.find(PoseClearanceHook.class, player)
            .filter(change -> change.usesFullBox(MovementRuntime.profile(player).target(), pose))
            .map(change -> 0.0D)
            .orElse(epsilon);
    }

    @ModifyReturnValue(method = "getDesiredPose", at = @At("RETURN"))
    private Pose legacyparkourcompat$sleepGlidePosePriority(Pose vanilla) {
        Player player = (Player)(Object)this;
        return MovementRuntime.find(SleepGlidePosePriorityHook.class, player)
            .map(hook -> hook.desiredPose(MovementRuntime.profile(player).target(), player.isSleeping(), player.isFallFlying(), vanilla))
            .orElse(vanilla);
    }

    @Inject(method = "updatePlayerPose()V", at = @At("HEAD"), cancellable = true)
    private void legacyparkourcompat$historicalPoseUpdate(CallbackInfo callback) {
        Player player = (Player) (Object) this;
        if (MovementRuntime.find(PlayerPoseHook.class, player)
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
        MovementRuntime.find(SneakEdgeProbeHook.class, player)
            .ifPresent(behavior -> callback.setReturnValue(
                behavior.canFallAtLeast(player, deltaX, deltaZ, minHeight)));
    }

    @Redirect(
        method = "aiStep",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;resetFallDistance()V")
    )
    private void legacyparkourcompat$resetFallDistanceBeforeMovement(Player entity) {
        Player player = (Player) (Object) this;
        MovementRuntime.find(FlightFallDistanceHook.class, player)
            .ifPresentOrElse(
                behavior -> behavior.beforeMovement(player, entity::resetFallDistance),
                entity::resetFallDistance
            );
    }

    @Redirect(
        method = "travel(Lnet/minecraft/world/phys/Vec3;)V",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/entity/player/Player;getLookAngle()Lnet/minecraft/world/phys/Vec3;"
        )
    )
    private Vec3 legacyparkourcompat$swimmingPitchLook(Player player) {
        Vec3 vanilla = player.getLookAngle();
        return MovementRuntime.find(SwimmingPitchHook.class, player)
            .filter(behavior -> behavior.supports(MovementRuntime.profile(player).target()))
            .map(behavior -> new Vec3(vanilla.x, behavior.lookY(player, vanilla.y), vanilla.z))
            .orElse(vanilla);
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
        MovementRuntime.find(AirSpeedUpdateHook.class, player)
            .ifPresent(behavior -> this.legacyparkourcompat$storedAirSpeed = behavior.afterAiStep(player));
    }

    @Inject(method = "getFlyingSpeed()F", at = @At("RETURN"), cancellable = true)
    private void legacyparkourcompat$useStoredAirSpeed(CallbackInfoReturnable<Float> cir) {
        Player player = (Player) (Object) this;
        MovementRuntime.find(AirSpeedHook.class, player).ifPresent(behavior ->
            cir.setReturnValue(behavior.speed(player, this.legacyparkourcompat$storedAirSpeed, cir.getReturnValue()))
        );
    }

    // Historical handlers replace the result, so resolve them before the modern probe loops run.
    @Inject(method = "maybeBackOffFromEdge", at = @At("HEAD"), cancellable = true)
    private void legacyparkourcompat$maybeBackOffFromEdge(
        Vec3 movement,
        MoverType moverType,
        CallbackInfoReturnable<Vec3> callback
    ) {
        Player player = (Player) (Object) this;
        MovementRuntime.find(SneakEdgeHook.class, player).ifPresent(behavior -> {
            boolean stayingOnGroundSurface = ((PlayerMovementAccessor) player)
                .legacyparkourcompat$isStayingOnGroundSurface();
            callback.setReturnValue(behavior.maybeBackOffFromEdge(
                player,
                movement,
                moverType,
                stayingOnGroundSurface,
                MovementRuntime.find(SneakEdgeDistanceHook.class, player)
                    .map(distance -> distance.edgeFallDistance(player, player.maxUpStep()))
                    .orElse(player.maxUpStep()),
                MovementRuntime.find(SneakEdgeCollisionQueryHook.class, player).orElse(null)
            ));
        });
    }

    @Redirect(
        method = "maybeBackOffFromEdge(Lnet/minecraft/world/phys/Vec3;Lnet/minecraft/world/entity/MoverType;)Lnet/minecraft/world/phys/Vec3;",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;maxUpStep()F")
    )
    private float legacyparkourcompat$edgeProbeDistance(Player player) {
        float vanillaDistance = player.maxUpStep();
        return MovementRuntime.find(SneakEdgeDistanceHook.class, player)
            .map(behavior -> behavior.edgeFallDistance(player, vanillaDistance))
            .orElse(vanillaDistance);
    }
}
