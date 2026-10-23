package me.wolfii.legacyparkourcompat.mechanic.hook.collision;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VanillaFn;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;

/** Selects the contact position at a caller-supplied vertical offset. */
@MechanicType("collision.on_position")
public interface OnPositionHook extends VersionedMechanic {
    BlockPos onPosition(Entity entity, float offset, VanillaFn<BlockPos> vanilla);
}
