package me.wolfii.legacyparkourcompat.mechanic.hook.input;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.player.Player;

/** Whether sneaking adds a downward impulse while the player is in water. */
@FunctionalInterface
@MechanicType("player.water.sneak")
public interface WaterSneakHook extends VersionedMechanic {
    boolean shouldApplyDownwardImpulse(Player player, boolean vanilla);
}
