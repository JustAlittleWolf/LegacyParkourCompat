package me.wolfii.legacyparkourcompat.change.v1_21_4;

import me.wolfii.legacyparkourcompat.mechanic.MovementChangeProvider;
import me.wolfii.legacyparkourcompat.mechanic.MovementChangeRegistry;

public final class MovementChanges implements MovementChangeProvider {
    @Override
    public void register(MovementChangeRegistry registry) {
        registry.register(new FallFlyingFirstPose());
        registry.register(new PerAxisNegligibleSpeed());
        registry.register(new PreSquareInput());
        registry.register(new OriginalFootSlice());
    }
}
