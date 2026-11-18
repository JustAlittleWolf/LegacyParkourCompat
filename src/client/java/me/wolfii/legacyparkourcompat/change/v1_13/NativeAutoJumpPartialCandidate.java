package me.wolfii.legacyparkourcompat.change.v1_13;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.jump.AutoJumpPartialCandidateHook;

/** Exact 1.13 retains its existing slab/frame union and full-cell aggregate routes. */
@MovementChange(emulates = ParkourVersion.V1_13)
public final class NativeAutoJumpPartialCandidate implements AutoJumpPartialCandidateHook {}
