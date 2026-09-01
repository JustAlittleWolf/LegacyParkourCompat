package me.wolfii.legacyparkourcompat.impl;

import me.wolfii.legacyparkourcompat.api.ParkourEra;
import me.wolfii.legacyparkourcompat.mechanic.MechanicKey;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Picks, for each mechanic, the change whose {@code vanillaChangedIn} is the
 * smallest version still at or after the next era ("closest to the target").
 *
 * <p>The vanilla/disabled era resolves to no changes. Selecting the 1.9 era
 * (which also covers 1.9.1 and 1.9.2) applies deltas introduced in 1.10+, and
 * when the same mechanic changed twice, only the first change after 1.9.x.
 */
final class ChangeResolver {
    private ChangeResolver() {
    }

    static Map<MechanicKey, RegisteredChange> resolve(Collection<RegisteredChange> changes, ParkourEra era) {
        if (era.isVanilla()) {
            return Map.of();
        }
        Map<MechanicKey, List<RegisteredChange>> grouped = changes.stream()
                .collect(Collectors.groupingBy(RegisteredChange::key));
        Map<MechanicKey, RegisteredChange> resolved = new HashMap<>();
        for (Map.Entry<MechanicKey, List<RegisteredChange>> entry : grouped.entrySet()) {
            entry.getValue().stream()
                    .filter(change -> change.vanillaChangedIn().newerThanOrEqual(era.untilExclusive()))
                    .min(Comparator.comparing(RegisteredChange::vanillaChangedIn))
                    .ifPresent(change -> resolved.put(entry.getKey(), change));
        }
        return Map.copyOf(resolved);
    }
}
