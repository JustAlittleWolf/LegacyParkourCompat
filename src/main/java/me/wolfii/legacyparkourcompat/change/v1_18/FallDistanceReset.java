package me.wolfii.legacyparkourcompat.change.v1_18;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.state.PlayerFallDistanceResetHook;

/**
 * Source evidence (paths are relative to decompiled_minecraft/):
 * 1.17.1/mojmap/net/minecraft/world/entity/Entity.java:
 *   move; lines [[553, 566]].
 *   Source SHA-256: ab28e1fba924771ec048140dfd293ee5a46a7dfe81f71a1a0b1aecc1927232de.
 * 1.18.2/mojmap/net/minecraft/world/entity/Entity.java:
 *   move; lines [[562, 575]].
 *   Source SHA-256: 2228fdaca5793171cbd94038306d571a6ada78ca96f5734efb4cada5b744c10a.
 * Bounded source decisions:
 *   Decision 4a5eae32b5819418dbd59eb4a763ee24ea1f2c52:development/research/archive/legacy-workflows/source-boundary-reviews/2026-10-08-mc1171-1182-snapshot-review.md; ACCEPT, bounded.
 *   Reviewed snapshot e531086eecc778432b40b2b9ef39499b8a7f0dba:workflows/source-campaign-2026-10-07/1.17.1--1.18.2/findings/F-005-fall-distance-reset-edge-gate.md.
 *   Decision 4a5eae32b5819418dbd59eb4a763ee24ea1f2c52:development/research/archive/legacy-workflows/source-boundary-reviews/2026-10-09-F005-r2-independent-review.md; ACCEPT, r2 witness correction.
 *   Reviewed snapshot 96b6f7253491903c0da443d74102dcc451be1245.
 * Source-content correspondence and compilation do not establish tick-level runtime parity.
 */
@MovementChange(emulates = ParkourVersion.V1_18)
public final class FallDistanceReset implements PlayerFallDistanceResetHook {
    @Override
    public boolean suppressMovementFallDistanceReset(ParkourVersion selected) {
        return selected.newerThanOrEqual(ParkourVersion.V1_17_1)
            && selected.olderThan(ParkourVersion.V1_18_2);
    }
}
