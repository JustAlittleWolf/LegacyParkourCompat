package me.wolfii.legacyparkourcompat.mechanic.hook.block;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;

/** Restores Honey Block's historical vertical velocity after a successful slide. */
@MechanicType("block.honey.slide_vertical_velocity")
public interface HoneySlideVelocityHook extends VersionedMechanic {
    double slideVerticalVelocity();
}
