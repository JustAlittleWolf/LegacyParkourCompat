package me.wolfii.legacyparkourcompat.change.v1_17_1;

import com.google.common.collect.Iterables;
import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.jump.AutoJumpCollisionOrderHook;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.CollisionGetter;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.VoxelShape;

/**
 * 1.17.1/mojmap net/minecraft/world/level/CollisionGetter.java, getCollisions,
 * lines 57-59: block candidates precede entity candidates.
 * 1.18/mojmap same file/member, lines 67-70: entity candidates precede blocks.
 * Existing F-003 closure proves the first stable boundary at 1.18.
 *
 * Source evidence (paths are relative to decompiled_minecraft/):
 * 1.17.1/mojmap/net/minecraft/client/player/LocalPlayer.java:
 *   updateAutoJump; lines [[956, 965]].
 *   Source SHA-256: c9a91cb6cb57806bc8d22e5bfe2d97daaf21d5d2a48f34a6e2c53164c61c5812.
 * 1.17.1/mojmap/net/minecraft/world/level/CollisionGetter.java:
 *   getCollisions; lines [[57, 59]].
 *   Source SHA-256: eb707479e75cf200731df4546a546fb984be33cf3e4a8e17d3065a916497f747.
 * 1.18.2/mojmap/net/minecraft/client/player/LocalPlayer.java:
 *   updateAutoJump; lines [[952, 960]].
 *   Source SHA-256: 99c2d18bcd23243afb8f95c5bafb21fb0be7ea04aacbb14fcf7be7ced2c9c095.
 * 1.18.2/mojmap/net/minecraft/world/level/CollisionGetter.java:
 *   getCollisions; lines [[67, 75]].
 *   Source SHA-256: ed62e6b800ec057c1269d3cc4e2fb7c0cc2037370223fac99f41371e000c6b5c.
 * Bounded source decisions:
 *   Decision 4a5eae32b5819418dbd59eb4a763ee24ea1f2c52:development/research/archive/legacy-workflows/source-boundary-reviews/2026-10-08-mc1171-1182-snapshot-review.md; ACCEPT, bounded.
 *   Reviewed snapshot e531086eecc778432b40b2b9ef39499b8a7f0dba:workflows/source-campaign-2026-10-07/1.17.1--1.18.2/findings/F-003-autojump-candidate-order.md.
 *   Decision 4a5eae32b5819418dbd59eb4a763ee24ea1f2c52:development/research/archive/legacy-workflows/source-campaign-2026-10-07/reviews/independent-review-F003-autojump-order-closure-2026-10-10.md; ACCEPT, witness/dependencies/boundary.
 *   Reviewed snapshot a5870a155106dbdf2a45010b267ae146c7735a8b.
 * Source-content correspondence and compilation do not establish tick-level runtime parity.
 */
@MovementChange(emulates = ParkourVersion.V1_17_1)
public final class AutoJumpCollisionOrder implements AutoJumpCollisionOrderHook {
    @Override
    public Iterable<VoxelShape> autoJumpCollisions(Player player, CollisionGetter level, AABB query) {
        return Iterables.concat(level.getBlockCollisions(player, query), level.getEntityCollisions(player, query));
    }
}
