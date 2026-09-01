package me.wolfii.legacyparkourcompat.api;

import me.wolfii.legacyparkourcompat.mechanic.MechanicKey;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;

import java.util.Map;
import java.util.Optional;

/**
 * Snapshot of which historical deltas are active for one selected version.
 */
public final class ActiveMovementProfile {
    private final ParkourVersion target;
    private final Map<MechanicKey, Object> active;

    public ActiveMovementProfile(ParkourVersion target, Map<MechanicKey, Object> active) {
        this.target = target;
        this.active = Map.copyOf(active);
    }

    public static ActiveMovementProfile current() {
        return new ActiveMovementProfile(ParkourVersion.CURRENT, Map.of());
    }

    public ParkourVersion target() {
        return this.target;
    }

    public boolean isCurrent() {
        return this.target.isCurrent() || this.active.isEmpty();
    }

    public int size() {
        return this.active.size();
    }

    public <T extends VersionedMechanic> Optional<T> get(Class<T> type) {
        return this.get(type, "");
    }

    public <T extends VersionedMechanic> Optional<T> get(Class<T> type, String variant) {
        Object implementation = this.active.get(MechanicKey.of(type, variant));
        if (!type.isInstance(implementation)) {
            return Optional.empty();
        }
        return Optional.of(type.cast(implementation));
    }

    public String describe() {
        if (this.isCurrent() && this.active.isEmpty()) {
            return this.target.id();
        }
        StringBuilder builder = new StringBuilder(this.target.id())
            .append(" (")
            .append(this.active.size())
            .append(" change(s): ");
        boolean first = true;
        for (MechanicKey key : this.active.keySet()) {
            if (!first) {
                builder.append(", ");
            }
            first = false;
            builder.append(key.qualifiedId());
        }
        return builder.append(')').toString();
    }
}
