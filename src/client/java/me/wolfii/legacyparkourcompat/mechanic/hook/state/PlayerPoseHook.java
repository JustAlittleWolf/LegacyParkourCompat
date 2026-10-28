package me.wolfii.legacyparkourcompat.mechanic.hook.state;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;

/** Reconciles the historical pose resize after native pose selection has run. */
@FunctionalInterface
@MechanicType("player.pose-update")
public interface PlayerPoseHook extends VersionedMechanic {
    boolean updatePose(Player player, EntityDimensions previousDimensions, AABB previousBox, float previousEyeHeight);
}
