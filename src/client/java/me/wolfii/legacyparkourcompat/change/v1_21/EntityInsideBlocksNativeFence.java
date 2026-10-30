package me.wolfii.legacyparkourcompat.change.v1_21;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.entity.EntityInsideBlocksScanHook;

/** Leaves the native inside-block callback traversal selected before the accepted 1.21.2 boundary. */
@MovementChange(emulates = ParkourVersion.V1_21)
public final class EntityInsideBlocksNativeFence implements EntityInsideBlocksScanHook {
    @Override
    public boolean usesHistoricalScan() {
        return false;
    }
}
