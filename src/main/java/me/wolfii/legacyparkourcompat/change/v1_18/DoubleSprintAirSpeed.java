package me.wolfii.legacyparkourcompat.change.v1_18;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.AirSpeedBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.AirSpeedUpdateBehavior;
import net.minecraft.world.entity.player.Player;

@MovementChange(emulates = ParkourVersion.V1_18)
public final class DoubleSprintAirSpeed implements AirSpeedBehavior, AirSpeedUpdateBehavior {
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
