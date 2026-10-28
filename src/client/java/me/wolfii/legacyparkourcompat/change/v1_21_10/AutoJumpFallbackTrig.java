package me.wolfii.legacyparkourcompat.change.v1_21_10;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.change.common.LegacyTrig;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.jump.AutoJumpFallbackTrigHook;

/**
 * Restores the float-table yaw lookup used by 1.21.9 and 1.21.10 auto-jump.
 *
 * <p>In Mojmap 1.21.9 and 1.21.10, {@code LocalPlayer.updateAutoJump(FF)V}
 * calls {@code Mth.sin(float)} and {@code Mth.cos(float)} after computing yaw
 * radians as a float. Their {@code Mth.sin(float)} and {@code Mth.cos(float)}
 * use the float scale {@code 10430.378F}. Mojmap 1.21.11 changes those helper
 * members to {@code Mth.sin(double)} and {@code Mth.cos(double)} with double
 * index arithmetic, while {@code LocalPlayer.updateAutoJump(FF)V} still
 * supplies float radians widened at the call. This reuses the same float table
 * arithmetic as the cited 1.21.9/1.21.10 members for the pre-change profiles.
 */
@MovementChange(emulates = ParkourVersion.V1_21_10)
public final class AutoJumpFallbackTrig implements AutoJumpFallbackTrigHook {
    @Override
    public float sin(float radians) {
        return LegacyTrig.sin(radians);
    }

    @Override
    public float cos(float radians) {
        return LegacyTrig.cos(radians);
    }
}
