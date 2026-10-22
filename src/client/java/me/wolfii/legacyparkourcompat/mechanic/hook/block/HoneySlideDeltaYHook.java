package me.wolfii.legacyparkourcompat.mechanic.hook.block;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;

/** Restores Honey Block's direct vertical-speed checks before 1.21.2. */
@MechanicType("block.honey.slide_delta_y")
public interface HoneySlideDeltaYHook extends VersionedMechanic {
    double oldDeltaY(double actualDeltaY);
}
