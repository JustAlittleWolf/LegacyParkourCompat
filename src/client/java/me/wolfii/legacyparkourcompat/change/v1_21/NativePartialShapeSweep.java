package me.wolfii.legacyparkourcompat.change.v1_21;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.entity.PartialShapeSweepHook;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.Optional;

/** Keeps the native partial-shape sweep for the 1.21 and 1.21.1 profiles. */
@MovementChange(emulates = ParkourVersion.V1_21)
public final class NativePartialShapeSweep implements PartialShapeSweepHook {
    @Override
    public Optional<Vec3> reverseDisplacement(AABB finalEntityBox, Vec3 from, Vec3 to) {
        return Optional.empty();
    }
}
