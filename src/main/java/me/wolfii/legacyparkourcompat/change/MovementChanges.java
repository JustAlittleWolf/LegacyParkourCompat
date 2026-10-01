package me.wolfii.legacyparkourcompat.change;

import me.wolfii.legacyparkourcompat.change.v1_10.FarmlandConversionV1_10;
import me.wolfii.legacyparkourcompat.change.v1_10.FarmlandFullCollision;
import me.wolfii.legacyparkourcompat.change.v1_10.PlayerExhaustionV1_10;
import me.wolfii.legacyparkourcompat.change.v1_10.SneakEdgeDistanceV1_10;
import me.wolfii.legacyparkourcompat.change.v1_10.SneakEdgeMoverV1_10;
import me.wolfii.legacyparkourcompat.change.v1_11.PistonMovementV1_11;
import me.wolfii.legacyparkourcompat.change.v1_15_2.Pre116MovementChanges;
import me.wolfii.legacyparkourcompat.change.v1_16.Pre1162MovementChanges;
import me.wolfii.legacyparkourcompat.change.v1_9.NoAutoJump;
import me.wolfii.legacyparkourcompat.mechanic.MovementChangeProvider;
import me.wolfii.legacyparkourcompat.mechanic.MovementChangeRegistry;

public final class MovementChanges implements MovementChangeProvider {
    @Override
    public void register(MovementChangeRegistry registry) {
        registry.register(new NoAutoJump());
        registry.register(new FarmlandFullCollision());
        registry.register(new SneakEdgeDistanceV1_10());
        registry.register(new SneakEdgeMoverV1_10());
        registry.register(new FarmlandConversionV1_10());
        registry.register(new PlayerExhaustionV1_10());
        registry.register(new PistonMovementV1_11());
        registry.register(new WaterMovement_1_12());
        registry.register(new GroundGlideMovement_1_12());
        registry.register(new Pre116MovementChanges());
        registry.register(new Pre1162MovementChanges());
    }
}
