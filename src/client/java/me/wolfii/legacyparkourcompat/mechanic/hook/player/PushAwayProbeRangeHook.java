package me.wolfii.legacyparkourcompat.mechanic.hook.player;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;

import java.util.Optional;

/** Selects the vertical rows checked by one local-player push-away probe. */
@MechanicType("player.push-away.probe-range")
public interface PushAwayProbeRangeHook extends VersionedMechanic {
    /** Empty preserves the existing native and independently resolved predicate path. */
    Optional<ProbeRange> probeRange(double boxMinY, ProbeRole role, boolean swimming);

    enum ProbeRole {
        ORIGIN,
        CANDIDATE
    }

    /** Inclusive lower and exclusive upper block Y bounds. */
    record ProbeRange(int minY, int maxYExclusive) {
    }
}
