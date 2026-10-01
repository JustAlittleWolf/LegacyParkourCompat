package me.wolfii.legacyparkourcompat.change;

import me.wolfii.legacyparkourcompat.change.v1_20_5.DoubleTapSprintWindow;
import me.wolfii.legacyparkourcompat.change.v1_20_5.EdgeBackoffProbeBounds;
import me.wolfii.legacyparkourcompat.change.v1_20_5.GroundJumpVerticalVelocity;
import me.wolfii.legacyparkourcompat.change.v1_20_5.KeyboardDiagonalInput;
import me.wolfii.legacyparkourcompat.change.v1_20_5.PowderSnowClimbBoost;
import me.wolfii.legacyparkourcompat.change.v1_20_5.ShallowWaterSprintEligibility;
import me.wolfii.legacyparkourcompat.mechanic.MovementChangeProvider;
import me.wolfii.legacyparkourcompat.mechanic.MovementChangeRegistry;

public final class MovementChanges implements MovementChangeProvider {
    @Override
    public void register(MovementChangeRegistry registry) {
        registry.register(new DoubleTapSprintWindow());
        registry.register(new ShallowWaterSprintEligibility());
        registry.register(new KeyboardDiagonalInput());
        registry.register(new GroundJumpVerticalVelocity());
        registry.register(new EdgeBackoffProbeBounds());
        registry.register(new PowderSnowClimbBoost());
    }
}
