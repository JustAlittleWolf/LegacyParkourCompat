package me.wolfii.legacyparkourcompat.change.v1_21_6;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.jump.AutoJumpFallbackTrigHook;
import net.minecraft.util.Mth;

/**
 * Keeps the native double-index lookup for profiles through 1.21.8.
 *
 * <p>The 1.21.11 Mojmap {@code Mth.sin(double)} and {@code Mth.cos(double)}
 * use a double scale and long index conversion; its
 * {@code LocalPlayer.updateAutoJump(FF)V} widens the float yaw radians at those
 * calls. The same members and arithmetic are present in 26.2's unobfuscated
 * {@code Mth} and its {@code LocalPlayer.updateAutoJump(FF)V}. This resolver
 * fence preserves that native lookup for selections resolved to V1_21_6 (the
 * 1.21.6/.7/.8 profile group, and any earlier selection without a nearer
 * provider); the V1_21_10 change then supplies the float lookup for 1.21.9/.10.
 */
@MovementChange(emulates = ParkourVersion.V1_21_6)
public final class NativeAutoJumpFallbackTrig implements AutoJumpFallbackTrigHook {
    @Override
    public float sin(float radians) {
        return Mth.sin((double) radians);
    }

    @Override
    public float cos(float radians) {
        return Mth.cos((double) radians);
    }
}
