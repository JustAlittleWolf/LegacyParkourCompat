package me.wolfii.legacyparkourcompat.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.hook.EffectFallDistanceResetBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.JumpBehavior;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
abstract class LivingEntityBehaviorMixin {

    @WrapOperation(
        method = "aiStep",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;resetFallDistance()V")
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

    @Inject(method = "travelInAir", at = @At("HEAD"), remap = true)
    private void legacyparkourcompat$historicalLevitationReset(Vec3 input, CallbackInfo ci) {
        LivingEntity entity = (LivingEntity)(Object)this;
        MovementRuntime.find(EffectFallDistanceResetBehavior.class, entity)
            .ifPresent(behavior -> behavior.beforeAirTravel(entity));
    }

    @Inject(method = "jumpFromGround", at = @At("HEAD"), cancellable = true)
    private void legacyparkourcompat$historicalJumpImpulse(CallbackInfo ci) {
        LivingEntity entity = (LivingEntity)(Object)this;
        MovementRuntime.find(JumpBehavior.class, entity).ifPresent(behavior -> {
            behavior.jumpFromGround(entity, () -> {});
            ci.cancel();
        });
    }
}
