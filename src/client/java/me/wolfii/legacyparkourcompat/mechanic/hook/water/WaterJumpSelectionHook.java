package me.wolfii.legacyparkourcompat.mechanic.hook.water;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;

@MechanicType("water.jump_selection")
public interface WaterJumpSelectionHook extends VersionedMechanic {
    double selectionDepth(boolean inWater, double nativeDepth);
}
