package me.wolfii.legacyparkourcompat.impl;

import me.wolfii.legacyparkourcompat.api.MinecraftVersion;
import me.wolfii.legacyparkourcompat.mechanic.MechanicKey;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Picks, for each mechanic, the change whose {@code vanillaChangedIn} is the
 * smallest version still newer than the selection ("closest to the target").
 *
 * <p>Selecting 1.8.9 therefore loads every post-1.8.9 delta, and when the same
 * mechanic changed twice, only the first change after 1.8.9 is used.
 */
final class ChangeResolver {
    private ChangeResolver() {
    }

    static Map<MechanicKey, RegisteredChange> resolve(Collection<RegisteredChange> changes, MinecraftVersion target) {
        Map<MechanicKey, List<RegisteredChange>> grouped = changes.stream()
                .collect(Collectors.groupingBy(RegisteredChange::key));
        Map<MechanicKey, RegisteredChange> resolved = new HashMap<>();
        for (Map.Entry<MechanicKey, List<RegisteredChange>> entry : grouped.entrySet()) {
            entry.getValue().stream()
                    .filter(change -> change.vanillaChangedIn().newerThan(target))
                    .min(Comparator.comparing(RegisteredChange::vanillaChangedIn))
                    .ifPresent(change -> resolved.put(entry.getKey(), change));
        }
        return Map.copyOf(resolved);
    }
}
