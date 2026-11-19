package me.wolfii.legacyparkourcompat.change.v1_20_5;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.item.RiptideReleaseDurabilityHook;

/**
 * Ornithe Feather 1.13 TridentItem.stopUsing and Mojmap 1.20.6
 * TridentItem.releaseUsing admit the local Riptide impulse after ten use
 * ticks in water or rain without rechecking durability. Mojmap 1.21
 * TridentItem.releaseUsing adds isTooDamagedToUse before that impulse.
 * Unobfuscated 26.2 uses ItemStack.nextDamageWillBreak at the same gate.
 */
@MovementChange(emulates = ParkourVersion.V1_20_5)
public final class RiptideReleaseDurability implements RiptideReleaseDurabilityHook {
    @Override
    public boolean rejectsRelease(float strength, boolean vanillaResult) {
        return vanillaResult && !(strength > 0.0F);
    }
}
