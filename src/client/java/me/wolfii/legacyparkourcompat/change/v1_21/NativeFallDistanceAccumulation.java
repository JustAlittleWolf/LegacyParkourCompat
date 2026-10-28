package me.wolfii.legacyparkourcompat.change.v1_21;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.entity.FallDistanceAccumulationHook;
import net.minecraft.world.entity.Entity;

import java.util.Optional;

/**
 * Resolver fence: profiles before the bounded 1.21.2–1.21.4 evidence retain
 * the native water predicate instead of inheriting the 1.21.4 change.
 */
@MovementChange(emulates = ParkourVersion.V1_21)
public final class NativeFallDistanceAccumulation implements FallDistanceAccumulationHook {
    @Override
    public Optional<Boolean> shouldAccumulateFallDistance(Entity entity, double yMovement, boolean onGround) {
        return Optional.empty();
    }
}
