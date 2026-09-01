package me.wolfii.legacyparkourcompat.api.access;

import me.wolfii.legacyparkourcompat.mixin.access.EntityInvoker;
import me.wolfii.legacyparkourcompat.mixin.access.LivingEntityInvoker;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.List;

/**
 * Vanilla private movement helpers, exposed so change authors do not write mixins.
 */
public final class VanillaMovement {
    private VanillaMovement() {
    }

    public static Vec3 collide(Entity entity, Vec3 movement) {
        return ((EntityInvoker) entity).lpc$collide(movement);
    }

    public static Vec3 collideWithShapes(Vec3 movement, AABB boundingBox, List<VoxelShape> shapes) {
        return EntityInvoker.lpc$collideWithShapes(movement, boundingBox, shapes);
    }

    public static BlockPos getOnPos(Entity entity, float offset) {
        return ((EntityInvoker) entity).lpc$getOnPos(offset);
    }

    public static Vec3 handleOnClimbable(LivingEntity entity, Vec3 delta) {
        return ((LivingEntityInvoker) entity).lpc$handleOnClimbable(delta);
    }

    public static Vec3 handleRelativeFrictionAndCalculateMovement(LivingEntity entity, Vec3 input, float friction) {
        return ((LivingEntityInvoker) entity).lpc$handleRelativeFrictionAndCalculateMovement(input, friction);
    }

    public static void travelInAir(LivingEntity entity, Vec3 input) {
        ((LivingEntityInvoker) entity).lpc$travelInAir(input);
    }

    public static void travelInFluid(LivingEntity entity, Vec3 input) {
        ((LivingEntityInvoker) entity).lpc$travelInFluid(input);
    }

    public static void travelFallFlying(LivingEntity entity, Vec3 input) {
        ((LivingEntityInvoker) entity).lpc$travelFallFlying(input);
    }

    public static float getJumpPower(LivingEntity entity) {
        return ((LivingEntityInvoker) entity).lpc$getJumpPower();
    }
}
