package me.wolfii.legacyparkourcompat.mechanic.hook.input;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.player.Player;

/** Horizontal water drag while sprinting. */
@FunctionalInterface
@MechanicType("player.travel.water.sprint_slowdown")
public interface WaterSprintSlowdownHook extends VersionedMechanic {
    float sprintSlowdown(Player player, float vanilla);
}
