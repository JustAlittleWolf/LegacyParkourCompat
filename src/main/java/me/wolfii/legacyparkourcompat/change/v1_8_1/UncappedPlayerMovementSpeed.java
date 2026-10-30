package me.wolfii.legacyparkourcompat.change.v1_8_1;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.player.MovementSpeedModifierIdentity;
import me.wolfii.legacyparkourcompat.mechanic.hook.player.PlayerMovementSpeedHook;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * Reconstructs the uncapped movement-speed attribute used by Minecraft 1.8.0–1.8.4.
 * Exact 1.8.0–1.8.1 Mojang bytecode (Feather Gen2 +build.2 Tiny) and mapped
 * 1.8.2–1.8.4 source show {@code ModifiableEntityAttributeInstance.computeValue()}
 * applying operations 0, 1, then 2, {@code RangedEntityAttribute.clamp()}
 * bounding the result to [0.0, Double.MAX_VALUE], and
 * {@code PlayerEntity.getSpeed()} casting the result to float. The source-bound
 * OpenJDK 8u412 {@code HashSet(Collection)} / {@code HashMap} order uses
 * UUID hash codes and 16-slot buckets, giving Slowness, sprint, then Speed
 * within each category for every subset of the supported built-in modifiers.
 * The historical movement-speed attribute has no parent, and these three
 * built-in modifiers use operation 2. This is a reference-runtime order, not
 * a Java API guarantee for every Java 8 implementation. Unsupported history,
 * non-total operation history, or a bucket collision retains native behavior;
 * the modern aggregate modifier order is not used. The exact
 * 1.8.4→1.8.5 Feather transition first adds a 1024 cap in 1.8.5; this provider
 * is bounded to the 1.8.1–1.8.4 source boundary; 1.8.5 and later profiles retain
 * the native cap.
 *
 * <p>Sources: 1.8/1.8.1 Mojang client bytecode resolved through Feather Gen2
 * +build.2 Tiny to the mapped {@code ModifiableEntityAttributeInstance.computeValue()},
 * {@code RangedEntityAttribute.clamp()} and {@code PlayerEntity.getSpeed()};
 * 1.8.2–1.8.4 Feather Gen2 +build.2 mapped sources for those members; and the
 * 1.8.4→1.8.5 Feather Gen2 +build.2 {@code EntityAttributes.MOVEMENT_SPEED}
 * declaration. The hash-table reference is OpenJDK 8u412-b08
 * {@code HashSet(Collection)}, {@code HashMap.hash()} and table iteration.
 */
@MovementChange(emulates = ParkourVersion.V1_8_1)
public final class UncappedPlayerMovementSpeed implements PlayerMovementSpeedHook {
    @Override
    public Optional<Float> movementSpeed(Snapshot snapshot) {
        if (!snapshot.history().complete() || !snapshot.history().totalOnly()
            || snapshot.modifiers().size() > 3) {
            return Optional.empty();
        }
        for (var identifier : snapshot.history().identifiers()) {
            if (MovementSpeedModifierIdentity.oldUuid(identifier) == null) {
                return Optional.empty();
            }
        }

        List<List<OrderedModifier>> categories = new ArrayList<>(3);
        categories.add(new ArrayList<>());
        categories.add(new ArrayList<>());
        categories.add(new ArrayList<>());
        for (Modifier modifier : snapshot.modifiers()) {
            if (modifier.operation() != 2) {
                return Optional.empty();
            }
            var oldUuid = MovementSpeedModifierIdentity.oldUuid(modifier.identifier());
            if (oldUuid == null) {
                return Optional.empty();
            }
            int bucket = MovementSpeedModifierIdentity.openJdk8u412HashSetBucket(oldUuid);
            categories.get(modifier.operation()).add(new OrderedModifier(modifier, bucket));
        }

        List<Modifier> orderedModifiers = new ArrayList<>(snapshot.modifiers().size());
        for (List<OrderedModifier> category : categories) {
            category.sort(Comparator.comparingInt(OrderedModifier::bucket));
            for (int i = 1; i < category.size(); i++) {
                if (category.get(i - 1).bucket() == category.get(i).bucket()) {
                    return Optional.empty();
                }
            }
            for (OrderedModifier modifier : category) {
                orderedModifiers.add(modifier.modifier());
            }
        }

        double value = snapshot.baseValue();
        for (Modifier modifier : orderedModifiers) {
            if (modifier.operation() == 0) {
                value += modifier.amount();
            }
        }

        double operationZeroValue = value;
        for (Modifier modifier : orderedModifiers) {
            if (modifier.operation() == 1) {
                value += operationZeroValue * modifier.amount();
            }
        }

        for (Modifier modifier : orderedModifiers) {
            if (modifier.operation() == 2) {
                value *= 1.0 + modifier.amount();
            }
        }

        // Comparison-based clamping preserves NaN as in RangedEntityAttribute.
        if (value < 0.0) {
            value = 0.0;
        } else if (value > Double.MAX_VALUE) {
            value = Double.MAX_VALUE;
        }
        return Optional.of((float) value);
    }

    private record OrderedModifier(Modifier modifier, int bucket) {
    }
}
