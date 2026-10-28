package me.wolfii.legacyparkourcompat.mixin;

import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.hook.player.JumpFromGroundAdmissionHook;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(LivingEntity.class)
abstract class LivingEntityJumpAdmissionMixin {
    /**
     * Mojmap 1.20.2 LivingEntity#jumpFromGround at 2011-2020 has no power
     * admission guard; Mojmap 1.20.5 LivingEntity#jumpFromGround at 2069-2081
     * adds {@code !(jumpPower <= 1.0E-5F)}. The target 26.2 unobfuscated
     * LivingEntity#jumpFromGround at 2389-2401 retains that predicate. Replacing
     * only its threshold with NaN makes the comparison false for every float,
     * including NaN, so the negated predicate admits every power as in 1.20.2.
     * The computed power and native response body remain untouched.
     */
    @ModifyConstant(method = "jumpFromGround", constant = @Constant(floatValue = 1.0E-5F))
    private float legacyparkourcompat$jumpAdmissionThreshold(float nativeThreshold) {
        LivingEntity entity = (LivingEntity) (Object) this;
        return MovementRuntime.find(JumpFromGroundAdmissionHook.class, entity)
            .map(hook -> hook.admissionThreshold(nativeThreshold))
            .orElse(nativeThreshold);
    }
}
