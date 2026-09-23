package me.wolfii.legacyparkourcompat.mechanic.hook;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.player.Player;

/** Determines whether a horizontal collision ends a player sprint. */
@MechanicType("player.sprint.collision")
public interface SprintCollisionBehavior extends VersionedMechanic {
    boolean shouldStopRunSprinting(Player player, boolean vanilla);
}
