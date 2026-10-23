package me.wolfii.legacyparkourcompat.change.v1_20_5;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.player.PlayerMovementSpeedHook;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

/**
 * In 1.20.6 (Mojmap), AttributeInstance.getModifiers(Operation) supplies the
 * Object2ObjectOpenHashMap values consumed by calculateValue() in descending
 * Fastutil table-slot order; Player.getSpeed() casts that result to float. In
 * 1.21.1 (Mojmap), those maps use ResourceLocation keys, changing the slots for
 * Speed, Slowness and sprint. This reproduces the old order only for the three
 * proven builtin identifiers with known history since attribute construction.
 *
 * AttributeInstance.getModifiers(Operation), addModifier,
 * addOrUpdateTransientModifier, removeModifier, removeModifiers, replaceFrom
 * and load in 1.20.6 (Mojmap), and the corresponding methods (apply replaces
 * load) in 26.2 (unobfuscated), create default Fastutil maps and mark every
 * modifier mutation dirty. The client observer retains all identifiers seen
 * at those dirty boundaries, including removed identifiers, and rejects any
 * non-total operation. With only these three identifiers ever present, the
 * UUID map never exceeds three entries and its default 32 slots never resize.
 * Their home slots are distinct (sprint 23, Slowness 18, Speed 14), so insert,
 * replace and delete history cannot move any surviving entry. Sorting by
 * descending home slot is therefore exact for this bounded history, without
 * inferring a historical layout from the current Identifier map's capacity.
 * Unsupported history permanently retains native behavior.
 *
 * Old UUIDs and amounts: 1.20.6 (Mojmap) MobEffects.MOVEMENT_SPEED /
 * MOVEMENT_SLOWDOWN and LivingEntity.SPEED_MODIFIER_SPRINTING. First new
 * identifiers: 1.21 (Mojmap), the same fields and AttributeInstance's maps;
 * confirmed in 1.21.1 (Mojmap). Fastutil 8.5.12 HashCommon.mix(int) and
 * Object2ObjectOpenHashMap.MapIterator.nextEntry() supply the descending-slot
 * iteration. Earlier profile groups retain native order until separately
 * established; V1_20_5 uses its 1.20.6 source representative.
 */
@MovementChange(emulates = ParkourVersion.V1_20_5)
public final class LegacyPlayerMovementSpeed implements PlayerMovementSpeedHook {
    private static final int PROVEN_TABLE_CAPACITY = 32;
    private static final Identifier SPEED_ID = Identifier.withDefaultNamespace("effect.speed");
    private static final Identifier SLOWNESS_ID = Identifier.withDefaultNamespace("effect.slowness");
    private static final Identifier SPRINT_ID = Identifier.withDefaultNamespace("sprinting");

    @Override
    public Optional<Float> movementSpeed(double baseValue, Collection<AttributeModifier> modifiers, Set<Identifier> observedIdentifiers) {
        for (Identifier observedIdentifier : observedIdentifiers) {
            if (oldBuiltinId(observedIdentifier) == null) {
                return Optional.empty();
            }
        }

        List<LegacyModifier> ordered = new ArrayList<>(modifiers.size());
        for (AttributeModifier modifier : modifiers) {
            if (modifier.operation() != AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL) {
                return Optional.empty();
            }
            UUID oldId = oldBuiltinId(modifier.id());
            if (oldId == null) {
                return Optional.empty();
            }
            ordered.add(new LegacyModifier(modifier, oldTableSlot(oldId)));
        }
        if (ordered.isEmpty()) {
            return Optional.empty();
        }

        ordered.sort(Comparator.comparingInt(LegacyModifier::slot).reversed());
        double value = baseValue;
        for (LegacyModifier legacyModifier : ordered) {
            value *= 1.0 + legacyModifier.modifier().amount();
        }
        return Optional.of((float) Attributes.MOVEMENT_SPEED.value().sanitizeValue(value));
    }

    private static UUID oldBuiltinId(Identifier id) {
        if (SPEED_ID.equals(id)) {
            return UUID.fromString("91AEAA56-376B-4498-935B-2F7F68070635");
        }
        if (SLOWNESS_ID.equals(id)) {
            return UUID.fromString("7107DE5E-7CE8-4030-940E-514C1F160890");
        }
        if (SPRINT_ID.equals(id)) {
            return UUID.fromString("662A6B8D-DA3E-4C1C-8813-96EA6097278D");
        }
        return null;
    }

    private static int oldTableSlot(UUID id) {
        int hash = (int)(id.getMostSignificantBits() ^ (id.getMostSignificantBits() >>> 32)
            ^ id.getLeastSignificantBits() ^ (id.getLeastSignificantBits() >>> 32));
        int mixed = hash * -1640531527;
        mixed ^= mixed >>> 16;
        return mixed & (PROVEN_TABLE_CAPACITY - 1);
    }

    private record LegacyModifier(AttributeModifier modifier, int slot) {
    }
}
