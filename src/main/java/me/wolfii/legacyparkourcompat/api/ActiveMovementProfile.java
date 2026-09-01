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

    public ParkourVersion target() {
        return this.target;
    }

    public boolean isVanilla() {
        return this.target.isVanilla() || this.active.isEmpty();
    }

    public static ActiveMovementProfile vanilla() {
        return new ActiveMovementProfile(ParkourVersion.VANILLA, Map.of());
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
        if (this.isVanilla() && this.active.isEmpty()) {
            return this.target + " (vanilla)";
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
