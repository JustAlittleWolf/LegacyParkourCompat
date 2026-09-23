package me.wolfii.legacyparkourcompat.change.v1_8;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;

/** 1.8–1.12.2 used the previous one-ULP ground-factor constant. */
@MovementChange(emulates = ParkourVersion.V1_12)
public final class GroundAccelerationThrough112 extends LegacyGroundAcceleration {
    public GroundAccelerationThrough112() {
        super(0.16277136F);
    }
}
