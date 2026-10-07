package me.wolfii.legacyparkourcompat.mechanic.hook;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

/** Versioned piston displacement cap for a player. */
@FunctionalInterface
@MechanicType("player.piston.movement")
public interface PistonMovementBehavior extends VersionedMechanic {
    Vec3 limitPistonMovement(Player player, Vec3 vanillaMovement);
}
