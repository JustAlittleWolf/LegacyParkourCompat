package me.wolfii.legacyparkourcompat.mechanic.hook;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VanillaFn;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;

/**
 * Historical supporting-block lookup ({@code getOnPos}, {@code getBlockPosBelowThatAffectsMyMovement}).
 */
@MechanicType("entity.supporting_block")
public interface SupportingBlockBehavior extends VersionedMechanic {
    default BlockPos getOnPos(Entity entity, float offset, VanillaFn<BlockPos> vanilla) {
        return vanilla.get();
    }

    default BlockPos getBlockPosBelowThatAffectsMyMovement(Entity entity, VanillaFn<BlockPos> vanilla) {
        return vanilla.get();
    }
}
