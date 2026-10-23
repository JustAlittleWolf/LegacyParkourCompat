package me.wolfii.legacyparkourcompat.change.v1_13;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.travel.InputAccelerationHook;
import me.wolfii.legacyparkourcompat.mechanic.input.KeyboardInputSupport;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

/**
 * Ornithe Feather 1.13.2 Entity#updateVelocity uses three float
 * input components, float squared length / 1.0E-4F cutoff, float sqrt and
 * scale division, float yaw rotation, then double velocity additions.
 * Mojmap 1.14.4 Entity#moveRelative and #getInputVector replace this with
 * double Vec3 math and a 1.0E-7 cutoff; unobfuscated 26.2 Entity#moveRelative
 * and #getInputVector retain it. This hook consumes supported player input
 * before that generic helper; InputVectorHook covers its remaining diagonal
 * keyboard path. The tiny-input cutoff's ordinary-tick reachability is not
 * established by the accepted source comparison.
 * Below the old cutoff do not write velocity at all, preserving signed zero.
 * Earlier two-axis helpers need separate applicability; unsupported modern
 * feature states retain native input acceleration.
 */
@MovementChange(emulates = ParkourVersion.V1_13)
public final class InputAcceleration implements InputAccelerationHook {
    @Override
    public boolean apply(Player player, ParkourVersion selected, float speed, Vec3 input) {
        if (selected.olderThan(ParkourVersion.V1_12)
            || !KeyboardInputSupport.supportsMovementFeatures(player, selected)) {
            return false;
        }
        float sideways = (float)input.x;
        float vertical = (float)input.y;
        float forward = (float)input.z;
        float length = sideways * sideways + vertical * vertical + forward * forward;
        if (!(length < 1.0E-4F)) {
            length = Mth.sqrt(length);
            if (length < 1.0F) {
                length = 1.0F;
            }
            length = speed / length;
            sideways *= length;
            vertical *= length;
            forward *= length;
            float sin = Mth.sin(player.getYRot() * (float)(Math.PI / 180.0));
            float cos = Mth.cos(player.getYRot() * (float)(Math.PI / 180.0));
            Vec3 movement = player.getDeltaMovement();
            player.setDeltaMovement(
                movement.x + (sideways * cos - forward * sin),
                movement.y + vertical,
                movement.z + (forward * cos + sideways * sin));
        }
        return true;
    }
}
