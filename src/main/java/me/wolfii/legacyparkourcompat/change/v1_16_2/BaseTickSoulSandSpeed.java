package me.wolfii.legacyparkourcompat.change.v1_16_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.SoulSandSpeedBehavior;
import me.wolfii.legacyparkourcompat.mixin.access.EntityInvoker;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

/** In 1.15–1.16, soul sand's speed factor applies once in Entity#baseTick. */
@MovementChange(emulates = ParkourVersion.V1_16_2)
public class BaseTickSoulSandSpeed implements SoulSandSpeedBehavior {
    @Override
    public float movementSpeedFactor(Entity entity, float vanilla) {
        return selectedSoulSand(entity) ? 1.0F : vanilla;
    }

    @Override
    public void baseTick(Entity entity) {
        if (selectedSoulSand(entity)) {
            Vec3 velocity = entity.getDeltaMovement();
            entity.setDeltaMovement(velocity.multiply(0.4F, 1.0, 0.4F));
        }
    }

    protected final boolean selectedSoulSand(Entity entity) {
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
