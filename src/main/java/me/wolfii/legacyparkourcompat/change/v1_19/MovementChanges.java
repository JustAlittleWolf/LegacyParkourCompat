package me.wolfii.legacyparkourcompat.change.v1_19;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.change.v1_18_2.AirSpeed_1_18_2;
import me.wolfii.legacyparkourcompat.change.v1_18_2.AutoJumpProbe_1_18_2;
import me.wolfii.legacyparkourcompat.mechanic.MovementChangeProvider;
import me.wolfii.legacyparkourcompat.mechanic.MovementChangeRegistry;
import me.wolfii.legacyparkourcompat.mechanic.hook.AirSpeedBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.AutoJumpBehavior;

public final class MovementChanges implements MovementChangeProvider {
    @Override
    public void register(MovementChangeRegistry registry) {
        registry.register(new SprintStart_1_19());
        registry.register(new GlideFallDistance_1_19());
        registry.register(new InsideBlockContact_1_19());
        registry.register(new BoatPassengerFluidPush_1_19());
        registry.register(new me.wolfii.legacyparkourcompat.change.v1_19.SprintEligibility_1_19_SprintFallFlyingGate());
        registry.register(new me.wolfii.legacyparkourcompat.change.v1_19.SprintEligibility_1_19_VehicleSprint());
    }
}
