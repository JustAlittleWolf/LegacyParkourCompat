package me.wolfii.legacyparkourcompat.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.hook.entity.SweptShapeIntersectionHook;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.List;

@Mixin(Entity.class)
abstract class EntitySweptShapeIntersectionMixin {
    @WrapOperation(
        method = "collidedWithShapeMovingFrom(Lnet/minecraft/world/phys/Vec3;Lnet/minecraft/world/phys/Vec3;Ljava/util/List;)Z",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/world/phys/AABB;collidedAlongVector(Lnet/minecraft/world/phys/Vec3;Ljava/util/List;)Z")
    )
    private boolean legacyparkourcompat$sweptShapeIntersection(
        AABB movingBox, Vec3 movement, List<AABB> shapeParts, Operation<Boolean> original
    ) {
        Entity entity = (Entity) (Object) this;
        return MovementRuntime.find(SweptShapeIntersectionHook.class, entity)
            .flatMap(hook -> hook.collidedAlongVector(movingBox, movement, shapeParts))
            .orElseGet(() -> original.call(movingBox, movement, shapeParts));
    }
}
