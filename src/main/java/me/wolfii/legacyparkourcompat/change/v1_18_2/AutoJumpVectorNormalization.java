package me.wolfii.legacyparkourcompat.change.v1_18_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.jump.AutoJumpInverseSqrtHook;
import net.minecraft.world.entity.player.Player;

/**
 * Source evidence (paths are relative to decompiled_minecraft/):
 * 1.18.2/mojmap/net/minecraft/client/player/LocalPlayer.java:
 *   updateAutoJump; lines 900-996.
 *   Source SHA-256: 99c2d18bcd23243afb8f95c5bafb21fb0be7ea04aacbb14fcf7be7ced2c9c095.
 * 1.18.2/mojmap/net/minecraft/util/Mth.java:
 *   fastInvSqrt; lines 485-491.
 *   Source SHA-256: 32747c5b09fc184baae356e39a0088fd66c9f08f69d9e98b67c19e1de6f1bb2e.
 * 1.19.3/mojmap/net/minecraft/client/player/LocalPlayer.java:
 *   updateAutoJump; lines 911-1007.
 *   move; lines 900-905.
 *   Source SHA-256: 64b670ee323d195b3928fb8ea629c26d2560e75a17379a12c376a7bc686d5479.
 * 1.19.3/mojmap/net/minecraft/util/Mth.java:
 *   fastInvSqrt; lines 495-501.
 *   Source SHA-256: f5b2738a7145303f8598b9876c0f6950e853110f594fc4cffa633ab7bab98d3f.
 * 1.19.4/mojmap/net/minecraft/client/player/LocalPlayer.java:
 *   updateAutoJump; lines 891-987.
 *   updateAutoJump; lines 891-987.
 *   updateAutoJump; lines 900-905.
 *   updateAutoJump; lines 911.
 *   Source SHA-256: 8e7da18f42d09fbb994f522c2b0e65fcb2bb83cabb21024360299d44d9674c58.
 * 1.19.4/mojmap/net/minecraft/util/Mth.java:
 *   invSqrt; lines 387-389.
 *   invSqrt; lines 387-389.
 *   Source SHA-256: 201d17ea024637036761c2703e407d4ab1dc7651a1f4b00a857c09803754c797.
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
