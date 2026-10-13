package me.wolfii.legacyparkourcompat.change.v1_14_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.jump.AutoJumpInverseSqrtHook;
import net.minecraft.world.entity.player.Player;

@MovementChange(emulates = ParkourVersion.V1_14_2)
public final class DoubleAutoJumpInverseSqrt implements AutoJumpInverseSqrtHook {
    /**
     * Minecraft 1.14.4 Mojmap LocalPlayer.updateAutoJump calls Mth.fastInvSqrt(double);
     * Minecraft 1.15 Mojmap LocalPlayer.updateAutoJump uses Mth.fastInvSqrt(float).
     */
    @Override
    public float inverseSqrt(Player player, ParkourVersion selected, float value, float vanilla) {
        if (selected.olderThan(ParkourVersion.V1_10)) {
            return vanilla;
        }

        double input = value;
        double half = 0.5 * input;
        long bits = Double.doubleToRawLongBits(input);
        bits = 6910469410427058090L - (bits >> 1);
        double seed = Double.longBitsToDouble(bits);
        return (float) (seed * (1.5 - half * seed * seed));
    }
}
