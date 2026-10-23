package me.wolfii.legacyparkourcompat.mechanic.hook.movement;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

/** Replaces the two-axis input-to-velocity operation when a version requires it. */
@FunctionalInterface
@MechanicType("player.travel.heading_velocity")
public interface HeadingVelocityHook extends VersionedMechanic {
    /** Return true when the operation was handled, including a historical no-write result. */
    boolean apply(Player player, float speed, Vec3 input);
}
