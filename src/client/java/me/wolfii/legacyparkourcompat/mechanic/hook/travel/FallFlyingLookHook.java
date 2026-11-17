package me.wolfii.legacyparkourcompat.mechanic.hook.travel;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

/** Historical look vector consumed by fall-flying travel and lift calculations. */
@FunctionalInterface
@MechanicType("player.fall_flying.look")
public interface FallFlyingLookHook extends VersionedMechanic {
    Vec3 lookVector(LivingEntity entity, ParkourVersion selected, Vec3 vanilla);
}
