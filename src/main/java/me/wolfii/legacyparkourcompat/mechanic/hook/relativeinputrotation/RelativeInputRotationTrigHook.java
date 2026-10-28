package me.wolfii.legacyparkourcompat.mechanic.hook.relativeinputrotation;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;

/** Historical trigonometric lookup operations used by relative movement input. */
@MechanicType("relative_input.rotation_trig")
public interface RelativeInputRotationTrigHook extends VersionedMechanic {
    float sin(float radians);

    float cos(float radians);
}
