package me.wolfii.legacyparkourcompat.mechanic.hook;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.player.Player;

/** Historical local-input rules for starting a sprint. */
@MechanicType("player.sprint.inputStart")
public interface SprintInputStartBehavior extends VersionedMechanic {
    boolean allowShallowWaterStart(Player player, boolean vanilla);

    boolean allowDoubleTapStart(Player player, boolean vanilla);

    boolean allowSprintKeyStart(Player player, boolean vanilla);
}
