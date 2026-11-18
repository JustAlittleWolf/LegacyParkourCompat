package me.wolfii.legacyparkourcompat.change.v1_12;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.collision.SnowLayerOneCollisionPlaneHook;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

/**
 * 1.12.2 Ornithe Feather SnowLayerBlock.SHAPES/getCollisionShape returns a
 * zero-height box for LAYERS=1; World.getCollisions admits it when the expanded
 * query strictly intersects it, and Entity.move clips Y against its maxY.
 * In 1.13 Ornithe Feather SnowLayerBlock.getCollisionShape returns the empty
 * SHAPES[0], so the same query and Entity.move have no such clip candidate.
 * The 1.8 through 1.12.2 Ornithe Feather SnowLayerBlock.getCollisionShape,
 * World.getCollisions and Box.intersectY use the same plane for this clip.
 * The support check bounds the fixed-state witness without replaying the
 * earlier placement predicates.
 */
@MovementChange(emulates = ParkourVersion.V1_12)
public final class SnowLayerOneCollisionPlane implements SnowLayerOneCollisionPlaneHook {
    public SnowLayerOneCollisionPlane() {
    }

    @Override
    public double clipDescendingY(Player player, AABB startBox, double requestedY, double nativeY) {
        if (!Double.isFinite(requestedY) || !Double.isFinite(nativeY)
            || requestedY >= 0.0 || nativeY < requestedY || nativeY >= 0.0
            || !(startBox.minX < startBox.maxX && startBox.minY < startBox.maxY
                && startBox.minZ < startBox.maxZ)) {
            return nativeY;
        }

        var level = player.level();
        var border = level.getWorldBorder();
        AABB swept = startBox.expandTowards(0.0, requestedY, 0.0);
        if (!(swept.minX - 2.0 > border.getMinX() && swept.maxX + 2.0 < border.getMaxX()
            && swept.minZ - 2.0 > border.getMinZ() && swept.maxZ + 2.0 < border.getMaxZ()
            && swept.minY >= 0.0 && swept.maxY <= 256.0)
            || border.isInsideCloseToBorder(player, swept)) {
            return nativeY;
        }

        // Validate the collector's surrounding cells as well as the sweep so
        // tall, moving, or otherwise unsupported geometry cannot compete.
        int minX = Mth.floor(swept.minX - 1.0E-7) - 1;
        int minY = Mth.floor(swept.minY - 1.0E-7) - 1;
        int minZ = Mth.floor(swept.minZ - 1.0E-7) - 1;
        int maxX = Mth.floor(swept.maxX + 1.0E-7) + 1;
        int maxY = Mth.floor(swept.maxY + 1.0E-7) + 1;
        int maxZ = Mth.floor(swept.maxZ + 1.0E-7) + 1;
        if (minY < Math.max(0, level.getMinY()) || maxY > Math.min(255, level.getMaxY())) {
            return nativeY;
        }

        double clippedY = nativeY;
        int clippingPlane = Integer.MIN_VALUE;
        int highestOccupiedTop = Integer.MIN_VALUE;
        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
        for (int x = minX; x <= maxX; x++) {
            for (int z = minZ; z <= maxZ; z++) {
                var chunk = level.getChunkForCollisions(x >> 4, z >> 4);
                if (chunk == null) {
                    return nativeY;
                }
                boolean overlapsColumn = swept.minX < x + 1.0 && swept.maxX > x
                    && swept.minZ < z + 1.0 && swept.maxZ > z;
                for (int y = minY; y <= maxY; y++) {
                    BlockState state = chunk.getBlockState(pos.set(x, y, z));
                    if (state.isAir()) {
                        continue;
                    }
                    if (state.is(Blocks.STONE) || isStackSupport(state)) {
                        // Both shapes are contained in their cells. A cell
                        // that extends above the winning plane is outside
                        // the noncompeting support/control witness.
                        if (overlapsColumn && swept.minY < y + 1.0 && swept.maxY > y) {
                            highestOccupiedTop = Math.max(highestOccupiedTop, y + 1);
                        }
                        continue;
                    }
                    if (!state.is(Blocks.SNOW) || state.getValue(SnowLayerBlock.LAYERS) != 1) {
                        return nativeY;
                    }

                    // Old World.getCollisions scans X, Z, then Y. The box for
                    // layer one has minY=maxY=y, and Box.intersects is strict.
                    if (!(overlapsColumn && swept.minY < y && swept.maxY > y && startBox.minY >= y)) {
                        continue;
                    }
                    if (!isStackSupport(chunk.getBlockState(pos.set(x, y - 1, z)))) {
                        return nativeY;
                    }

                    // Box.intersectY clips a negative request to maxY-minY
                    // only when the new limit is greater than the old one.
                    double planeLimit = y - startBox.minY;
                    if (clippedY < 0.0 && planeLimit > clippedY) {
                        clippedY = planeLimit;
                        clippingPlane = y;
                    }
                }
            }
        }
        if (clippedY == nativeY || highestOccupiedTop > clippingPlane
            || !level.getEntities(player, swept.inflate(0.25), other -> true).isEmpty()) {
            return nativeY;
        }
        return clippedY;
    }

    private static boolean isStackSupport(BlockState state) {
        return state.is(Blocks.SNOW) && state.getValue(SnowLayerBlock.LAYERS) >= 7;
    }
}
