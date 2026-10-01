package me.wolfii.legacyparkourcompat.change;

import me.wolfii.legacyparkourcompat.change.v1_9.NoAutoJump;
import me.wolfii.legacyparkourcompat.change.v1_10.FarmlandFullCollision;
import me.wolfii.legacyparkourcompat.mechanic.MovementChangeProvider;
import me.wolfii.legacyparkourcompat.mechanic.MovementChangeRegistry;

/** Registers the historical deltas implemented by this compatibility layer. */
public final class MovementChanges implements MovementChangeProvider {
    @Override
    public void register(MovementChangeRegistry registry) {
        registry.register(new NoAutoJump());
        registry.register(new FarmlandFullCollision());
    }
}
