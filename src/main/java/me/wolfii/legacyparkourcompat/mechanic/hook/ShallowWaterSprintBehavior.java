package me.wolfii.legacyparkourcompat.mechanic.hook;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.player.Player;

/** Whether the water state permits sprint eligibility. */
@FunctionalInterface
@MechanicType("player.sprint.isInShallowWaterForSprintEligibility")
public interface ShallowWaterSprintBehavior extends VersionedMechanic {
    boolean isInShallowWaterForSprintEligibility(
        Player player,
        boolean sprintKeyDown,
        boolean sprinting,
        boolean vanilla
    );
}
