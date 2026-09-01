package me.wolfii.legacyparkourcompat.change;

import me.wolfii.legacyparkourcompat.mechanic.MovementChangeProvider;
import me.wolfii.legacyparkourcompat.mechanic.MovementChangeRegistry;

public final class BuiltinMovementChanges implements MovementChangeProvider {
    @Override
    public void register(MovementChangeRegistry registry) {
        registry.register(new NoSwimming_1_12());
        registry.register(new LadderCollision_1_8());
        registry.register(new SupportingBlock_1_19_4());
    }
}
