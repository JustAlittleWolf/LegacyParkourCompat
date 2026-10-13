package me.wolfii.legacyparkourcompat.change.v1_16;

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
 * 1.15.2/mojmap/net/minecraft/world/level/CollisionGetter.java:
 *   getBlockCollisions.
 *   getBlockCollisions.
 * 1.16.5/mojmap/net/minecraft/world/level/CollisionGetter.java:
 *   noCollision.
 *   getBlockCollisions.
 * 1.16.5/mojmap/net/minecraft/world/level/CollisionSpliterator.java:
 *   getChunk.
 * No exact independently accepted source binding is established for this operation.
 * Source-content correspondence and compilation do not establish tick-level runtime parity.
 */
@MovementChange(emulates = ParkourVersion.V1_16)
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
