package me.wolfii.legacyparkourcompat.change.v1_13_1.border;

import java.util.Optional;
import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.border.BorderCollisionAdmissionHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.border.BorderOutsideStateHook;

/**
 * 1.13.2 Ornithe Feather Gen2 build.2 WorldView.getCollisions and collidesWithBorder
 * (170–199) update the previous outside bit with strict center comparisons and
 * one-block thresholds. WorldView.getBlockCollisions (112–151) supplies exterior
 * cells only while the predicate admits the border. The same transition was
 * independently checked in 1.13 and 1.13.1 Feather Gen2 build.2.
 * 1.14.4 Feather Gen2 build.2 Entity.m_72373519 (656–665) instead checks the
 * current box deflated by 1.0E-7 against the rounded exterior voxel shape
 * built by WorldBorder, independently of the old bit.
 */
@MovementChange(emulates = ParkourVersion.V1_13_1)
public final class BorderContractionHysteresis implements BorderCollisionAdmissionHook, BorderOutsideStateHook {
    @Override
    public Optional<Boolean> outside(boolean wasOutside, double x, double z,
                                     double minX, double maxX, double minZ, double maxZ) {
        if (wasOutside) {
            minX++;
            minZ++;
            maxX--;
            maxZ--;
        } else {
            minX--;
            minZ--;
            maxX++;
            maxZ++;
        }
        return Optional.of(!(x > minX && x < maxX && z > minZ && z < maxZ));
    }

    @Override
    public Optional<Boolean> includeBorderAfterContraction(boolean wasOutside, boolean outside) {
        return wasOutside && outside ? Optional.of(false) : Optional.empty();
    }
}
