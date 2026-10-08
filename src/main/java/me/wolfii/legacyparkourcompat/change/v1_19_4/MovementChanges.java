package me.wolfii.legacyparkourcompat.change.v1_19_4;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.change.v1_15_2.GroundFrictionSamplePoint;
import me.wolfii.legacyparkourcompat.mechanic.MovementChangeProvider;
import me.wolfii.legacyparkourcompat.mechanic.MovementChangeRegistry;
import me.wolfii.legacyparkourcompat.mechanic.hook.GroundFrictionBlockBehavior;

public final class MovementChanges implements MovementChangeProvider {
    @Override
    public void register(MovementChangeRegistry registry) {
        registry.register(
            GroundFrictionBlockBehavior.class,
            ParkourVersion.V1_19_4,
            new GroundFrictionSamplePoint()
        );
        registry.register(new DoubleJumpBoostAddition());
        registry.register(new EffectFallDistanceReset());
        registry.register(new SprintJumpImpulse());
        registry.register(new SprintStart());
    }
}
