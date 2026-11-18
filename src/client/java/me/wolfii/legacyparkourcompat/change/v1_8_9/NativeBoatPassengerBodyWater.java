package me.wolfii.legacyparkourcompat.change.v1_8_9;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.water.BoatPassengerBodyWaterHook;
import net.minecraft.world.entity.Entity;
import org.jspecify.annotations.Nullable;

/** Retains the existing contact path through the 1.8.9 boat boundary. */
@MovementChange(emulates = ParkourVersion.V1_8_9)
public final class NativeBoatPassengerBodyWater implements BoatPassengerBodyWaterHook {
    @Override
    public boolean skipsBodyWater(@Nullable Entity vehicle, ParkourVersion selected) {
        return false;
    }
}
