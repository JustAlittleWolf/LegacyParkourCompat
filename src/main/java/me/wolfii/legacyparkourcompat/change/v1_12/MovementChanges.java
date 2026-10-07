package me.wolfii.legacyparkourcompat.change.v1_12;

import me.wolfii.legacyparkourcompat.mechanic.MovementChangeProvider;
import me.wolfii.legacyparkourcompat.mechanic.MovementChangeRegistry;

public final class MovementChanges implements MovementChangeProvider {
    @Override
    public void register(MovementChangeRegistry registry) {
        registry.register(new GroundAcceleration());
        registry.register(new SwimmingDimensions());
        registry.register(new SwimmingState());
        registry.register(new VerticalPitchGlideLook());
        registry.register(new WaterJump());
        registry.register(new WaterSneak());
        registry.register(new WaterSprintGate());
        registry.register(new WaterTravel());
    }
}
