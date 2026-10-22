package me.wolfii.legacyparkourcompat.change.v1_21_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.shulker.ShulkerPeekPushQueryHook;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

/**
 * Restores the origin-corner query geometry used from 1.20.5 through 1.21.3.
 * In 1.21.3 Mojmap, {@code net.minecraft.world.entity.monster.Shulker.onPeekAmountChange}
 * passes {@code Shulker.getProgressDeltaAabb} to
 * {@code net.minecraft.world.level.Level.getEntities} before applying the
 * direct push. In 1.21.4 Mojmap, the same call remains but
 * {@code Shulker.getProgressDeltaAabb} centers its horizontal base at the
 * Shulker position. The scale-based push itself is absent in 1.20.4 and
 * present in 1.20.5 Mojmap {@code Shulker.onPeekAmountChange}; profiles before
 * that introduction retain the modern feature unchanged.
 */
@MovementChange(emulates = ParkourVersion.V1_21_2)
public final class ShulkerPeekPushQuery implements ShulkerPeekPushQueryHook {
    public ShulkerPeekPushQuery() {
    }

    @Override
    public AABB bounds(float scale, Direction direction, float progressFrom, float progressTo, Vec3 position) {
        AABB bounds = new AABB(0.0, 0.0, 0.0, scale, scale, scale);
        double maxMovement = Math.max(progressFrom, progressTo);
        double minMovement = Math.min(progressFrom, progressTo);
        return bounds.expandTowards(
                direction.getStepX() * maxMovement * scale,
                direction.getStepY() * maxMovement * scale,
                direction.getStepZ() * maxMovement * scale
            )
            .contract(
                -direction.getStepX() * (1.0 + minMovement) * scale,
                -direction.getStepY() * (1.0 + minMovement) * scale,
                -direction.getStepZ() * (1.0 + minMovement) * scale
            )
            .move(position.x - 0.5, position.y, position.z - 0.5);
    }
}
