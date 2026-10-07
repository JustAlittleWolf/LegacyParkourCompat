package me.wolfii.legacyparkourcompat.mechanic.hook;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.Entity;

/** Historical player gravity value. */
@FunctionalInterface
@MechanicType("player.gravity")
public interface GravityBehavior extends VersionedMechanic {
    double gravity(Entity entity, double vanilla);
}
