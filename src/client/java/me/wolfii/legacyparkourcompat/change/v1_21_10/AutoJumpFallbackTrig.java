package me.wolfii.legacyparkourcompat.change.v1_21_10;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.change.common.LegacyTrig;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.jump.AutoJumpFallbackTrigHook;

/**
 * Restores the float-table yaw lookup used from the first auto-jump release
 * through 1.21.10.
 *
 * <p>In Ornithe Feather 1.10, {@code LocalClientPlayerEntity.move} calls
 * {@code autoJump(float,float)}, whose fallback calls {@code MathHelper.sin}
 * and {@code MathHelper.cos} with float yaw radians. Those helpers use float
 * scale {@code 10430.378F}. Mojmap 1.21.10 retains that arithmetic in
 * {@code LocalPlayer.updateAutoJump(FF)V} and {@code Mth.sin(float)}/
 * {@code Mth.cos(float)}. In 1.21.11 Mojmap, the same owner method widens its
 * float yaw radians into {@code Mth.sin(double)}/
 * {@code Mth.cos(double)}, whose table indexing uses double arithmetic. The
 * 1.9.4 Ornithe Feather source has no auto-jump entry point; the V1_9 native
 * resolver barrier keeps this float-table change out of pre-feature profiles.
 * This delegates to {@link LegacyTrig}, which implements the shared 1.10 and
 * 1.21.10 float lookup.
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
