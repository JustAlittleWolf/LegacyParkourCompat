package me.wolfii.legacyparkourcompat.mixin;

import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.hook.entity.ResolvedMovementCutoffHook;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.MoverType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(Entity.class)
abstract class EntityMovementCutoffMixin {
    @ModifyConstant(
        method = "move",
        constant = @Constant(doubleValue = 1.0E-7, ordinal = 1)
    )
    private double legacyparkourcompat$resolvedMovementCutoff(double vanillaCutoff, MoverType moverType) {
        Entity entity = (Entity) (Object) this;
        return MovementRuntime.find(ResolvedMovementCutoffHook.class, entity)
            .map(hook -> hook.squaredCutoff(moverType, vanillaCutoff))
            .orElse(vanillaCutoff);
    }
}
