package me.wolfii.legacyparkourcompat.change.v1_21_10;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.change.common.LegacyTrig;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.item.RiptideReleaseTrigHook;

/**
 * Ornithe Feather 1.13 TridentItem.stopUsing computes the launch direction
 * with MathHelper.sin(float) and cos(float). Mojmap 1.21.10
 * TridentItem.releaseUsing still uses the float-indexed Mth lookups.
 * Mojmap 1.21.11 and unobfuscated 26.2 TridentItem.releaseUsing widen the
 * float radians for Mth.sin(double) and cos(double), which can change the
 * table indices before the float normalization.
 */
@MovementChange(emulates = ParkourVersion.V1_21_10)
public final class RiptideReleaseTrig implements RiptideReleaseTrigHook {
    @Override
    public float sin(float radians) {
        return LegacyTrig.sin(radians);
    }

    @Override
    public float cos(float radians) {
        return LegacyTrig.cos(radians);
    }
}
