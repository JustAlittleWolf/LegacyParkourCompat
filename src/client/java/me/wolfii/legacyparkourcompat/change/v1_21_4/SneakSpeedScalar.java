package me.wolfii.legacyparkourcompat.change.v1_21_4;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.input.SneakSpeedScalarHook;

/**
 * Mojmap 1.20.6 LocalPlayer.aiStep computes the clamped enchantment formula;
 * Mojmap 1.21 and 1.21.4 LocalPlayer.aiStep cast effective SNEAKING_SPEED to
 * float before keyboard slowdown. Attribute/enchantment production stays vanilla.
 */
@MovementChange(emulates = ParkourVersion.V1_21_4)
public final class SneakSpeedScalar implements SneakSpeedScalarHook {
    @Override
    public float scalar(int swiftSneakLevel, double attributeValue) {
        return (float)attributeValue;
    }
}
