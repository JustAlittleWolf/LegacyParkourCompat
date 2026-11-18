package me.wolfii.legacyparkourcompat.change.v1_19;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.change.common.HistoricalRideables;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.water.BoatPassengerBodyWaterHook;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.vehicle.boat.Boat;
import net.minecraft.world.entity.vehicle.boat.ChestBoat;
import org.jspecify.annotations.Nullable;

/**
 * 1.9 Ornithe Feather Entity.checkWaterCollisions clears inWater for BoatEntity
 * before the fluid query. 1.18 through 1.19.2 Mojmap Entity.updateInWaterStateAndDoWaterCurrentPushing
 * retains this unconditional clear. In 1.19 through 1.19.2 Mojmap, ChestBoat
 * extends Boat and shares this branch. 1.19.3 adds an underwater-boat exception.
 *
 * <p>26.2 unobfuscated Entity.updateFluidInteraction publishes wasTouchingWater
 * from the native tracker. The registered Boat and ChestBoat classes are now
 * separate, so apply the contact guard to the historically available types in both.
 */
@MovementChange(emulates = ParkourVersion.V1_19)
public final class BoatPassengerBodyWater implements BoatPassengerBodyWaterHook {
    @Override
    public boolean skipsBodyWater(@Nullable Entity vehicle, ParkourVersion selected) {
        return (vehicle instanceof Boat || vehicle instanceof ChestBoat)
            && HistoricalRideables.contains(vehicle, selected);
    }
}
