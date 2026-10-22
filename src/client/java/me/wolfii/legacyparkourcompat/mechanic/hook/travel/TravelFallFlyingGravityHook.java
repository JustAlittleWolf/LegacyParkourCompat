package me.wolfii.legacyparkourcompat.mechanic.hook.travel;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.LivingEntity;

/** Supplies the gravity value used by the fall-flying velocity update. */
@MechanicType("travel.fall_flying_gravity")
public interface TravelFallFlyingGravityHook extends VersionedMechanic {
    double gravity(LivingEntity entity);
}
