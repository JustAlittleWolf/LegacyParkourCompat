package me.wolfii.legacyparkourcompat.change.v1_8;

import me.wolfii.legacyparkourcompat.mechanic.MovementChangeProvider;
import me.wolfii.legacyparkourcompat.mechanic.MovementChangeRegistry;

public final class MovementChanges implements MovementChangeProvider {
    @Override
    public void register(MovementChangeRegistry registry) {
        registry.register(new NegligibleSpeed());
        registry.register(new StandingPose());
        registry.register(new NoClientEntityPush());
        registry.register(new LadderCollision());
        registry.register(new LilyPadCollision());
        registry.register(new PistonHeadCollision());
        AnvilCollision.register(registry);
        ChestCollision.register(registry);
        PaneCollision.register(registry);
    }
}
