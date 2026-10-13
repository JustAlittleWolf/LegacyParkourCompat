package me.wolfii.legacyparkourcompat.change.v1_17_1;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.border.WakeBorderAdmissionHook;

/**
 * 1.17.1 mojmap DismountHelper#findSafeDismountLocation returns its candidate
 * after empty block collisions, without a world-border test. 1.18 mojmap adds
 * WorldBorder#isWithinBounds(AABB); 26.2 unobfuscated retains that guard.
 * 1.17.1 mojmap and 26.2 unobfuscated LivingEntity#stopSleeping call
 * BedBlock#findStandUpPosition; BedBlock#findStandUpPositionAtOffset calls
 * DismountHelper#findSafeDismountLocation. Only that logical-client player consumer
 * uses this delta; candidate order, other gates, pose and vehicle physics stay
 * with their independently resolved operations.
 */
@MovementChange(emulates = ParkourVersion.V1_17_1)
public final class WakeBorderAdmission implements WakeBorderAdmissionHook {
    @Override
    public boolean admitsCandidate() {
        return true;
    }
}
