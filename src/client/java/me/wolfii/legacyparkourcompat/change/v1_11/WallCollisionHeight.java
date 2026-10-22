package me.wolfii.legacyparkourcompat.change.v1_11;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.block.WallCollisionHeightHook;

/**
 * Ornithe Feather 1.8.9 {@code WallBlock#getCollisionShape} sets {@code maxY=1.5};
 * 1.10.2 and 1.11 {@code WallBlock#getCollisionShape} select collision boxes whose
 * entries use {@code withMaxY(1.5)}. The 1.11.1 collision-collection exception is
 * represented by the next versioned change.
 */
@MovementChange(emulates = ParkourVersion.V1_11)
public final class WallCollisionHeight implements WallCollisionHeightHook {
    @Override
    public double maxY() {
        return 1.5;
    }
}
