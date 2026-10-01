package me.wolfii.legacyparkourcompat.mixin;

import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.hook.DismountPositionBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.FallFlyingLookAngleBehavior;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
abstract class LivingEntityMixin {
    @Inject(
        method = "dismountVehicle(Lnet/minecraft/world/entity/Entity;)V",
        at = @At("HEAD"),
        cancellable = true
    )
    private void legacyparkourcompat$historicalDismountPosition(Entity vehicle, CallbackInfo callback) {
        LivingEntity passenger = (LivingEntity)(Object)this;
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
        return MovementRuntime.find(FallFlyingLookAngleBehavior.class, entity)
            .map(behavior -> behavior.lookAngle(entity))
            .orElseGet(entity::getLookAngle);
    }
}
