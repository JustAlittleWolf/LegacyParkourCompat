package me.wolfii.legacyparkourcompat.change.v1_18;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.falldistance.FallDistanceAccumulatorHook;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;

/**
 * Ornithe Feather 1.8 and Mojmap 1.18.1 Entity#checkFallDamage subtract the double
 * resolved Y from the old float when airborne, then narrow the result to float.
 * Mojmap 1.18.2 instead narrows the displacement before subtraction. In 26.2
 * unobfuscated, Entity#checkFallDamage also writes on a downward landing before
 * the ground callback; keep that native write outside this airborne operation.
 */
@MovementChange(emulates = ParkourVersion.V1_18)
public final class FallDistanceAccumulator implements FallDistanceAccumulatorHook {
    @Override
    public double updatedDistance(
        Entity entity, double previousDistance, double resolvedY, boolean onGround, double nativeUpdatedDistance
    ) {
        if (onGround || !(entity instanceof LocalPlayer)) {
            return nativeUpdatedDistance;
        }
        return (float)((float)previousDistance - resolvedY);
    }
}
