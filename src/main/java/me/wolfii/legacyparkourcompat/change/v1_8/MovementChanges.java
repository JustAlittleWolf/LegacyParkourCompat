package me.wolfii.legacyparkourcompat.change.v1_8;

import me.wolfii.legacyparkourcompat.mechanic.MovementChangeProvider;
import me.wolfii.legacyparkourcompat.mechanic.MovementChangeRegistry;

public final class MovementChanges implements MovementChangeProvider {
    @Override
    public void register(MovementChangeRegistry registry) {
        registry.register(new BoatRiderInput());
        registry.register(new CreativeFlightFallDistance());
        registry.register(new CreativeFlightSneakInput());
        registry.register(new RideableJumpCharge());
        registry.register(new SprintDuration());
        registry.register(new TruncatedMovementChunkLookup());
        registry.register(new VelocityZeroThreshold());
    }
}
