package me.wolfii.legacyparkourcompat.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.VanillaFn;
import me.wolfii.legacyparkourcompat.mechanic.hook.player.SprintJumpTrigLookupHook;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/** Routes only the sine and cosine lookups in the ground-jump sprint impulse. */
@Mixin(LivingEntity.class)
abstract class LivingEntitySprintJumpTrigMixin {
    @WrapOperation(
        method = "jumpFromGround()V",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/util/Mth;sin(D)F")
    )
    private float legacyparkourcompat$sprintJumpSine(
        double radians,
        Operation<Float> original
    ) {
        LivingEntity entity = (LivingEntity) (Object) this;
        VanillaFn<Float> vanilla = () -> original.call(radians);
        return MovementRuntime.find(SprintJumpTrigLookupHook.class, entity)
            .map(hook -> hook.lookup(radians, false, vanilla))
            .orElseGet(vanilla::get);
    }

    @WrapOperation(
        method = "jumpFromGround()V",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/util/Mth;cos(D)F")
    )
    private float legacyparkourcompat$sprintJumpCosine(
        double radians,
        Operation<Float> original
    ) {
        LivingEntity entity = (LivingEntity) (Object) this;
        VanillaFn<Float> vanilla = () -> original.call(radians);
        return MovementRuntime.find(SprintJumpTrigLookupHook.class, entity)
            .map(hook -> hook.lookup(radians, true, vanilla))
            .orElseGet(vanilla::get);
    }
}
