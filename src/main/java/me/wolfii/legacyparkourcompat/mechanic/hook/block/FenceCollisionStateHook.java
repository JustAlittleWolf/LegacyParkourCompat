package me.wolfii.legacyparkourcompat.mechanic.hook.block;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Optional;

/**
 * Resolves a fence's stored connection bits for a collision-shape query.
 */
@MechanicType("block.fence-collision-state")
public interface FenceCollisionStateHook extends VersionedMechanic {
    /**
     * Returns the query-time state to use for collision geometry, or empty to retain vanilla geometry.
     */
    Optional<BlockState> collisionState(BlockState state, BlockGetter level, BlockPos pos);
}
