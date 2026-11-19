package me.wolfii.legacyparkourcompat.change.v1_14_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.item.TridentUseDurabilityHook;
import net.minecraft.world.item.ItemStack;

/**
 * Ornithe Feather 1.13.2 TridentItem.startUsing and Mojmap 1.14.4
 * TridentItem.use permit Riptide at one durability remaining; Mojmap 1.15.2
 * TridentItem.use rejects it. Neither 1.13.2 TridentItem.stopUsing nor 1.14.4
 * TridentItem.releaseUsing rechecks durability before the charged launch.
 * In 26.2 unobfuscated TridentItem.use and releaseUsing both call
 * ItemStack.nextDamageWillBreak before a launch can succeed.
 */
@MovementChange(emulates = ParkourVersion.V1_14_2)
public final class TridentUseDurability implements TridentUseDurabilityHook {
    public TridentUseDurability() {
    }

    @Override
    public boolean allowsUse(ItemStack stack, float riptideStrength) {
        return riptideStrength > 0.0F && stack.getDamageValue() < stack.getMaxDamage();
    }
}
