package me.wolfii.legacyparkourcompat.mechanic.hook;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.player.Player;

/** Whether farmland conversion moves an intersecting player to the new shape. */
@MechanicType("player.farmland.conversion_push")
public interface FarmlandEntityPushBehavior extends VersionedMechanic {
    boolean suppressPlayerPushUp(Player player);
}
