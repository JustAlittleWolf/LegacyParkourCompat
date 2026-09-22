package me.wolfii.legacyparkourcompat.change.v1_19_4;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.change.v1_18.FullBoxSneakEdge;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;

/** 1.19 added the positive-Y guard but still probed the whole player box. */
@MovementChange(emulates = ParkourVersion.V1_19_4)
public final class DownwardFullBoxSneakEdge extends FullBoxSneakEdge {
    @Override
    protected boolean allowsPositiveY() {
        return false;
    }
}
