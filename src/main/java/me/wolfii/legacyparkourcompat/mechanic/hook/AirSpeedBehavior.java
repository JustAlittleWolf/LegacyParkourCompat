package me.wolfii.legacyparkourcompat.mechanic.hook;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.player.Player;

/** Air speed read during player travel. */
@FunctionalInterface
@MechanicType("player.air.speed")
public interface AirSpeedBehavior extends VersionedMechanic {
    float speed(Player player, float stored, float vanilla);
}
