package me.wolfii.legacyparkourcompat.mechanic.attribute;

import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import me.wolfii.legacyparkourcompat.mechanic.hook.player.PlayerMovementSpeedHook;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/** Bounded provenance captured from an attribute's construction onward. */
public final class MovementSpeedModifierHistory {
    private final Set<Identifier> identifiers = new HashSet<>();
    private boolean complete = true;
    private boolean totalOnly = true;

    public void observe(Map<AttributeModifier.Operation, Map<Identifier, AttributeModifier>> operations) {
        if (!complete) {
            return;
        }
        for (var operation : operations.entrySet()) {
            if (operation.getValue().isEmpty()) {
                continue;
            }
            if (operation.getKey() != AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL) {
                totalOnly = false;
            }
            for (Identifier identifier : operation.getValue().keySet()) {
                identifiers.add(identifier);
                if (identifiers.size() > 3) {
                    complete = false;
                    identifiers.clear();
                    break;
                }
            }
            if (!complete) {
                break;
            }
        }
    }

    public PlayerMovementSpeedHook.History snapshot() {
        return new PlayerMovementSpeedHook.History(identifiers, complete, totalOnly);
    }
}
