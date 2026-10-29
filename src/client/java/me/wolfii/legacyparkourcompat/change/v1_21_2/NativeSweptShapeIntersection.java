package me.wolfii.legacyparkourcompat.change.v1_21_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.entity.SweptShapeIntersectionHook;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.Optional;

/**
 * Keeps the native swept intersection through Mojmap 1.21.3. The 1.21.4-1.21.8
 * player callback path uses the full half-size swept operation; Mojmap 1.21.9
 * and unobfuscated 26.2 use the inset operation. Source: 1.21.2 and 1.21.3
 * Entity.collidedWithShapeMovingFrom; 1.21.4-1.21.9 AABB.collidedAlongVector and
 * Entity.collidedWithShapeMovingFrom.
 */
@MovementChange(emulates = ParkourVersion.V1_21_2)
public final class NativeSweptShapeIntersection implements SweptShapeIntersectionHook {
    @Override
    public Optional<Boolean> collidedAlongVector(AABB movingBox, Vec3 movement, List<AABB> shapeParts) {
        return Optional.empty();
    }
}
