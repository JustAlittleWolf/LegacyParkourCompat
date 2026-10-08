package me.wolfii.legacyparkourcompat.mechanic.hook;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.player.Player;

@FunctionalInterface
@MechanicType("player.client_unstuck.push_away_velocity")
public interface PushAwayVelocityBehavior extends VersionedMechanic {
    double pushAwayVelocity(Player player, ParkourVersion target, double vanilla);
}
