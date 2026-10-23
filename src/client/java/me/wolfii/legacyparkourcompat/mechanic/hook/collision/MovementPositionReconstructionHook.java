package me.wolfii.legacyparkourcompat.mechanic.hook.collision;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

import java.util.Optional;

/** Pure conversion of resolved displacement, evaluated for bookkeeping and the actual write. */
@MechanicType("player.movement.position_reconstruction")
public interface MovementPositionReconstructionHook extends VersionedMechanic {
    record Position(Vec3 position, AABB box) {}
    Optional<Position> resolve(Player player, ParkourVersion selected, Vec3 movement, @Nullable AABB resolvedBox);
}
