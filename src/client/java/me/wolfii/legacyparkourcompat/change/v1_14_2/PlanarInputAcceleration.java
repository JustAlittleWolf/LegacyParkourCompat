package me.wolfii.legacyparkourcompat.change.v1_14_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.change.common.LegacyTrig;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.OrdinaryPlayerContext;
import me.wolfii.legacyparkourcompat.mechanic.hook.travel.InputAccelerationHook;
import me.wolfii.legacyparkourcompat.mechanic.input.KeyboardInputSupport;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.KeyboardInput;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

/**
 * Replays the 1.14 double-vector acceleration for ordinary planar keyboard movement.
 * Feather 1.14.0 and 1.14.4 {@code Entity.updateVelocity(float, Vec3d)} plus its static
 * movement-input helper use a strict {@code 1.0E-7} squared-length cutoff, normalize only above
 * 1.0, scale the vector before float-yaw rotation, then add the result to current velocity.
 * Yarn 1.14.1, 1.14.2, and 1.14.3 {@code Entity.updateVelocity}/{@code movementInputToVelocity}
 * preserve that operation. The {@code Vec3d} input for ordinary keyboard travel is
 * {@code (sidewaysSpeed, upwardSpeed, forwardSpeed)} with zero Y. The closest-version
 * {@code V1_14_2} registration covers the 1.14, 1.14.1, and 1.14.2–1.14.4 profile groups.
 * Current 26.2 {@code Entity.getInputVector} retains the double-vector arithmetic but uses
 * {@code Mth.sin/cos}; {@link LegacyTrig} preserves the historical float table indices and table.
 */
@MovementChange(emulates = ParkourVersion.V1_14_2)
public final class PlanarInputAcceleration implements InputAccelerationHook {
    public PlanarInputAcceleration() {
    }

    @Override
    public boolean apply(Player player, ParkourVersion selected, float speed, Vec3 input) {
        if (!(player instanceof LocalPlayer localPlayer)
            || Minecraft.getInstance().player != localPlayer
            || !(localPlayer.input instanceof KeyboardInput)
            || !OrdinaryPlayerContext.matches(player)
            || player.isSpectator()
            || player.getAbilities().flying
            || player.isInWater()
            || player.isInLava()
            || player.isUsingItem()
            || input.y != 0.0
            || !KeyboardInputSupport.supportsMovementFeatures(player, selected)) {
            return false;
        }

        double squaredLength = input.lengthSqr();
        if (squaredLength < 1.0E-7) {
            return false;
        }

        Vec3 scaled = (squaredLength > 1.0 ? input.normalize() : input).scale(speed);
        float radians = player.getYRot() * (float)(Math.PI / 180.0);
        float sin = LegacyTrig.sin(radians);
        float cos = LegacyTrig.cos(radians);
        Vec3 acceleration = new Vec3(
            scaled.x * cos - scaled.z * sin,
            scaled.y,
            scaled.z * cos + scaled.x * sin
        );
        player.setDeltaMovement(player.getDeltaMovement().add(acceleration));
        return true;
    }
}
