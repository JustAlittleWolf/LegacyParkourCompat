package me.wolfii.legacyparkourcompat.change.v1_15;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.VanillaFn;
import me.wolfii.legacyparkourcompat.mechanic.hook.SupportingBlockBehavior;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;

/**
 * Until 1.15, slipperiness/speed-factor used the block 1b below the feet
 * ({@code minY - 1.0} in 1.12.2 {@code LivingEntity#method_2657}). 1.15 only
 * looks 0.5b down, so full blocks no longer inherit ice from underneath.
 */
@MovementChange(emulates = ParkourVersion.V1_14)
public final class DeepFrictionBlock implements SupportingBlockBehavior {
    @Override
    public BlockPos getBlockPosBelowThatAffectsMyMovement(Entity entity, VanillaFn<BlockPos> vanilla) {
        return BlockPos.containing(entity.getX(), entity.getBoundingBox().minY - 1.0, entity.getZ());
    }
}
