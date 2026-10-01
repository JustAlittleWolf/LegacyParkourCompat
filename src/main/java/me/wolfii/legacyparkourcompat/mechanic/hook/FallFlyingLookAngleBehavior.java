package me.wolfii.legacyparkourcompat.mechanic.hook;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

/** Selects the look vector used by fall-flying movement. */
@MechanicType("player.travel.fall_flying.look_angle")
public interface FallFlyingLookAngleBehavior extends VersionedMechanic {
    Vec3 lookAngle(LivingEntity entity);
}
