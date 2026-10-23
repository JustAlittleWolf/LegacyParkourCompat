package me.wolfii.legacyparkourcompat.change.common;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

/** Existing query policies shared unchanged by historical sneak-edge changes. */
public final class SneakEdgeCollisionSupport {
    private SneakEdgeCollisionSupport() {
    }

    public static boolean noCollisionWithNativeEntities(Player player, AABB candidate) {
        if (hasBorderCollision(player)) {
            return false;
        }
        return player.level().noBlockCollision(player, candidate)
            && player.level().noEntityCollision(player, candidate);
    }

    public static boolean hasBorderCollision(Player player) {
        WorldBorder border = player.level().getWorldBorder();
        AABB currentBox = player.getBoundingBox();
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
}
