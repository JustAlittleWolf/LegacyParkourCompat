package me.wolfii.legacyparkourcompat.mechanic.hook;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.player.Player;

/** Historical shallow-lava travel branch selection. */
@FunctionalInterface
@MechanicType("player.travel.lava")
public interface LavaTravelBehavior extends VersionedMechanic {
    boolean isShallowForTravel(Player player, boolean vanillaShallow);
}
