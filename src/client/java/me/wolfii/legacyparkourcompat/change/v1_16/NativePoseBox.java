package me.wolfii.legacyparkourcompat.change.v1_16;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.position.PoseBoxHook;

/** Earlier pose-box applicability is unbound; retain native reconstruction. */
@MovementChange(emulates = ParkourVersion.V1_16)
public final class NativePoseBox implements PoseBoxHook {
}
