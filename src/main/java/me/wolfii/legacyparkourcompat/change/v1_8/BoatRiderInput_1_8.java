package me.wolfii.legacyparkourcompat.change.v1_8;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.BoatRiderInputBehavior;

@MovementChange(emulates = ParkourVersion.V1_8)
public final class BoatRiderInput_1_8 implements BoatRiderInputBehavior {
    @Override
    public float sideOnlyAcceleration(float vanilla, boolean left, boolean right, boolean forward, boolean backward) {
        return left != right && !forward && !backward ? 0.0F : vanilla;
    }

    @Override
    public float sneakingAcceleration(float vanilla, boolean sneaking) {
        return sneaking ? vanilla * 0.3F : vanilla;
    }
    @Override
    public boolean appliesToBoat(net.minecraft.world.entity.Entity boat, ParkourVersion selected) {
        return me.wolfii.legacyparkourcompat.change.common.HistoricalRideables.contains(boat, selected);
    }
}
