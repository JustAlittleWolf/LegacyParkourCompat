package me.wolfii.legacyparkourcompat.mechanic.input;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.component.UseEffects;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.resources.Identifier;
import net.minecraft.world.phys.Vec2;

import java.util.OptionalInt;

/** Shared native-feature applicability and unchanged movement-copy arithmetic. */
public final class KeyboardInputSupport {
    private KeyboardInputSupport() {}

    /** KeyboardInput constructs signed unit axes; opposing keys cancel. */
    public static float impulse(boolean positive, boolean negative) {
        return positive == negative ? 0.0F : positive ? 1.0F : -1.0F;
    }

    public static boolean supports(Player player, ParkourVersion selected) {
        return player.getAttributeValue(Attributes.SNEAKING_SPEED) == 0.3
            && supportsMovementFeatures(player, selected);
    }

    public static boolean supportsMovementFeatures(Player player, ParkourVersion selected) {
        if (player.getAttributeValue(Attributes.SCALE) != 1.0) {
            return false;
        }
        if (selected.olderThan(ParkourVersion.V1_13) && player.isSwimming()
            || selected.olderThan(ParkourVersion.V1_14) && player.getPose() == Pose.SWIMMING && !player.isSwimming()) {
            return false;
        }
        if (player.isUsingItem() && !player.isPassenger()) {
            UseEffects effects = player.getUseItem().getOrDefault(DataComponents.USE_EFFECTS, UseEffects.DEFAULT);
            return effects.speedMultiplier() == UseEffects.DEFAULT.speedMultiplier()
                && effects.canSprint() == UseEffects.DEFAULT.canSprint();
        }
        return true;
    }

    /**
     * Admit only vanilla's default sneak attribute and ordinary Swift Sneak.
     * Mojmap 1.20.6 Enchantments / EnchantmentHelper restrict the bonus to legs.
     * Mojmap 1.21 and 26.2 unobfuscated Enchantments / LevelBasedValue.Linear /
     * EnchantmentAttributeEffect widen the float linear amount into an ADD_VALUE
     * modifier named enchantment.swift_sneak/legs. Extra modifiers, nondefault
     * bases and modified bonus amounts retain native processing.
     */
    public static OptionalInt swiftSneakLevel(Player player, ParkourVersion selected) {
        ItemEnchantments enchantments = player.getItemBySlot(EquipmentSlot.LEGS)
            .getOrDefault(DataComponents.ENCHANTMENTS, ItemEnchantments.EMPTY);
        int level = 0;
        for (var entry : enchantments.entrySet()) {
            if (entry.getKey().is(Enchantments.SWIFT_SNEAK)) {
                level = entry.getIntValue();
                break;
            }
        }
        if (level < 0 || level > 3 || level > 0 && selected.olderThan(ParkourVersion.V1_19)) {
            return OptionalInt.empty();
        }
        AttributeInstance attribute = player.getAttribute(Attributes.SNEAKING_SPEED);
        if (attribute == null) {
            throw new IllegalStateException("Missing player sneaking-speed attribute");
        }
        if (attribute.getBaseValue() != 0.3 || attribute.getModifiers().size() != (level == 0 ? 0 : 1)) {
            return OptionalInt.empty();
        }
        if (level > 0) {
            AttributeModifier modifier = attribute.getModifier(Identifier.withDefaultNamespace("enchantment.swift_sneak/legs"));
            if (modifier == null || modifier.operation() != AttributeModifier.Operation.ADD_VALUE
                || modifier.amount() != (double)(0.15F + 0.15F * (level - 1))) {
                return OptionalInt.empty();
            }
        }
        return OptionalInt.of(level);
    }

    /**
     * Mojmap 1.14.4 and 1.21.4 LocalPlayer.serverAiStep copy stored input;
     * LivingEntity.aiStep scales those copies without writing back to input.
     */
    public static Vec2 travelInput(Vec2 input) {
        return new Vec2(input.x * 0.98F, input.y * 0.98F);
    }
}
