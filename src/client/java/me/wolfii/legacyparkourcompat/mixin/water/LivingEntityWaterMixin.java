package me.wolfii.legacyparkourcompat.mixin.water;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.hook.water.WaterJumpSelectionHook;
import me.wolfii.legacyparkourcompat.mechanic.water.WaterContext;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LivingEntity.class)
public abstract class LivingEntityWaterMixin {
    @ModifyExpressionValue(method = "aiStep", at = @At(value = "INVOKE",
        target = "Lnet/minecraft/world/entity/LivingEntity;getFluidHeight(Lnet/minecraft/tags/TagKey;)D"))
    private double legacyparkourcompat$jumpDepth(double nativeDepth) {
        LivingEntity entity = (LivingEntity)(Object)this;
        if (!((WaterContext)entity).legacyparkourcompat$hasSupportedWaterContext()) return nativeDepth;
        return MovementRuntime.find(WaterJumpSelectionHook.class, entity)
            .map(hook -> hook.selectionDepth(entity.isInWater(), nativeDepth)).orElse(nativeDepth);
    }
}
