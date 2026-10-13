package me.wolfii.legacyparkourcompat.change.v1_8;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.change.common.HistoricalRideables;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.vehicle.BoatSideAccelerationHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.vehicle.BoatSneakAccelerationHook;
import net.minecraft.world.entity.Entity;

@MovementChange(emulates = ParkourVersion.V1_8)
public final class BoatRiderInput implements BoatSideAccelerationHook, BoatSneakAccelerationHook {
    @Override
    public float sideOnlyAcceleration(
        Entity boat,
        ParkourVersion selected,
        float vanilla,
        boolean left,
        boolean right,
        boolean forward,
        boolean backward
    ) {
        return HistoricalRideables.contains(boat, selected) && left != right && !forward && !backward ? 0.0F : vanilla;
    }

    @Override
    public float sneakingAcceleration(Entity boat, ParkourVersion selected, float vanilla, boolean sneaking) {
        return HistoricalRideables.contains(boat, selected) && sneaking ? vanilla * 0.3F : vanilla;
    }
}
