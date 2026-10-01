package me.wolfii.legacyparkourcompat.mixin;

import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.hook.JumpBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.PowderSnowClimbBehavior;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.objectweb.asm.Opcodes;

@Mixin(LivingEntity.class)
abstract class LivingEntityMovementMixin {
    @Redirect(
        method = "jumpFromGround()V",
        at = @At(value = "INVOKE", target = "Ljava/lang/Math;max(DD)D")
    )
    private double legacy$jumpVerticalVelocity(double jumpPower, double currentVelocity) {
        LivingEntity entity = (LivingEntity) (Object) this;
        double vanilla = Math.max(jumpPower, currentVelocity);
        return MovementRuntime.find(JumpBehavior.class, entity)
            .map(behavior -> behavior.jumpVerticalVelocity(entity, jumpPower, currentVelocity, vanilla))
            .orElse(vanilla);
    }

    @Redirect(
        method = "handleRelativeFrictionAndCalculateMovement(Lnet/minecraft/world/phys/Vec3;F)Lnet/minecraft/world/phys/Vec3;",
        at = @At(
            value = "FIELD",
            target = "Lnet/minecraft/world/entity/Entity;wasInPowderSnow:Z",
            opcode = Opcodes.GETFIELD
        )
    )
    private boolean legacy$currentPowderSnowState(Entity entity) {
        boolean vanilla = entity.wasInPowderSnow;
        if (!(entity instanceof LivingEntity livingEntity)) {
            return vanilla;
        }
        return MovementRuntime.find(PowderSnowClimbBehavior.class, livingEntity)
            .map(behavior -> behavior.wasInPowderSnowForBoost(livingEntity, vanilla))
            .orElse(vanilla);
    }
}
