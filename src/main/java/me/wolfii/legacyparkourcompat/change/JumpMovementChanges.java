package me.wolfii.legacyparkourcompat.change;

import me.wolfii.legacyparkourcompat.change.v1_17.FloatJumpBoostAddition;
import me.wolfii.legacyparkourcompat.change.v1_17_1.DoubleJumpBoostAddition;
import me.wolfii.legacyparkourcompat.mechanic.MovementChangeProvider;
import me.wolfii.legacyparkourcompat.mechanic.MovementChangeRegistry;

public final class JumpMovementChanges implements MovementChangeProvider {
    @Override
    public void register(MovementChangeRegistry registry) {
        registry.register(new FloatJumpBoostAddition());
        registry.register(new DoubleJumpBoostAddition());
    }
}
