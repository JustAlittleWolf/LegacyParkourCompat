package me.wolfii.legacyparkourcompat.mechanic.hook;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

/** Historical fall-flying look vector at the vertical-pitch boundary. */
@MechanicType("player.fall_flying.look")
public interface FallFlyingLookBehavior extends VersionedMechanic {
    Vec3 lookVector(LivingEntity entity, Vec3 vanilla);
    default boolean appliesToVersion(me.wolfii.legacyparkourcompat.api.ParkourVersion selected) {
        return selected.newerThanOrEqual(me.wolfii.legacyparkourcompat.api.ParkourVersion.V1_9);
    }
}
