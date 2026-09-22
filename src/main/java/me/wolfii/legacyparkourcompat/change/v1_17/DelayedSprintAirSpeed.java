package me.wolfii.legacyparkourcompat.change.v1_17;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChangeRegistry;
import me.wolfii.legacyparkourcompat.mechanic.hook.AirSpeedBehavior;
import net.minecraft.world.entity.player.Player;

/** Before 1.19, Player updated flyingSpeed after its movement tick. */
public final class DelayedSprintAirSpeed implements AirSpeedBehavior {
    private final boolean floatSprintAddition;

    private DelayedSprintAirSpeed(boolean floatSprintAddition) {
        this.floatSprintAddition = floatSprintAddition;
    }

    public static void register(MovementChangeRegistry registry) {
        registry.register(AirSpeedBehavior.class, ParkourVersion.V1_18, new DelayedSprintAirSpeed(true));
        registry.register(AirSpeedBehavior.class, ParkourVersion.V1_17, new DelayedSprintAirSpeed(false));
    }

    @Override
    public float speed(Player player, boolean sprintingAtTickStart, float vanilla) {
        if (player.getAbilities().flying) {
            return vanilla;
        }
        float speed = 0.02F;
        if (sprintingAtTickStart) {
            speed = this.floatSprintAddition ? speed + 0.006F : (float) (speed + speed * 0.3);
        }
        return speed;
    }
}
