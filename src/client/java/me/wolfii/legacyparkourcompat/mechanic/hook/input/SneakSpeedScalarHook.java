package me.wolfii.legacyparkourcompat.mechanic.hook.input;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;

/** Scalar supplied to the independently resolved stored keyboard operation. */
@MechanicType("player.input.sneak_speed_scalar")
public interface SneakSpeedScalarHook extends VersionedMechanic {
    float scalar(int swiftSneakLevel, double attributeValue);
}
