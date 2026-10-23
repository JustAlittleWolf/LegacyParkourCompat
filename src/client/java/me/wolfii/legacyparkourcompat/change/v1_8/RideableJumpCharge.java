package me.wolfii.legacyparkourcompat.change.v1_8;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.VanillaCall;
import me.wolfii.legacyparkourcompat.mechanic.hook.vehicle.RideableJumpHook;
import net.minecraft.world.entity.PlayerRideableJumping;
import net.minecraft.world.entity.player.Player;

/**
 * Source evidence (paths are relative to decompiled_minecraft/):
 * 1.8.9/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java:
 *   public void mobTick().
 * 1.8.9/ornithe-feather/net/minecraft/entity/living/mob/passive/animal/HorseBaseEntity.java:
 *   public void setJumpStrength(int strength).
 * 1.8.9/ornithe-feather/net/minecraft/util/math/MathHelper.java:
 *   public static int floor(float x).
 *   public static int floor(double x).
 * 1.9.4/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java:
 *   public void mobTick().
 * 1.9.4/ornithe-feather/net/minecraft/entity/living/mob/passive/animal/HorseBaseEntity.java:
 *   public void setJumpStrength(int strength).
 * 1.9.4/ornithe-feather/net/minecraft/util/math/MathHelper.java:
 *   public static int floor(float x).
 *   public static int floor(double x).
 * No exact independently accepted source binding is established for this operation.
 * Source-content correspondence and compilation do not establish tick-level runtime parity.
 */
@MovementChange(emulates = ParkourVersion.V1_8_9)
public final class RideableJumpCharge implements RideableJumpHook {
    @Override
    public void onPlayerJump(Player player, PlayerRideableJumping mount, int strength, VanillaCall vanilla) {
        // 1.8 sends the charge packet without applying the charge to the local mount.
        if (!(mount instanceof net.minecraft.world.entity.Entity entity)) {
            throw new IllegalStateException("Rideable jumping mount is not an entity");
        }
        if (!me.wolfii.legacyparkourcompat.change.common.HistoricalRideables.contains(entity, ParkourVersion.V1_8)) {
            vanilla.run();
        }
    }
}
