package me.wolfii.legacyparkourcompat.change.v1_20;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.vehicle.PassengerCrouchHook;
import net.minecraft.world.entity.player.Player;

/**
 * Source evidence (paths are relative to decompiled_minecraft/):
 * 1.19.4/mojmap/net/minecraft/client/player/KeyboardInput.java:
 *   tick.
 * 1.19.4/mojmap/net/minecraft/client/player/LocalPlayer.java:
 *   aiStep.
 *   serverAiStep.
 *   isEffectiveAi.
 * 1.19.4/mojmap/net/minecraft/world/entity/LivingEntity.java:
 *   aiStep.
 *   travel.
 * 1.20.1/mojmap/net/minecraft/client/player/KeyboardInput.java:
 *   tick.
 * 1.20.1/mojmap/net/minecraft/client/player/LocalPlayer.java:
 *   aiStep.
 *   isCrouching.
 *   isCrouching.
 *   serverAiStep.
 *   isEffectiveAi.
 * 1.20.1/mojmap/net/minecraft/world/entity/Entity.java:
 *   moveRelative.
 *   isControlledByLocalInstance.
 * 1.20.1/mojmap/net/minecraft/world/entity/LivingEntity.java:
 *   aiStep.
 *   travel.
 * 1.20.1/mojmap/net/minecraft/world/entity/player/Player.java:
 *   travel.
 *   updatePlayerPose.
 * 1.20.1/mojmap/net/minecraft/world/item/enchantment/EnchantmentHelper.java:
 *   getSneakingSpeedBonus.
 * 1.20.2/mojmap/net/minecraft/client/player/KeyboardInput.java:
 *   tick.
 * 1.20.2/mojmap/net/minecraft/client/player/LocalPlayer.java:
 *   aiStep.
 *   isCrouching.
 *   isCrouching.
 *   serverAiStep.
 *   isEffectiveAi.
 * 1.20.2/mojmap/net/minecraft/world/entity/LivingEntity.java:
 *   aiStep.
 * 1.20.2/mojmap/net/minecraft/world/entity/player/Player.java:
 *   updatePlayerPose.
 * 1.20.2/mojmap/net/minecraft/world/item/enchantment/EnchantmentHelper.java:
 *   getSneakingSpeedBonus.
 * 1.20.6/mojmap/net/minecraft/client/player/KeyboardInput.java:
 *   tick.
 * 1.20.6/mojmap/net/minecraft/client/player/LocalPlayer.java:
 *   aiStep.
 *   isMovingSlowly.
 *   isEffectiveAi.
 * 1.20.6/mojmap/net/minecraft/world/entity/LivingEntity.java:
 *   travel.
 * Source-content correspondence and compilation do not establish tick-level runtime parity.
 */
@MovementChange(emulates = ParkourVersion.V1_20)
public final class PassengerCrouch implements PassengerCrouchHook {
    @Override
    public boolean blocksCrouching(Player player, boolean vanilla) {
        return false;
    }
}
