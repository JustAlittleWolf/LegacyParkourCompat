package me.wolfii.legacyparkourcompat.mechanic.hook.border;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;

@MechanicType("border.wake_candidate_admission")
public interface WakeBorderAdmissionHook extends VersionedMechanic {
    boolean admitsCandidate();
}
