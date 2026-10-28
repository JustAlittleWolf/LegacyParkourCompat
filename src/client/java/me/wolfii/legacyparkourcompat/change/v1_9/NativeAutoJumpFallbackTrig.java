package me.wolfii.legacyparkourcompat.change.v1_9;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.jump.AutoJumpFallbackTrigHook;
import net.minecraft.util.Mth;

/**
 * Keeps the native double-index lookup before auto-jump first appears.
 *
 * <p>The 1.9.4 Ornithe Feather {@code LocalClientPlayerEntity} source has no
 * auto-jump entry point. The first one is 1.10 Ornithe Feather
 * {@code LocalClientPlayerEntity.autoJump(float,float)}, reached after
 * {@code move}; it uses the float lookup also present in Mojmap 1.21.10
 * {@code LocalPlayer.updateAutoJump(FF)V} and {@code Mth.sin(float)}/
 * {@code Mth.cos(float)}. Mojmap 1.21.11 changes those helpers to double-index
 * lookup while retaining the float-radian call, and 26.2's unobfuscated
 * {@code LocalPlayer.updateAutoJump(FF)V} and {@code Mth.sin(double)}/
 * {@code Mth.cos(double)} retain that newer behavior. This V1_9 resolver fence
 * preserves the runtime's native lookup through 1.9.4; the V1_21_10 change is
 * then selected for profiles from 1.10 through 1.21.10.
 */
@MovementChange(emulates = ParkourVersion.V1_9)
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
