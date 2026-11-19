package me.wolfii.legacyparkourcompat.change.v1_11_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.change.common.LegacyTrig;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.OrdinaryPlayerContext;
import me.wolfii.legacyparkourcompat.mechanic.hook.movement.HeadingVelocityHook;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

/**
 * Ornithe Feather 1.9 through 1.11.2 Entity#updateVelocity normalizes two
 * float axes under the dry LivingEntity#moveRelative route and rotates them
 * with MathHelper's lookup table. Unlike 1.8.9, yaw multiplies by the float
 * cast of PI / 180. Ornithe Feather 1.12 adds a third normalized axis in
 * Entity#updateVelocity; the ordinary local player passes zero for that axis.
 */
@MovementChange(emulates = ParkourVersion.V1_11_2)
public final class HeadingVelocity implements HeadingVelocityHook {
    @Override
    public boolean apply(Player player, float speed, Vec3 input) {
        if (!OrdinaryPlayerContext.matchesHeadingVelocity(player) || player.isSpectator()
            || player.getAbilities().flying || input.y != 0.0) {
            return false;
        }

        float sideways = (float)input.x;
        float forwards = (float)input.z;
        float lengthSquared = sideways * sideways + forwards * forwards;
        if (lengthSquared < 1.0E-4F) {
            return true;
        }

        float length = (float)Math.sqrt(lengthSquared);
        if (length < 1.0F) {
            length = 1.0F;
        }
        float multiplier = speed / length;
        sideways *= multiplier;
        forwards *= multiplier;

        float angle = player.getYRot() * (float)(Math.PI / 180.0);
        float sine = LegacyTrig.sin(angle);
        float cosine = LegacyTrig.cos(angle);
        Vec3 movement = player.getDeltaMovement();
        player.setDeltaMovement(
            movement.x + (sideways * cosine - forwards * sine),
            movement.y,
            movement.z + (forwards * cosine + sideways * sine)
        );
        return true;
    }
}
