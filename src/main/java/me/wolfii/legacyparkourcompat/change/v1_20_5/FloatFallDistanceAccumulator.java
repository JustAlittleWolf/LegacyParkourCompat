package me.wolfii.legacyparkourcompat.change.v1_20_5;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.falldistance.FallDistanceAccumulatorHook;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

/**
 * Mojmap 1.18.2 through 1.20.6 Entity#checkFallDamage subtracts a float-cast
 * resolved Y from the float accumulator when airborne. Mojmap 1.18.1
 * casts after subtraction instead. The 26.2 unobfuscated callback can
 * also write before handling a downward landing; retain that native value.
 */
@MovementChange(emulates = ParkourVersion.V1_20_5)
public final class FloatFallDistanceAccumulator implements FallDistanceAccumulatorHook {
    @Override
    public double updatedDistance(
        Entity entity, double previousDistance, double resolvedY, boolean onGround, double nativeUpdatedDistance
    ) {
        if (onGround || !(entity instanceof Player player) || !player.isLocalPlayer()) {
            return nativeUpdatedDistance;
        }
        return FallDistanceAccumulatorHook.subtractFloatDisplacement(previousDistance, resolvedY);
    }
}
