package me.wolfii.legacyparkourcompat.change.common;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

/** The existing border policy shared by the pre-1.16 sneak-edge queries. */
public final class SneakEdgeBorderCollision {
    private SneakEdgeBorderCollision() {
    }

    public static boolean hasCollision(Player player) {
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
