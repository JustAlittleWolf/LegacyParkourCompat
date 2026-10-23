package me.wolfii.legacyparkourcompat.change.v1_21_5;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.entity.SweptShapeIntersectionHook;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.Optional;

/**
 * In Minecraft 1.21.5-1.21.8 (Mojmap), AABB.collidedAlongVector inflates each candidate
 * by the moving box's half-size; Entity.collidedWithShapeMovingFrom delegates
 * both entity-inside block shapes and fluid AABBs to that method. In Minecraft
 * 1.21.9 (Mojmap), the same AABB.collidedAlongVector member subtracts 1.0E-7
 * from each half-size first, while Entity.collidedWithShapeMovingFrom still
 * delegates to it. This restores the 1.21.8 operation order and preserves the
 * native shapes. The 1.21.5 member is identical to 1.21.8, and the complete
 * AABB source in 1.21.6 and 1.21.7 is identical to 1.21.8, establishing the
 * grouped profile. In 26.2 (unobfuscated), AABB.collidedAlongVector retains
 * the inset and Entity.collidedWithShapeMovingFrom still delegates to it for
 * block and fluid callbacks. Only that player's intersection operation is
 * replaced; callback precision flags and contact traversal remain separate.
 */
@MovementChange(emulates = ParkourVersion.V1_21_6)
public final class SweptShapeIntersection implements SweptShapeIntersectionHook {
    @Override
    public Optional<Boolean> collidedAlongVector(AABB movingBox, Vec3 movement, List<AABB> shapeParts) {
        Vec3 from = movingBox.getCenter();
        Vec3 to = from.add(movement);

        for (AABB shapePart : shapeParts) {
            AABB inflated = shapePart.inflate(
                movingBox.getXsize() * 0.5,
                movingBox.getYsize() * 0.5,
                movingBox.getZsize() * 0.5
            );
            if (inflated.contains(to) || inflated.contains(from)) {
                return Optional.of(true);
            }

            if (inflated.clip(from, to).isPresent()) {
                return Optional.of(true);
            }
        }

        return Optional.of(false);
    }
}
