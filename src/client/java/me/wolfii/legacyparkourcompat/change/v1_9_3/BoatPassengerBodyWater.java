package me.wolfii.legacyparkourcompat.change.v1_9_3;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.change.common.HistoricalRideables;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.water.BoatPassengerBodyWaterHook;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.vehicle.boat.Boat;
import org.jspecify.annotations.Nullable;

/**
 * 1.9 through 1.9.4 Ornithe Feather Entity.checkWaterCollisions clears inWater
 * when riding BoatEntity and skips the water query. 1.8.9 calls that query
 * unconditionally. In 26.2 unobfuscated, Entity.updateFluidInteraction publishes
 * wasTouchingWater after scanning a box that AbstractBoat.modifyPassengerFluidInteractionBox
 * can leave wet underwater. Preserve the historical body predicate for supported boats.
 */
@MovementChange(emulates = ParkourVersion.V1_9_3)
public final class BoatPassengerBodyWater implements BoatPassengerBodyWaterHook {
    @Override
    public boolean skipsBodyWater(@Nullable Entity vehicle, ParkourVersion selected) {
        return vehicle instanceof Boat && HistoricalRideables.contains(vehicle, selected);
    }
}
