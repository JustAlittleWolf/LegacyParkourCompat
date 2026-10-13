package me.wolfii.legacyparkourcompat.change.input;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;

/** Identical pose-box arithmetic in both accepted source versions. */
public final class HistoricalInputSlowdown {
    private HistoricalInputSlowdown() {}

    /**
     * Minecraft 1.14.1 (Yarn 1.14.1+build.10-v2) and 1.14.2
     * (Yarn 1.14.2+build.7-v2), Entity.wouldPoseNotCollide and
     * Entity.method_20343; PlayerEntity.SIZES: width 0.6F, crouch height
     * 1.5F, standing height 1.8F. Preserve float half-width then double
     * coordinate arithmetic; modern pose dimensions can include scaling.
     */
    public static boolean fits(Player player, float height) {
        float halfWidth = 0.6F / 2.0F;
        return player.level().noCollision(player, new AABB(
            player.getX() - halfWidth, player.getY(), player.getZ() - halfWidth,
            player.getX() + halfWidth, player.getY() + height, player.getZ() + halfWidth
        ));
    }
}
