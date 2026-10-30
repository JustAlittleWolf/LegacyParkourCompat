package me.wolfii.legacyparkourcompat.change.v1_10_1.piston;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.piston.PistonStairPushHook;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.piston.PistonMovingBlockEntity;
import net.minecraft.world.phys.AABB;

import java.util.OptionalDouble;

/** Restores the historical directional stair clearance used by piston pushes. */
@MovementChange(emulates = ParkourVersion.V1_10_1)
public final class CobblestoneStairPush implements PistonStairPushHook {
    @Override
    public String variant() {
        return "minecraft:cobblestone_stairs";
    }

    @Override
    public OptionalDouble requestedDistance(PistonMovingBlockEntity mover, BlockPos pos, float newProgress,
                                             Entity recipient, Direction movementDirection) {
        if (!mover.isExtending()) {
            return OptionalDouble.empty();
        }

        // 1.9 Ornithe Feather MovingBlockEntity.getShape and moveEntities use the
        // union of the current and previous moved-state outlines. The same
        // methods in 1.10.2 retain that behavior; inherited Block.getShape gives
        // ordinary stairs a unit outline. The old caller computes the directional
        // union clearance and adds 0.01 before its ordinary Entity.move request.
        // 26.2 PistonMovingBlockEntity.moveCollidedEntities instead uses swept
        // collision boxes and a deltaProgress cap. The mixin opens the native
        // pre-loop delta gate for an old positive response, then supplies this
        // final historical distance at moveEntityByPiston.
        float previousProgress = mover.getProgress(0.0F);
        float currentOffset = extendedProgress(mover.isExtending(), newProgress);
        float previousOffset = extendedProgress(mover.isExtending(), previousProgress);
        Direction facing = mover.getDirection();
        AABB current = translatedUnit(pos, facing, currentOffset);
        AABB previous = translatedUnit(pos, facing, previousOffset);
        AABB sweptOutline = current.minmax(previous);
        AABB entityBox = recipient.getBoundingBox();
        if (!sweptOutline.intersects(entityBox)) {
            // The modern stair collision boxes are contained by this old unit
            // outline. Seed zero to preserve the old no-response gate; no modern
            // per-box clearance can raise it outside this union.
            return OptionalDouble.of(0.0);
        }

        double clearance = switch (movementDirection) {
            case EAST -> sweptOutline.maxX - entityBox.minX;
            case WEST -> entityBox.maxX - sweptOutline.minX;
            case UP -> sweptOutline.maxY - entityBox.minY;
            case DOWN -> entityBox.maxY - sweptOutline.minY;
            case SOUTH -> sweptOutline.maxZ - entityBox.minZ;
            case NORTH -> entityBox.maxZ - sweptOutline.minZ;
        };
        return OptionalDouble.of(clearance + 0.01);
    }

    private static float extendedProgress(boolean extending, float progress) {
        return extending ? progress - 1.0F : 1.0F - progress;
    }

    private static AABB translatedUnit(BlockPos pos, Direction facing, float offset) {
        float offsetX = offset * facing.getStepX();
        float offsetY = offset * facing.getStepY();
        float offsetZ = offset * facing.getStepZ();
        double x = pos.getX() + (double)offsetX;
        double y = pos.getY() + (double)offsetY;
        double z = pos.getZ() + (double)offsetZ;
        return new AABB(x, y, z, x + 1.0, y + 1.0, z + 1.0);
    }
}
