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
 * Picks, for each mechanic, the change whose {@code emulates} is closest to the
 * selection without being older than it.
 *
 * <p>{@link ParkourVersion#CURRENT} resolves to no changes. Selecting 1.9
 * (which also covers 1.9.1 and 1.9.2) applies deltas that emulate 1.9 or later,
 * and when the same mechanic changed twice, only the one closest to 1.9.
 */
final class ChangeResolver {
    private ChangeResolver() {
    }

    static Map<MechanicKey, RegisteredChange> resolve(Collection<RegisteredChange> changes, ParkourVersion selected) {
        if (selected.isCurrent()) {
            return Map.of();
        }
        Map<MechanicKey, List<RegisteredChange>> grouped = changes.stream()
                .collect(Collectors.groupingBy(RegisteredChange::key));
        Map<MechanicKey, RegisteredChange> resolved = new HashMap<>();
        for (Map.Entry<MechanicKey, List<RegisteredChange>> entry : grouped.entrySet()) {
            entry.getValue().stream()
                    .filter(change -> change.emulates().newerThanOrEqual(selected))
                    .min(Comparator.comparingInt(change -> change.emulates().ordinal()))
                    .ifPresent(change -> resolved.put(entry.getKey(), change));
        }
        return Map.copyOf(resolved);
    }
}
