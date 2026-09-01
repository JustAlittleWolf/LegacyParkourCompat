package me.wolfii.legacyparkourcompat.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.hook.*;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
    @Unique
    private boolean lpc$vanillaJump;

    @WrapOperation(
        method = "travel",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/entity/LivingEntity;travelInAir(Lnet/minecraft/world/phys/Vec3;)V"
        )
    )
    private void lpc$travelInAir(LivingEntity instance, Vec3 input, Operation<Void> original) {
        if (!(instance instanceof Player player) || !MovementRuntime.appliesTo(player)) {
            original.call(instance, input);
            return;
        }
        MovementRuntime.find(AirTravelBehavior.class, player)
            .ifPresentOrElse(
                behavior -> behavior.travelInAir(player, input, () -> original.call(instance, input)),
                () -> original.call(instance, input)
            );
    }

    @WrapOperation(
        method = "travel",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/entity/LivingEntity;travelInFluid(Lnet/minecraft/world/phys/Vec3;)V"
        )
    )
    private void lpc$travelInFluid(LivingEntity instance, Vec3 input, Operation<Void> original) {
        if (!(instance instanceof Player player) || !MovementRuntime.appliesTo(player)) {
            original.call(instance, input);
            return;
        }
        MovementRuntime.find(FluidTravelBehavior.class, player)
            .ifPresentOrElse(
                behavior -> behavior.travelInFluid(player, input, () -> original.call(instance, input)),
                () -> original.call(instance, input)
            );
    }

    @WrapOperation(
        method = "travel",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/entity/LivingEntity;travelFallFlying(Lnet/minecraft/world/phys/Vec3;)V"
        )
    )
    private void lpc$travelFallFlying(LivingEntity instance, Vec3 input, Operation<Void> original) {
        if (!(instance instanceof Player player) || !MovementRuntime.appliesTo(player)) {
            original.call(instance, input);
            return;
        }
        MovementRuntime.find(ElytraTravelBehavior.class, player)
            .ifPresentOrElse(
                behavior -> behavior.travelFallFlying(player, input, () -> original.call(instance, input)),
                () -> original.call(instance, input)
            );
    }

    @Inject(method = "jumpFromGround", at = @At("HEAD"), cancellable = true)
    private void lpc$jump(CallbackInfo ci) {
        if (this.lpc$vanillaJump) {
            return;
        }
        LivingEntity self = (LivingEntity) (Object) this;
        if (!MovementRuntime.appliesTo(self)) {
            return;
        }
        MovementRuntime.find(JumpBehavior.class, self).ifPresent(behavior -> {
            behavior.jumpFromGround(self, () -> {
                this.lpc$vanillaJump = true;
                try {
                    self.jumpFromGround();
                } finally {
                    this.lpc$vanillaJump = false;
                }
            });
            ci.cancel();
        });
    }

    @ModifyReturnValue(
        method = "getDimensions(Lnet/minecraft/world/entity/Pose;)Lnet/minecraft/world/entity/EntityDimensions;",
        at = @At("RETURN")
    )
    private EntityDimensions lpc$dimensions(EntityDimensions vanilla, Pose pose) {
        LivingEntity self = (LivingEntity) (Object) this;
        if (!(self instanceof Player player) || !MovementRuntime.appliesTo(player)) {
            return vanilla;
        }
        return MovementRuntime.find(PlayerDimensionsBehavior.class, player)
            .map(behavior -> behavior.dimensions(player, pose, vanilla))
            .orElse(vanilla);
    }

    @ModifyReturnValue(method = "getJumpPower()F", at = @At("RETURN"))
    private float lpc$jumpPower(float vanilla) {
        LivingEntity self = (LivingEntity) (Object) this;
        if (!MovementRuntime.appliesTo(self)) {
            return vanilla;
        }
        return MovementRuntime.find(JumpBehavior.class, self)
            .map(behavior -> behavior.jumpPower(self, vanilla))
            .orElse(vanilla);
    }

    @ModifyReturnValue(method = "onClimbable", at = @At("RETURN"))
    private boolean lpc$onClimbable(boolean vanilla) {
        LivingEntity self = (LivingEntity) (Object) this;
        if (!MovementRuntime.appliesTo(self)) {
            return vanilla;
        }
        return MovementRuntime.find(ClimbingBehavior.class, self)
            .map(behavior -> behavior.onClimbable(self, vanilla))
            .orElse(vanilla);
    }

    @WrapOperation(
        method = "handleRelativeFrictionAndCalculateMovement",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/entity/LivingEntity;handleOnClimbable(Lnet/minecraft/world/phys/Vec3;)Lnet/minecraft/world/phys/Vec3;"
        )
    )
    private Vec3 lpc$handleOnClimbable(LivingEntity instance, Vec3 delta, Operation<Vec3> original) {
        if (!MovementRuntime.appliesTo(instance)) {
            return original.call(instance, delta);
        }
        return MovementRuntime.find(ClimbingBehavior.class, instance)
            .map(behavior -> behavior.handleOnClimbable(instance, delta, () -> original.call(instance, delta)))
            .orElseGet(() -> original.call(instance, delta));
    }

    @WrapOperation(
        method = "travelInAir",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/entity/LivingEntity;handleRelativeFrictionAndCalculateMovement(Lnet/minecraft/world/phys/Vec3;F)Lnet/minecraft/world/phys/Vec3;"
        )
    )
    private Vec3 lpc$frictionMovement(LivingEntity instance, Vec3 input, float friction, Operation<Vec3> original) {
        if (!MovementRuntime.appliesTo(instance)) {
            return original.call(instance, input, friction);
        }
        return MovementRuntime.find(FrictionMovementBehavior.class, instance)
            .map(behavior -> behavior.handleRelativeFrictionAndCalculateMovement(
                instance,
                input,
                friction,
                () -> original.call(instance, input, friction)
            ))
            .orElseGet(() -> original.call(instance, input, friction));
    }

    @WrapOperation(
        method = "aiStep",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/entity/Entity;setDeltaMovement(DDD)V"
        )
    )
    private void lpc$negligibleSpeed(Entity instance, double x, double y, double z, Operation<Void> original) {
        if (!MovementRuntime.appliesTo(instance)) {
            original.call(instance, x, y, z);
            return;
        }
        MovementRuntime.find(NegligibleSpeedBehavior.class, instance)
            .ifPresentOrElse(
                behavior -> {
                    Vec3 adjusted = behavior.apply(instance, instance.getDeltaMovement());
                    original.call(instance, adjusted.x, adjusted.y, adjusted.z);
                },
                () -> original.call(instance, x, y, z)
            );
    }
}
