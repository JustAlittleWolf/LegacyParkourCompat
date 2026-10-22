package me.wolfii.legacyparkourcompat.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalFloatRef;
import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.VanillaFn;
import me.wolfii.legacyparkourcompat.mechanic.hook.vehicle.DismountPositionHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.state.EffectFallDistanceResetHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.travel.ElytraLiftForceHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.travel.FallFlyingLookHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.travel.GlideClimbableExemptionHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.travel.FallFlyingClimbableExitHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.travel.ClimbHeldJumpImpulseHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.jump.FluidJumpHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.state.GlideFallDistanceHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.gliding.GlideFallDistanceLimiterStageHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.block.GroundFrictionBlockHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.jump.GroundJumpGateHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.travel.GroundSpeedHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.jump.JumpPowerHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.jump.JumpVerticalVelocityHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.jump.LavaGroundJumpHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.travel.LavaTravelHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.player.JumpBoostResponseHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.state.LevitationFallDistanceHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.jump.LiquidJumpGateHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.travel.MovementChunkLookupHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.state.PlayerDimensionsHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.vehicle.PortalDismountHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.travel.PowderSnowClimbHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.state.SlowFallingFallDistanceHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.state.SleepPitchResetHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.jump.SprintJumpImpulseHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.fluid.RetainedWaterJumpHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.fluid.PostMoveLavaCurrentHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.travel.VelocityZeroThresholdHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.fluid.WaterGravityHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.fluid.WaterEnchantmentFactorsHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.fluid.WaterClimbImpulseHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.fluid.SlowFallingWaterDescentHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.jump.WaterJumpHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.input.WaterSprintSlowdownHook;
import me.wolfii.legacyparkourcompat.mixin.accessor.EntityInvoker;
import me.wolfii.legacyparkourcompat.mixin.accessor.WaterJumpDepthAccess;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.phys.Vec3;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
abstract class LivingEntityMixin {
    @WrapOperation(
        method = "checkFallDamage",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;updateFluidInteraction()Z")
    )
    private boolean legacyparkourcompat$historicalPostMoveLavaCurrent(
        Entity entity,
        Operation<Boolean> original
    ) {
        var behavior = MovementRuntime.find(PostMoveLavaCurrentHook.class, entity);
        if (behavior.isEmpty() || behavior.get().appliesLavaCurrent(entity)) {
            return original.call(entity);
        }
        return PostMoveFluidRefreshContext.suppressLavaCurrent(entity, () -> original.call(entity));
    }

    @WrapOperation(method = "tick()V", at = @At(value = "INVOKE",
        target = "Lnet/minecraft/world/entity/LivingEntity;setXRot(F)V"))
    private void legacyparkourcompat$sleepPitchReset(LivingEntity entity, float pitch, Operation<Void> original) {
        boolean resets = MovementRuntime.find(SleepPitchResetHook.class, entity)
            .map(hook -> hook.resetsPitch(MovementRuntime.profile(entity).target()))
            .orElse(true);
        if (resets) {
            original.call(entity, pitch);
        }
    }

    @WrapOperation(method = "travelInWater", at = @At(value = "INVOKE",
        target = "Lnet/minecraft/world/entity/LivingEntity;onClimbable()Z"))
    private boolean legacyparkourcompat$waterClimbImpulse(LivingEntity entity, Operation<Boolean> original) {
        if (entity instanceof Player player) {
            return MovementRuntime.find(WaterClimbImpulseHook.class, player)
                .map(hook -> hook.onClimbable(player, MovementRuntime.profile(player).target(), () -> original.call(entity)))
                .orElseGet(() -> original.call(entity));
        }
        return original.call(entity);
    }

