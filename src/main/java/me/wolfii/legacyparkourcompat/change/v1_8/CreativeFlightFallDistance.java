package me.wolfii.legacyparkourcompat.change.v1_8;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.VanillaCall;
import me.wolfii.legacyparkourcompat.mechanic.hook.FlightFallDistanceBehavior;
import net.minecraft.world.entity.player.Player;

@MovementChange(emulates = ParkourVersion.V1_8)
public final class CreativeFlightFallDistance implements FlightFallDistanceBehavior {
    @Override
    public void beforeMovement(Player player, VanillaCall vanilla) {
        // Version 1.8 preserves fall distance through creative-flight movement.
    }
}
