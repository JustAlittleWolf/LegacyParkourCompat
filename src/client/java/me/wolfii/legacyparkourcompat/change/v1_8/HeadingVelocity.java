package me.wolfii.legacyparkourcompat.change.v1_8;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.change.common.LegacyHeadingVelocity;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.OrdinaryPlayerContext;
import me.wolfii.legacyparkourcompat.mechanic.hook.movement.HeadingVelocityHook;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

/**
 * Ornithe Feather 1.8 through 1.8.8 Entity#updateVelocity and
 * LivingEntity#moveRelative use the same two-axis float acceleration as 1.8.9.
 * Apply it to the audited dry, nonflying local-player path. Ornithe Feather
 * 1.9 Entity#updateVelocity instead casts PI / 180 before multiplying yaw.
 */
@MovementChange(emulates = ParkourVersion.V1_8_5)
public final class HeadingVelocity implements HeadingVelocityHook {
    @Override
    public boolean apply(Player player, float speed, Vec3 input) {
        if (!OrdinaryPlayerContext.matchesHeadingVelocity(player) || player.isSpectator()
            || player.getAbilities().flying || input.y != 0.0) {
            return false;
        }
        LegacyHeadingVelocity.apply(player, speed, input);
        return true;
    }
}
