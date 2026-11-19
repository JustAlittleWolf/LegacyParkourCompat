package me.wolfii.legacyparkourcompat.change.v1_13_1;

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
 * 1.13.1 and 1.13.2 Feather Gen2 build.2 SlabBlock (24–59) supplies the
 * top-half collision box. WorldView.getBlockCollisions (112–151) yields it
 * before the full-cell aggregate, and LocalClientPlayerEntity.autoJump
 * (871–980) uses it as the first side-ray hit for the bounded landing.
 * 1.14 Feather Gen2 build.2 DataFixes (452–476) renames that stone_slab ID
 * to smooth_stone_slab. The separate 1.14 candidate uses the new Stone slab.
 */
@MovementChange(emulates = ParkourVersion.V1_13_1)
public final class TopSmoothStoneSlabAutoJumpCandidate implements AutoJumpPartialCandidateHook {
    @Override
    public boolean observesForwardLanding() {
        return true;
    }

    @Override
    public Optional<Partial> candidate(Player player, Level level, AABB query, ProbeSegments probes,
                                       BlockState state, BlockPos slab, CollisionContext context) {
        var start = probes.groundedTravelStart();
        var box = player.getBoundingBox();
        // Keep the observed pre- and post-move footprint over the same two
        // Stone supports and its full swept height in the certified air column.
        if (start == null || slab.getY() != 8 || player.getSpeed() != 0.1F
            || start.x - player.getBbWidth() * 0.5F < slab.getX() - 1.0
            || start.y + player.getBbHeight() >= slab.getY() + 2.0
            || box.minZ < slab.getZ() - 1.0 || box.maxZ >= slab.getZ() + 1.0) {
            return Optional.empty();
        }
        return TopSlabAutoJumpCandidates.candidate(
            Blocks.SMOOTH_STONE_SLAB, player, level, query, probes, state, slab, context);
    }
}
