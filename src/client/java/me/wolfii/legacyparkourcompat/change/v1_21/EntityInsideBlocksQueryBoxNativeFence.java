package me.wolfii.legacyparkourcompat.change.v1_21;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.entity.EntityInsideBlocksQueryBoxHook;

/** Keeps the native per-segment query-box operation below the 1.21.2 boundary. */
@MovementChange(emulates = ParkourVersion.V1_21)
public final class EntityInsideBlocksQueryBoxNativeFence implements EntityInsideBlocksQueryBoxHook {
    @Override
    public boolean shareFinalQueryBox() {
        return false;
    }
}
