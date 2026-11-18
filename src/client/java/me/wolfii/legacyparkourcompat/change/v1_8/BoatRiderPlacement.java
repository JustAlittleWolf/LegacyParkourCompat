package me.wolfii.legacyparkourcompat.change.v1_8;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.change.boat.LegacyBoatRiderPlacement;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.entity.PlayerPassengerPlacementHook;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.vehicle.boat.Boat;

import java.util.Optional;

/**
 * Preserves 1.8's float-rounded boat mount height for the ordinary direct oak-boat rider.
 * Ornithe Feather 1.8 {@code BoatEntity.updateRiderPositon()} writes its yaw-based X/Z offsets and
 * {@code boatY + getMountHeight() + rider.getRideHeight()}; in 1.8,
 * {@code BoatEntity.getMountHeight()} returns {@code height * 0.0 - 0.3F} and
 * {@code PlayerEntity.getRideHeight()} returns {@code -0.35}.
 */
@MovementChange(emulates = ParkourVersion.V1_8)
public final class BoatRiderPlacement implements PlayerPassengerPlacementHook {
    @Override
    public Optional<Placement> placement(LocalPlayer passenger, Entity vehicle) {
        if (!(vehicle instanceof Boat boat)) {
            return Optional.empty();
        }
        double mountHeight = boat.getBbHeight() * 0.0 - 0.3F;
        return LegacyBoatRiderPlacement.ordinaryOakBoat(boat, passenger, mountHeight);
    }
}
