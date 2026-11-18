package me.wolfii.legacyparkourcompat.change.v1_8_9;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.change.boat.BoatRiderWitness;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.boat.BoatRiderOrientationHook;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.vehicle.boat.Boat;

/**
 * Ornithe Feather 1.8.9 {@code BoatEntity.updateRiderPositon()} writes no rider yaw;
 * Ornithe Feather 1.9.4 adds yaw/head-yaw and clamp writes for this rider operation.
 */
@MovementChange(emulates = ParkourVersion.V1_8_9)
public final class BoatRiderOrientation implements BoatRiderOrientationHook {
    @Override
    public boolean preserveRiderOrientation(Boat boat, LocalPlayer rider) {
        return BoatRiderWitness.isOrdinaryOakBoatRider(boat, rider);
    }
}
