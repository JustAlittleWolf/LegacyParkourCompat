package me.wolfii.legacyparkourcompat.change.v1_19_3;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.travel.AirSpeedHook;
import net.minecraft.world.entity.player.Player;

/**
 * Ordinary airborne travel reads the preceding completed tick's sprint coefficient.
 * In Mojmap 1.18.2 through 1.19.3, LivingEntity.flyingSpeed starts at 0.02F;
 * getFrictionInfluencedSpeed reads that field outside ground travel. Player.aiStep
 * resets it to 0.02F, then adds 0.006F when sprinting, after super.aiStep has already
 * performed inherited travel. LocalPlayer.aiStep can change sprinting before that
 * inherited call, so the new sprint choice affects the next travel tick.
 *
 * Mojmap 1.19.4 replaces the field read with Player.getFlyingSpeed, sampling the
 * current sprint flag and returning 0.025999999F or 0.02F outside ability flight.
 * Preserve the old float addition: its sprint result has bits 0x3cd4fdf4, one
 * float step above the replacement literal's 0x3cd4fdf3 even during steady sprint.
 * Ability flight retains its separate native ability-speed branch.
 *
 * Source evidence (paths are relative to decompiled_minecraft/):
 * 1.18.2, 1.19.3 and 1.19.4 Mojmap LivingEntity:
 *   flyingSpeed (through 1.19.3), getFrictionInfluencedSpeed,
 *   handleRelativeFrictionAndCalculateMovement.
 * 1.18.2, 1.19, 1.19.1, 1.19.2 and 1.19.3 Mojmap Player: aiStep, travel.
 * 1.19.4 Mojmap Player: aiStep, getFlyingSpeed.
 * 1.18.2, 1.19.3 and 1.19.4 Mojmap LocalPlayer: aiStep.
 * Source-content correspondence and compilation do not establish tick-level runtime parity.
 */
@MovementChange(emulates = ParkourVersion.V1_19_3)
public final class AirSpeed implements AirSpeedHook {
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
