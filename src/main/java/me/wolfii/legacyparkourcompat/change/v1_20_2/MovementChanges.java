package me.wolfii.legacyparkourcompat.change.v1_20_2;

import me.wolfii.legacyparkourcompat.mechanic.MovementChangeProvider;
import me.wolfii.legacyparkourcompat.mechanic.MovementChangeRegistry;

public final class MovementChanges implements MovementChangeProvider {
    @Override
    public void register(MovementChangeRegistry registry) {
        registry.register(new AttributeGravity());
        registry.register(new EffectFallDistanceReset());
        registry.register(new FlightActivationJump());
        registry.register(new SprintJumpImpulse());
    }
}
