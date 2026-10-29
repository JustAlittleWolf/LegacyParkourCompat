package me.wolfii.legacyparkourcompat.change.v1_21_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.player.PlayerChunkReadinessHook;

/** Keeps the readiness operation native through the unproven 1.21.0–1.21.3 range. */
@MovementChange(emulates = ParkourVersion.V1_21_2)
public final class NativePlayerChunkReadiness implements PlayerChunkReadinessHook {
    @Override
    public boolean isReady(Probe probe) {
        return probe.nativeReady();
    }
}
