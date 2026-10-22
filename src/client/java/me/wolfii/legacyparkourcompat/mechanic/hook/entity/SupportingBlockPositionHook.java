package me.wolfii.legacyparkourcompat.mechanic.hook.entity;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VanillaFn;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;

/** Position used to select supporting-block movement factors and ground friction. */
@MechanicType("entity.supporting_block_position")
public interface SupportingBlockPositionHook extends VersionedMechanic {
    BlockPos getPosition(Entity entity, VanillaFn<BlockPos> vanilla);
}
