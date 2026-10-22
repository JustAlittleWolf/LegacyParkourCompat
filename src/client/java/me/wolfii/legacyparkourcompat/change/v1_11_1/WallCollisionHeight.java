package me.wolfii.legacyparkourcompat.change.v1_11_1;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.block.WallCollisionHeightHook;

/**
 * Ornithe Feather 1.11.1 {@code WallBlock#addCollisions} adds the outline from
 * {@code SHAPES}, whose maximum Y is 1.0, even though {@code getCollisionShape}
 * still returns {@code COLLISION_SHAPES}. In 1.11.2, {@code addCollisions} uses
 * {@code COLLISION_SHAPES}, whose entries have maximum Y 1.5.
 */
@MovementChange(emulates = ParkourVersion.V1_11_1)
public final class WallCollisionHeight implements WallCollisionHeightHook {
    @Override
    public double maxY() {
        return 1.0;
    }
}
