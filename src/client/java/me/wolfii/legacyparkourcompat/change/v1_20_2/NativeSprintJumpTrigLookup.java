package me.wolfii.legacyparkourcompat.change.v1_20_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.VanillaFn;
import me.wolfii.legacyparkourcompat.mechanic.hook.player.SprintJumpTrigLookupHook;

/**
 * Lower applicability barrier for the independent trig lookup operation.
 * The existing 1.20.2 sprint-impulse change retains its earlier float-table and
 * {@code 0.2F} arithmetic.
 */
@MovementChange(emulates = ParkourVersion.V1_20_2)
public final class NativeSprintJumpTrigLookup implements SprintJumpTrigLookupHook {
    @Override
    public float lookup(double radians, boolean cosine, VanillaFn<Float> vanilla) {
        return vanilla.get();
    }
}
