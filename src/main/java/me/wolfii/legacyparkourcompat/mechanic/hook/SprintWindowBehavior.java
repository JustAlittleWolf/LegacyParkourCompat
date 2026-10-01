package me.wolfii.legacyparkourcompat.mechanic.hook;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.player.Player;

/** Historical duration of the local player's double-tap sprint window. */
@MechanicType("player.sprint.window")
public interface SprintWindowBehavior extends VersionedMechanic {
    int doubleTapWindow(Player player, int vanilla);
}
