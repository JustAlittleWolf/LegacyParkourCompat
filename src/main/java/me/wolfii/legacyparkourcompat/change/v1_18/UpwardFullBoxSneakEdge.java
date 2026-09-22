package me.wolfii.legacyparkourcompat.change.v1_18;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;

/** Through 1.18, sneak-edge backoff also ran for positive-Y moves. */
@MovementChange(emulates = ParkourVersion.V1_18)
public final class UpwardFullBoxSneakEdge extends FullBoxSneakEdge {
    @Override
    protected boolean allowsPositiveY() {
        return true;
    }
}
