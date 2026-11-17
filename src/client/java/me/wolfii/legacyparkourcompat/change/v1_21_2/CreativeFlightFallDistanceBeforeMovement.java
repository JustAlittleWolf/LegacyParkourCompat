package me.wolfii.legacyparkourcompat.change.v1_21_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.VanillaCall;
import me.wolfii.legacyparkourcompat.mechanic.hook.state.FlightFallDistanceHook;
import net.minecraft.world.entity.player.Player;

/**
 * Mojmap 1.21.2 Player.aiStep resets fall distance for flying non-passengers
 * before super.aiStep; Player.travel no longer resets it after inherited
 * travel. Keep that source order at the current Player.aiStep reset callsite.
 */
@MovementChange(emulates = ParkourVersion.V1_21_2)
public final class CreativeFlightFallDistanceBeforeMovement implements FlightFallDistanceHook {
    public CreativeFlightFallDistanceBeforeMovement() {
    }

    @Override
    public void beforeMovement(Player player, VanillaCall vanilla) {
        vanilla.run();
    }
}