    @ModifyExpressionValue(method = "handleRelativeFrictionAndCalculateMovement", at = @At(value = "FIELD",
        target = "Lnet/minecraft/world/entity/LivingEntity;jumping:Z", opcode = Opcodes.GETFIELD))
    private boolean legacyparkourcompat$climbHeldJumpImpulse(boolean jumping) {
        LivingEntity entity = (LivingEntity)(Object)this;
        return MovementRuntime.find(ClimbHeldJumpImpulseHook.class, entity)
            .map(hook -> hook.heldJump(entity.getInBlockState(), jumping))
            .orElse(jumping);
    }

    @WrapOperation(method = "travelFallFlying", at = @At(value = "INVOKE",
        target = "Lnet/minecraft/world/entity/LivingEntity;onClimbable()Z"))
    private boolean legacyparkourcompat$fallFlyingClimbableExit(LivingEntity entity, Operation<Boolean> original) {
        return MovementRuntime.find(FallFlyingClimbableExitHook.class, entity)
            .map(hook -> hook.exitsOnClimbable(MovementRuntime.profile(entity).target(), () -> original.call(entity)))
            .orElseGet(() -> original.call(entity));
    }

    @WrapOperation(method = "onClimbable()Z", at = @At(value = "INVOKE",
        target = "Lnet/minecraft/world/entity/LivingEntity;isFallFlying()Z"))
    private boolean legacyparkourcompat$glideClimbableExemption(LivingEntity entity, Operation<Boolean> original) {
        boolean gliding = original.call(entity);
        return MovementRuntime.find(GlideClimbableExemptionHook.class, entity)
            .map(hook -> hook.exemptsGlidingPlayer(MovementRuntime.profile(entity).target(), entity.getInBlockState(), gliding))
            .orElse(gliding);
    }

    @Unique
    private int legacyparkourcompat$movementEpoch;

    @Unique
    private boolean legacyparkourcompat$movementEpochInitialized;

    @Unique
    private boolean legacyparkourcompat$staleWaterJumpInitiated;

    @Unique
    private int legacyparkourcompat$noJumpDelayBeforeStaleWaterJump;

    @Inject(method = "tick()V", at = @At("HEAD"))
    private void legacyparkourcompat$refreshDimensionsOnProfileChange(CallbackInfo ci) {
        LivingEntity entity = (LivingEntity)(Object)this;
        if (!(entity instanceof Player player) || !player.level().isClientSide()) {
            return;
        }

        // Profile epochs cover global selection, per-player overrides, and registry changes.
        int epoch = MovementRuntime.epoch();
        if (this.legacyparkourcompat$movementEpochInitialized && this.legacyparkourcompat$movementEpoch == epoch) {
            return;
        }
        this.legacyparkourcompat$movementEpoch = epoch;
        this.legacyparkourcompat$movementEpochInitialized = true;

        EntityDimensions resolved = player.getDimensions(player.getPose());
        if (player.getBbWidth() != resolved.width()
            || player.getBbHeight() != resolved.height()
            || player.getEyeHeight() != resolved.eyeHeight()) {
            player.refreshDimensions();
        }
    }

    @WrapOperation(
        method = "aiStep",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;resetFallDistance()V")
    )
    private void legacyparkourcompat$effectFallDistanceReset(LivingEntity entity, Operation<Void> vanilla) {
        MovementRuntime.find(EffectFallDistanceResetHook.class, entity)
            .ifPresentOrElse(
                behavior -> {
                    if (behavior.resetBeforeTravel(entity, true)) vanilla.call(entity);
                },
                () -> vanilla.call(entity)
            );
    }

    @Inject(method = "travel", at = @At("HEAD"))
    private void legacyparkourcompat$historicalSlowFallingReset(Vec3 input, CallbackInfo ci) {
        LivingEntity entity = (LivingEntity)(Object)this;
        MovementRuntime.find(SlowFallingFallDistanceHook.class, entity)
            .ifPresent(behavior -> behavior.beforeTravel(entity));
    }

