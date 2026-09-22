package me.wolfii.legacyparkourcompat.change.v1_13;

import me.wolfii.legacyparkourcompat.mechanic.MovementChangeProvider;
import me.wolfii.legacyparkourcompat.mechanic.MovementChangeRegistry;

public final class MovementChanges implements MovementChangeProvider {
    @Override
    public void register(MovementChangeRegistry registry) {
        registry.register(new SneakHeight165());
        LegacyInputVector.register(registry);
        LegacyGroundAcceleration.register(registry);
        registry.register(new LegacySprintJumpImpulse());
        SimpleAnvilCollision.register(registry);
        LegacyCauldronCollision.register(registry);
        LegacyHopperCollision.register(registry);
        LegacyBrewingStandCollision.register(registry);
        LegacySnowLayerCollision.register(registry);
        LegacyEndPortalFrameCollision.register(registry);
        SolidBedCollision.register(registry);
        WholeBaseCauldronCollision.register(registry);
    }
}
