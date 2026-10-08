package me.wolfii.legacyparkourcompat.change.v1_9;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.PushAwayVelocityBehavior;
import net.minecraft.world.entity.player.Player;

/** Source-matched float response shared by the accepted 1.8.9 and 1.9.4 endpoints. */
@MovementChange(emulates = ParkourVersion.V1_9)
public final class PushAwayVelocity implements PushAwayVelocityBehavior {
    @Override
    public double pushAwayVelocity(Player player, ParkourVersion target, double vanilla) {
        if (target != ParkourVersion.V1_8 && target != ParkourVersion.V1_9) {
            return vanilla;
        }

        // The float is widened when assigned to the double velocity fields.
        float g = 0.1F;
        return vanilla < 0.0D ? -g : g;
    }
}
