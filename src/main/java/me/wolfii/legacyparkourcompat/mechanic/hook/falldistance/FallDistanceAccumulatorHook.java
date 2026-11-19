package me.wolfii.legacyparkourcompat.mechanic.hook.falldistance;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.Entity;

/**
 * Resolves the value for the native accumulator write from its pre-write distance,
 * raw resolved Y displacement and ground flag.
 */
@MechanicType("entity.fall-distance-accumulator")
public interface FallDistanceAccumulatorHook extends VersionedMechanic {
    double updatedDistance(Entity entity, double previousDistance, double resolvedY, boolean onGround, double nativeUpdatedDistance);

    static double subtractFloatDisplacement(double previousDistance, double resolvedY) {
        return (float)previousDistance - (float)resolvedY;
    }
}
