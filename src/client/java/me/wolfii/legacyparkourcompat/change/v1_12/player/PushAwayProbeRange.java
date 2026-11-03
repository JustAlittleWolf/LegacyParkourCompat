package me.wolfii.legacyparkourcompat.change.v1_12.player;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.player.PushAwayProbeRangeHook;

import java.util.Optional;

/** Keeps earlier profiles on the existing push-away occupancy path. */
@MovementChange(emulates = ParkourVersion.V1_12)
public final class PushAwayProbeRange implements PushAwayProbeRangeHook {
    @Override
    public Optional<ProbeRange> probeRange(double boxMinY, ProbeRole role, boolean swimming) {
        return Optional.empty();
    }
}
