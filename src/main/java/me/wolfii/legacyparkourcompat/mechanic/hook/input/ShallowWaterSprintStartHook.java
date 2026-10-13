package me.wolfii.legacyparkourcompat.mechanic.hook.input;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.player.Player;

/** Historical gate for starting a sprint in shallow water. */
@MechanicType("player.sprint.inputStart.shallowWater")
public interface ShallowWaterSprintStartHook extends VersionedMechanic {
    boolean allowShallowWaterStart(Player player, boolean vanilla);
}