    @Inject(
        method = "travelInAir",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/entity/LivingEntity;setDeltaMovement(DDD)V",
            shift = At.Shift.BEFORE
        )
    )
    private void legacyparkourcompat$historicalLevitationReset(Vec3 input, CallbackInfo ci) {
        LivingEntity entity = (LivingEntity)(Object)this;
        MovementRuntime.find(LevitationFallDistanceHook.class, entity)
            .ifPresent(behavior -> behavior.beforeAirTravel(entity));
    }

    @Redirect(
        method = "updateFallFlyingMovement(Lnet/minecraft/world/phys/Vec3;)Lnet/minecraft/world/phys/Vec3;",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/util/Mth;square(D)D", ordinal = 0)
    )
    private double legacyparkourcompat$selectElytraLiftForce(double cosine) {
        double vanilla = Mth.square(cosine);
        if (!((Object) this instanceof Player player)) {
            return vanilla;
        }
        return MovementRuntime.find(ElytraLiftForceHook.class, player)
            .map(behavior -> {
                var selected = MovementRuntime.profile(player).target();
                Vec3 vanillaLook = player.getLookAngle();
                Vec3 look = MovementRuntime.find(FallFlyingLookHook.class, player)
                    .map(change -> change.lookVector(player, selected, vanillaLook))
                    .orElse(vanillaLook);
                return behavior.liftForce(player, selected, look, vanilla);
            })
            .orElse(vanilla);
    }

    @Redirect(
        method = "aiStep",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;isAffectedByFluids()Z")
    )
    private boolean legacyparkourcompat$fluidJumpEligibility(LivingEntity entity) {
        boolean vanilla = entity.isAffectedByFluids();
        if (entity instanceof Player player) {
            return MovementRuntime.find(FluidJumpHook.class, player)
                .map(behavior -> behavior.mayJumpInFluid(player, vanilla))
                .orElse(vanilla);
        }
        return vanilla;
    }

    @Redirect(
        method = "aiStep",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;isInShallowFluid(Lnet/minecraft/tags/TagKey;)Z")
    )
    private boolean legacyparkourcompat$shallowLavaJump(
        LivingEntity entity,
        TagKey<Fluid> fluid
    ) {
        boolean vanilla = entity.getFluidHeight(fluid) <= entity.getFluidJumpThreshold();
        if (fluid.equals(FluidTags.LAVA) && entity instanceof Player player) {
            return MovementRuntime.find(LavaGroundJumpHook.class, player)
                .map(behavior -> behavior.isShallowLavaForGroundJump(player, vanilla))
                .orElse(vanilla);
        }
        return vanilla;
    }

    @Redirect(
        method = "travelInLava",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;isInShallowFluid(Lnet/minecraft/tags/TagKey;)Z")
    )
    private boolean legacyparkourcompat$shallowLavaTravel(
        LivingEntity entity,
        TagKey<Fluid> fluid
    ) {
        boolean vanilla = entity.getFluidHeight(fluid) <= entity.getFluidJumpThreshold();
        if (entity instanceof Player player) {
            return MovementRuntime.find(LavaTravelHook.class, player)
                .map(behavior -> behavior.isShallowForTravel(player, vanilla))
                .orElse(vanilla);
        }
        return vanilla;
    }

    @Redirect(
        method = "travelInAir",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/level/Level;hasChunkAt(Lnet/minecraft/core/BlockPos;)Z"
        )
    )
    private boolean legacyparkourcompat$hasMovementChunk(Level level, BlockPos position) {
        LivingEntity entity = (LivingEntity) (Object) this;
        var behavior = MovementRuntime.find(MovementChunkLookupHook.class, entity);
        if (behavior.isEmpty() || !(entity instanceof net.minecraft.world.entity.player.Player)) {
            return level.hasChunkAt(position);
        }
        return behavior.orElseThrow().hasChunkAt(entity, level, position, () -> level.hasChunkAt(position));
    }

    @Shadow
    protected boolean jumping;

    @Shadow
    private int noJumpDelay;

    @Shadow
    protected abstract boolean isImmobile();

    @Invoker("jumpFromGround")
    protected abstract void legacyparkourcompat$invokeJumpFromGround();

    @Invoker("jumpInLiquid")
    protected abstract void legacyparkourcompat$invokeJumpInLiquid(TagKey<Fluid> type);

    @Inject(method = "getDimensions", at = @At("RETURN"), cancellable = true)
    private void legacyparkourcompat$playerDimensions(Pose pose, CallbackInfoReturnable<EntityDimensions> cir) {
        LivingEntity entity = (LivingEntity)(Object)this;
        if (entity instanceof Player player) {
            cir.setReturnValue(MovementRuntime.find(PlayerDimensionsHook.class, player)
                .map(behavior -> behavior.dimensions(player, pose, cir.getReturnValue()))
                .orElse(cir.getReturnValue()));
        }
    }

    @Inject(method = "getFrictionInfluencedSpeed", at = @At("RETURN"), cancellable = true)
    private void legacyparkourcompat$groundAcceleration(float blockFriction, CallbackInfoReturnable<Float> cir) {
        LivingEntity entity = (LivingEntity)(Object)this;
        if (entity instanceof Player player) {
            cir.setReturnValue(MovementRuntime.find(GroundSpeedHook.class, player)
                .map(behavior -> behavior.speed(entity, blockFriction, cir.getReturnValue()))
                .orElse(cir.getReturnValue()));
        }
    }

    @Redirect(
        method = "updateFallFlyingMovement",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;getLookAngle()Lnet/minecraft/world/phys/Vec3;")
    )
    private Vec3 legacyparkourcompat$fallFlyingLook(LivingEntity entity) {
        Vec3 vanilla = entity.getLookAngle();
        return MovementRuntime.find(FallFlyingLookHook.class, entity)
            .map(behavior -> behavior.lookVector(entity, MovementRuntime.profile(entity).target(), vanilla))
            .orElse(vanilla);
    }

    @Inject(
        method = "aiStep",
        at = @At("HEAD")
    )
    private void legacyparkourcompat$beginStaleWaterJumpTick(CallbackInfo ci) {
        this.legacyparkourcompat$staleWaterJumpInitiated = false;
    }

    @Inject(
        method = "aiStep",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/entity/LivingEntity;isAffectedByFluids()Z",
            shift = At.Shift.BEFORE
        )
    )
    private void legacyparkourcompat$initiateStaleWaterJump(CallbackInfo ci) {
        LivingEntity entity = (LivingEntity)(Object)this;
        if (!(entity instanceof Player player) || this.isImmobile()) {
            return;
        }

        boolean vanillaAffectedByFluids = player.isAffectedByFluids();
        boolean mayJumpInFluid = MovementRuntime.find(FluidJumpHook.class, player)
            .map(behavior -> behavior.mayJumpInFluid(player, vanillaAffectedByFluids))
            .orElse(vanillaAffectedByFluids);
        if (!mayJumpInFluid) {
            return;
        }

        double retainedDepth = ((WaterJumpDepthAccess)(Object)player).legacyparkourcompat$getRetainedWaterDepth();
        MovementRuntime.find(RetainedWaterJumpHook.class, player)
            .filter(behavior -> behavior.selectsWaterJump(MovementRuntime.profile(player).target(),
                player.isPassenger(), player.isInWater(), player.isInLava(), this.jumping,
                player.onGround(), retainedDepth))
            .ifPresent(behavior -> {
                this.legacyparkourcompat$noJumpDelayBeforeStaleWaterJump = this.noJumpDelay;
                this.legacyparkourcompat$invokeJumpInLiquid(FluidTags.WATER);
                this.legacyparkourcompat$staleWaterJumpInitiated = true;
            });
    }

    @Inject(method = "aiStep", at = @At("RETURN"))
    private void legacyparkourcompat$restoreStaleWaterJumpDelay(CallbackInfo ci) {
        if (this.legacyparkourcompat$staleWaterJumpInitiated) {
            this.noJumpDelay = this.legacyparkourcompat$noJumpDelayBeforeStaleWaterJump;
        }
    }

    @Inject(
        method = "aiStep",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;applyInput()V", shift = org.spongepowered.asm.mixin.injection.At.Shift.AFTER)
    )
    private void legacyparkourcompat$waterJumpInput(CallbackInfo ci) {
        LivingEntity entity = (LivingEntity)(Object)this;
        if (this.jumping && !this.isImmobile() && entity instanceof Player player) {
            MovementRuntime.find(WaterJumpHook.class, player)
                .ifPresent(behavior -> behavior.jumpInWater(player));
        }
    }

    @Redirect(
        method = "aiStep",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;jumpFromGround()V")
    )
    private void legacyparkourcompat$replaceShallowWaterJump(LivingEntity entity) {
        if (this.legacyparkourcompat$staleWaterJumpInitiated) {
            return;
        }
        if (entity instanceof Player player
            && !MovementRuntime.find(GroundJumpGateHook.class, player)
                .map(behavior -> behavior.shouldJumpFromGround(player, true)).orElse(true)) {
            return;
        }
        this.legacyparkourcompat$invokeJumpFromGround();
    }

    @Redirect(
        method = "aiStep",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;jumpInLiquid(Lnet/minecraft/tags/TagKey;)V")
    )
    private void legacyparkourcompat$replaceLiquidJump(LivingEntity entity, TagKey<Fluid> type) {
        if (this.legacyparkourcompat$staleWaterJumpInitiated) {
            return;
        }
        if (entity instanceof Player player
            && !MovementRuntime.find(LiquidJumpGateHook.class, player)
                .map(behavior -> behavior.shouldJumpInLiquid(player, type, true)).orElse(true)) {
            return;
        }
        this.legacyparkourcompat$invokeJumpInLiquid(type);
    }

    @WrapOperation(method = "travelInWater", at = @At(value = "INVOKE",
        target = "Lnet/minecraft/world/entity/LivingEntity;moveRelative(FLnet/minecraft/world/phys/Vec3;)V"))
    private void legacyparkourcompat$waterEnchantmentFactors(LivingEntity entity, float speed, Vec3 input,
        Operation<Void> original, @Local(name = "slowDown") LocalFloatRef drag) {
        if (entity instanceof Player player) {
            float vanillaSpeed = speed;
            var factors = MovementRuntime.find(WaterEnchantmentFactorsHook.class, player)
                .map(hook -> hook.factors(player, MovementRuntime.profile(player).target(), drag.get(), vanillaSpeed))
                .orElse(new WaterEnchantmentFactorsHook.Factors(drag.get(), speed));
            drag.set(factors.drag());
            speed = factors.acceleration();
        }
        original.call(entity, speed, input);
    }

    @ModifyConstant(method = "travelInWater", constant = @Constant(floatValue = 0.9F))
    private float legacyparkourcompat$waterSprintSlowdown(float vanilla) {
        LivingEntity entity = (LivingEntity)(Object)this;
        if (entity instanceof Player player) {
            return MovementRuntime.find(WaterSprintSlowdownHook.class, player)
                .map(behavior -> behavior.sprintSlowdown(player, vanilla))
                .orElse(vanilla);
        }
        return vanilla;
    }

    @Redirect(
        method = "travelInWater",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/entity/LivingEntity;getFluidFallingAdjustedMovement(DZLnet/minecraft/world/phys/Vec3;)Lnet/minecraft/world/phys/Vec3;"
        )
    )
    private Vec3 legacyparkourcompat$waterGravity(
        LivingEntity entity,
        double baseGravity,
        boolean falling,
        Vec3 movement
    ) {
        boolean selectedFalling = entity instanceof Player player
            ? MovementRuntime.find(SlowFallingWaterDescentHook.class, player)
                .map(hook -> hook.isFallingAtWaterDescentCheck(
                    MovementRuntime.profile(player).target(),
                    falling,
                    movement.y
                ))
                .orElse(falling)
            : falling;
        Vec3 vanilla = entity.getFluidFallingAdjustedMovement(baseGravity, selectedFalling, movement);
        if (entity instanceof Player player) {
            return MovementRuntime.find(WaterGravityHook.class, player)
                .map(behavior -> behavior.gravityAdjustedMovement(player, baseGravity, selectedFalling, movement, vanilla))
                .orElse(vanilla);
        }
        return vanilla;
    }

    @WrapOperation(
        method = "updateFallFlying()V",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;checkFallDistanceAccumulation()V")
    )
    private void legacyparkourcompat$earlyGlideFallDistance(LivingEntity entity, Operation<Void> original) {
        MovementRuntime.find(GlideFallDistanceLimiterStageHook.class, entity).ifPresentOrElse(
            behavior -> behavior.atStage(entity, MovementRuntime.profile(entity).target(),
                GlideFallDistanceLimiterStageHook.Stage.UPDATE_FALL_FLYING, () -> original.call(entity)),
            () -> original.call(entity));
    }

    @Inject(method = "travelFallFlying(Lnet/minecraft/world/phys/Vec3;)V", at = @At("HEAD"))
    private void legacyparkourcompat$fallFlyingFallDistance(Vec3 input, CallbackInfo ci) {
        LivingEntity entity = (LivingEntity)(Object)this;
        MovementRuntime.find(GlideFallDistanceLimiterStageHook.class, entity).ifPresent(behavior ->
            behavior.atStage(entity, MovementRuntime.profile(entity).target(),
                GlideFallDistanceLimiterStageHook.Stage.TRAVEL_FALL_FLYING, () -> {
                    if (entity instanceof Player player) {
                        MovementRuntime.find(GlideFallDistanceHook.class, player).ifPresentOrElse(
                            assignment -> assignment.beforeFallFlyingTravel(player, MovementRuntime.profile(player).target()),
                            entity::checkFallDistanceAccumulation);
                    } else {
                        entity.checkFallDistanceAccumulation();
                    }
                }));
    }

    @Redirect(
        method = "jumpFromGround()V",
        at = @At(value = "INVOKE", target = "Ljava/lang/Math;max(DD)D")
    )
    private double legacyparkourcompat$jumpVerticalVelocity(double jumpPower, double currentVelocity) {
        LivingEntity entity = (LivingEntity) (Object) this;
        double power = MovementRuntime.find(JumpPowerHook.class, entity)
            .map(behavior -> behavior.jumpPower(entity, jumpPower)).orElse(jumpPower);
        if (entity instanceof LocalPlayer player) {
            double resolvedPower = power;
            power = MovementRuntime.find(JumpBoostResponseHook.class, player)
                .map(hook -> hook.jumpPower(player, MovementRuntime.profile(player).target(), jumpPower, resolvedPower))
                .orElse(resolvedPower);
        }
        double vanilla = Math.max(power, currentVelocity);
        return MovementRuntime.find(JumpVerticalVelocityHook.class, entity)
            .map(behavior -> behavior.jumpVerticalVelocity(entity, power, currentVelocity, vanilla))
            .orElse(vanilla);
    }

    @ModifyArg(
        method = "aiStep()V",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/entity/LivingEntity;setDeltaMovement(DDD)V",
            ordinal = 0
        ),
        index = 0
    )
    private double legacyparkourcompat$velocityZeroThresholdX(double component) {
        return this.legacyparkourcompat$filterVelocityComponent(component);
    }

    @ModifyArg(
        method = "aiStep()V",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/entity/LivingEntity;setDeltaMovement(DDD)V",
            ordinal = 0
        ),
        index = 1
    )
    private double legacyparkourcompat$velocityZeroThresholdY(double component) {
        return this.legacyparkourcompat$filterVelocityComponent(component);
    }

    @ModifyArg(
        method = "aiStep()V",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/entity/LivingEntity;setDeltaMovement(DDD)V",
            ordinal = 0
        ),
        index = 2
    )
    private double legacyparkourcompat$velocityZeroThresholdZ(double component) {
        return this.legacyparkourcompat$filterVelocityComponent(component);
    }

    private double legacyparkourcompat$filterVelocityComponent(double component) {
        LivingEntity entity = (LivingEntity)(Object)this;
        return MovementRuntime.find(VelocityZeroThresholdHook.class, entity)
            .map(behavior -> behavior.filterComponent(entity, component))
            .orElse(component);
    }

    @Redirect(
        method = "handleRelativeFrictionAndCalculateMovement(Lnet/minecraft/world/phys/Vec3;F)Lnet/minecraft/world/phys/Vec3;",
        at = @At(
            value = "FIELD",
            target = "Lnet/minecraft/world/entity/LivingEntity;wasInPowderSnow:Z",
            opcode = Opcodes.GETFIELD
        )
    )
    private boolean legacyparkourcompat$currentPowderSnowState(LivingEntity entity) {
        boolean vanilla = entity.wasInPowderSnow;
        return MovementRuntime.find(PowderSnowClimbHook.class, entity)
            .map(behavior -> behavior.wasInPowderSnowForBoost(entity, MovementRuntime.profile(entity).target(), vanilla))
            .orElse(vanilla);
    }

    @Redirect(
        method = "travelInAir(Lnet/minecraft/world/phys/Vec3;)V",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/entity/LivingEntity;getBlockPosBelowThatAffectsMyMovement()Lnet/minecraft/core/BlockPos;"
        )
    )
    private BlockPos legacyparkourcompat$groundFrictionBlockPosition(LivingEntity entity) {
        VanillaFn<BlockPos> vanilla = () -> ((EntityInvoker)entity)
            .legacyparkourcompat$invokeGetBlockPosBelowThatAffectsMyMovement();
        return MovementRuntime.find(GroundFrictionBlockHook.class, entity)
            .map(behavior -> behavior.getBlockPosBelowThatAffectsMyMovement(entity, vanilla))
            .orElseGet(vanilla::get);
    }

    @ModifyArg(method = "jumpFromGround()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;addDeltaMovement(Lnet/minecraft/world/phys/Vec3;)V"), index = 0)
    private Vec3 legacyparkourcompat$sprintJumpImpulse(Vec3 vanilla) {
        LivingEntity entity = (LivingEntity)(Object)this;
        return MovementRuntime.find(SprintJumpImpulseHook.class, entity)
            .map(behavior -> behavior.impulse(entity, vanilla)).orElse(vanilla);
    }

    @Inject(method = "dismountVehicle(Lnet/minecraft/world/entity/Entity;)V", at = @At("HEAD"), cancellable = true)
    private void legacyparkourcompat$dismount(Entity vehicle, CallbackInfo ci) {
        LivingEntity passenger = (LivingEntity)(Object)this;
        if (!(passenger instanceof Player)) {
            return;
        }
        if (MovementRuntime.find(DismountPositionHook.class, passenger)
            .map(behavior -> behavior.dismount(passenger, vehicle)).orElse(false)) {
            ci.cancel();
            return;
        }
        if (!passenger.isRemoved() && !vehicle.isRemoved()
            && passenger.level().getBlockState(vehicle.blockPosition()).is(BlockTags.PORTALS)) {
            boolean handled = MovementRuntime.find(PortalDismountHook.class, passenger)
                .map(behavior -> behavior.dismountFromPortal(passenger, vehicle, MovementRuntime.profile(passenger).target()))
                .orElse(false);
            if (handled) {
                ci.cancel();
            }
        }
    }
}
