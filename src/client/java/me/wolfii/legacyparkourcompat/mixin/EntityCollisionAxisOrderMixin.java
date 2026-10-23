package me.wolfii.legacyparkourcompat.mixin;

import com.google.common.collect.ImmutableList;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.hook.collision.CollisionAxisOrderHook;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Entity.class)
abstract class EntityCollisionAxisOrderMixin {
    @Redirect(
        method = "collideWithShapes",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/core/Direction;axisStepOrder(Lnet/minecraft/world/phys/Vec3;)Lcom/google/common/collect/ImmutableList;"
        )
    )
    private static ImmutableList<Direction.Axis> legacyparkourcompat$axisStepOrder(Vec3 movement) {
        CollisionAxisOrderHook hook = MovementRuntime.find(
            CollisionAxisOrderHook.class,
            CollisionAxisOrderContext.currentEntity()
        ).orElse(null);
        if (hook == null) {
            return Direction.axisStepOrder(movement);
        }

        Direction.Axis firstHorizontal = hook.zBeforeX(movement.x, movement.z)
            ? Direction.Axis.Z
            : Direction.Axis.X;
        Direction.Axis secondHorizontal = firstHorizontal == Direction.Axis.X
            ? Direction.Axis.Z
            : Direction.Axis.X;
        return ImmutableList.of(Direction.Axis.Y, firstHorizontal, secondHorizontal);
    }

    @WrapOperation(
        method = "collideWithShapes(Lnet/minecraft/world/phys/Vec3;Lnet/minecraft/world/phys/AABB;Ljava/util/List;)Lnet/minecraft/world/phys/Vec3;",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/world/phys/shapes/Shapes;collide(Lnet/minecraft/core/Direction$Axis;Lnet/minecraft/world/phys/AABB;Ljava/lang/Iterable;D)D"))
    private static double legacyparkourcompat$axisClipping(Direction.Axis axis,
        AABB moving, Iterable<VoxelShape> shapes,
        double distance, Operation<Double> original) {
        return MovementRuntime.clipMovementAxis(CollisionAxisOrderContext.currentEntity(), axis,
            moving, shapes, distance, () -> original.call(axis, moving, shapes, distance));
    }
}
