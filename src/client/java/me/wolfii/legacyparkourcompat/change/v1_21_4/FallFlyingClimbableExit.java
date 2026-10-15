package me.wolfii.legacyparkourcompat.change.v1_21_4;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.VanillaFn;
import me.wolfii.legacyparkourcompat.mechanic.hook.travel.FallFlyingClimbableExitHook;

/**
 * Ornithe Feather 1.9.4 LivingEntity.moveRelative and Mojmap 1.16 / 1.21.4
 * LivingEntity.travel enter gliding regardless of
 * onClimbable. Mojmap 1.21.5 travel / travelFallFlying adds the climbable
 * branch that runs travelInAir then stopFallFlying, retained in 26.2
 * unobfuscated travelFallFlying. Bypass only that branch for the bound
 * historical range; fluid precedence and independent glide math stay native.
 * This is separate from the 1.21.11 vine exception in onClimbable itself.
 * Elytra is modern-only for the 1.8 profile, which retains native behavior.
 */
@MovementChange(emulates = ParkourVersion.V1_21_4)
public final class FallFlyingClimbableExit implements FallFlyingClimbableExitHook {
    @Override
    public boolean exitsOnClimbable(ParkourVersion selected, VanillaFn<Boolean> nativeCheck) {
        return selected.olderThan(ParkourVersion.V1_9) && nativeCheck.get();
    }
}
