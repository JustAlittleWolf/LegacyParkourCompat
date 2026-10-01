package me.wolfii.legacyparkourcompat.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.hook.GravityBehavior;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Entity.class)
abstract class EntityGravityMixin {
    @ModifyReturnValue(method = "getGravity", at = @At("RETURN"))
    private double legacyparkourcompat$historicalPlayerGravity(double vanilla) {
        Entity entity = (Entity)(Object)this;
        return MovementRuntime.find(GravityBehavior.class, entity)
            .map(behavior -> behavior.gravity(entity, vanilla))
            .orElse(vanilla);
    }
}
