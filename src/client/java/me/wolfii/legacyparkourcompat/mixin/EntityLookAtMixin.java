package me.wolfii.legacyparkourcompat.mixin;

import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.hook.entity.LookAtHorizontalLengthHook;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Entity.class)
abstract class EntityLookAtMixin {
    @Redirect(
        method = "lookAt",
        at = @At(value = "INVOKE", target = "Ljava/lang/Math;sqrt(D)D")
    )
    private double legacyparkourcompat$historicalLookAtHorizontalLength(double squaredHorizontalDistance) {
        Entity entity = (Entity) (Object) this;
        if (Minecraft.getInstance().player == entity) {
            return MovementRuntime.find(LookAtHorizontalLengthHook.class, entity)
                .map(hook -> hook.horizontalLength(squaredHorizontalDistance))
                .orElseGet(() -> Math.sqrt(squaredHorizontalDistance));
        }
        return Math.sqrt(squaredHorizontalDistance);
    }
}
