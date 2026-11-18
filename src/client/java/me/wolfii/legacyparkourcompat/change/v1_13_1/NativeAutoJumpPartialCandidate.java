package me.wolfii.legacyparkourcompat.change.v1_13_1;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.jump.AutoJumpPartialCandidateHook;

/** Keeps the existing 1.13 slab/frame and aggregate routes separate from the 1.14 fixture. */
@MovementChange(emulates = ParkourVersion.V1_13_1)
public final class NativeAutoJumpPartialCandidate implements AutoJumpPartialCandidateHook {}
