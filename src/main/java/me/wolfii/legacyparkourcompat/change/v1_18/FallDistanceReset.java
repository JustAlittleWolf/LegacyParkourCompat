package me.wolfii.legacyparkourcompat.change.v1_18;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.PlayerFallDistanceResetBehavior;

@MovementChange(emulates = ParkourVersion.V1_18)
public final class FallDistanceReset implements PlayerFallDistanceResetBehavior {
    @Override
    public boolean suppressMovementFallDistanceReset(ParkourVersion selected) {
        return selected.newerThanOrEqual(ParkourVersion.V1_17_1)
            && selected.olderThan(ParkourVersion.V1_18_2);
    }
}
