package me.wolfii.legacyparkourcompat.mechanic.hook.input;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.player.Player;

/** Historical duration of the local player's double-tap sprint window. */
@FunctionalInterface
@MechanicType("player.sprint.window")
public interface SprintWindowHook extends VersionedMechanic {
    int doubleTapWindow(Player player, int vanilla);
}
