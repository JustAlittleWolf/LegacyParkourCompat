package me.wolfii.legacyparkourcompat.impl;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MechanicKey;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Picks, for each mechanic, the change whose {@code vanillaChangedIn} is the
 * smallest version still at or after the next version ("closest to the target").
 *
 * <p>The vanilla/disabled version resolves to no changes. Selecting 1.9
 * (which also covers 1.9.1 and 1.9.2) applies deltas introduced in 1.10+, and
 * when the same mechanic changed twice, only the first change after 1.9.x.
 */
final class ChangeResolver {
    private ChangeResolver() {
    }

    static Map<MechanicKey, RegisteredChange> resolve(Collection<RegisteredChange> changes, ParkourVersion version) {
        if (version.isVanilla()) {
            return Map.of();
        }
        Map<MechanicKey, List<RegisteredChange>> grouped = changes.stream()
                .collect(Collectors.groupingBy(RegisteredChange::key));
        Map<MechanicKey, RegisteredChange> resolved = new HashMap<>();
        for (Map.Entry<MechanicKey, List<RegisteredChange>> entry : grouped.entrySet()) {
            entry.getValue().stream()
                    .filter(change -> change.vanillaChangedIn().newerThanOrEqual(version.untilExclusive()))
                    .min(Comparator.comparing(RegisteredChange::vanillaChangedIn))
                    .ifPresent(change -> resolved.put(entry.getKey(), change));
        }
        return Map.copyOf(resolved);
    }
}
