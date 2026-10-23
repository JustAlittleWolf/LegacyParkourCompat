package me.wolfii.legacyparkourcompat.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.CollisionBorderShapeContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import java.util.List;

@Mixin(Entity.class)
public abstract class EntityEmptyShapeMovementMixin {
    @WrapMethod(method = "collideBoundingBox(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/phys/Vec3;Lnet/minecraft/world/phys/AABB;Lnet/minecraft/world/level/Level;Ljava/util/List;)Lnet/minecraft/world/phys/Vec3;")
    private static Vec3 legacyparkourcompat$borderShapeFrame(Entity entity, Vec3 movement, AABB boundingBox,
        Level level, List<VoxelShape> entityColliders, Operation<Vec3> original) {
        return CollisionBorderShapeContext.withFrame(entity,
            () -> original.call(entity, movement, boundingBox, level, entityColliders));
    }

    @WrapOperation(
        method = "collideBoundingBox(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/phys/Vec3;Lnet/minecraft/world/phys/AABB;Lnet/minecraft/world/level/Level;Ljava/util/List;)Lnet/minecraft/world/phys/Vec3;",
        at = @At(value = "INVOKE",
            target = "Lnet/minecraft/world/entity/Entity;collideWithShapes(Lnet/minecraft/world/phys/Vec3;Lnet/minecraft/world/phys/AABB;Ljava/util/List;)Lnet/minecraft/world/phys/Vec3;"))
    private static Vec3 legacyparkourcompat$resolveEmptyShapes(Vec3 movement, AABB boundingBox,
        List<VoxelShape> shapes, Operation<Vec3> original, @Local(argsOnly = true) Entity entity) {
        return MovementRuntime.resolveEmptyShapeMovement(entity, movement, shapes,
            () -> original.call(movement, boundingBox, shapes));
    }
}
