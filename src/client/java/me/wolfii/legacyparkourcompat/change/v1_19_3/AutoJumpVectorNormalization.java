package me.wolfii.legacyparkourcompat.change.v1_19_3;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.jump.AutoJumpInverseSqrtHook;
import net.minecraft.world.entity.player.Player;

/**
 * Retains float fast-inverse-square-root normalization from 1.15 through 1.19.3.
 * The earlier double operation resolves independently through 1.14.4.
 * 1.15/mojmap: LocalPlayer.updateAutoJump and Mth.fastInvSqrt(float).
 * 1.19.3/mojmap: LocalPlayer.updateAutoJump and Mth.fastInvSqrt(float).
 * 1.19.4/mojmap: LocalPlayer.updateAutoJump switches to Mth.invSqrt(float).
 */
@MovementChange(emulates = ParkourVersion.V1_19_3)
public final class AutoJumpVectorNormalization implements AutoJumpInverseSqrtHook {
    @Override
    public float inverseSqrt(Player player, ParkourVersion selected, float value, float vanilla) {
        if (selected.olderThan(ParkourVersion.V1_10)) {
            return vanilla;
        }

        float half = 0.5F * value;
        int bits = Float.floatToIntBits(value);
        bits = 1597463007 - (bits >> 1);
        value = Float.intBitsToFloat(bits);
        return value * (1.5F - half * value * value);
    }
}
