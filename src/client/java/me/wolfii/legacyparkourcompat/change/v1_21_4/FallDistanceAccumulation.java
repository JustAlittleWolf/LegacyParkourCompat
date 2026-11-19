package me.wolfii.legacyparkourcompat.change.v1_21_4;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.entity.FallDistanceAccumulationHook;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;

import java.util.Optional;

/**
 * Restores the pre-water-gate admission rule for fall-distance accumulation.
 *
 * <p>Ornithe Feather 1.7.10 and Mojmap 1.16.2 through 1.21.4
 * {@code Entity#checkFallDamage} accumulate on airborne negative Y
 * displacement even in water. Mojmap 1.16.2 {@code Player#isAboveGround}
 * reads that distance on a later move to decide whether to back off an edge.
 * Mojmap 1.21.5 and unobfuscated 26.2 {@code Entity#checkFallDamage} gate
 * accumulation on {@code !isInWater() && dy < 0} before handling a landing.
 * The admission rule applies after the separate fluid refresh in
 * {@code LivingEntity#checkFallDamage}.
 */
@MovementChange(emulates = ParkourVersion.V1_21_4)
public final class FallDistanceAccumulation implements FallDistanceAccumulationHook {
    @Override
    public Optional<Boolean> shouldAccumulateFallDistance(Entity entity, double yMovement, boolean onGround) {
        return entity instanceof LocalPlayer
            ? Optional.of(!onGround && yMovement < 0.0)
            : Optional.empty();
    }
}
