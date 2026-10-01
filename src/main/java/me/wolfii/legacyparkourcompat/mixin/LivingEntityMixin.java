package me.wolfii.legacyparkourcompat.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.VanillaFn;
import me.wolfii.legacyparkourcompat.mechanic.hook.DismountPositionBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.EffectFallDistanceResetBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.ElytraLiftForceBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.FallFlyingLookBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.FluidJumpBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.GlideFallDistanceBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.GroundSpeedBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.JumpBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.JumpVerticalVelocityBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.SprintJumpImpulseBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.LavaTravelBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.MovementChunkLookupBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.PlayerDimensionsBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.PortalDismountBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.PowderSnowClimbBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.SupportingBlockBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.WaterJumpBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.WaterTravelBehavior;
import me.wolfii.legacyparkourcompat.mixin.accessor.EntityInvoker;
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
        method = "aiStep",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;resetFallDistance()V")
    )
    private void legacyparkourcompat$effectFallDistanceReset(LivingEntity entity, Operation<Void> vanilla) {
        MovementRuntime.find(EffectFallDistanceResetBehavior.class, entity)
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
        MovementRuntime.find(EffectFallDistanceResetBehavior.class, entity)
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
        MovementRuntime.find(EffectFallDistanceResetBehavior.class, entity)
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
        return MovementRuntime.find(ElytraLiftForceBehavior.class, player)
            .filter(change -> change.appliesToVersion(MovementRuntime.profile(player).target()))
            .map(behavior -> behavior.liftForce(player,
                MovementRuntime.find(FallFlyingLookBehavior.class, player)
            .filter(change -> change.appliesToVersion(MovementRuntime.profile(player).target()))
                    .map(look -> look.lookVector(player, player.getLookAngle())).orElseGet(player::getLookAngle), vanilla))
            .orElse(vanilla);
    }


    @Redirect(
        method = "aiStep",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;isAffectedByFluids()Z")
    )
    private boolean legacyparkourcompat$fluidJumpEligibility(LivingEntity entity) {
        boolean vanilla = entity.isAffectedByFluids();
        if (entity instanceof Player player) {
            return MovementRuntime.find(FluidJumpBehavior.class, player)
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
            return MovementRuntime.find(FluidJumpBehavior.class, player)
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
            return MovementRuntime.find(LavaTravelBehavior.class, player)
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
    private boolean legacyParkourCompat$hasMovementChunk(Level level, BlockPos position) {
        LivingEntity entity = (LivingEntity) (Object) this;
        var behavior = MovementRuntime.find(MovementChunkLookupBehavior.class, entity);
        if (behavior.isEmpty() || !(entity instanceof net.minecraft.world.entity.player.Player)) {
            return level.hasChunkAt(position);
        }
        return behavior.orElseThrow().hasChunkAt(entity, level, position, () -> level.hasChunkAt(position));
    }




    @Shadow
    protected boolean jumping;

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
            cir.setReturnValue(MovementRuntime.find(PlayerDimensionsBehavior.class, player)
                .map(behavior -> behavior.dimensions(player, pose, cir.getReturnValue()))
                .orElse(cir.getReturnValue()));
        }
    }

    @Inject(method = "getFrictionInfluencedSpeed", at = @At("RETURN"), cancellable = true)
    private void legacyparkourcompat$groundAcceleration(float blockFriction, CallbackInfoReturnable<Float> cir) {
        LivingEntity entity = (LivingEntity)(Object)this;
        if (entity instanceof Player player) {
            cir.setReturnValue(MovementRuntime.find(GroundSpeedBehavior.class, player)
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
        if (entity instanceof LivingEntity livingEntity) {
            return MovementRuntime.find(FallFlyingLookBehavior.class, livingEntity)
            .filter(change -> change.appliesToVersion(MovementRuntime.profile(livingEntity).target()))
                .map(behavior -> behavior.lookVector(livingEntity, vanilla))
                .orElse(vanilla);
        }
        return vanilla;
    }

    @Inject(
        method = "aiStep",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;applyInput()V", shift = org.spongepowered.asm.mixin.injection.At.Shift.AFTER)
    )
    private void legacyparkourcompat$waterJumpInput(CallbackInfo ci) {
        LivingEntity entity = (LivingEntity)(Object)this;
        if (this.jumping && !this.isImmobile() && entity instanceof Player player && player.isInWater()) {
            MovementRuntime.find(WaterJumpBehavior.class, player)
                .ifPresent(behavior -> behavior.jumpInWater(player));
        }
    }

    @Redirect(
        method = "aiStep",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;jumpFromGround()V")
    )
    private void legacyparkourcompat$replaceShallowWaterJump(LivingEntity entity) {
        if (entity instanceof Player player && player.isInWater()
            && MovementRuntime.find(WaterJumpBehavior.class, player).isPresent()) {
            return;
        }
        this.legacyparkourcompat$invokeJumpFromGround();
    }

    @Redirect(
        method = "aiStep",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;jumpInLiquid(Lnet/minecraft/tags/TagKey;)V")
    )
    private void legacyparkourcompat$replaceLiquidJump(LivingEntity entity, TagKey<Fluid> type) {
        if (type == FluidTags.WATER && entity instanceof Player player && player.isInWater()
            && MovementRuntime.find(WaterJumpBehavior.class, player).isPresent()) {
            return;
        }
        this.legacyparkourcompat$invokeJumpInLiquid(type);
    }

    @ModifyConstant(method = "travelInWater", constant = @Constant(floatValue = 0.9F))
    private float legacyparkourcompat$waterSprintSlowdown(float vanilla) {
        LivingEntity entity = (LivingEntity)(Object)this;
        if (entity instanceof Player player) {
            return MovementRuntime.find(WaterTravelBehavior.class, player)
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
        Vec3 vanilla = entity.getFluidFallingAdjustedMovement(baseGravity, falling, movement);
        if (entity instanceof Player player) {
            return MovementRuntime.find(WaterTravelBehavior.class, player)
                .map(behavior -> behavior.gravityAdjustedMovement(player, baseGravity, falling, movement, vanilla))
                .orElse(vanilla);
        }
        return vanilla;
    }



    @Inject(method = "travelFallFlying(Lnet/minecraft/world/phys/Vec3;)V", at = @At("HEAD"))
    private void legacyparkourcompat$fallFlyingFallDistance(Vec3 input, CallbackInfo ci) {
        if ((Object) this instanceof Player player) {
            MovementRuntime.find(GlideFallDistanceBehavior.class, player)
            .filter(change -> change.appliesToVersion(MovementRuntime.profile(player).target()))
                .ifPresent(behavior -> behavior.beforeFallFlyingTravel(player));
        }
    }



    @Redirect(
        method = "jumpFromGround()V",
        at = @At(value = "INVOKE", target = "Ljava/lang/Math;max(DD)D")
    )
    private double legacy$jumpVerticalVelocity(double jumpPower, double currentVelocity) {
        LivingEntity entity = (LivingEntity) (Object) this;
        double power = MovementRuntime.find(JumpBehavior.class, entity)
            .map(behavior -> behavior.jumpPower(entity, jumpPower)).orElse(jumpPower);
        double vanilla = Math.max(power, currentVelocity);
        return MovementRuntime.find(JumpVerticalVelocityBehavior.class, entity)
            .map(behavior -> behavior.jumpVerticalVelocity(entity, power, currentVelocity, vanilla))
            .orElse(vanilla);
    }

    @Redirect(
        method = "handleRelativeFrictionAndCalculateMovement(Lnet/minecraft/world/phys/Vec3;F)Lnet/minecraft/world/phys/Vec3;",
        at = @At(
            value = "FIELD",
            target = "Lnet/minecraft/world/entity/LivingEntity;wasInPowderSnow:Z",
            opcode = Opcodes.GETFIELD
        )
    )
    private boolean legacy$currentPowderSnowState(LivingEntity entity) {
        boolean vanilla = entity.wasInPowderSnow;
        return MovementRuntime.find(PowderSnowClimbBehavior.class, entity)
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
        return MovementRuntime.find(SupportingBlockBehavior.class, entity)
            .map(behavior -> behavior.getBlockPosBelowThatAffectsMyMovement(entity, vanilla))
            .orElseGet(vanilla::get);
    }

    @ModifyArg(method = "jumpFromGround()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;addDeltaMovement(Lnet/minecraft/world/phys/Vec3;)V"), index = 0)
    private Vec3 legacyparkourcompat$sprintJumpImpulse(Vec3 vanilla) {
        LivingEntity entity = (LivingEntity)(Object)this;
        return MovementRuntime.find(SprintJumpImpulseBehavior.class, entity)
            .map(behavior -> behavior.impulse(entity, vanilla)).orElse(vanilla);
    }
    @Inject(method = "dismountVehicle(Lnet/minecraft/world/entity/Entity;)V", at = @At("HEAD"), cancellable = true)
    private void legacyparkourcompat$dismount(Entity vehicle, CallbackInfo ci) {
        LivingEntity passenger = (LivingEntity)(Object)this;
        if (!(passenger instanceof Player)) return;
        if (MovementRuntime.find(DismountPositionBehavior.class, passenger)
            .map(behavior -> behavior.dismount(passenger, vehicle)).orElse(false)) {
            ci.cancel();
            return;
        }
        if (!passenger.isRemoved() && !vehicle.isRemoved()
            && passenger.level().getBlockState(vehicle.blockPosition()).is(BlockTags.PORTALS)) {
            MovementRuntime.find(PortalDismountBehavior.class, passenger).ifPresent(behavior -> {
                behavior.dismountFromPortal(passenger, vehicle);
                ci.cancel();
            });
        }
    }
}
