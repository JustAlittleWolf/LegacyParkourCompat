package me.wolfii.legacyparkourcompat.mechanic.hook.block;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.state.BlockState;

/** Historical response applied at the end of a contacted block callback. */
@MechanicType("block.inside_response")
public interface BlockInsideResponseHook extends VersionedMechanic {
    void afterInside(Entity entity, BlockState state);
}
