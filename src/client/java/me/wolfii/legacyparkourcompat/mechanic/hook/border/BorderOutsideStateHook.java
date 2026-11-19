package me.wolfii.legacyparkourcompat.mechanic.hook.border;

import java.util.Optional;
import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;

@MechanicType("border.collision_outside_state")
public interface BorderOutsideStateHook extends VersionedMechanic {
    /** Empty preserves the native path for the unmodelled outside-state operation. */
    Optional<Boolean> outside(boolean wasOutside, double x, double z,
                              double minX, double maxX, double minZ, double maxZ);
}
