package me.wolfii.legacyparkourcompat.mechanic.hook;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.player.Player;

/** Supplies the fall-flying lift coefficient used for player movement. */
@MechanicType("player.travel.elytra_lift_force")
public interface ElytraLiftForceBehavior extends VersionedMechanic {
    double liftForce(Player player, double vanilla);
}
