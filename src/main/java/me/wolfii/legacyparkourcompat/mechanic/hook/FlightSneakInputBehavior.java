package me.wolfii.legacyparkourcompat.mechanic.hook;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec2;

/** Input transformation while flying and sneaking. */
@FunctionalInterface
@MechanicType("player.input.flight_sneak")
public interface FlightSneakInputBehavior extends VersionedMechanic {
    Vec2 modify(Player player, Vec2 input, boolean slowedBySneaking);
}
