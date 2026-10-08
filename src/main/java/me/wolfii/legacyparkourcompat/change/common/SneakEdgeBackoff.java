package me.wolfii.legacyparkourcompat.change.common;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

/** Shared horizontal backoff loops used by historical sneak-edge changes. */
public final class SneakEdgeBackoff {
    private SneakEdgeBackoff() {
    }

    public static Vec3 apply(Player player, Vec3 delta, float probeDistance) {
        double x = delta.x;
        double z = delta.z;
        double downStep = -probeDistance;
        while (x != 0.0D && player.level().noCollision(player, player.getBoundingBox().move(x, downStep, 0.0D))) {
            x = reduceTowardZero(x);
        }
        while (z != 0.0D && player.level().noCollision(player, player.getBoundingBox().move(0.0D, downStep, z))) {
            z = reduceTowardZero(z);
        }
        while (x != 0.0D && z != 0.0D
            && player.level().noCollision(player, player.getBoundingBox().move(x, downStep, z))) {
            x = reduceTowardZero(x);
            z = reduceTowardZero(z);
        }
        return new Vec3(x, delta.y, z);
    }

    private static double reduceTowardZero(double value) {
        if (value < 0.05D && value >= -0.05D) {
            return 0.0D;
        }
        return value > 0.0D ? value - 0.05D : value + 0.05D;
    }
}
