package me.wolfii.legacyparkourcompat.change.v1_16_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.shulker.ShulkerOpeningPushAmountHook;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.ShulkerBoxBlock;
import net.minecraft.world.level.block.entity.ShulkerBoxBlockEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

/**
 * Restores the 1.16.5 opening lid-slab overlap amount while retaining native progress, query admission,
 * and movement. In 1.16.5 Mojang mappings, ShulkerBoxBlockEntity#getTopBoundingBox contracts the
 * full ShulkerBoxBlockEntity#getBoundingBox result to the moving lid slab; #moveCollidedEntities
 * pushes by its directional overlap plus 0.01. In 1.17 Mojang mappings, #moveCollidedEntities instead
 * uses Shulker#getProgressDeltaAabb's unhalved axis size plus 0.01. Only this local-player amount is
 * replaced; the current native sweep and all non-player responses remain unchanged.
 */
@MovementChange(emulates = ParkourVersion.V1_16_2)
public final class ShulkerOpeningPushAmount implements ShulkerOpeningPushAmountHook {
    private static final double PUSH_EPSILON = 0.01;

    public ShulkerOpeningPushAmount() {
    }

    @Override
    public Vec3 pushAmount(ShulkerBoxBlockEntity box, Entity player, Vec3 nativeAmount) {
        Direction direction = box.getBlockState().getValue(ShulkerBoxBlock.FACING);
        AABB lid = box.getBoundingBox(box.getBlockState())
            .contract(-direction.getStepX(), -direction.getStepY(), -direction.getStepZ())
            .move(box.getBlockPos());
        AABB target = player.getBoundingBox();
        double amount = switch (direction.getAxis()) {
            case X -> direction.getAxisDirection() == Direction.AxisDirection.POSITIVE
                ? lid.maxX - target.minX + PUSH_EPSILON
                : target.maxX - lid.minX + PUSH_EPSILON;
            case Y -> direction.getAxisDirection() == Direction.AxisDirection.POSITIVE
                ? lid.maxY - target.minY + PUSH_EPSILON
                : target.maxY - lid.minY + PUSH_EPSILON;
            case Z -> direction.getAxisDirection() == Direction.AxisDirection.POSITIVE
                ? lid.maxZ - target.minZ + PUSH_EPSILON
                : target.maxZ - lid.minZ + PUSH_EPSILON;
        };
        return new Vec3(
            amount * direction.getStepX(),
            amount * direction.getStepY(),
            amount * direction.getStepZ()
        );
    }
}
