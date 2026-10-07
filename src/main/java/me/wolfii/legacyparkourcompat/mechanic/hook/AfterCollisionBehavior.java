package me.wolfii.legacyparkourcompat.mechanic.hook;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.Entity;

/** Callback after collision resolution, before the block speed multiplier. */
@FunctionalInterface
@MechanicType("entity.movement.after_collision")
public interface AfterCollisionBehavior extends VersionedMechanic {
    void afterCollision(Entity entity);
}
