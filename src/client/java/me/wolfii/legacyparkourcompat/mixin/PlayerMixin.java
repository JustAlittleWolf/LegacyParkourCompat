package me.wolfii.legacyparkourcompat.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.hook.player.BubbleColumnPlayerCallbackHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.player.PlayerClimbabilityFlyingGateHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.fluid.PlayerEyeWaterQueryHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.travel.AirSpeedHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.state.FlightFallDistanceHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.state.PoseClearanceHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.state.SleepGlidePosePriorityHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.collision.SneakEdgeHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.collision.SneakEdgeCollisionQueryHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.collision.SneakEdgeDistanceHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.collision.SneakEdgeProbeHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.travel.SwimmingPitchHook;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.phys.Vec3;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
abstract class PlayerMixin {
    // Keep Player's native super calls intact, including the inside callback's fall-distance reset.
    @ModifyExpressionValue(
        method = "onAboveBubbleColumn(ZLnet/minecraft/core/BlockPos;)V",
        at = @At(
            value = "FIELD",
            target = "Lnet/minecraft/world/entity/player/Abilities;flying:Z",
            opcode = Opcodes.GETFIELD
        )
    )
    private boolean legacyparkourcompat$forceLegacyAboveBubbleCallback(boolean flying) {
        Player player = (Player)(Object)this;
        return MovementRuntime.find(BubbleColumnPlayerCallbackHook.class, player)
            .flatMap(hook -> hook.forceLegacyCallback(player))
            .map(forceLegacy -> forceLegacy ? false : flying)
            .orElse(flying);
    }

    @ModifyExpressionValue(
        method = "onInsideBubbleColumn(Z)V",
        at = @At(
            value = "FIELD",
            target = "Lnet/minecraft/world/entity/player/Abilities;flying:Z",
            opcode = Opcodes.GETFIELD
        )
    )
    private boolean legacyparkourcompat$forceLegacyInsideBubbleCallback(boolean flying) {
        Player player = (Player)(Object)this;
        return MovementRuntime.find(BubbleColumnPlayerCallbackHook.class, player)
            .flatMap(hook -> hook.forceLegacyCallback(player))
            .map(forceLegacy -> forceLegacy ? false : flying)
            .orElse(flying);
    }

    @ModifyExpressionValue(
        method = "onClimbable()Z",
        at = @At(
            value = "FIELD",
            target = "Lnet/minecraft/world/entity/player/Abilities;flying:Z",
            opcode = Opcodes.GETFIELD
        )
    )
    private boolean legacyparkourcompat$preserveInheritedClimbabilityWhileFlying(boolean flying) {
        Player player = (Player)(Object)this;
        if (!player.isLocalPlayer()) {
            return flying;
        }
        return MovementRuntime.find(PlayerClimbabilityFlyingGateHook.class, player)
            .map(hook -> hook.shouldApplyFlyingVeto(player, flying))
            .orElse(flying);
    }

    /**
     * 1.13.2 Feather {@code PlayerEntity#tick/updateWaterSubmersion} queries water before inherited
     * {@code Entity#tick}. In 26.2, {@code Player#tick/updateIsUnderwater} reads the cached eye tracker
     * before {@code Entity#baseTick} refreshes it, so dispatch here replaces only the cache producer.
     */
    @Redirect(
        method = "updateIsUnderwater",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/entity/player/Player;isEyeInFluid(Lnet/minecraft/tags/TagKey;)Z"
        )
    )
    private boolean legacyparkourcompat$queryCachedEyeWater(Player player, TagKey<Fluid> fluidTag) {
        if (fluidTag.equals(net.minecraft.tags.FluidTags.WATER)) {
            return MovementRuntime.find(PlayerEyeWaterQueryHook.class, player)
                .flatMap(hook -> hook.queryEyeWater(player))
                .orElseGet(() -> player.isEyeInFluid(fluidTag));
        }
        return player.isEyeInFluid(fluidTag);
    }

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

    @Inject(
        method = "aiStep()V",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Avatar;aiStep()V", shift = At.Shift.AFTER)
    )
    private void legacyparkourcompat$storeAirSpeedAfterTravel(CallbackInfo ci) {
        Player player = (Player) (Object) this;
        if (!player.isLocalPlayer()) {
            return;
        }
        MovementRuntime.find(AirSpeedHook.class, player)
            .ifPresent(behavior -> this.legacyparkourcompat$storedAirSpeed = behavior.afterAiStep(player));
    }

    @Inject(method = "getFlyingSpeed()F", at = @At("RETURN"), cancellable = true)
    private void legacyparkourcompat$useStoredAirSpeed(CallbackInfoReturnable<Float> cir) {
        Player player = (Player) (Object) this;
        if (!player.isLocalPlayer()) {
            return;
        }
        MovementRuntime.find(AirSpeedHook.class, player).ifPresent(behavior ->
            cir.setReturnValue(behavior.speed(player, this.legacyparkourcompat$storedAirSpeed, cir.getReturnValue()))
        );
    }

    // A resolved handler can replace the result; empty preserves native behavior.
    @Inject(method = "maybeBackOffFromEdge", at = @At("HEAD"), cancellable = true)
    private void legacyparkourcompat$maybeBackOffFromEdge(
        Vec3 movement,
        MoverType moverType,
        CallbackInfoReturnable<Vec3> callback
    ) {
        Player player = (Player) (Object) this;
        MovementRuntime.find(SneakEdgeHook.class, player).flatMap(behavior -> {
            boolean stayingOnGroundSurface = ((PlayerMovementAccessor) player)
                .legacyparkourcompat$isStayingOnGroundSurface();
            return behavior.maybeBackOffFromEdge(
                player,
                movement,
                moverType,
                stayingOnGroundSurface,
                MovementRuntime.find(SneakEdgeDistanceHook.class, player)
                    .map(distance -> distance.edgeFallDistance(player, player.maxUpStep()))
                    .orElse(player.maxUpStep()),
                MovementRuntime.find(SneakEdgeCollisionQueryHook.class, player).orElse(null)
            );
        }).ifPresent(callback::setReturnValue);
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
