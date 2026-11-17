package me.wolfii.legacyparkourcompat.change.v1_21;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.VanillaCall;
import me.wolfii.legacyparkourcompat.mechanic.hook.state.FlightFallDistanceHook;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.player.Player;

/**
 * Mojmap 1.21.1 Player.travel calls inherited travel then resets fall distance
 * in the non-passenger creative-flight branch. Mojmap 1.21.2 moves that reset
 * into Player.aiStep before super.aiStep. The 1.21 and 1.21.1 Player sources
 * are byte-identical; their Player.travel reset is therefore deferred from the
 * current aiStep site and applied after travel only for the local creative
 * flyer. The 1.21.2 provider supersedes this change and retains the pre-travel
 * reset. Exact behavioral delta: 1.21.1→1.21.2.
 */
@MovementChange(emulates = ParkourVersion.V1_21)
public final class CreativeFlightFallDistanceAfterTravel implements FlightFallDistanceHook {
    public CreativeFlightFallDistanceAfterTravel() {
    }

    @Override
    public void beforeMovement(Player player, VanillaCall vanilla) {
        if (!(player instanceof LocalPlayer)) {
            vanilla.run();
        }
        // For the local player, defer the current Player.aiStep reset until after inherited travel.
    }

    @Override
    public void afterTravel(Player player) {
        if (player instanceof LocalPlayer && player.getAbilities().flying && !player.isPassenger()) {
            player.resetFallDistance();
        }
    }
}
