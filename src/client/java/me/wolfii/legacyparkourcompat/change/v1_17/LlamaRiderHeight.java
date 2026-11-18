package me.wolfii.legacyparkourcompat.change.v1_17;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.change.llama.LlamaPassengerPlacement;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.entity.PlayerPassengerPlacementHook;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;

import java.util.Optional;

/**
 * Mojmap 1.17.1 {@code net.minecraft.world.entity.animal.horse.Llama.positionRider(Entity)} writes
 * the direct passenger Y sum with {@code getBbHeight() * 0.67}; Mojmap 1.18
 * {@code net.minecraft.world.entity.animal.horse.Llama.positionRider(Entity)} is the paired later
 * source, where that term changes to {@code getBbHeight() * 0.6}. Both releases then add
 * {@code net.minecraft.world.entity.player.Player.getMyRidingOffset() == -0.35}. Earlier profiles
 * retain their native placement through the lower resolver fence.
 */
@MovementChange(emulates = ParkourVersion.V1_17)
public final class LlamaRiderHeight implements PlayerPassengerPlacementHook {
    @Override
    public Optional<Placement> placement(LocalPlayer passenger, Entity vehicle) {
        return LlamaPassengerPlacement.defaultAdult(passenger, vehicle, 0.67);
    }
}
