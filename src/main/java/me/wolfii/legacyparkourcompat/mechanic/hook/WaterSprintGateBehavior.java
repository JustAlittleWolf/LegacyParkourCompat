package me.wolfii.legacyparkourcompat.mechanic.hook;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;

@MechanicType("player.sprint.allowShallowWaterSprint")
public interface WaterSprintGateBehavior extends VersionedMechanic {
    default boolean allowShallowWaterSprint(Player player, boolean vanilla) {
        return vanilla;
    }
}
