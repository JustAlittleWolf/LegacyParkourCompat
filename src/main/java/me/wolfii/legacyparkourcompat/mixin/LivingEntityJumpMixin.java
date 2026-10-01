package me.wolfii.legacyparkourcompat.mixin;

import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.hook.JumpBehavior;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(LivingEntity.class)
public abstract class LivingEntityJumpMixin {
    @ModifyArg(
        method = "jumpFromGround()V",
        at = @At(value = "INVOKE", target = "Ljava/lang/Math;max(DD)D"),
        index = 0
    )
    private double legacyparkour$jumpPower(double vanilla) {
        LivingEntity entity = (LivingEntity) (Object) this;
        return MovementRuntime.find(JumpBehavior.class, entity)
            .map(behavior -> behavior.jumpPower(entity, vanilla))
            .orElse(vanilla);
    }
}
