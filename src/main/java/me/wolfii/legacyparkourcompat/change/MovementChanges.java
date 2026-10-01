package me.wolfii.legacyparkourcompat.change;

import me.wolfii.legacyparkourcompat.mechanic.MovementChangeProvider;
import me.wolfii.legacyparkourcompat.mechanic.MovementChangeRegistry;

public final class MovementChanges implements MovementChangeProvider {
    @Override
    public void register(MovementChangeRegistry registry) {
        registry.register(new WaterMovement_1_12());
        registry.register(new GroundGlideMovement_1_12());
    }
}
