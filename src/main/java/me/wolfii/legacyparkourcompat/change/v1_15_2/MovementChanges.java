package me.wolfii.legacyparkourcompat.change.v1_15_2;

import me.wolfii.legacyparkourcompat.mechanic.MovementChangeProvider;
import me.wolfii.legacyparkourcompat.mechanic.MovementChangeRegistry;

public final class MovementChanges implements MovementChangeProvider {
    @Override
    public void register(MovementChangeRegistry registry) {
        registry.register(new GroundFrictionSamplePoint());
        registry.register(new SuffocationProbe());
        registry.register(new FluidCurrentMinimum());
        registry.register(new FluidJump());
        registry.register(new LavaCurrent());
        registry.register(new LavaTravel());
        registry.register(new SprintTrigger());
        registry.register(new WaterDescent());
    }
}
