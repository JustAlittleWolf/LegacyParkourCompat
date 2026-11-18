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
 * Preserves 1.8's float-rounded mount height for an ordinary direct oak-boat rider. Ornithe
 * Feather 1.8 {@code net.minecraft.entity.vehicle.BoatEntity.updateRiderPositon()} writes the
 * yaw-based X/Z offsets and ordered Y sum; paired Ornithe Feather 1.9.4
 * {@code net.minecraft.entity.vehicle.BoatEntity.updateRiderPositon(Entity)} uses its new centered
 * sole-passenger path and float Y offset. The placement alias comparison for Ornithe Feather 1.8
 * and 1.8.1–1.8.9 confirms that 1.8 alone evaluates
 * {@code BoatEntity.getMountHeight()} as {@code height * 0.0 - 0.3F}; 1.8.1 onward uses double
 * {@code -0.3}. The compared 1.8.9 and 1.9.4
 * {@code net.minecraft.entity.player.PlayerEntity.getRideHeight()} methods return {@code -0.35}.
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
