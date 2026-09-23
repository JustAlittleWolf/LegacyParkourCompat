package me.wolfii.legacyparkourcompat.change.v1_18;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.AirSpeedBehavior;
import net.minecraft.world.entity.player.Player;

/** Through 1.18.1, the stored sprint air speed adds a double-precision constant. */
@MovementChange(emulates = ParkourVersion.V1_18)
public class DoubleSprintAirSpeed implements AirSpeedBehavior {
    @Override
    public float afterAiStep(Player player) {
        float speed = 0.02F;
        if (player.isSprinting()) {
            speed = (float) (speed + 0.005999999865889549);
        }
        return speed;
    }
}
