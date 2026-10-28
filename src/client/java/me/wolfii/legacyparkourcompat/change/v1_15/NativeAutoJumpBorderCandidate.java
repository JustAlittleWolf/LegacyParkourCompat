package me.wolfii.legacyparkourcompat.change.v1_15;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.jump.AutoJumpBorderCandidateHook;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Optional;

/**
 * The accepted source boundary for the synthetic auto-jump border candidate
 * begins at 1.15.2. Keep 1.15 and earlier profiles on the native collision
 * candidate stream until their historical border behavior is established.
 */
@MovementChange(emulates = ParkourVersion.V1_15)
public final class NativeAutoJumpBorderCandidate implements AutoJumpBorderCandidateHook {
    @Override
    public Optional<VoxelShape> borderCandidate(Player player, ParkourVersion selected) {
        return Optional.empty();
    }
}
