package me.wolfii.legacyparkourcompat.change.v1_21;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.change.common.HistoricalGlideFallDistanceLimiter;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.VanillaCall;
import me.wolfii.legacyparkourcompat.mechanic.hook.gliding.GlideFallDistanceLimiterStageHook;
import net.minecraft.world.entity.LivingEntity;

/**
 * In Mojmap 1.21 and 1.21.1 LivingEntity#travel, the lava branch precedes the
 * fall-flying limiter, including when the player's foot cell is climbable.
 * Mojmap 1.21.2 LivingEntity#updateFallFlying performs the limiter before
 * fluid routing. In 26.2 unobfuscated, LivingEntity#travelInFluid still takes
 * precedence over travelFallFlying and dispatches to water before lava.
 * Defer the native limiter to the glide travel callback for qualifying lava,
 * where fluid routing bypasses that callback.
 */
@MovementChange(emulates = ParkourVersion.V1_21)
public final class GlideFallDistanceLimiterStage implements GlideFallDistanceLimiterStageHook {
    @Override
    public void atStage(LivingEntity entity, ParkourVersion selectedVersion, Stage stage, VanillaCall nativeLimiter) {
        HistoricalGlideFallDistanceLimiter.atStage(entity, selectedVersion, stage, nativeLimiter, true);
    }
}
