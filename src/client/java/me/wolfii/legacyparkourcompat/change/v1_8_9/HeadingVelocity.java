package me.wolfii.legacyparkourcompat.change.v1_8_9;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.change.common.LegacyHeadingVelocity;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.OrdinaryPlayerContext;
import me.wolfii.legacyparkourcompat.mechanic.hook.movement.HeadingVelocityHook;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

/**
 * Ornithe Feather 1.8 through 1.8.9 Entity#updateVelocity uses float
 * multiply-then-divide yaw conversion; Ornithe Feather 1.9 casts PI / 180
 * first. LivingEntity#moveRelative includes a flying-player fluid exception.
 * Preserve the existing 1.8.9 admission separately from the earlier dry scope.
 */
@MovementChange(emulates = ParkourVersion.V1_8_9)
public final class HeadingVelocity implements HeadingVelocityHook {
    @Override
    public boolean apply(Player player, float speed, Vec3 input) {
        if (!OrdinaryPlayerContext.matchesHeadingVelocity(player) || input.y != 0.0) {
            return false;
        }
        LegacyHeadingVelocity.apply(player, speed, input);
        return true;
    }
}
