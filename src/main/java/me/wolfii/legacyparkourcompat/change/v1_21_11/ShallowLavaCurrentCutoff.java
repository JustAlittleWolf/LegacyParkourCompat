package me.wolfii.legacyparkourcompat.change.v1_21_11;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.ShallowLavaCurrentCutoffBehavior;

@MovementChange(emulates = ParkourVersion.V1_21_11)
public final class ShallowLavaCurrentCutoff implements ShallowLavaCurrentCutoffBehavior {
    @Override
    public boolean bypassWeakCurrentCutoff(double lavaHeight) {
        return lavaHeight > 0.0 && lavaHeight < 0.4;
    }
}
