package me.wolfii.legacyparkourcompat.mechanic.water;

import net.minecraft.world.phys.AABB;

/** Shared query-box and liquid-surface arithmetic of the old material scan. */
public final class LegacyWaterProbe {
    private LegacyWaterProbe() { }

    public static AABB bounds(AABB body) {
        return new AABB(body.minX, body.minY - -0.4F, body.minZ,
            body.maxX, body.maxY + -0.4F, body.maxZ).deflate(0.001);
    }

    public static boolean includes(int exclusiveMaxY, int cellY, int liquidLevel) {
        int level = liquidLevel >= 8 ? 0 : liquidLevel;
        double surface = cellY + 1 - (level + 1) / 9.0F;
        return exclusiveMaxY >= surface;
    }
}
