package me.wolfii.legacyparkourcompat.change.v1_12;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.entity.turn.CurrentPitchHook;

/**
 * The exact 1.12.2 Feather Entity.updateLocalPlayerCamera(float, float) takes
 * float camera deltas, before the double-cursor-input method begins in 1.13
 * Feather Entity.updateLocalPlayerCamera(double, double). This identity change
 * is only the resolver floor for the distinct precision operation.
 */
@MovementChange(emulates = ParkourVersion.V1_12)
public final class CurrentPitchNative implements CurrentPitchHook {
    @Override
    public float transformFirstPitchArgument(float currentPitch, double pitchInput, float vanillaArgument) {
        return vanillaArgument;
    }
}
