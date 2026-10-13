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
                throw new IllegalStateException("Duplicate movement change " + key.qualifiedId()
                    + " emulating " + emulates + ": " + existing.implementation().getClass().getName()
                    + " and " + implementation.getClass().getName());
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
