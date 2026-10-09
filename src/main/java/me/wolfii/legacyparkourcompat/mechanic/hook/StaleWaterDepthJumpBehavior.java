package me.wolfii.legacyparkourcompat.mechanic.hook;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.player.Player;

/** Whether retained water depth selects a player's water-jump operation. */
@FunctionalInterface
@MechanicType("player.jump.water.stale-depth")
public interface StaleWaterDepthJumpBehavior extends VersionedMechanic {
    boolean shouldJumpFromRetainedWaterDepth(Player player, double retainedDepth, boolean jumpInput);
}
