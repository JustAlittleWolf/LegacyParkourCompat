package me.wolfii.legacyparkourcompat.impl;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MechanicKey;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Picks, for each mechanic, the change whose {@code emulates} is closest to the
 * selection without being older than it.
 *
 * <p>{@link ParkourVersion#CURRENT} resolves to no changes. Selecting 1.9
 * (which also covers 1.9.1 and 1.9.2) applies deltas that emulate 1.9 or later;
 * selecting 1.9.3 (which also covers 1.9.4) starts at its dedicated profile
 * boundary. When the same mechanic changed twice, only the change closest to
 * the selected version is resolved.
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
