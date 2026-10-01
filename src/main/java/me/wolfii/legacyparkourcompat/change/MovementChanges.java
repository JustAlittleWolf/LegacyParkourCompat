package me.wolfii.legacyparkourcompat.change;

import me.wolfii.legacyparkourcompat.mechanic.MovementChangeProvider;
import me.wolfii.legacyparkourcompat.mechanic.MovementChangeRegistry;

/** Source-confirmed player movement deltas across 1.19.4 through 1.20.6. */
public final class MovementChanges implements MovementChangeProvider {
    @Override
    public void register(MovementChangeRegistry registry) {
        registry.register(new FlightActivationJump_1_20_2());
        registry.register(new PassengerCrouch_1_20());
        registry.register(new AttributeGravity_1_20_2());
        registry.register(new JumpBoostPrecision_1_19_4());
        registry.register(new EffectFallDistanceReset_1_19_4());
        registry.register(new EffectFallDistanceReset_1_20());
        registry.register(new EffectFallDistanceReset_1_20_2());
    }
}
