package me.wolfii.legacyparkourcompat.change.v1_13;

import me.wolfii.legacyparkourcompat.change.v1_8.LegacyGroundAcceleration;
import me.wolfii.legacyparkourcompat.change.v1_8.LegacySprintJump;
import me.wolfii.legacyparkourcompat.mechanic.MovementChangeProvider;
import me.wolfii.legacyparkourcompat.mechanic.MovementChangeRegistry;

public final class MovementChanges implements MovementChangeProvider {
    @Override
    public void register(MovementChangeRegistry registry) {
        registry.register(new LegacyGroundAcceleration());
        registry.register(new LegacySprintJump());
        registry.register(new DoubleClimbVerticalClamp());
        registry.register(new SneakHeight165());
        registry.register(new KeepPoseWhenResizeBlocked());
        registry.register(new Pre114CollisionVelocity());
        SimpleAnvilCollision.register(registry);
        LegacyCauldronCollision.register(registry);
        WholeBaseCauldronCollision.register(registry);
        SolidBedCollision.register(registry);
        LegacyHopperCollision.register(registry);
        LegacyBrewingStandCollision.register(registry);
        LegacySnowLayerCollision.register(registry);
        LegacyEndPortalFrameCollision.register(registry);
    }
}
