package me.wolfii.legacyparkourcompat.change;

import me.wolfii.legacyparkourcompat.mechanic.MovementChangeProvider;
import me.wolfii.legacyparkourcompat.mechanic.MovementChangeRegistry;
import me.wolfii.legacyparkourcompat.change.v1_15_2.Pre116MovementChanges;
import me.wolfii.legacyparkourcompat.change.v1_16.Pre1162MovementChanges;

public final class MovementChanges implements MovementChangeProvider {
    @Override
    public void register(MovementChangeRegistry registry) {
        registry.register(new Pre116MovementChanges());
        registry.register(new Pre1162MovementChanges());
    }
}
