package me.wolfii.legacyparkourcompat.mechanic.hook;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VanillaCall;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.player.Player;

/** Historical jump impulse invoked when a player enables creative flight. */
@FunctionalInterface
@MechanicType("player.flight.activation_jump")
public interface FlightActivationJumpBehavior extends VersionedMechanic {
    void onFlightActivated(Player player, VanillaCall vanilla);
}
