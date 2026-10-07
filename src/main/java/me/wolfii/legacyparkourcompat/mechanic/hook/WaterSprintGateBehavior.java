package me.wolfii.legacyparkourcompat.mechanic.hook;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.player.Player;

/** Whether sprint eligibility may allow shallow water. */
@FunctionalInterface
@MechanicType("player.sprint.allowShallowWaterSprint")
public interface WaterSprintGateBehavior extends VersionedMechanic {
    boolean allowShallowWaterSprint(Player player, boolean vanilla);
}
