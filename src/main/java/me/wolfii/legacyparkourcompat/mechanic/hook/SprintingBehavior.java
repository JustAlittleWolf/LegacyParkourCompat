package me.wolfii.legacyparkourcompat.mechanic.hook;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.player.Player;

/**
 * Historical sprint rules (collision cancel, water sprint, sneak-sprint).
 */
@MechanicType("player.sprint")
public interface SprintingBehavior extends VersionedMechanic {
    default boolean canSprint(Player player, boolean vanilla) {
        return vanilla;
    }
}
