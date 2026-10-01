package me.wolfii.legacyparkourcompat.mechanic.hook;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VanillaCall;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.player.Player;

/** Timing of the fall-distance reset during creative flight. */
@MechanicType("player.flight_fall_distance")
public interface FlightFallDistanceBehavior extends VersionedMechanic {
    void beforeMovement(Player player, VanillaCall vanilla);
}
