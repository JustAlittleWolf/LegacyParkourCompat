package me.wolfii.legacyparkourcompat.mechanic.hook;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.player.Player;

/** Historical cancellation rules for the local auto-sprint trigger timer. */
@FunctionalInterface
@MechanicType("player.sprint.trigger")
public interface SprintTriggerBehavior extends VersionedMechanic {
    boolean shouldReset(Player player, boolean anotherResetCause);
}
