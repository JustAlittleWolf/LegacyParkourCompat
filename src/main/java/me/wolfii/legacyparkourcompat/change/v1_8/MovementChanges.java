package me.wolfii.legacyparkourcompat.change.v1_8;

import me.wolfii.legacyparkourcompat.mechanic.MovementChangeProvider;
import me.wolfii.legacyparkourcompat.mechanic.MovementChangeRegistry;

public final class MovementChanges implements MovementChangeProvider {
    @Override
    public void register(MovementChangeRegistry registry) {
        registry.register(new BoatRiderInput());
        registry.register(new CreativeFlightFallDistance());
        registry.register(new CreativeFlightSneakInput());
        for (String blockId : FencePortalFrameConnection.BLOCK_IDS) {
            registry.register(new FencePortalFrameConnection(blockId));
        }
        for (String blockId : PaneCollisionShape.BLOCK_IDS) {
            registry.register(new PaneCollisionShape(blockId));
        }
        registry.register(new RideableJumpCharge());
        registry.register(new SneakingDimensions());
        registry.register(new SprintDuration());
        registry.register(new TruncatedMovementChunkLookup());
        registry.register(new VelocityZeroThreshold());
    }
}
