package me.wolfii.legacyparkourcompat.change.v1_19_3;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.travel.AirSpeedHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.travel.AirSpeedUpdateHook;
import net.minecraft.world.entity.player.Player;

/**
 * Keeps the cached flyingSpeed consumer and the Player.aiStep float update through
 * 1.19.3; 1.19.4 derives the airborne speed from current sprint state instead.
 * 1.19/mojmap/net/minecraft/world/entity/player/Player.java: aiStep, travel.
 * 1.19.1/mojmap/net/minecraft/world/entity/player/Player.java: aiStep, travel.
 * 1.19.2/mojmap/net/minecraft/world/entity/player/Player.java: aiStep, travel.
 *
 * Source evidence (paths are relative to decompiled_minecraft/):
 * 1.17.1/mojmap/net/minecraft/world/entity/player/Player.java:
 *   aiStep.
 * 1.18.2/mojmap/net/minecraft/client/player/LocalPlayer.java:
 *   aiStep.
 * 1.18.2/mojmap/net/minecraft/world/entity/LivingEntity.java:
 *   getFrictionInfluencedSpeed.
 *   aiStep.
 *   getFrictionInfluencedSpeed.
 * 1.18.2/mojmap/net/minecraft/world/entity/player/Player.java:
 *   aiStep.
 *   aiStep.
 * 1.19.3/mojmap/net/minecraft/client/player/LocalPlayer.java:
 *   aiStep.
 * 1.19.3/mojmap/net/minecraft/world/entity/LivingEntity.java:
 *   handleRelativeFrictionAndCalculateMovement.
 * 1.19.3/mojmap/net/minecraft/world/entity/player/Player.java:
 *   aiStep.
 * 1.19.4/mojmap/net/minecraft/client/player/LocalPlayer.java:
 *   aiStep.
 * 1.19.4/mojmap/net/minecraft/world/entity/LivingEntity.java:
 *   getFrictionInfluencedSpeed.
 *   handleRelativeFrictionAndCalculateMovement.
 * 1.19.4/mojmap/net/minecraft/world/entity/player/Player.java:
 *   getFlyingSpeed.
 *   aiStep.
 *   getFlyingSpeed.
 * Source-content correspondence and compilation do not establish tick-level runtime parity.
 */
@MovementChange(emulates = ParkourVersion.V1_19_3)
public final class AirSpeed implements AirSpeedHook, AirSpeedUpdateHook {
    @Override
    public float speed(Player player, float stored, float vanilla) {
        return player.getAbilities().flying && !player.isPassenger() ? vanilla : stored;
    }

    @Override
    public float afterAiStep(Player player) {
        float speed = 0.02F;
        if (player.isSprinting()) {
            speed += 0.006F;
        }
        return speed;
    }
}
