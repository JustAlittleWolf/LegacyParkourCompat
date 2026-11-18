package me.wolfii.legacyparkourcompat.change.v1_20_5;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.falldistance.FallDistanceAccumulatorHook;
import net.minecraft.world.entity.Entity;

/**
 * Keeps the native accumulator for selections from 1.18.2 through 1.20.6 between historical providers.
 */
@MovementChange(emulates = ParkourVersion.V1_20_5)
public final class NativeFallDistanceAccumulator implements FallDistanceAccumulatorHook {
    @Override
    public double updatedDistance(Entity entity, double previousDistance, double resolvedY, boolean onGround, double nativeUpdatedDistance) {
        return nativeUpdatedDistance;
    }
}
