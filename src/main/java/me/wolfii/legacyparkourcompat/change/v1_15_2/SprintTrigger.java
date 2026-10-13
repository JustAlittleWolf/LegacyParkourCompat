package me.wolfii.legacyparkourcompat.change.v1_15_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.input.SprintTriggerHook;
import net.minecraft.world.entity.player.Player;

/**
 * Source evidence (paths are relative to decompiled_minecraft/):
 * 1.15.2/mojmap/net/minecraft/client/player/LocalPlayer.java:
 *   aiStep; lines [[625, 671]].
 *   aiStep; lines [[625, 640], [657, 670]].
 *   Source SHA-256: 3a9019bd7b860e251c23fd8d0cd70b7f5b38566d34470c4e29b1014ef689ccbd.
 * 1.16.5/mojmap/net/minecraft/client/player/LocalPlayer.java:
 *   aiStep; lines [[627, 681]].
 *   aiStep; lines [[627, 646], [663, 679]].
 *   Source SHA-256: 6011569e766bb1568609147be9aa14e9c08c51948e3d3a60fd066e848f6a8c2b.
 * Bounded source decisions:
 *   Decision 4a5eae32b5819418dbd59eb4a763ee24ea1f2c52:development/research/reconciliation/review-decisions/90e97d96803bd8ab98f1d68181af01e1eaf00a48f2bb58ff13634c8757778c9d.md; accepted, bounded.
 *   Reviewed snapshot ce9369edd2bfe296e56a0a5253a47a5cd486dc3a:workflows/source-campaign-2026-10-07/1.15.2--1.16.5/findings/F-S1-SPRINT-RESET.md.
 * Source-content correspondence and compilation do not establish tick-level runtime parity.
 */
@MovementChange(emulates = ParkourVersion.V1_15_2)
public final class SprintTrigger implements SprintTriggerHook {
    @Override
    public boolean shouldReset(Player player, boolean anotherResetCause) {
        return anotherResetCause;
    }
}
