package me.wolfii.legacyparkourcompat.change.v1_16_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.change.llama.LlamaPassengerPlacement;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.entity.PlayerPassengerPlacementHook;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;

import java.util.Optional;

/**
 * Mojmap 1.16.1 and 1.16.2–1.16.5
 * {@code net.minecraft.world.entity.animal.horse.Llama.positionRider(Entity)} retain the accepted
 * 0.67 direct-player Y operation and {@code Player.getMyRidingOffset() == -0.35}; the paired
 * 1.16.5→1.17 comparison also retains that operation. The later 1.17 profile provider owns its
 * exact source boundary.
 */
@MovementChange(emulates = ParkourVersion.V1_16_2)
public final class LlamaRiderHeight implements PlayerPassengerPlacementHook {
    @Override
    public Optional<Placement> placement(LocalPlayer passenger, Entity vehicle) {
        return LlamaPassengerPlacement.defaultAdult(passenger, vehicle, 0.67);
    }
}
