package me.wolfii.legacyparkourcompat.mechanic.hook;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

/** Vertical water velocity adjustment after travel. */
@FunctionalInterface
@MechanicType("player.travel.water.gravity")
public interface WaterGravityBehavior extends VersionedMechanic {
    Vec3 gravityAdjustedMovement(Player player, double baseGravity, boolean falling, Vec3 movement, Vec3 vanilla);
}
