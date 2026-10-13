package me.wolfii.legacyparkourcompat.mechanic.hook.input;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.player.Player;

/** Whether the local player may start sprinting. */
@FunctionalInterface
@MechanicType("player.sprint.canStartSprinting")
public interface SprintStartHook extends VersionedMechanic {
    boolean canStartSprinting(Player player, boolean vanilla);
}
