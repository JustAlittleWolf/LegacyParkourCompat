package me.wolfii.legacyparkourcompat.mechanic.hook;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.player.Player;

/** Airborne input speed, including the historical sprint-update timing. */
@MechanicType("player.travel.air_speed")
public interface AirSpeedBehavior extends VersionedMechanic {
    float speed(Player player, boolean sprintingAtTickStart, float vanilla);
}
