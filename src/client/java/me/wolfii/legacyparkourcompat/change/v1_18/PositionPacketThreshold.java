package me.wolfii.legacyparkourcompat.change.v1_18;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.network.PositionPacketThresholdHook;

/**
 * Source evidence (paths are relative to decompiled_minecraft/):
 * 1.17.1/mojmap/net/minecraft/client/player/LocalPlayer.java:
 *   sendPosition.
 * 1.18.2/mojmap/net/minecraft/client/player/LocalPlayer.java:
 *   sendPosition.
 * Source-content correspondence and compilation do not establish tick-level runtime parity.
 */
@MovementChange(emulates = ParkourVersion.V1_18)
public final class PositionPacketThreshold implements PositionPacketThresholdHook {
    @Override
    public double squaredDisplacementThreshold(double vanillaThreshold) {
        return 9.0E-4;
    }
}
