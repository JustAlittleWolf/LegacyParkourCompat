package me.wolfii.legacyparkourcompat.mechanic.hook;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.player.Player;

/** Versioned collision probe for the sneak-edge foot slice. */
@MechanicType("player.sneak.probe")
public interface SneakEdgeProbeBehavior extends VersionedMechanic {
    boolean appliesTo(Player player);

    boolean canFallAtLeast(Player player, double deltaX, double deltaZ, double minHeight);
}
