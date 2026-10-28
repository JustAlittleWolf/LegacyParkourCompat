package me.wolfii.legacyparkourcompat.mechanic.hook.player;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VanillaFn;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;

/** Selects the trigonometric lookup used by the sprint impulse in a ground jump. */
@MechanicType("player.sprint_jump.trig_lookup")
public interface SprintJumpTrigLookupHook extends VersionedMechanic {
    float lookup(double radians, boolean cosine, VanillaFn<Float> vanilla);
}
