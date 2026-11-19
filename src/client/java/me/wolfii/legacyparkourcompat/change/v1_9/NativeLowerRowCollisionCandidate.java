package me.wolfii.legacyparkourcompat.change.v1_9;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.collision.LowerRowCollisionCandidateHook;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.CollisionGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.CollisionContext;

/** Earlier profiles retain native eligibility for this lower-row witness. */
@MovementChange(emulates = ParkourVersion.V1_9_3)
public final class NativeLowerRowCollisionCandidate implements LowerRowCollisionCandidateHook {
    @Override
    public boolean excludeCandidate(BlockState state, CollisionGetter level, BlockPos pos, AABB query,
                                    CollisionContext context) {
        return false;
    }
}
