package me.wolfii.legacyparkourcompat.change.v1_15_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.change.common.SuffocationProbeColumn;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.VanillaFn;
import me.wolfii.legacyparkourcompat.mechanic.hook.collision.SuffocationProbeHook;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.ShulkerBoxBlock;

/**
 * Source evidence (paths are relative to decompiled_minecraft/):
 * 1.13.2/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java:
 *   pushAwayFrom, mobTick; lines [[401, 455], [717, 721]].
 *   Source SHA-256: 2583495f3a02b4791aa036e6a8d354d7596c4984761969a0f29b29d8d9bf42bf.
 * 1.14.4/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java:
 *   addMessage, mobTick; lines [[388, 447], [645, 651]].
 *   Source SHA-256: 708af6a3880fb58b67bf4604a5509b351719a9c2a0c06a8ec261586435bf00ce.
 * 1.15.2/mojmap/net/minecraft/client/player/LocalPlayer.java:
 *   checkInBlock; lines [[386, 440]].
 *   checkInBlock; lines [[385, 424]].
 *   Source SHA-256: 3a9019bd7b860e251c23fd8d0cd70b7f5b38566d34470c4e29b1014ef689ccbd.
 * 1.16.5/mojmap/net/minecraft/client/player/LocalPlayer.java:
 *   moveTowardsClosestSpace; lines [[414, 447]].
 *   moveTowardsClosestSpace; lines [[414, 440]].
 *   Source SHA-256: 6011569e766bb1568609147be9aa14e9c08c51948e3d3a60fd066e848f6a8c2b.
 * Bounded source decisions:
 *   Decision 4a5eae32b5819418dbd59eb4a763ee24ea1f2c52:development/research/archive/legacy-workflows/coverage-review-2026-10-07/middle.md; accepted for finding-only handoff.
 *   Reviewed snapshot d859478b9f6de63ba4a121a1f31d6dc90ad4f51e:workflows/source-campaign-2026-10-07/1.15.2--1.16.5/findings/F-S1-OPEN-SHULKER-ESCAPE.md.
 * Remaining source/applicability gates:
 *   1.13.2 original Feather mapped-client JAR d118ff5b9eb93fed06680466cb26c41a30647cdb2fc8744638cf9b35cf7864e5 remains unavailable; revised source equality does not prove original derived-JAR equivalence.
 * Source-content correspondence and compilation do not establish tick-level runtime parity.
 */
@MovementChange(emulates = ParkourVersion.V1_15_2)
public final class SuffocationProbe implements SuffocationProbeHook {
    @Override
    public boolean suffocatesAt(Player player, ParkourVersion target, BlockPos pos, VanillaFn<Boolean> vanilla) {
        return SuffocationProbeColumn.any(player, pos, cursor -> this.isSuffocating(player, target, cursor));
    }

    private boolean isSuffocating(Player player, ParkourVersion target, BlockPos pos) {
        var blockState = player.level().getBlockState(pos);
        // This bounded finding proves the behavior at 1.15.2; the first changed release is unknown.
        if (target == ParkourVersion.V1_15_2
            && blockState.getBlock() instanceof ShulkerBoxBlock) {
            return true;
        }
        return player.level().getBlockState(pos).isSuffocating(player.level(), pos);
    }
}
