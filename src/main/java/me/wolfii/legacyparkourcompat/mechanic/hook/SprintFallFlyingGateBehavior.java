package me.wolfii.legacyparkourcompat.mechanic.hook;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;

@MechanicType("player.sprint.fallFlyingForSprintGate")
public interface SprintFallFlyingGateBehavior extends VersionedMechanic {
    default boolean fallFlyingForSprintGate(Player player, boolean vanilla) {
        return vanilla;
    }
}
