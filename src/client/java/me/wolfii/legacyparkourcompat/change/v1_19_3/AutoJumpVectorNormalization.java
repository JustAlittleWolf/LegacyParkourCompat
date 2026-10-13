package me.wolfii.legacyparkourcompat.change.v1_19_3;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.jump.AutoJumpInverseSqrtHook;
import net.minecraft.world.entity.player.Player;

/**
 * Retains the float fast-inverse-square-root auto-jump operation through 1.19.3.
 * Profiles before auto-jump's 1.10 introduction preserve the native operation.
 * 1.19/mojmap/net/minecraft/client/player/LocalPlayer.java: updateAutoJump.
 * 1.19.1/mojmap/net/minecraft/client/player/LocalPlayer.java: updateAutoJump.
 * 1.19.2/mojmap/net/minecraft/client/player/LocalPlayer.java: updateAutoJump.
 * The separate older double inverse-square-root call remains unresolved; this
 * float delta and its native fallback do not establish full 1.10 parity.
 *
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
 * Source correspondence is bounded to this float call; older double-call fidelity remains open.
 * Source-content correspondence and compilation do not establish tick-level runtime parity.
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
