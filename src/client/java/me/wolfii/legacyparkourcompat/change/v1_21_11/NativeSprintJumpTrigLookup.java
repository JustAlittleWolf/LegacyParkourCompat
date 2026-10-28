package me.wolfii.legacyparkourcompat.change.v1_21_11;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.VanillaFn;
import me.wolfii.legacyparkourcompat.mechanic.hook.player.SprintJumpTrigLookupHook;

/** The double-indexed lookup introduced in 1.21.11 matches the current target. */
@MovementChange(emulates = ParkourVersion.V1_21_11)
public final class NativeSprintJumpTrigLookup implements SprintJumpTrigLookupHook {
    @Override
    public float lookup(double radians, boolean cosine, VanillaFn<Float> vanilla) {
        return vanilla.get();
    }
}
