package me.wolfii.legacyparkourcompat.change.v1_20_5;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.fluid.WaterEnchantmentFactorsHook;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.Identifier;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.ItemEnchantments;

/**
 * Ornithe Feather 1.8.9 LivingEntity.moveRelative(float,float), reading
 * EnchantmentHelper.getDepthStriderLevel(Entity), and Mojmap 1.20.6
 * LivingEntity.travel(Vec3), reading EnchantmentHelper.getDepthStrider(LivingEntity),
 * both cap the equipped Depth Strider level at 3F, halve it off ground, and
 * multiply the water drag and acceleration interpolation differences by that
 * factor before dividing by 3F. The 1.8.9 water drag starts at 0.8F; Mojmap
 * 1.20.6 starts at sprinting ? 0.9F :
 * getWaterSlowDown() (0.8F for players). Mojmap 1.21.1 LivingEntity.travel(Vec3)
 * instead multiplies by the effective water-efficiency attribute with no final
 * division; 26.2 LivingEntity.travelInWater retains it. Depth Strider III can
 * therefore differ by one float ULP. Preserve the subsequent Dolphin's Grace
 * drag override. Attribute production stays vanilla; only ordinary foot-slot
 * levels 0–3 and the canonical default attribute state are admitted, leaving
 * additional modern efficiency modifiers on the native path.
 */
@MovementChange(emulates = ParkourVersion.V1_20_5)
public final class WaterEnchantmentFactors implements WaterEnchantmentFactorsHook {
    @Override
    public Factors factors(Player player, ParkourVersion selected, float vanillaDrag, float vanillaAcceleration) {
        Factors vanilla = new Factors(vanillaDrag, vanillaAcceleration);
        ItemEnchantments enchantments = player.getItemBySlot(EquipmentSlot.FEET)
            .getOrDefault(DataComponents.ENCHANTMENTS, ItemEnchantments.EMPTY);
        int level = 0;
        for (var entry : enchantments.entrySet()) {
            if (entry.getKey().is(Enchantments.DEPTH_STRIDER)) {
                level = entry.getIntValue();
                break;
            }
        }
        if (level < 0 || level > 3) {
            return vanilla;
        }
        AttributeInstance attribute = player.getAttribute(Attributes.WATER_MOVEMENT_EFFICIENCY);
        if (attribute == null) {
            throw new IllegalStateException("Missing player water-efficiency attribute");
        }
        if (attribute.getBaseValue() != 0.0 || attribute.getModifiers().size() != (level == 0 ? 0 : 1)) {
            return vanilla;
        }
        if (level > 0) {
            AttributeModifier modifier = attribute.getModifier(Identifier.withDefaultNamespace("enchantment.depth_strider/feet"));
            if (modifier == null || modifier.operation() != AttributeModifier.Operation.ADD_VALUE
                || modifier.amount() != (double)(0.33333334F + 0.33333334F * (level - 1))) {
                return vanilla;
            }
        }
        float drag = 0.8F;
        if (!selected.olderThan(ParkourVersion.V1_16) && player.isSprinting()) {
            drag = 0.9F;
        }
        float acceleration = 0.02F;
        float depthStrider = level;
        if (depthStrider > 3.0F) {
            depthStrider = 3.0F;
        }
        if (!player.onGround()) {
            depthStrider *= 0.5F;
        }
        if (depthStrider > 0.0F) {
            drag += (0.54600006F - drag) * depthStrider / 3.0F;
            acceleration += (player.getSpeed() - acceleration) * depthStrider / 3.0F;
        }
        if (player.hasEffect(MobEffects.DOLPHINS_GRACE)) {
            drag = 0.96F;
        }
        return new Factors(drag, acceleration);
    }
}
