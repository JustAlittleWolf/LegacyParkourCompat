package me.wolfii.legacyparkourcompat.change.v1_17_1;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.collision.SprintCollisionHook;
import net.minecraft.world.entity.player.Player;

/**
 * Restores stopping sprint on every horizontal collision.
 *
 * Source evidence (paths are relative to decompiled_minecraft/):
 * 1.17.1/mojmap/net/minecraft/client/player/LocalPlayer.java:
 *   aiStep; lines [[715, 724]].
 *   aiStep; lines [[718, 724]].
 *   Source SHA-256: c9a91cb6cb57806bc8d22e5bfe2d97daaf21d5d2a48f34a6e2c53164c61c5812.
 * 1.18.2/mojmap/net/minecraft/client/player/LocalPlayer.java:
 *   aiStep, isHorizontalCollisionMinor; lines [[708, 718], [999, 1014]].
 *   aiStep; lines [[711, 717]].
 *   Source SHA-256: 99c2d18bcd23243afb8f95c5bafb21fb0be7ea04aacbb14fcf7be7ced2c9c095.
 * Bounded source decisions:
 *   Decision 4a5eae32b5819418dbd59eb4a763ee24ea1f2c52:development/research/archive/legacy-workflows/source-boundary-reviews/2026-10-08-mc1171-1182-snapshot-review.md; ACCEPT, bounded.
 *   Reviewed snapshot e531086eecc778432b40b2b9ef39499b8a7f0dba:workflows/source-campaign-2026-10-07/1.17.1--1.18.2/findings/F-001-minor-horizontal-collision-sprint.md.
 *   Decision 4a5eae32b5819418dbd59eb4a763ee24ea1f2c52:development/research/archive/legacy-workflows/source-boundary-reviews/2026-10-09-1171-1182-boundaries-independent-review.md; ACCEPT, exact sampled boundaries.
 *   Reviewed snapshot 14443f43fbcc30152916ac75796846d796cf0268.
 * Source-content correspondence and compilation do not establish tick-level runtime parity.
 */
@MovementChange(emulates = ParkourVersion.V1_17_1)
public final class SprintCollision implements SprintCollisionHook {
    @Override
    public boolean shouldStopRunSprinting(Player player, boolean vanilla) {
        return vanilla || player.horizontalCollision;
    }
}
