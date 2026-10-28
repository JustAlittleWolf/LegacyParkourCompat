package me.wolfii.legacyparkourcompat.mechanic.hook.player;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;

/** Supplies the threshold used by the native jump-from-ground admission predicate. */
@MechanicType("player.jump_from_ground.admission")
public interface JumpFromGroundAdmissionHook extends VersionedMechanic {
    float admissionThreshold(float nativeThreshold);
}
