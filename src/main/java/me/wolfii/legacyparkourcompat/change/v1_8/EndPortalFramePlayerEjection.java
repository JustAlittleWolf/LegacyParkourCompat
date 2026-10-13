package me.wolfii.legacyparkourcompat.change.v1_8;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.change.common.SuffocationProbeColumn;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.VanillaFn;
import me.wolfii.legacyparkourcompat.mechanic.hook.collision.SuffocationProbeHook;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Blocks;

/**
 * Source evidence (paths are relative to decompiled_minecraft/):
 * 1.8.9/ornithe-feather/net/minecraft/block/Block.java:
 *   public boolean isCube() {; lines 245-247.
 *   Source SHA-256: ea10f05106a3cf7189aec85236a7ecf9c7106a717f37bed583b5ea4adeffa528.
 * 1.8.9/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java:
 *   protected boolean pushAwayFrom(double x, double y, double z) {; lines 292-342.
 *   Source SHA-256: 1762b116e6b06d682b7daaa0fc8cce39b0ff455b3db8cf79dab03ac74f6c4053.
 * 1.9.4/ornithe-feather/net/minecraft/block/Block.java:
 *   public boolean isCube(BlockState state) {; lines 223-225.
 *   Source SHA-256: e62ece80c6a7e7121346f65f8fdfd9b148c29441a27de9f568bba9afe1d84fe6.
 * 1.9.4/ornithe-feather/net/minecraft/block/EndPortalFrameBlock.java:
 *   public boolean isCube(BlockState state) {; lines 109-111.
 *   Source SHA-256: 1c6495a6d6c5d777eb643983c7e7b8151bb5b99ef1a4a3b991655792a0ad58eb.
 * 1.9.4/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java:
 *   protected boolean pushAwayFrom(double x, double y, double z) {; lines 349-399.
 *   Source SHA-256: 8aaf711948b7602c2e6c015a37e36ed06073d39727d999d80480b4910b704f5d.
 * Bounded source decisions:
 *   Review a2e510bdc7b284b4b4d0e7b323f6eea079abee80; accepted, bounded.
 *   Reviewed snapshot aa66894e64733ee729bf7176e08232d73b3bc03f; SHA-256 f45dfb003c1dfcc64df5c5d7710fd22a4e6c311b1b8b50a3d77ed1537689479d.
 *   Review a2e510bdc7b284b4b4d0e7b323f6eea079abee80; accepted, bounded.
 *   Reviewed snapshot aa66894e64733ee729bf7176e08232d73b3bc03f; SHA-256 f45dfb003c1dfcc64df5c5d7710fd22a4e6c311b1b8b50a3d77ed1537689479d.
 *   Decision 4a5eae32b5819418dbd59eb4a763ee24ea1f2c52:development/research/archive/legacy-workflows/source-campaign-2026-10-07/SNAPSHOT-REVIEW-2026-10-08.md; [{&#x27;section&#x27;: &#x27;Decision 1 — ACCEPT&#x27;, &#x27;verdict&#x27;: [], &#x27;snapshot_hash_matches&#x27;: True}].
 * Remaining source/applicability gates:
 *   Later applicability gate: exact introduction of campaign-WORLD-03-end-portal-frame-player-ejection is unknown within (1.8.9, 1.9.4]
 * Source-content correspondence and compilation do not establish tick-level runtime parity.
 */
@MovementChange(emulates = ParkourVersion.V1_8)
public final class EndPortalFramePlayerEjection implements SuffocationProbeHook {
    @Override
    public boolean suffocatesAt(Player player, ParkourVersion target, BlockPos pos, VanillaFn<Boolean> vanilla) {
        // The 1.8 probe floors getShape().minY + 0.5 and tests that cell plus the cell above.
        if (target == ParkourVersion.V1_8) {
            double sampleY = player.getBoundingBox().minY + 0.5D;
            BlockPos samplePos = BlockPos.containing(pos.getX(), sampleY, pos.getZ());
            if (this.isEndPortalFrame(player, samplePos) || this.isEndPortalFrame(player, samplePos.above())) {
                return true;
            }
        }
        return SuffocationProbeColumn.any(player, pos, cursor ->
            player.level().getBlockState(cursor).isSuffocating(player.level(), cursor));
    }

    private boolean isEndPortalFrame(Player player, BlockPos pos) {
        return player.level().getBlockState(pos).is(Blocks.END_PORTAL_FRAME);
    }
}
