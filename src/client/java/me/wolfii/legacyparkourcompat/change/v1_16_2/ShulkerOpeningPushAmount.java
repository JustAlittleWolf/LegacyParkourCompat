package me.wolfii.legacyparkourcompat.change.v1_16_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.shulker.ShulkerOpeningPushAmountHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.shulker.ShulkerOpeningPushQueryHook;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.ShulkerBoxBlock;
import net.minecraft.world.level.block.entity.ShulkerBoxBlockEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.Shapes;

/**
 * In 1.11 Ornithe Feather mappings, ShulkerBoxBlockEntity#moveEntities queries the full lid slab from
 * #getLidShape and pushes by directional overlap plus 0.01. In 1.16.5 Mojang mappings,
 * #moveCollidedEntities and #getTopBoundingBox do the same. Both opening completion paths query and
 * push at the incremented progress before clamping it. In 1.17 Mojang mappings, #moveCollidedEntities
 * queries the unhalved progress delta slab and pushes by its axis size plus 0.01.
 */
@MovementChange(emulates = ParkourVersion.V1_16_2)
public final class ShulkerOpeningPushAmount implements ShulkerOpeningPushAmountHook, ShulkerOpeningPushQueryHook {
    private static final double PUSH_EPSILON = 0.01;

    public ShulkerOpeningPushAmount() {
    }

    @Override
    public AABB openingPushBounds(ShulkerBoxBlockEntity box) {
        Direction direction = box.getBlockState().getValue(ShulkerBoxBlock.FACING);
        return openingPushBounds(box, direction);
    }

    @Override
    public Vec3 pushAmount(AABB lid, Direction direction, Entity player) {
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

    private static AABB openingPushBounds(ShulkerBoxBlockEntity box, Direction direction) {
        float oldProgress = box.getProgress(0.0F);
        float newProgress = oldProgress + 0.1F;
        float lidLength = 0.5F * (oldProgress + (newProgress - oldProgress) * 1.0F);
        return Shapes.block().bounds()
            .expandTowards(lidLength * direction.getStepX(), lidLength * direction.getStepY(), lidLength * direction.getStepZ())
            .contract(-direction.getStepX(), -direction.getStepY(), -direction.getStepZ())
            .move(box.getBlockPos());
    }
}
