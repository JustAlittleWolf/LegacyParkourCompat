package me.wolfii.legacyparkourcompat.change.v1_12;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.player.SlowFallingGravitySamplingHook;

import java.util.OptionalDouble;

/**
 * Keeps profiles through 1.12 on native gravity routing, before Slow Falling
 * existed.
 *
 * <p>Exact source references: Minecraft 1.12.2, Ornithe Feather,
 * {@code net/minecraft/entity/living/effect/StatusEffects.java:1-74} has no
 * Slow Falling registration; {@code net/minecraft/entity/living/LivingEntity.java:1514-1527}
 * subtracts fixed gravity in ordinary air movement.
 */
@MovementChange(emulates = ParkourVersion.V1_12)
public final class SlowFallingGravitySamplingFence implements SlowFallingGravitySamplingHook {
    @Override
    public OptionalDouble effectiveGravity(boolean slowFalling, double baseGravity, double sampledYVelocity) {
        return OptionalDouble.empty();
    }
}
