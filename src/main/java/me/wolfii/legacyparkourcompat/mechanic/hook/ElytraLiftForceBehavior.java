package me.wolfii.legacyparkourcompat.mechanic.hook;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

/** Supplies the fall-flying lift coefficient used for player movement. */
@FunctionalInterface
@MechanicType("player.travel.elytra_lift_force")
public interface ElytraLiftForceBehavior extends VersionedMechanic {
    double liftForce(Player player, ParkourVersion selected, Vec3 lookAngle, double vanilla);
}
