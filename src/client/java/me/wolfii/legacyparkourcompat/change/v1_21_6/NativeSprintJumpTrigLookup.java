package me.wolfii.legacyparkourcompat.change.v1_21_6;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.VanillaFn;
import me.wolfii.legacyparkourcompat.mechanic.hook.player.SprintJumpTrigLookupHook;

/** Lower applicability barrier: preserve the native trig lookup through 1.21.8. */
@MovementChange(emulates = ParkourVersion.V1_21_6)
public final class NativeSprintJumpTrigLookup implements SprintJumpTrigLookupHook {
    @Override
    public float lookup(double radians, boolean cosine, VanillaFn<Float> vanilla) {
        return vanilla.get();
    }
}
