package me.wolfii.legacyparkourcompat.change.v1_11_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.PlayerRestSafetyQueryBehavior;

@MovementChange(emulates = ParkourVersion.V1_11_2)
public final class SleepSafetyQueryGate implements PlayerRestSafetyQueryBehavior {
    @Override
    public boolean skipSafetyQuery(boolean vanilla) {
        return false;
    }
}
