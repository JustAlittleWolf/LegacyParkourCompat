package me.wolfii.legacyparkourcompat.change.v1_8;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;

/** 1.8 used a different float rounding order for yaw conversion. */
@MovementChange(emulates = ParkourVersion.V1_8)
public final class GroundAccelerationThrough18 extends LegacyGroundAcceleration {
    public GroundAccelerationThrough18() {
        super(0.16277136F, true);
    }
}
