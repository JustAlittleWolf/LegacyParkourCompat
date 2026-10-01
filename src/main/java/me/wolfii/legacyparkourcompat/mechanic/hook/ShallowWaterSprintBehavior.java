package me.wolfii.legacyparkourcompat.mechanic.hook;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;

@MechanicType("player.sprint.isInShallowWaterForSprintEligibility")
public interface ShallowWaterSprintBehavior extends VersionedMechanic {
    default boolean isInShallowWaterForSprintEligibility(
        Player player,
        boolean sprintKeyDown,
        boolean sprinting,
        boolean vanilla
    ) {
        return vanilla;
    }
}
