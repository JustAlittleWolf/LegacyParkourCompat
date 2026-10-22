package me.wolfii.legacyparkourcompat.change.v1_14_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.item.TridentUseDurabilityHook;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

/**
 * Ornithe Feather 1.13.2 TridentItem.startUsing and Mojmap 1.14.4
 * TridentItem.use permit Riptide at one durability remaining; Mojmap 1.15.2
 * TridentItem.use rejects it. In 26.2 unobfuscated TridentItem.use calls
 * ItemStack.nextDamageWillBreak. The release timing, water/rain gate and player
 * movement remain in TridentItem.releaseUsing.
 */
@MovementChange(emulates = ParkourVersion.V1_14_2)
public final class TridentUseDurability implements TridentUseDurabilityHook {
    public TridentUseDurability() {
    }

    @Override
    public boolean rejectsUse(Player player, ItemStack stack, boolean vanillaResult) {
        if (EnchantmentHelper.getTridentSpinAttackStrength(stack, player) <= 0.0F) {
            return vanillaResult;
        }
        return stack.getDamageValue() >= stack.getMaxDamage();
    }
}
