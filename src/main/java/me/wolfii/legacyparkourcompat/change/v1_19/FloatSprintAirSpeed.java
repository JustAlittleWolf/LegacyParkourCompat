package me.wolfii.legacyparkourcompat.change.v1_19;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.change.v1_18.DoubleSprintAirSpeed;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import net.minecraft.world.entity.player.Player;

/** 1.18.2–1.19.3 keep the delayed air speed but add the sprint term as float. */
@MovementChange(emulates = ParkourVersion.V1_19)
public final class FloatSprintAirSpeed extends DoubleSprintAirSpeed {
    @Override
    public float afterAiStep(Player player) {
        float speed = 0.02F;
        if (player.isSprinting()) {
            speed += 0.006F;
        }
        return speed;
    }
}
