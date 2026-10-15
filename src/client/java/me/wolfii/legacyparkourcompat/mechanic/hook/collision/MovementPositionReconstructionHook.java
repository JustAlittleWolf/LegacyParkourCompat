package me.wolfii.legacyparkourcompat.mechanic.hook.collision;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.Optional;

/** Converts resolved displacement into the stored position and bounding box. */
@MechanicType("player.movement.position_reconstruction")
public interface MovementPositionReconstructionHook extends VersionedMechanic {
    record Position(Vec3 position, AABB box) {}
    Optional<Position> resolve(Player player, ParkourVersion selected, Vec3 movement);
}
