package me.wolfii.legacyparkourcompat.change.v1_15;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.change.llama.LlamaPassengerPlacement;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.entity.PlayerPassengerPlacementHook;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;

import java.util.Optional;

/**
 * Mojmap 1.15 and 1.15.1
 * {@code net.minecraft.world.entity.animal.horse.Llama.positionRider(Entity)} write
 * {@code getY() + getRideHeight() + getRidingHeight()}, where the Llama override computes
 * {@code getBbHeight() * 0.67} and the Player override returns {@code -0.35}. Mojmap
 * {@code Llama.positionRider(Entity)} paired comparisons for 1.15.1→1.15.2 and 1.15.2→1.16
 * confirm the same operation in those later profile groups; the exact 1.16 and 1.16.5 Mojmap
 * members use {@code getPassengersRidingOffset()} and {@code getMyRidingOffset()} for the same
 * left-associative Y sum. Later source-confirmed profile groups retain their own resolver
 * providers; V1_14_2 keeps earlier profiles native.
 */
@MovementChange(emulates = ParkourVersion.V1_15)
public final class LlamaRiderHeight implements PlayerPassengerPlacementHook {
    @Override
    public Optional<Placement> placement(LocalPlayer passenger, Entity vehicle) {
        return LlamaPassengerPlacement.defaultAdult(passenger, vehicle, 0.67);
    }
}
