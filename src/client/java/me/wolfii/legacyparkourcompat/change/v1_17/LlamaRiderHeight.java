package me.wolfii.legacyparkourcompat.change.v1_17;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.change.llama.LlamaPassengerPlacement;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.entity.PlayerPassengerPlacementHook;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;

import java.util.Optional;

/**
 * Mojmap 1.15 {@code net.minecraft.world.entity.animal.horse.Llama.positionRider(Entity)} writes
 * {@code getY() + getRideHeight() + getRidingHeight()}, with
 * {@code Llama.getRideHeight()} returning {@code getBbHeight() * 0.67} and
 * {@code Player.getRidingHeight()} returning {@code -0.35}. Accepted adjacent Mojmap comparisons
 * across 1.15→1.15.1, 1.15.1→1.15.2, 1.15.2→1.16, and every 1.16 profile alias through 1.16.5
 * preserve this left-associative direct-player Y operation. The 1.16 Mojmap
 * {@code Llama.positionRider(Entity)} uses {@code getPassengersRidingOffset()} and
 * {@code Player.getMyRidingOffset()}; the 1.16.5→1.17 comparison closes the final earlier alias.
 * The closest-applicable resolver selects this V1_17 provider for V1_15, V1_15_2, V1_16 and
 * V1_16_2 because no same-hook provider intervenes. The V1_14_2 native fence preserves earlier
 * selected profiles. V1_17_1 has its own provider, and Mojmap 1.18 changes the factor to 0.6.
 */
@MovementChange(emulates = ParkourVersion.V1_17)
public final class LlamaRiderHeight implements PlayerPassengerPlacementHook {
    @Override
    public Optional<Placement> placement(LocalPlayer passenger, Entity vehicle) {
        return LlamaPassengerPlacement.defaultAdult(passenger, vehicle, 0.67);
    }
}
