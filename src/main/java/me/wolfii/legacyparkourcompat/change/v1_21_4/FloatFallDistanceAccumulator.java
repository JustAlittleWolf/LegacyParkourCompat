package me.wolfii.legacyparkourcompat.change.v1_21_4;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.falldistance.FallDistanceAccumulatorHook;
import net.minecraft.world.entity.Entity;

/**
 * Mojmap 1.21 through 1.21.4 Entity#checkFallDamage subtracts the float-cast
 * resolved Y from a float field in the airborne branch. Mojmap 1.21.5 and
 * 26.2 unobfuscated retain the displacement cast but use a double field.
 * Keep the existing rounding of the 26.2 pre-callback write on downward landings.
 */
@MovementChange(emulates = ParkourVersion.V1_21_4)
public final class FloatFallDistanceAccumulator implements FallDistanceAccumulatorHook {
    @Override
    public double updatedDistance(Entity entity, double previousDistance, double resolvedY, boolean onGround, double nativeUpdatedDistance) {
        return onGround
            ? (float)nativeUpdatedDistance
            : FallDistanceAccumulatorHook.subtractFloatDisplacement(previousDistance, resolvedY);
    }
}
