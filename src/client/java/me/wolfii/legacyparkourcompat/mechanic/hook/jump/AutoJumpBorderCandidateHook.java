package me.wolfii.legacyparkourcompat.mechanic.hook.jump;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Optional;

/** Border candidate placed before auto-jump's block and entity candidates. */
@MechanicType("player.auto_jump.border_candidate")
public interface AutoJumpBorderCandidateHook extends VersionedMechanic {
    Optional<VoxelShape> borderCandidate(Player player, ParkourVersion selected);
}
