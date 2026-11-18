package me.wolfii.legacyparkourcompat.change.v1_12.border;

import java.util.Optional;
import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.border.BorderCollisionAdmissionHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.border.BorderOutsideStateHook;

/** Keeps the contraction witness on the paths already used before the accepted 1.13 range. */
@MovementChange(emulates = ParkourVersion.V1_12)
public final class NativeBeforeBorderContraction implements BorderCollisionAdmissionHook, BorderOutsideStateHook {
    @Override
    public Optional<Boolean> outside(boolean wasOutside, double x, double z,
                                     double minX, double maxX, double minZ, double maxZ) {
        return Optional.empty();
    }
}
