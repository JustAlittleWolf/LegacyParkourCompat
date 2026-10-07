package me.wolfii.legacyparkourcompat.mechanic.hook;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.player.Player;

/** Versioned collision probe for the sneak-edge foot slice. */
@FunctionalInterface
@MechanicType("player.sneak.probe")
public interface SneakEdgeProbeBehavior extends VersionedMechanic {
    boolean canFallAtLeast(Player player, double deltaX, double deltaZ, double minHeight);
}
