package me.wolfii.legacyparkourcompat.change.v1_21_4;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.entity.FallDistanceAccumulationHook;
import net.minecraft.world.entity.Entity;

import java.util.Optional;

/**
 * Restores the pre-water-gate admission rule for fall-distance accumulation.
 *
 * <p>Mojmap 1.21.4 {@code net.minecraft.world.entity.Entity.checkFallDamage}
 * resets on-ground fall distance first, then accumulates only in
 * {@code else if (dy < 0)}. Mojmap 1.21.5's same method instead accumulates
 * under {@code !isInWater() && dy < 0} before on-ground handling. Replacing the
 * water predicate with the grounded flag preserves that old downward branch
 * while leaving the native float cast and accumulation expression intact.
 */
@MovementChange(emulates = ParkourVersion.V1_21_4)
public final class FallDistanceAccumulation implements FallDistanceAccumulationHook {
    @Override
    public Optional<Boolean> shouldAccumulateFallDistance(Entity entity, double yMovement, boolean onGround) {
        return Optional.of(!onGround && yMovement < 0.0);
    }
}
