package me.wolfii.legacyparkourcompat.mechanic.hook.jump;

import java.util.Optional;
import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import me.wolfii.legacyparkourcompat.mechanic.hook.jump.AutoJumpCollisionOrderHook.ProbeSegments;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

/** Supplies an ordered partial before the aggregate for a closed mixed candidate query. */
@MechanicType("player.auto_jump.partial_candidate")
public interface AutoJumpPartialCandidateHook extends VersionedMechanic {
    default Optional<Partial> candidate(Player player, Level level, AABB query, ProbeSegments probes,
                                       BlockState state, BlockPos pos, CollisionContext context) {
        return Optional.empty();
    }

    record Partial(VoxelShape shape, int requiredFullCells) {}
}
