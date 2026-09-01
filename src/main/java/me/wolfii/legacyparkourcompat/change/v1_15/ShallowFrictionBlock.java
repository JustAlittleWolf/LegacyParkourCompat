package me.wolfii.legacyparkourcompat.change.v1_15;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.VanillaFn;
import me.wolfii.legacyparkourcompat.mechanic.hook.SupportingBlockBehavior;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;

/**
 * 1.15–1.19.3 friction lookup: {@code minY - 0.5000001} under the entity
 * centre. 1.19.4 switched supporting-block to the collided edge
 * ({@link me.wolfii.legacyparkourcompat.change.v1_19_4.SupportingBlock}).
 */
@MovementChange(emulates = ParkourVersion.V1_19)
public final class ShallowFrictionBlock implements SupportingBlockBehavior {
    @Override
    public BlockPos getBlockPosBelowThatAffectsMyMovement(Entity entity, VanillaFn<BlockPos> vanilla) {
        return BlockPos.containing(entity.getX(), entity.getBoundingBox().minY - 0.5000001, entity.getZ());
    }
}
