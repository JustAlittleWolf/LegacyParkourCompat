package me.wolfii.legacyparkourcompat.impl;

import me.wolfii.legacyparkourcompat.LegacyParkourCompat;
import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MechanicKey;
import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.MovementChangeRegistry;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;

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
    public void register(Object implementation) {
        if (!(implementation instanceof VersionedMechanic mechanic)) {
            throw new IllegalArgumentException(
                    implementation.getClass().getName() + " must implement a @MechanicType interface"
            );
        }
        MovementChange annotation = implementation.getClass().getAnnotation(MovementChange.class);
        if (annotation == null) {
            throw new IllegalArgumentException(
                    implementation.getClass().getName() + " is missing @MovementChange"
            );
        }
        @SuppressWarnings("unchecked")
        Class<VersionedMechanic> type = (Class<VersionedMechanic>) mechanicType(implementation.getClass());
        this.register(type, annotation.emulates(), mechanic);
    }

    @Override
    public <T extends VersionedMechanic> void register(Class<T> type, ParkourVersion emulates, T implementation) {
        if (!type.isInstance(implementation)) {
            throw new IllegalArgumentException(implementation + " is not a " + type.getName());
        }
        MechanicKey key = MechanicKey.of(type, implementation.variant());
        this.changes.add(new RegisteredChange(key, emulates, implementation));
        LegacyParkourCompat.LOGGER.debug(
                "Registered {} emulating {} (vanilla changed in {})",
                key.qualifiedId(),
                emulates,
                emulates.next()
        );
        this.onChanged.run();
    }

    @SuppressWarnings("unchecked")
    private static Class<? extends VersionedMechanic> mechanicType(Class<?> implementation) {
        for (Class<?> iface : implementation.getInterfaces()) {
            if (VersionedMechanic.class.isAssignableFrom(iface) && iface.isAnnotationPresent(MechanicType.class)) {
                return (Class<? extends VersionedMechanic>) iface;
            }
        }
        Class<?> superclass = implementation.getSuperclass();
        if (superclass != null && superclass != Object.class) {
            return mechanicType(superclass);
        }
        throw new IllegalArgumentException(
                implementation.getName() + " does not implement a @MechanicType interface"
        );
    }
}
