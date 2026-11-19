package me.wolfii.legacyparkourcompat.change.v1_14;

import java.util.Optional;
import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.change.common.TopSlabAutoJumpCandidates;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.jump.AutoJumpCollisionOrderHook.ProbeSegments;
import me.wolfii.legacyparkourcompat.mechanic.hook.jump.AutoJumpPartialCandidateHook;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.CollisionContext;

/**
 * 1.14 Ornithe Feather Gen2 build.2 WorldView.getCollisions (127–194) uses the
 * exact shape query and yields intersecting partials before the full-cube aggregate.
 * SlabBlock.getShape (46–53) returns the dry top-half Stone slab's single box.
 * LocalClientPlayerEntity.autoJump (898–951) stops at the first side-probe hit.
 * 1.14.1 Yarn build.10 ViewableWorld.getCollisionShapes (129–193) and
 * ClientPlayerEntity.method_3148 (886–940) instead consume per-cell boxes, so
 * the earlier stone cell reaches the one-block headroom sample first.
 */
@MovementChange(emulates = ParkourVersion.V1_14)
public final class TopStoneSlabAutoJumpCandidate implements AutoJumpPartialCandidateHook {
    @Override
    public Optional<Partial> candidate(Player player, Level level, AABB query, ProbeSegments probes,
                                       BlockState state, BlockPos slab, CollisionContext context) {
        return TopSlabAutoJumpCandidates.candidate(Blocks.STONE_SLAB, player, level, query, probes, state, slab, context);
    }
}
