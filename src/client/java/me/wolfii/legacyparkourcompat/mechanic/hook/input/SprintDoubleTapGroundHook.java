package me.wolfii.legacyparkourcompat.mechanic.hook.input;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.player.Player;

/** Selects the ground admission for the local player's double-tap sprint route. */
@MechanicType("player.sprint.double_tap_ground")
public interface SprintDoubleTapGroundHook extends VersionedMechanic {
    /** Applies only to the timer arm and completion of double-tap sprint. */
    boolean admits(Player player);
}
