package me.wolfii.legacyparkourcompat.mechanic.hook.input;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.player.Player;

/** Historical gate for the double-tap sprint input. */
@MechanicType("player.sprint.inputStart.doubleTap")
public interface DoubleTapSprintStartHook extends VersionedMechanic {
    boolean allowDoubleTapStart(Player player, boolean vanilla);
}
