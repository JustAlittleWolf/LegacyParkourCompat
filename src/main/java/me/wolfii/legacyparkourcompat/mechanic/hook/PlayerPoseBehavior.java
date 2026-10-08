package me.wolfii.legacyparkourcompat.mechanic.hook;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.player.Player;

/** Historical player pose sizing update. Return true when handled to cancel the native update. */
@FunctionalInterface
@MechanicType("player.pose-update")
public interface PlayerPoseBehavior extends VersionedMechanic {
    boolean updatePose(Player player);
}
