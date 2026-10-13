package me.wolfii.legacyparkourcompat.change.v1_18_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.jump.AutoJumpInverseSqrtHook;
import net.minecraft.world.entity.player.Player;

/**
 * Source evidence (paths are relative to decompiled_minecraft/):
 * 1.18.2/mojmap/net/minecraft/client/player/LocalPlayer.java:
 *   updateAutoJump.
 * 1.18.2/mojmap/net/minecraft/util/Mth.java:
 *   fastInvSqrt.
 * 1.19.3/mojmap/net/minecraft/client/player/LocalPlayer.java:
 *   updateAutoJump.
 *   move.
 * 1.19.3/mojmap/net/minecraft/util/Mth.java:
 *   fastInvSqrt.
 * 1.19.4/mojmap/net/minecraft/client/player/LocalPlayer.java:
 *   updateAutoJump.
 *   updateAutoJump.
 *   updateAutoJump.
 *   updateAutoJump.
 * 1.19.4/mojmap/net/minecraft/util/Mth.java:
 *   invSqrt.
 *   invSqrt.
 * No exact independently accepted source binding is established for this operation.
 * Source-content correspondence and compilation do not establish tick-level runtime parity.
 */
@MovementChange(emulates = ParkourVersion.V1_18_2)
public final class AutoJumpVectorNormalization implements AutoJumpInverseSqrtHook {
    @Override
    public float inverseSqrt(Player player, float value, float vanilla) {
        float half = 0.5F * value;
        int bits = Float.floatToIntBits(value);
        bits = 1597463007 - (bits >> 1);
        value = Float.intBitsToFloat(bits);
        return value * (1.5F - half * value * value);
    }
}
