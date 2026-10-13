package me.wolfii.legacyparkourcompat.change.v1_8;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.travel.VelocityZeroThresholdHook;
import net.minecraft.world.entity.LivingEntity;

/**
 * Source evidence (paths are relative to decompiled_minecraft/):
 * 1.8.9/ornithe-feather/net/minecraft/entity/living/LivingEntity.java:
 *   public void mobTick() {; lines 1385-1458.
 *   Source SHA-256: 082831c6578e3a70fa6cea5b90bc3eefc26678259b66334470de22b90b5b0e4e.
 * 1.9.4/ornithe-feather/net/minecraft/entity/living/LivingEntity.java:
 *   public void mobTick() {; lines 1645-1716.
 *   Source SHA-256: bbb7703f18fd5da05c4e4a43a77ea644b388e63c01d34166d308ea52054be4e5.
 * Bounded source decisions:
 *   Review 7c824136305037d63fc6efaacdb4d94fc7199de6; accepted, bounded.
 *   Reviewed snapshot 7437cfb2782e7085bd63b9360ba36e07b605f23f; SHA-256 8d74c339535db2e86dee838bef9b3a1cf1a52872b97b3c3a20a505552d8dc412.
 *   Review 7c824136305037d63fc6efaacdb4d94fc7199de6; accepted, bounded.
 *   Reviewed snapshot 7437cfb2782e7085bd63b9360ba36e07b605f23f; SHA-256 8d74c339535db2e86dee838bef9b3a1cf1a52872b97b3c3a20a505552d8dc412.
 *   Decision 4a5eae32b5819418dbd59eb4a763ee24ea1f2c52:development/research/archive/legacy-workflows/coverage-review-2026-10-07/early.md; [{&#x27;section&#x27;: &#x27;Source admission update&#x27;, &#x27;verdict&#x27;: [&#x27;**Decision: accepted for this bounded finding only.** The owner’s immutable evidence commit is `9222079631775ff5a6d0d566624ba3add7c36c2b`; finding-file SHA-256 `C9D5EF96FCE3DFFC0B433DA08B7DDCBB3BD615C69AA773EF9FC36193AE50CEB2` matches the current file and snapshot log. I independently rehashed all twelve cited ready-source files: paired `PaneBlock`, `Block`, `StainedGlassPaneBlock`, `World`, `Entity`, and `AirBlock`; every hash matches. The finding cites the revised A/B snapshot hashes and states that unavailable original mapped-JAR identity is unproven.&#x27;], &#x27;snapshot_hash_matches&#x27;: True}].
 * Remaining source/applicability gates:
 *   Later applicability gate: exact introduction of campaign-STATE-01-velocity-zero-threshold is unknown within (1.8.9, 1.9.4]
 * Source-content correspondence and compilation do not establish tick-level runtime parity.
 */
@MovementChange(emulates = ParkourVersion.V1_8)
public final class VelocityZeroThreshold implements VelocityZeroThresholdHook {
    private static final double THRESHOLD = 0.005;

    @Override
    public double filterComponent(LivingEntity entity, double component) {
        return Math.abs(component) < THRESHOLD ? 0.0 : component;
    }
}
