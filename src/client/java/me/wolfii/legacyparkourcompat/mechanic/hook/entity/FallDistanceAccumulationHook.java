package me.wolfii.legacyparkourcompat.mechanic.hook.entity;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.Entity;

import java.util.Optional;

/** Controls the admission predicate for fall-distance accumulation. */
@MechanicType("entity.fall_distance_accumulation")
public interface FallDistanceAccumulationHook extends VersionedMechanic {
    /** Empty keeps the native {@code isInWater()} predicate at the redirected call site. */
    Optional<Boolean> shouldAccumulateFallDistance(Entity entity, double yMovement, boolean onGround);
}
