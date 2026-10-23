package me.wolfii.legacyparkourcompat.mechanic.attribute;

import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/** Bounded provenance captured from an attribute's construction onward. */
public final class MovementSpeedModifierHistory {
    private final Set<Identifier> identifiers = new HashSet<>();
    private boolean boundedTotalOnly = true;

    public void observe(Map<AttributeModifier.Operation, Map<Identifier, AttributeModifier>> operations) {
        if (!boundedTotalOnly) {
            return;
        }
        for (var operation : operations.entrySet()) {
            if (operation.getValue().isEmpty()) {
                continue;
            }
            if (operation.getKey() != AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL) {
                boundedTotalOnly = false;
                return;
            }
            for (Identifier identifier : operation.getValue().keySet()) {
                identifiers.add(identifier);
                if (identifiers.size() > 3) {
                    boundedTotalOnly = false;
                    identifiers.clear();
                    return;
                }
            }
        }
    }

    public boolean boundedTotalOnly() {
        return boundedTotalOnly;
    }

    public Set<Identifier> identifiers() {
        return Set.copyOf(identifiers);
    }
}
