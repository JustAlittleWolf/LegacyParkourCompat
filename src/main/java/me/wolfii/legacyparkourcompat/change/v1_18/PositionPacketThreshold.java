package me.wolfii.legacyparkourcompat.change.v1_18;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.network.PositionPacketThresholdHook;

/**
 * Source evidence (paths are relative to decompiled_minecraft/):
 * 1.17.1/mojmap/net/minecraft/client/player/LocalPlayer.java:
 *   sendPosition; lines [[240, 269]].
 *   Source SHA-256: c9a91cb6cb57806bc8d22e5bfe2d97daaf21d5d2a48f34a6e2c53164c61c5812.
 * 1.18.2/mojmap/net/minecraft/client/player/LocalPlayer.java:
 *   sendPosition; lines [[232, 261]].
 *   Source SHA-256: 99c2d18bcd23243afb8f95c5bafb21fb0be7ea04aacbb14fcf7be7ced2c9c095.
 * Bounded source decisions:
 *   Decision 4a5eae32b5819418dbd59eb4a763ee24ea1f2c52:development/research/archive/legacy-workflows/source-boundary-reviews/2026-10-08-mc1171-1182-snapshot-review.md; ACCEPT, bounded.
 *   Reviewed snapshot e531086eecc778432b40b2b9ef39499b8a7f0dba:workflows/source-campaign-2026-10-07/1.17.1--1.18.2/findings/F-007-position-packet-threshold.md.
 *   Decision 4a5eae32b5819418dbd59eb4a763ee24ea1f2c52:development/research/archive/legacy-workflows/source-boundary-reviews/2026-10-09-F007-position-packet-threshold-boundary-review.md; ACCEPT, sampled source boundary.
 * Source-content correspondence and compilation do not establish tick-level runtime parity.
 */
@MovementChange(emulates = ParkourVersion.V1_18)
public final class PositionPacketThreshold implements PositionPacketThresholdHook {
    @Override
    public double squaredDisplacementThreshold(double vanillaThreshold) {
        return 9.0E-4;
    }
}
