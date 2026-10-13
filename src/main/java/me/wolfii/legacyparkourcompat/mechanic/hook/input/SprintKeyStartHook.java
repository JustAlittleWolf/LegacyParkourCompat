package me.wolfii.legacyparkourcompat.mechanic.hook.input;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.player.Player;

/** Historical gate for starting a sprint with the sprint key. */
@MechanicType("player.sprint.inputStart.key")
public interface SprintKeyStartHook extends VersionedMechanic {
    boolean allowSprintKeyStart(Player player, boolean vanilla);
}
