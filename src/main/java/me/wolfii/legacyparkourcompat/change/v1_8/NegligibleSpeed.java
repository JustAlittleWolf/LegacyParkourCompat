package me.wolfii.legacyparkourcompat.change.v1_8;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.NegligibleSpeedBehavior;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

/**
 * 1.8 zeroes each velocity axis below {@code 0.005}. 1.9 lowered that to
 * {@code 0.003}, which is also why jump height rose from 1.249 to 1.252.
 */
@MovementChange(emulates = ParkourVersion.V1_8)
public final class NegligibleSpeed implements NegligibleSpeedBehavior {
    @Override
    public double threshold(Entity entity, double vanilla) {
        return 0.005;
    }

    @Override
    public Vec3 apply(Entity entity, Vec3 movement) {
        double x = movement.x;
        double y = movement.y;
        double z = movement.z;
        if (Math.abs(x) < 0.005) {
            x = 0.0;
        }
        if (Math.abs(y) < 0.005) {
            y = 0.0;
        }
        if (Math.abs(z) < 0.005) {
            z = 0.0;
        }
        return new Vec3(x, y, z);
    }
}
