package me.wolfii.legacyparkourcompat.change.v1_19_4;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.change.v1_19.BoatPassengerFluidPush_1_19;
import me.wolfii.legacyparkourcompat.change.v1_19.GlideFallDistance_1_19;
import me.wolfii.legacyparkourcompat.change.v1_19.InsideBlockContact_1_19;
import me.wolfii.legacyparkourcompat.mechanic.MovementChangeProvider;
import me.wolfii.legacyparkourcompat.mechanic.MovementChangeRegistry;
import me.wolfii.legacyparkourcompat.mechanic.hook.BoatPassengerFluidPushBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.GlideFallDistanceBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.InsideBlockContactBehavior;

public final class MovementChanges implements MovementChangeProvider {
    @Override
    public void register(MovementChangeRegistry registry) {
        registry.register(BoatPassengerFluidPushBehavior.class, ParkourVersion.V1_19_4, new BoatPassengerFluidPush_1_19());
        registry.register(new SprintEligibility_1_19_4());
    }
}
