package me.wolfii.legacyparkourcompat.change.v1_8;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.player.MovementSpeedModifierIdentity;
import me.wolfii.legacyparkourcompat.mechanic.hook.player.PlayerMovementSpeedHook;

import java.util.Optional;

/**
 * Reconstructs the uncapped movement-speed attribute used by Minecraft 1.8.0–1.8.4.
 * Exact 1.8.0–1.8.1 Mojang bytecode (Feather Gen2 +build.2 Tiny) and mapped
 * 1.8.2–1.8.4 source show {@code ModifiableEntityAttributeInstance.computeValue()}
 * applying operations 0, 1, then 2, {@code RangedEntityAttribute.clamp()}
 * bounding the result to [0.0, Double.MAX_VALUE], and
 * {@code PlayerEntity.getSpeed()} casting the result to float. The exact
 * 1.8.4→1.8.5 Feather transition first adds a 1024 cap in 1.8.5; this provider
 * is bounded to the V1_8 alias and leaves that and later profiles native.
 *
 * <p>1.8 builds category-local UUID HashSets and merges parents before iterating.
 * The client supplies only a global modern modifier collection, which cannot
 * reproduce those sets. This provider therefore retains native behavior when
 * multiple current modifiers occupy the same operation category, or when any
 * modifier identifier seen since construction lacks a proven old UUID mapping.
 *
 * <p>Sources: 1.8/1.8.1 Mojang client bytecode resolved through Feather Gen2
 * +build.2 Tiny to the mapped {@code ModifiableEntityAttributeInstance.computeValue()},
 * {@code RangedEntityAttribute.clamp()} and {@code PlayerEntity.getSpeed()};
 * 1.8.2–1.8.4 Feather Gen2 +build.2 mapped sources for those members; and the
 * 1.8.4→1.8.5 Feather Gen2 +build.2 {@code EntityAttributes.MOVEMENT_SPEED}
 * declaration.
 */
@MovementChange(emulates = ParkourVersion.V1_8)
public final class UncappedPlayerMovementSpeed implements PlayerMovementSpeedHook {
    @Override
    public Optional<Float> movementSpeed(Snapshot snapshot) {
        if (!snapshot.history().complete()) {
            return Optional.empty();
        }
        for (var identifier : snapshot.history().identifiers()) {
            if (MovementSpeedModifierIdentity.oldUuid(identifier) == null) {
                return Optional.empty();
            }
        }

        int[] categoryCounts = new int[3];
        for (Modifier modifier : snapshot.modifiers()) {
            if (modifier.operation() < 0 || modifier.operation() > 2
                || MovementSpeedModifierIdentity.oldUuid(modifier.identifier()) == null
                || ++categoryCounts[modifier.operation()] > 1) {
                return Optional.empty();
            }
        }

        double value = snapshot.baseValue();
        for (Modifier modifier : snapshot.modifiers()) {
            if (modifier.operation() == 0) {
                value += modifier.amount();
            }
        }

        double operationZeroValue = value;
        for (Modifier modifier : snapshot.modifiers()) {
            if (modifier.operation() == 1) {
                value += operationZeroValue * modifier.amount();
            }
        }

        for (Modifier modifier : snapshot.modifiers()) {
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
}
