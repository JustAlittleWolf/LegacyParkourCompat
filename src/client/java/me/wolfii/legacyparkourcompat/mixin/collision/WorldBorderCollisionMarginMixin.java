package me.wolfii.legacyparkourcompat.mixin.collision;

import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.hook.collision.WorldBorderCollisionMarginHook;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(WorldBorder.class)
public abstract class WorldBorderCollisionMarginMixin {
    @Redirect(
        method = "isInsideCloseToBorder(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/phys/AABB;)Z",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/border/WorldBorder;isWithinBounds(DDD)Z")
    )
    private boolean legacyParkourCompat$collisionMargin(
        WorldBorder border, double x, double z, double margin, Entity source, AABB boundingBox
    ) {
        return MovementRuntime.find(WorldBorderCollisionMarginHook.class, source)
            .map(hook -> hook.isWithinCollisionMargin(border, x, z, margin))
            .orElseGet(() -> border.isWithinBounds(x, z, margin));
    }
}
