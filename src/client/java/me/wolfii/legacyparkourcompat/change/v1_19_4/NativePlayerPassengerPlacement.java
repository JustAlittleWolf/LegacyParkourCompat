package me.wolfii.legacyparkourcompat.change.v1_19_4;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.change.llama.LlamaPassengerPlacement;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.entity.PlayerPassengerPlacementHook;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;

import java.util.Optional;

/**
 * Preserves native placement for other carriers while the accepted Llama Y operation continues
 * through 1.19.4. Mojmap 1.18.2–1.19.4
 * {@code net.minecraft.world.entity.animal.horse.Llama.positionRider(Entity)} and 1.20–1.20.1
 * {@code Llama.positionRider(Entity, Entity.MoveFunction)} keep the ordered Y sum using
 * {@code getPassengersRidingOffset() == getBbHeight() * 0.6} followed by
 * {@code net.minecraft.world.entity.player.Player.getMyRidingOffset() == -0.35}.
 */
@MovementChange(emulates = ParkourVersion.V1_19_4)
public final class NativePlayerPassengerPlacement implements PlayerPassengerPlacementHook {
    @Override
    public Optional<Placement> placement(LocalPlayer passenger, Entity vehicle) {
        return LlamaPassengerPlacement.defaultAdult(passenger, vehicle, 0.6);
    }
}
