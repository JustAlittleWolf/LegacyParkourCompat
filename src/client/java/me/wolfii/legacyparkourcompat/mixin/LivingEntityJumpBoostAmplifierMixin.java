package me.wolfii.legacyparkourcompat.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.hook.jump.JumpBoostAmplifierHook;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/**
 * Mojmap 1.20.2–1.20.6 and 26.2 unobfuscated LivingEntity#getJumpBoostPower
 * reads MobEffectInstance#getAmplifier to form the effect contribution. Wraps
 * only that read so the native float formula and its operation order remain intact.
 */
@Mixin(LivingEntity.class)
abstract class LivingEntityJumpBoostAmplifierMixin {
    @WrapOperation(
        method = "getJumpBoostPower()F",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/effect/MobEffectInstance;getAmplifier()I"
        )
    )
    private int legacyparkourcompat$interpretJumpBoostAmplifier(
        MobEffectInstance effect,
        Operation<Integer> original
    ) {
        int nativeAmplifier = original.call(effect);
        LivingEntity entity = (LivingEntity) (Object) this;
        return MovementRuntime.find(JumpBoostAmplifierHook.class, entity)
            .map(hook -> hook.interpretAmplifier(nativeAmplifier))
            .orElse(nativeAmplifier);
    }
}
