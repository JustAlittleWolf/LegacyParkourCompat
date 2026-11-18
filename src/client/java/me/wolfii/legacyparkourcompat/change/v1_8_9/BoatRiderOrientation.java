package me.wolfii.legacyparkourcompat.change.v1_8_9;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.change.boat.BoatRiderWitness;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.boat.BoatRiderOrientationHook;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.vehicle.boat.Boat;

/**
 * Ornithe Feather 1.8.9 {@code net.minecraft.entity.vehicle.BoatEntity.updateRiderPositon()} writes
 * no rider yaw. Paired Ornithe Feather 1.9.4
 * {@code net.minecraft.entity.vehicle.BoatEntity.updateRiderPositon(Entity)} adds rider yaw and
 * head-yaw writes, then {@code BoatEntity.copyEntityData(Entity)} copies boat body yaw and clamps
 * rider yaw to the boat-relative range. The 1.8.9–1.9.4 comparison found the new operation in every
 * checked 1.9 patch release.
 */
@MovementChange(emulates = ParkourVersion.V1_8_9)
public final class BoatRiderOrientation implements BoatRiderOrientationHook {
    @Override
    public boolean preserveRiderOrientation(Boat boat, LocalPlayer rider) {
        return BoatRiderWitness.isOrdinaryOakBoatRider(boat, rider);
    }
}
