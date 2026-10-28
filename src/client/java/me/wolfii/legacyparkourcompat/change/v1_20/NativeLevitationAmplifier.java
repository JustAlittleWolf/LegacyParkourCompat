package me.wolfii.legacyparkourcompat.change.v1_20;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.player.LevitationAmplifierHook;

/**
 * Keeps the amplifier movement read native before the accepted 1.20.2 profile bucket.
 * This resolver boundary lets the 1.20.2 change apply only to its established range without
 * checking the selected profile inside the mixin or leaving earlier profiles exposed to it.
 */
@MovementChange(emulates = ParkourVersion.V1_20)
public final class NativeLevitationAmplifier implements LevitationAmplifierHook {
    @Override
    public int movementAmplifier(int receivedAmplifier) {
        return receivedAmplifier;
    }
}
