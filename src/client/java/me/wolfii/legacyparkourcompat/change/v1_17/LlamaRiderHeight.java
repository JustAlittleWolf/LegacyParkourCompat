package me.wolfii.legacyparkourcompat.change.v1_17;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.change.llama.LlamaPassengerPlacement;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.entity.PlayerPassengerPlacementHook;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;

import java.util.Optional;

/**
 * Mojmap 1.17 {@code Llama.positionRider} writes the direct passenger Y sum with the Llama's
 * {@code getBbHeight() * 0.67} offset followed by {@code Player.getMyRidingOffset() == -0.35}.
 * Exact local sources establish this 1.17 profile term; earlier profiles retain their native
 * placement through the lower resolver fence.
 */
@MovementChange(emulates = ParkourVersion.V1_17)
public final class LlamaRiderHeight implements PlayerPassengerPlacementHook {
    @Override
    public Optional<Placement> placement(LocalPlayer passenger, Entity vehicle) {
        return LlamaPassengerPlacement.defaultAdult(passenger, vehicle, 0.67);
    }
}
