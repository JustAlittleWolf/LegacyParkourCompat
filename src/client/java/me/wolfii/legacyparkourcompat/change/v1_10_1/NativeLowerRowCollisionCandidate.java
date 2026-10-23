package me.wolfii.legacyparkourcompat.change.v1_10_1;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.collision.LowerRowCollisionCandidateHook;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.CollisionGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

/**
 * Lower applicability boundary, intentionally retaining native eligibility.
 * 1.10.2, ornithe-feather gen2 build.2, World.getCollisions skips this row but
 * MovingBlockEntity.getShape uses the moved fence's 1.0-high outline; it does
 * not establish the 1.11 FenceBlock.addCollisions 1.5-high post witness.
 * 26.2, unobfuscated, BlockCollisions.computeNext and
 * PistonMovingBlockEntity.getCollisionShape remain unchanged for this range.
 * This prevents the independently resolved 1.11 subset from leaking backwards.
 */
@MovementChange(emulates = ParkourVersion.V1_10_1)
public final class NativeLowerRowCollisionCandidate implements LowerRowCollisionCandidateHook {
    @Override
    public boolean excludeCandidate(BlockState state, CollisionGetter level, BlockPos pos, AABB query) {
        return false;
    }
}
