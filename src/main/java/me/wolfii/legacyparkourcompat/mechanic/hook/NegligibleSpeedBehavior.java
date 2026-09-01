package me.wolfii.legacyparkourcompat.mechanic.hook;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

/**
 * Historical negligible-speed cutoff (0.005 per axis in 1.8, 0.003 from 1.9).
 */
@MechanicType("player.speed.negligible")
public interface NegligibleSpeedBehavior extends VersionedMechanic {
    /**
     * Per-axis cutoff used when {@link #apply} is not overridden.
     */
    double threshold(Entity entity, double vanilla);

    /**
     * Replaces vanilla {@code LivingEntity#aiStep} momentum cancel.
     * 1.8 zeroes each axis independently at {@code 0.005}.
     */
    default Vec3 apply(Entity entity, Vec3 movement) {
        double t = this.threshold(entity, 0.003);
        double x = Math.abs(movement.x) < t ? 0.0 : movement.x;
        double y = Math.abs(movement.y) < t ? 0.0 : movement.y;
        double z = Math.abs(movement.z) < t ? 0.0 : movement.z;
        return new Vec3(x, y, z);
    }
}
