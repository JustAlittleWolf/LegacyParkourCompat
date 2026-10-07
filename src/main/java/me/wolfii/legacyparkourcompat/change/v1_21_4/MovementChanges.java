package me.wolfii.legacyparkourcompat.change.v1_21_4;

import me.wolfii.legacyparkourcompat.mechanic.MovementChangeProvider;
import me.wolfii.legacyparkourcompat.mechanic.MovementChangeRegistry;

public final class MovementChanges implements MovementChangeProvider {
    @Override
    public void register(MovementChangeRegistry registry) {
        registry.register(new EdgeBackoffProbeBounds());
        registry.register(new GlideFallDistance());
        registry.register(new KeyboardDiagonalInput());
        registry.register(new PowderSnowClimbBoost());
        registry.register(new ShallowWaterSprintEligibility());
    }
}
