package me.wolfii.legacyparkourcompat.change.v1_20_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.player.PlayerMovementSpeedHook;
import net.minecraft.resources.Identifier;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

/**
 * Replays the UUID HashSet operation buckets used through 1.20.4 when their
 * iteration order is independent of the old table capacity. The 1.20.4 Mojmap
 * AttributeInstance#getModifiers/calculateValue path uses UUID-hashed sets and
 * Player#getSpeed casts the sanitized result to float. In 1.20.5 Mojmap,
 * AttributeInstance#calculateValue consumes UUID-keyed map values in the
 * changed order. Current identifiers are mapped only when their old UUID is
 * known; the lossy soul_speed alias and unknown identifiers retain native
 * behavior. Calculation uses only the captured Snapshot, including the
 * movement-speed attribute bounds and local-client identity supplied by the
 * client runtime.
 *
 * <p>Sources: 1.20.4 Mojmap
 * {@code AttributeInstance#getModifiers/calculateValue},
 * {@code AttributeModifier#hashCode}, {@code Player#getSpeed}; 1.20.5 Mojmap
 * {@code AttributeInstance#getModifiers/calculateValue},
 * {@code Player#getSpeed}; and 26.2 unobfuscated
 * {@code AttributeModifierIdFix#ID_MAP} for current identifier aliases.
 */
@MovementChange(emulates = ParkourVersion.V1_20_2)
public final class LegacyPlayerMovementSpeed implements PlayerMovementSpeedHook {
    private static final int INITIAL_TABLE_CAPACITY = 16;
    private static final int MAX_SUPPORTED_OPERATION_SIZE = 12;
    private static final Map<String, UUID> BUILTIN_UUIDS = Map.of(
        "minecraft:sprinting", UUID.fromString("662a6b8d-da3e-4c1c-8813-96ea6097278d"),
        "minecraft:powder_snow", UUID.fromString("1eaf83ff-7207-4596-b37a-d7a07b3ec4ce"),
        "minecraft:effect.speed", UUID.fromString("91aeaa56-376b-4498-935b-2f7f68070635"),
        "minecraft:effect.slowness", UUID.fromString("7107de5e-7ce8-4030-940e-514c1f160890")
    );

    @Override
    public Optional<Float> movementSpeed(Snapshot snapshot) {
        if (!snapshot.localClientPlayer()
            || !Double.isFinite(snapshot.minimumValue())
            || !Double.isFinite(snapshot.maximumValue())
            || snapshot.minimumValue() > snapshot.maximumValue()) {
            return Optional.empty();
        }

        Map<Integer, List<OrderedModifier>> byOperation = new HashMap<>();
        Set<UUID> seenIds = new HashSet<>();
        for (Modifier modifier : snapshot.modifiers()) {
            UUID legacyId = legacyUuid(modifier.identifier());
            if (legacyId == null || !seenIds.add(legacyId)
                || modifier.operation() < 0 || modifier.operation() > 2) {
                return Optional.empty();
            }
            List<OrderedModifier> operation = byOperation.computeIfAbsent(
                modifier.operation(), ignored -> new ArrayList<>());
            operation.add(new OrderedModifier(legacyId, modifier.amount()));
            if (operation.size() > MAX_SUPPORTED_OPERATION_SIZE) {
                return Optional.empty();
            }
        }

        List<OrderedModifier> additions = ordered(byOperation.getOrDefault(0, List.of()));
        List<OrderedModifier> baseMultipliers = ordered(byOperation.getOrDefault(1, List.of()));
        List<OrderedModifier> totalMultipliers = ordered(byOperation.getOrDefault(2, List.of()));
        if (additions == null || baseMultipliers == null || totalMultipliers == null) {
            return Optional.empty();
        }

        double base = snapshot.baseValue();
        for (OrderedModifier modifier : additions) {
            base += modifier.amount();
        }
        double result = base;
        for (OrderedModifier modifier : baseMultipliers) {
            result += base * modifier.amount();
        }
        for (OrderedModifier modifier : totalMultipliers) {
            result *= 1.0 + modifier.amount();
        }

        double sanitized;
        if (Double.isNaN(result)) {
            sanitized = snapshot.minimumValue();
        } else if (result < snapshot.minimumValue()) {
            sanitized = snapshot.minimumValue();
        } else if (result > snapshot.maximumValue()) {
            sanitized = snapshot.maximumValue();
        } else {
            sanitized = result;
        }
        return Optional.of((float)sanitized);
    }

    private static UUID legacyUuid(Identifier id) {
        UUID builtin = BUILTIN_UUIDS.get(id.toString());
        if (builtin != null) {
            return builtin;
        }
        if (!"minecraft".equals(id.getNamespace())) {
            return null;
        }

        String path = id.getPath();
        try {
            UUID parsed = UUID.fromString(path);
            return parsed.toString().equals(path) ? parsed : null;
        } catch (IllegalArgumentException ignored) {
            return null;
        }
    }

    private static List<OrderedModifier> ordered(List<OrderedModifier> modifiers) {
        for (int i = 0; i < modifiers.size(); i++) {
            for (int j = i + 1; j < modifiers.size(); j++) {
                int leftHash = spread(modifiers.get(i).id());
                int rightHash = spread(modifiers.get(j).id());
                int priorOrder = Integer.compare(
                    bucket(leftHash, INITIAL_TABLE_CAPACITY),
                    bucket(rightHash, INITIAL_TABLE_CAPACITY));
                if (priorOrder == 0) {
                    return null;
                }
                for (int bucketBits = 5; bucketBits <= 30; bucketBits++) {
                    int capacity = 1 << bucketBits;
                    int order = Integer.compare(bucket(leftHash, capacity), bucket(rightHash, capacity));
                    if (order == 0 || order != priorOrder) {
                        return null;
                    }
                }
            }
        }

        List<OrderedModifier> result = new ArrayList<>(modifiers);
        result.sort(Comparator.comparingInt(modifier -> bucket(spread(modifier.id()), INITIAL_TABLE_CAPACITY)));
        return result;
    }

    private static int spread(UUID id) {
        int hash = id.hashCode();
        return hash ^ (hash >>> 16);
    }

    private static int bucket(int spreadHash, int capacity) {
        return spreadHash & (capacity - 1);
    }

    private record OrderedModifier(UUID id, double amount) {
    }
}
