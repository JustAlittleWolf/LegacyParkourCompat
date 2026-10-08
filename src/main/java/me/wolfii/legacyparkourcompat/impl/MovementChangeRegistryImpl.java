package me.wolfii.legacyparkourcompat.impl;

import me.wolfii.legacyparkourcompat.LegacyParkourCompat;
import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.*;

import java.util.ArrayList;
import java.util.List;

final class MovementChangeRegistryImpl implements MovementChangeRegistry {
    private final List<RegisteredChange> changes = new ArrayList<>();
    private final Runnable onChanged;

    MovementChangeRegistryImpl(Runnable onChanged) {
        this.onChanged = onChanged;
    }

    List<RegisteredChange> snapshot() {
        return List.copyOf(this.changes);
    }

    @Override
    public <T extends VersionedMechanic> void register(Class<T> type, ParkourVersion emulates, T implementation) {
        if (!type.isInstance(implementation)) {
            throw new IllegalArgumentException(implementation + " is not a " + type.getName());
        }
        MechanicKey key = MechanicKey.of(type, implementation.variant());
        for (RegisteredChange existing : this.changes) {
            if (existing.key().equals(key) && existing.emulates() == emulates) {
                LegacyParkourCompat.LOGGER.warn(
                    "Duplicate movement change {} emulating {}; both remain registered",
                    key.qualifiedId(),
                    emulates
                );
                break;
            }
        }
        this.changes.add(new RegisteredChange(key, emulates, implementation));
        LegacyParkourCompat.LOGGER.debug(
            "Registered {} emulating {} (vanilla changed in {})",
            key.qualifiedId(),
            emulates,
            emulates.next()
        );
        this.onChanged.run();
    }
}
