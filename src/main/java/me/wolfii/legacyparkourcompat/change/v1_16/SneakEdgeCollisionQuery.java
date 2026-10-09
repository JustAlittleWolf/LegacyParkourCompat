package me.wolfii.legacyparkourcompat.change.v1_16;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.SneakEdgeCollisionQueryBehavior;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

@MovementChange(emulates = ParkourVersion.V1_16)
public final class SneakEdgeCollisionQuery implements SneakEdgeCollisionQueryBehavior {
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
