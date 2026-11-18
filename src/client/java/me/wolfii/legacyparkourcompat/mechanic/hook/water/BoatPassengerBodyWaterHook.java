package me.wolfii.legacyparkourcompat.mechanic.hook.water;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.Entity;
import org.jspecify.annotations.Nullable;

@MechanicType("water.body_contact.boat_passenger")
public interface BoatPassengerBodyWaterHook extends VersionedMechanic {
    /** Whether this passenger skips the legacy body-water query and reports dry contact. */
    boolean skipsBodyWater(@Nullable Entity vehicle, ParkourVersion selected);
}
