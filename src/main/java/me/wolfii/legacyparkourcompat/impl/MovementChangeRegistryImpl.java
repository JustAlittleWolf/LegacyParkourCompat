package me.wolfii.legacyparkourcompat.impl;

import me.wolfii.legacyparkourcompat.LegacyParkourCompat;
import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.*;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

final class MovementChangeRegistryImpl implements MovementChangeRegistry {
    private final List<RegisteredChange> changes = new ArrayList<>();
    private final Runnable onChanged;

    MovementChangeRegistryImpl(Runnable onChanged) {
        this.onChanged = onChanged;
    }

    /**
     * Every {@link MechanicType} interface this class implements, including those
     * inherited through super-interfaces and superclasses.
     */
    @SuppressWarnings("unchecked")
    static List<Class<? extends VersionedMechanic>> mechanicTypes(Class<?> implementation) {
        Set<Class<?>> seen = new LinkedHashSet<>();
        collectMechanicTypes(implementation, seen);
        List<Class<? extends VersionedMechanic>> types = new ArrayList<>();
        for (Class<?> type : seen) {
            if (type.isInterface()
                && VersionedMechanic.class.isAssignableFrom(type)
                && type.isAnnotationPresent(MechanicType.class)
            ) {
                types.add((Class<? extends VersionedMechanic>) type);
            }
        }
        if (types.isEmpty()) {
            throw new IllegalArgumentException(
                implementation.getName() + " does not implement a @MechanicType interface"
            );
        }
        return List.copyOf(types);
    }

    private static void collectMechanicTypes(Class<?> type, Set<Class<?>> seen) {
        if (type == null || type == Object.class || !seen.add(type)) {
            return;
        }
        for (Class<?> iface : type.getInterfaces()) {
            collectMechanicTypes(iface, seen);
        }
        collectMechanicTypes(type.getSuperclass(), seen);
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
        for (Class<? extends VersionedMechanic> type : mechanicTypes(implementation.getClass())) {
            @SuppressWarnings("unchecked")
            Class<VersionedMechanic> cast = (Class<VersionedMechanic>) type;
            this.register(cast, annotation.emulates(), mechanic);
        }
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
