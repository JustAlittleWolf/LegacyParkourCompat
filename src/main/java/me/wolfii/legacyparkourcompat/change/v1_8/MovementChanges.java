package me.wolfii.legacyparkourcompat.change.v1_8;

import me.wolfii.legacyparkourcompat.mechanic.MovementChangeProvider;
import me.wolfii.legacyparkourcompat.mechanic.MovementChangeRegistry;

public final class MovementChanges implements MovementChangeProvider {
    @Override
    public void register(MovementChangeRegistry registry) {
        registry.register(new SprintDuration_1_8());
        registry.register(new CreativeFlightSneakInput_1_8());
        registry.register(new TruncatedMovementChunkLookup_1_8());
        registry.register(new CreativeFlightFallDistance_1_8());
        registry.register(new NaturalRegeneration_1_8());
        registry.register(new BoatRiderInput_1_8());
        registry.register(new RideableJumpCharge_1_8());
    }
}
