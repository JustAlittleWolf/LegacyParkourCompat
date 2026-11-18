package me.wolfii.legacyparkourcompat.change.v1_18_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.change.llama.LlamaPassengerPlacement;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.entity.PlayerPassengerPlacementHook;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;

import java.util.Optional;

/**
 * Mojmap 1.18.2 {@code net.minecraft.world.entity.animal.horse.Llama.positionRider(Entity)} uses
 * {@code getPassengersRidingOffset() == getBbHeight() * 0.6}; paired Mojmap 1.20.1
 * {@code net.minecraft.world.entity.animal.horse.Llama.positionRider(Entity, Entity.MoveFunction)}
 * preserves that term and its order before Mojmap
 * {@code net.minecraft.world.entity.player.Player.getMyRidingOffset() == -0.35}. The checked
 * intermediate releases retain the same expression.
 */
@MovementChange(emulates = ParkourVersion.V1_18_2)
public final class LlamaRiderHeight implements PlayerPassengerPlacementHook {
    @Override
    public Optional<Placement> placement(LocalPlayer passenger, Entity vehicle) {
        return LlamaPassengerPlacement.defaultAdult(passenger, vehicle, 0.6);
    }
}
