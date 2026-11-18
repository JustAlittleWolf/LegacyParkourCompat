package me.wolfii.legacyparkourcompat.change.v1_17_1;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.change.llama.LlamaPassengerPlacement;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.entity.PlayerPassengerPlacementHook;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;

import java.util.Optional;

/**
 * Mojmap 1.17.1 {@code net.minecraft.world.entity.animal.horse.Llama.positionRider(Entity)} adds
 * {@code getPassengersRidingOffset() == getBbHeight() * 0.67}; paired Mojmap 1.18
 * {@code net.minecraft.world.entity.animal.horse.Llama.positionRider(Entity)} changes the factor to
 * {@code 0.6}. Both then add Mojmap
 * {@code net.minecraft.world.entity.player.Player.getMyRidingOffset() == -0.35}. Current generic
 * passenger position and attachment operations supply X/Z unchanged.
 */
@MovementChange(emulates = ParkourVersion.V1_17_1)
public final class LlamaRiderHeight implements PlayerPassengerPlacementHook {
    @Override
    public Optional<Placement> placement(LocalPlayer passenger, Entity vehicle) {
        return LlamaPassengerPlacement.defaultAdult(passenger, vehicle, 0.67);
    }
}
