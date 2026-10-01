package me.wolfii.legacyparkourcompat.mechanic.hook;


/**
 * Historical sprint rules (collision cancel, water sprint, sneak-sprint).
 */
@MechanicType("player.sprint")

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

public interface SprintingBehavior extends VersionedMechanic {
    default boolean allowShallowWaterSprint(Player player, boolean vanilla) {
        return vanilla;
    }

    default boolean canSprint(Player player, boolean vanilla) {
        return vanilla;
    }

    default boolean canStartSprinting(Player player, boolean vanilla) {
        return vanilla;
    }

    default boolean fallFlyingForSprintGate(Player player, boolean vanilla) {
        return vanilla;
    }

    default boolean vehicleCanSprint(Player player, Entity vehicle, boolean vanilla) {
        return vanilla;
    }

    default boolean shouldStopRunSprinting(Player player, boolean vanilla) {
        return vanilla;
    }


    default boolean canSprint(Player player, boolean vanilla) {
        return vanilla;
    }

    default boolean canStartSprinting(Player player, boolean vanilla) {
        return vanilla;
    }

    default boolean isInShallowWaterForSprintEligibility(
        Player player,
        boolean sprintKeyDown,
        boolean sprinting,
        boolean vanilla
    ) {
        return vanilla;
    }

    default boolean shouldStopRunSprinting(Player player, boolean vanilla) {
        return vanilla;
    }

}
