package me.wolfii.legacyparkourcompat.change.v1_21_4;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.VanillaFn;
import me.wolfii.legacyparkourcompat.mechanic.hook.travel.FallFlyingClimbableExitHook;

/**
 * Mojmap 1.16 and 1.21.4 LivingEntity.travel enter gliding regardless of
 * onClimbable. Mojmap 1.21.5 travel / travelFallFlying adds the climbable
 * branch that runs travelInAir then stopFallFlying, retained in 26.2
 * unobfuscated travelFallFlying. Bypass only that branch for the bound
 * historical range; fluid precedence and independent glide math stay native.
 * This is separate from the 1.21.11 vine exception in onClimbable itself.
 */
@MovementChange(emulates = ParkourVersion.V1_21_4)
public final class FallFlyingClimbableExit implements FallFlyingClimbableExitHook {
    @Override
    public boolean exitsOnClimbable(ParkourVersion selected, VanillaFn<Boolean> nativeCheck) {
        return selected.olderThan(ParkourVersion.V1_16) && nativeCheck.get();
    }
}
