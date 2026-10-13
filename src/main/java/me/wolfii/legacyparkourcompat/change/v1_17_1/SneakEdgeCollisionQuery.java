package me.wolfii.legacyparkourcompat.change.v1_17_1;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.collision.SneakEdgeCollisionQueryHook;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

/**
 * Source evidence (paths are relative to decompiled_minecraft/):
 * 1.17.1/mojmap/net/minecraft/world/entity/player/Player.java:
 *   maybeBackOffFromEdge; lines [[1017, 1067]].
 *   Source SHA-256: 724bb298499dabe489dffd5ce7ec81c9773619a8d2c70044911eae4c9e8ff481.
 * 1.18.2/mojmap/net/minecraft/world/entity/player/Player.java:
 *   maybeBackOffFromEdge; lines [[1032, 1080]].
 *   Source SHA-256: bf639c1962ff90d69e4569b2b18f6fcf57ac46ef80b19686f0fbc1687fca744a.
 * Bounded source decisions:
 *   Decision 4a5eae32b5819418dbd59eb4a763ee24ea1f2c52:development/research/archive/legacy-workflows/source-boundary-reviews/2026-10-08-mc1171-1182-snapshot-review.md; ACCEPT, bounded.
 *   Reviewed snapshot e531086eecc778432b40b2b9ef39499b8a7f0dba:workflows/source-campaign-2026-10-07/1.17.1--1.18.2/findings/F-008-border-edge-gate.md.
 *   Decision 4a5eae32b5819418dbd59eb4a763ee24ea1f2c52:development/research/archive/legacy-workflows/source-boundary-reviews/2026-10-09-F008-border-edge-gate-review.md; ACCEPT, sampled source boundary.
 * Source-content correspondence and compilation do not establish tick-level runtime parity.
 */
@MovementChange(emulates = ParkourVersion.V1_17_1)
public final class SneakEdgeCollisionQuery implements SneakEdgeCollisionQueryHook {
    @Override
    public boolean noCollision(Player player, AABB candidate) {
        if (this.hasBorderCollision(player)) {
            return false;
        }

        return player.level().noBlockCollision(player, candidate)
            && player.level().noEntityCollision(player, candidate);
    }

    private boolean hasBorderCollision(Player player) {
        WorldBorder border = player.level().getWorldBorder();
        AABB currentBox = player.getBoundingBox();
        if (isBoxFullyWithinWorldBorder(border, currentBox)) {
            return false;
        }

        VoxelShape borderShape = border.getCollisionShape();
        boolean intersectsDeflatedBox = Shapes.joinIsNotEmpty(
            borderShape,
            Shapes.create(currentBox.deflate(1.0E-7D)),
            BooleanOp.AND
        );
        boolean intersectsInflatedBox = Shapes.joinIsNotEmpty(
            borderShape,
            Shapes.create(currentBox.inflate(1.0E-7D)),
            BooleanOp.AND
        );
        return !intersectsDeflatedBox && intersectsInflatedBox;
    }

    private static boolean isBoxFullyWithinWorldBorder(WorldBorder border, AABB box) {
        double minX = Mth.floor(border.getMinX());
        double minZ = Mth.floor(border.getMinZ());
        double maxX = Mth.ceil(border.getMaxX());
        double maxZ = Mth.ceil(border.getMaxZ());
        return box.minX > minX && box.minX < maxX
            && box.minZ > minZ && box.minZ < maxZ
            && box.maxX > minX && box.maxX < maxX
            && box.maxZ > minZ && box.maxZ < maxZ;
    }
}
