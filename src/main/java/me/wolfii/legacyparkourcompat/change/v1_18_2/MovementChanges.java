package me.wolfii.legacyparkourcompat.change.v1_18_2;

import me.wolfii.legacyparkourcompat.mechanic.MovementChangeProvider;
import me.wolfii.legacyparkourcompat.mechanic.MovementChangeRegistry;

public final class MovementChanges implements MovementChangeProvider {
    @Override
    public void register(MovementChangeRegistry registry) {
        registry.register(new GlideFallDistance_1_18_2());
        registry.register(new AirSpeed_1_18_2());
        registry.register(new InsideBlockContact_1_18_2());
        registry.register(new SprintEligibility_1_18_2());
        registry.register(new AutoJumpProbe_1_18_2());
        registry.register(new BoatPassengerFluidPush_1_18_2());
    }
}
