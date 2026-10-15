package me.wolfii.legacyparkourcompat.mechanic.hook.travel;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import me.wolfii.legacyparkourcompat.mechanic.VanillaFn;

/** Climbable gate selecting ordinary air travel and stopping fall flight. */
@MechanicType("player.fall_flying.climbable_exit")
public interface FallFlyingClimbableExitHook extends VersionedMechanic {
    boolean exitsOnClimbable(ParkourVersion selected, VanillaFn<Boolean> nativeCheck);
}
