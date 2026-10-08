package me.wolfii.legacyparkourcompat.change.v1_18_2;

import me.wolfii.legacyparkourcompat.mechanic.MovementChangeProvider;
import me.wolfii.legacyparkourcompat.mechanic.MovementChangeRegistry;

public final class MovementChanges implements MovementChangeProvider {
    @Override
    public void register(MovementChangeRegistry registry) {
        registry.register(new AirSpeed());
        registry.register(new AutoJumpVectorNormalization());
        registry.register(new BoatPassengerFluidPush());
        registry.register(new ElytraLiftForce());
        registry.register(new GlideFallDistance());
        registry.register(new InsideBlockContact());
        registry.register(new SneakEdge());
    }
}
