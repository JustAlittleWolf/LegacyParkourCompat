package me.wolfii.legacyparkourcompat.mechanic.hook;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.Entity;

/**
 * Historical negligible-speed cutoff (0.005 in 1.8, 0.003 from 1.9).
 */
@MechanicType("player.speed.negligible")
public interface NegligibleSpeedBehavior extends VersionedMechanic {
    double threshold(Entity entity, double vanilla);
}
