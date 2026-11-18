package me.wolfii.legacyparkourcompat.change.v1_15_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.change.llama.LlamaPassengerPlacement;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.entity.PlayerPassengerPlacementHook;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;

import java.util.Optional;

/**
 * Mojmap 1.15.1 and 1.15.2
 * {@code net.minecraft.world.entity.animal.horse.Llama.positionRider(Entity)} retain the accepted
 * left-associative {@code getY() + getRideHeight() + getRidingHeight()} sum and 0.67 factor.
 */
@MovementChange(emulates = ParkourVersion.V1_15_2)
public final class LlamaRiderHeight implements PlayerPassengerPlacementHook {
    @Override
    public Optional<Placement> placement(LocalPlayer passenger, Entity vehicle) {
        return LlamaPassengerPlacement.defaultAdult(passenger, vehicle, 0.67);
    }
}
