package me.wolfii.legacyparkourcompat.mechanic.water;

import net.minecraft.world.phys.Vec3;

/** Shared Feather Vec3d length and normalization used by the old water samples. */
public final class LegacyWaterFlowMath {
    private LegacyWaterFlowMath() { }

    public static double length(Vec3 vector) {
        return (float) Math.sqrt(vector.x * vector.x + vector.y * vector.y + vector.z * vector.z);
    }

    public static Vec3 normalize(Vec3 vector) {
        double length = length(vector);
        return length < 1.0E-4 ? Vec3.ZERO : new Vec3(vector.x / length, vector.y / length, vector.z / length);
    }
}
