package me.wolfii.legacyparkourcompat.change.v1_19_3;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.equipment.EquipmentUseHook;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import org.jspecify.annotations.Nullable;

import java.util.Objects;
import java.util.Set;

@MovementChange(emulates = ParkourVersion.V1_19_3)
public final class OccupiedEquipmentUse implements EquipmentUseHook {
    private static final Set<Item> CHESTPLATES = Set.of(
        Items.LEATHER_CHESTPLATE, Items.CHAINMAIL_CHESTPLATE, Items.IRON_CHESTPLATE,
        Items.GOLDEN_CHESTPLATE, Items.DIAMOND_CHESTPLATE
    );
    private static final Set<Item> BOOTS = Set.of(
        Items.LEATHER_BOOTS, Items.CHAINMAIL_BOOTS, Items.IRON_BOOTS,
        Items.GOLDEN_BOOTS, Items.DIAMOND_BOOTS, Items.NETHERITE_BOOTS
    );

    @Override
    public @Nullable InteractionResult result(ParkourVersion target, EquipmentSlot slot, ItemStack held, ItemStack equipped) {
        if (equipped.isEmpty() || !hasDefaultEquipmentComponents(held) || !hasDefaultEquipmentComponents(equipped)) {
            return null;
        }

        // 1.19.3 Mojmap ElytraItem.use and ArmorItem.use fail when the target slot is occupied.
        // 1.19.4 Mojmap ElytraItem.use and ArmorItem.use delegate to Equipable.swapWithEquipmentSlot.
        // 26.2 unobfuscated Item.use delegates to Equippable.swapWithEquipmentSlot after selecting the equipment component.
        // LocalPlayer.aiStep and LivingEntity.getBlockSpeedFactor read the equipped Elytra and Soul Speed,
        // respectively, on both releases.
        // 1.9 Feather Items and ElytraItem.startUsing cover Elytra and the original chestplates.
        // 1.16 Mojmap Items, Enchantments.SOUL_SPEED and LivingEntity.getBlockSpeedFactor cover
        // netherite gear and Soul Speed boots.
        if (slot == EquipmentSlot.CHEST && target.newerThanOrEqual(ParkourVersion.V1_9)
            && held.getItem() == Items.ELYTRA && isHistoricalChestEquipment(target, equipped.getItem())) {
            return InteractionResult.FAIL;
        }

        if (slot == EquipmentSlot.FEET && target.newerThanOrEqual(ParkourVersion.V1_16)
            && BOOTS.contains(held.getItem()) && BOOTS.contains(equipped.getItem())
            && hasSoulSpeed(held) && !hasSoulSpeed(equipped)) {
            return InteractionResult.FAIL;
        }

        return null;
    }

    private static boolean isHistoricalChestEquipment(ParkourVersion target, Item item) {
        return item == Items.ELYTRA || CHESTPLATES.contains(item)
            || (item == Items.NETHERITE_CHESTPLATE && target.newerThanOrEqual(ParkourVersion.V1_16));
    }

    private static boolean hasDefaultEquipmentComponents(ItemStack stack) {
        var defaults = stack.getItem().components();
        return Objects.equals(stack.get(DataComponents.EQUIPPABLE), defaults.get(DataComponents.EQUIPPABLE))
            && stack.has(DataComponents.GLIDER) == defaults.has(DataComponents.GLIDER);
    }

    private static boolean hasSoulSpeed(ItemStack stack) {
        return stack.getEnchantments().entrySet().stream()
            .anyMatch(entry -> entry.getIntValue() > 0 && entry.getKey().is(Enchantments.SOUL_SPEED));
    }
}
