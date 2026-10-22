package me.wolfii.legacyparkourcompat.mechanic.hook.entity;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.state.BlockState;

/** Resolves the point at which a grounded block's step callback runs. */
@MechanicType("entity.grounded_block_step")
public interface GroundedBlockStepCallbackHook extends VersionedMechanic {
    /** Runs the callback after ground and fall state have updated. */
    void afterGroundState(Entity entity, BlockPos pos, BlockState state);

    /** Whether to suppress the current native callback after travel. */
    boolean suppressPostTravel(Entity entity);
}
