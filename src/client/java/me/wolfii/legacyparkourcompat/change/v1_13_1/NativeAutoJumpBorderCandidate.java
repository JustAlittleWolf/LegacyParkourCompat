package me.wolfii.legacyparkourcompat.change.v1_13_1;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.jump.AutoJumpBorderCandidateHook;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Optional;

/**
 * Keeps the infinite synthetic border candidate out of 1.13.2 and earlier
 * profiles. Ornithe Feather 1.13.2 {@code WorldView.getBlockCollisions}
 * aggregates outside-border cells within the scan; 1.14
 * {@code WorldView.getCollisions} emits the unbounded border before the
 * other candidates. Earlier collector behavior resolves separately.
 */
@MovementChange(emulates = ParkourVersion.V1_13_1)
public final class NativeAutoJumpBorderCandidate implements AutoJumpBorderCandidateHook {
    @Override
    public Optional<VoxelShape> borderCandidate(Player player, ParkourVersion selected) {
        return Optional.empty();
    }
}
