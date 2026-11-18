package me.wolfii.legacyparkourcompat.change.v1_20_5;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.change.common.HistoricalGlideFallDistanceLimiter;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.VanillaCall;
import me.wolfii.legacyparkourcompat.mechanic.hook.gliding.GlideFallDistanceLimiterStageHook;
import net.minecraft.world.entity.LivingEntity;

/** Preserves the existing limiter stages through the 1.20.5 and 1.20.6 profile. */
@MovementChange(emulates = ParkourVersion.V1_20_5)
public final class GlideFallDistanceLimiterStage implements GlideFallDistanceLimiterStageHook {
    @Override
    public void atStage(LivingEntity entity, ParkourVersion selectedVersion, Stage stage, VanillaCall nativeLimiter) {
        HistoricalGlideFallDistanceLimiter.atStage(entity, selectedVersion, stage, nativeLimiter, false);
    }
}
