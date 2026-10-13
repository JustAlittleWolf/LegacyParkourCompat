package me.wolfii.legacyparkourcompat.mechanic.hook.block;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.player.Player;

/** Whether farmland conversion moves an intersecting player to the new shape. */
@FunctionalInterface
@MechanicType("player.farmland.conversion_push")
public interface FarmlandEntityPushHook extends VersionedMechanic {
    boolean suppressPlayerPushUp(Player player);
}
