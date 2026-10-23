package me.wolfii.legacyparkourcompat.change.v1_16_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.position.PoseBoxHook;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.phys.AABB;

import java.util.Optional;

/**
 * Mojmap 1.16.2, 1.16.3, 1.16.4 and 1.16.5 Entity.refreshDimensions:
 * shrinking width uses double division; equal or increasing width retains the
 * actual box minimum, including equal-width standing/crouching pose changes.
 * Mojmap 1.17 and 1.17.1 Entity.refreshDimensions instead call reapplyPosition,
 * rebuilding through EntityDimensions.makeBoundingBox (float half-width).
 * Earlier pose-box applicability is unbound; modern entity scaling retains native fallback.
 * Both versions' extra size-growth displacement/search excludes client players.
 */
@MovementChange(emulates = ParkourVersion.V1_16_2)
public final class MinimumAnchoredPoseBox implements PoseBoxHook {
    @Override
    public Optional<AABB> poseBox(Entity entity, EntityDimensions previous, EntityDimensions next) {
        if (((Player) entity).getAttributeValue(Attributes.SCALE) != 1.0) {
            return Optional.empty();
        }
        if (next.width() < previous.width()) {
            double halfWidth = next.width() / 2.0;
            return Optional.of(new AABB(entity.getX() - halfWidth, entity.getY(), entity.getZ() - halfWidth,
                entity.getX() + halfWidth, entity.getY() + next.height(), entity.getZ() + halfWidth));
        }
        AABB box = entity.getBoundingBox();
        return Optional.of(new AABB(box.minX, box.minY, box.minZ,
            box.minX + next.width(), box.minY + next.height(), box.minZ + next.width()));
    }
}
