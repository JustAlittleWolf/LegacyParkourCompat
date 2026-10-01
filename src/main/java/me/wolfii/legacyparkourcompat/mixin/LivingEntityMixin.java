package me.wolfii.legacyparkourcompat.mixin;


@Mixin(LivingEntity.class)

import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.hook.DismountPositionBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.FallFlyingLookAngleBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.FallFlyingLookBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.GroundSpeedBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.MovementChunkLookupBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.PlayerDimensionsBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.WaterJumpBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.WaterTravelBehavior;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

abstract class LivingEntityMixin {
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


    @Inject(
        method = "dismountVehicle(Lnet/minecraft/world/entity/Entity;)V",
        at = @At("HEAD"),
        cancellable = true
    )
    private void legacyparkourcompat$historicalDismountPosition(Entity vehicle, CallbackInfo callback) {
        LivingEntity passenger = (LivingEntity)(Object)this;
        if (!(passenger instanceof Player)) {
            return;
        }
        MovementRuntime.find(DismountPositionBehavior.class, passenger)
            .ifPresent(behavior -> {
                if (behavior.dismount(passenger, vehicle)) {
                    callback.cancel();
                }
            });
    }

    @Redirect(
        method = "updateFallFlyingMovement(Lnet/minecraft/world/phys/Vec3;)Lnet/minecraft/world/phys/Vec3;",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/entity/LivingEntity;getLookAngle()Lnet/minecraft/world/phys/Vec3;"
        )
    )
    private static Vec3 legacyparkourcompat$fallFlyingLookAngle(LivingEntity entity) {
        if (!(entity instanceof Player)) {
            return entity.getLookAngle();
        }
        return MovementRuntime.find(FallFlyingLookAngleBehavior.class, entity)
            .map(behavior -> behavior.lookAngle(entity))
            .orElseGet(entity::getLookAngle);
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
        ((LivingEntityMixin)(Object)entity).legacyparkourcompat$invokeJumpFromGround();
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
        ((LivingEntityMixin)(Object)entity).legacyparkourcompat$invokeJumpInLiquid(type);
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

}
