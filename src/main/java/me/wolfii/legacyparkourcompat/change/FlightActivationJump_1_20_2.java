package me.wolfii.legacyparkourcompat.change;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.VanillaCall;
import me.wolfii.legacyparkourcompat.mechanic.hook.FlightActivationJumpBehavior;
import net.minecraft.world.entity.player.Player;

@MovementChange(emulates = ParkourVersion.V1_20_2)
public final class FlightActivationJump_1_20_2 implements FlightActivationJumpBehavior {
    @Override
    public void onFlightActivated(Player player, VanillaCall vanilla) {
        // Flight activation did not jump in 1.20.2 and earlier.
    }
}
