package me.wolfii.legacyparkourcompat.change.v1_13.player.pose;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.state.PlayerMovementEyeHeightHook;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.boat.AbstractBoat;
import net.minecraft.world.entity.vehicle.boat.Boat;

/**
 * Ornithe Feather 1.13, 1.13.1 and 1.13.2 PlayerEntity.getEyeHeight reads the swimming
 * flag and current size at each call. Entity.isSubmergedIn uses that value before
 * Entity.updateSwimming, and later callers can read it again before PlayerEntity.updatePlayerPose.
 * Feather 1.14 PlayerEntity.m_86089714 uses pose-dependent eye height instead.
 * In unobfuscated 26.2, Avatar.POSES supplies that value and Entity.getEyeY reads the cached
 * eyeHeight field set by Entity.refreshDimensions. Use the live 1.13 selector for fluid queries.
 */
@MovementChange(emulates = ParkourVersion.V1_13_1)
public final class SwimmingEyeHeight implements PlayerMovementEyeHeightHook {
    @Override
    public float eyeHeight(Player player, float vanilla) {
        var vehicle = player.getVehicle();
        if (vehicle instanceof AbstractBoat && !(vehicle instanceof Boat)) {
            return vanilla;
        }
        return switch (player.getPose()) {
            case STANDING, FALL_FLYING, SLEEPING, SWIMMING, SPIN_ATTACK, CROUCHING -> eyeHeight(player);
            default -> vanilla;
        };
    }

    private static float eyeHeight(Player player) {
        float height = player.getBbHeight();
        float eyeHeight = 1.62F;
        if (player.isSleeping()) {
            eyeHeight = 0.2F;
        } else if (player.isSwimming() || player.isFallFlying() || height == 0.6F) {
            eyeHeight = 0.4F;
        } else if (player.isShiftKeyDown() || height == 1.65F) {
            eyeHeight -= 0.08F;
        }
        return eyeHeight;
    }
}
