package me.wolfii.legacyparkourcompat.mechanic.hook.collision;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.CollisionGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

/** Eligibility of an already visited block collision candidate; does not collect shapes. */
@MechanicType("collision.lower_row_candidate")
public interface LowerRowCollisionCandidateHook extends VersionedMechanic {
    boolean excludeCandidate(BlockState state, CollisionGetter level, BlockPos pos, AABB query);
}
