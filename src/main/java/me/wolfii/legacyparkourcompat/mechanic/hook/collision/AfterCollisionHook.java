package me.wolfii.legacyparkourcompat.mechanic.hook.collision;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.Entity;

/** Callback after collision resolution, before the block speed multiplier. */
@FunctionalInterface
@MechanicType("entity.movement.after_collision")
public interface AfterCollisionHook extends VersionedMechanic {
    void afterCollision(Entity entity);
}
