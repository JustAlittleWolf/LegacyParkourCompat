package me.wolfii.legacyparkourcompat.change.v1_21_4;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.entity.SweptShapeIntersectionHook;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.Optional;

/**
 * The exact-half-size swept operation is established for Mojmap 1.21.5-1.21.8
 * AABB.collidedAlongVector and Entity.collidedWithShapeMovingFrom, compared
 * with the inset operation in Mojmap 1.21.9 and unobfuscated 26.2 at those same
 * members. Earlier profiles retain their independently resolved contact
 * traversal and native swept operation; this boundary prevents the 1.21.5
 * change from resolving for them.
 */
@MovementChange(emulates = ParkourVersion.V1_21_4)
public final class NativeSweptShapeIntersection implements SweptShapeIntersectionHook {
    @Override
    public Optional<Boolean> collidedAlongVector(AABB movingBox, Vec3 movement, List<AABB> shapeParts) {
        return Optional.empty();
    }
}
