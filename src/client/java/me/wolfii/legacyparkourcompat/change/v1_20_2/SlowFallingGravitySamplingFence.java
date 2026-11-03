package me.wolfii.legacyparkourcompat.change.v1_20_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.player.SlowFallingGravitySamplingHook;

import java.util.OptionalDouble;

/**
 * Samples the Slow Falling cap from ordinary air-travel velocity before the
 * movement response, matching releases through 1.20.4.
 *
 * <p>Exact source references: Minecraft 1.20.2, Mojmap,
 * {@code net/minecraft/world/entity/LivingEntity.travel(Vec3)},
 * {@code decompiled_minecraft/1.20.2/mojmap/net/minecraft/world/entity/LivingEntity.java:2038-2157};
 * and Minecraft 1.20.4, Mojmap,
 * {@code net/minecraft/world/entity/LivingEntity.travel(Vec3)},
 * {@code decompiled_minecraft/1.20.4/mojmap/net/minecraft/world/entity/LivingEntity.java:2035-2154}.
 * Both ordinary dry-air paths select {@code Math.min(gravity, 0.01)} from the
 * nonpositive Y-velocity sample before movement, then subtract the saved
 * gravity after the movement helper.
 */
@MovementChange(emulates = ParkourVersion.V1_20_2)
public final class SlowFallingGravitySamplingFence implements SlowFallingGravitySamplingHook {
    @Override
    public OptionalDouble effectiveGravity(boolean slowFalling, double baseGravity, double sampledYVelocity) {
        if (!slowFalling || baseGravity == 0.0) {
            return OptionalDouble.empty();
        }
        return OptionalDouble.of(sampledYVelocity <= 0.0 ? Math.min(baseGravity, 0.01) : baseGravity);
    }
}
