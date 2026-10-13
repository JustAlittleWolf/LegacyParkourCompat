package me.wolfii.legacyparkourcompat.change.v1_18;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.travel.AirSpeedHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.travel.AirSpeedUpdateHook;
import net.minecraft.world.entity.player.Player;

/**
 * Source evidence (paths are relative to decompiled_minecraft/):
 * 1.17.1/mojmap/net/minecraft/world/entity/player/Player.java:
 *   aiStep.
 * 1.18.2/mojmap/net/minecraft/world/entity/player/Player.java:
 *   aiStep.
 * Source-content correspondence and compilation do not establish tick-level runtime parity.
 */
@MovementChange(emulates = ParkourVersion.V1_18)
public final class DoubleSprintAirSpeed implements AirSpeedHook, AirSpeedUpdateHook {
    @Override
    public float speed(Player player, float stored, float vanilla) {
        return player.getAbilities().flying && !player.isPassenger() ? vanilla : stored;
    }

    @Override
    public float afterAiStep(Player player) {
        float speed = 0.02F;
        if (player.isSprinting()) {
            speed = (float) (speed + 0.005999999865889549);
        }
        return speed;
    }
}
