package me.wolfii.legacyparkourcompat.change.v1_14;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.SoulSandSpeedBehavior;
import me.wolfii.legacyparkourcompat.mixin.access.EntityInvoker;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

/** Before 1.15, every soul sand cell intersecting the player applies .4. */
@MovementChange(emulates = ParkourVersion.V1_14)
public final class OverlappingSoulSandSpeed implements SoulSandSpeedBehavior {
    @Override
    public float movementSpeedFactor(Entity entity, float vanilla) {
        return selectedSoulSand(entity) ? 1.0F : vanilla;
    }

    @Override
    public void afterCollision(Entity entity) {
        AABB box = entity.getBoundingBox();
        BlockPos min = BlockPos.containing(box.minX + 0.001, box.minY + 0.001, box.minZ + 0.001);
        BlockPos max = BlockPos.containing(box.maxX - 0.001, box.maxY - 0.001, box.maxZ - 0.001);
        if (!entity.level().hasChunksAt(min, max)) {
            return;
        }
        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
        for (int x = min.getX(); x <= max.getX(); x++) {
            for (int y = min.getY(); y <= max.getY(); y++) {
                for (int z = min.getZ(); z <= max.getZ(); z++) {
                    if (entity.level().getBlockState(pos.set(x, y, z)).is(Blocks.SOUL_SAND)) {
                        Vec3 velocity = entity.getDeltaMovement();
                        entity.setDeltaMovement(velocity.multiply(0.4, 1.0, 0.4));
                    }
                }
            }
        }
    }

    private static boolean selectedSoulSand(Entity entity) {
        BlockState here = entity.level().getBlockState(entity.blockPosition());
        if (here.is(Blocks.SOUL_SAND)) {
            return true;
        }
        if (here.is(Blocks.WATER) || here.is(Blocks.BUBBLE_COLUMN)
            || here.getBlock().getSpeedFactor() != 1.0F) {
            return false;
        }
        BlockPos below = ((EntityInvoker) entity).lpc$getBlockPosBelowThatAffectsMyMovement();
        return entity.level().getBlockState(below).is(Blocks.SOUL_SAND);
    }
}
