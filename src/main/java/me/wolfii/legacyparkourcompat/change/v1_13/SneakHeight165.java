package me.wolfii.legacyparkourcompat.change.v1_13;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.PlayerDimensionsBehavior;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;

/**
 * 1.9–1.13 sneaking is {@code 0.6×1.65} with eye height {@code 1.62 - 0.08}.
 * 1.14 lowered crouch to {@code 1.5}m. Selecting 1.8 keeps
 * {@link me.wolfii.legacyparkourcompat.change.v1_8.StandingPose} (always 1.8m).
 */
@MovementChange(emulates = ParkourVersion.V1_13)
public final class SneakHeight165 implements PlayerDimensionsBehavior {
    private static final EntityDimensions CROUCHING = EntityDimensions.scalable(0.6F, 1.65F).withEyeHeight(1.54F);

    @Override
    public EntityDimensions dimensions(Player player, Pose pose, EntityDimensions vanilla) {
        if (pose != Pose.CROUCHING) {
            return vanilla;
        }
        return CROUCHING;
    }
}
