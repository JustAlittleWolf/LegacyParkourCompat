package me.wolfii.legacyparkourcompat.mixin;

import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.hook.WaterJumpBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.WaterTravelBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.PlayerDimensionsBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.GroundSpeedBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.FallFlyingLookBehavior;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
abstract class LivingEntityMixin {
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
