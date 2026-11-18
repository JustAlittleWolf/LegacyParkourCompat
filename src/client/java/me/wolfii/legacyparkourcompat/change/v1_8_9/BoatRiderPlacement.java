package me.wolfii.legacyparkourcompat.change.v1_8_9;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.change.boat.LegacyBoatRiderPlacement;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.entity.PlayerPassengerPlacementHook;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.vehicle.boat.Boat;

import java.util.Optional;

/**
 * Preserves the 1.8.1–1.8.9 boat mount height for the ordinary direct oak-boat rider.
 * Ornithe Feather 1.8.1–1.8.9 {@code BoatEntity.updateRiderPositon()} writes its yaw-based X/Z offsets and
 * {@code boatY + getMountHeight() + rider.getRideHeight()}; from 1.8.1,
 * {@code BoatEntity.getMountHeight()} returns double {@code -0.3} and
 * {@code PlayerEntity.getRideHeight()} returns double {@code -0.35}.
 */
@MovementChange(emulates = ParkourVersion.V1_8_9)
public final class BoatRiderPlacement implements PlayerPassengerPlacementHook {
    @Override
    public Optional<Placement> placement(LocalPlayer passenger, Entity vehicle) {
        if (!(vehicle instanceof Boat boat)) {
            return Optional.empty();
        }
        return LegacyBoatRiderPlacement.ordinaryOakBoat(boat, passenger, -0.3);
    }
}
