package me.wolfii.legacyparkourcompat.change.v1_21_11;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.fluid.ShallowWaterCurrentCutoffHook;

@MovementChange(emulates = ParkourVersion.V1_21_11)
public final class ShallowWaterCurrentCutoff implements ShallowWaterCurrentCutoffHook {
    @Override
    public boolean bypassWeakCurrentCutoff(double waterHeight) {
        return waterHeight > 0.0 && waterHeight < 0.4;
    }
}
