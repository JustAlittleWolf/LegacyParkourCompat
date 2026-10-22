package me.wolfii.legacyparkourcompat.change.v1_16_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.push.PlayerPushNormalizationHook;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

/**
 * 1.16.5 Mojmap net.minecraft.world.entity.Entity#push(Entity) assigns
 * net.minecraft.util.Mth#sqrt(double)'s float result to its double divisor.
 * 1.17 Mojmap net.minecraft.world.entity.Entity#push(Entity) assigns the
 * double Math.sqrt(double) result instead. Profiles through 1.16.5 retain the
 * float rounding; 1.17 and later use the native implementation.
 */
@MovementChange(emulates = ParkourVersion.V1_16_2)
public final class PlayerPushNormalization implements PlayerPushNormalizationHook {
    @Override
    public Vec3 normalize(Entity receiver, Entity other) {
        double xa = other.getX() - receiver.getX();
        double za = other.getZ() - receiver.getZ();
        double distance = Mth.absMax(xa, za);
        distance = (float) Math.sqrt(distance);
        xa /= distance;
        za /= distance;
        double reciprocal = 1.0 / distance;
        if (reciprocal > 1.0) {
            reciprocal = 1.0;
        }
        xa *= reciprocal;
        za *= reciprocal;
        xa *= 0.05F;
        za *= 0.05F;
        return new Vec3(-xa, 0.0, -za);
    }
}
