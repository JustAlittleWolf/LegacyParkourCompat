package me.wolfii.legacyparkourcompat.mechanic.hook;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;

@MechanicType("player.sprint.canStartSprinting")
public interface SprintStartBehavior extends VersionedMechanic {
    default boolean canStartSprinting(Player player, boolean vanilla) {
        return vanilla;
    }
}
