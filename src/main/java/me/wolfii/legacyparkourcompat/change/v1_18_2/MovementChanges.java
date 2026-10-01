package me.wolfii.legacyparkourcompat.change.v1_18_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChangeProvider;
import me.wolfii.legacyparkourcompat.mechanic.MovementChangeRegistry;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import me.wolfii.legacyparkourcompat.mechanic.hook.AirSpeedBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.AutoJumpBehavior;

public final class MovementChanges implements MovementChangeProvider {
    @Override
    public void register(MovementChangeRegistry registry) {
        registry.register(new GlideFallDistance_1_18_2());
        var airSpeed = new AirSpeed_1_18_2();
        registry.register(airSpeed);
        registerAs(registry, AirSpeedBehavior.class, ParkourVersion.V1_19, airSpeed);

        registry.register(new InsideBlockContact_1_18_2());
        registry.register(new SprintEligibility_1_18_2());

        var autoJump = new AutoJumpProbe_1_18_2();
        registry.register(autoJump);
        registerAs(registry, AutoJumpBehavior.class, ParkourVersion.V1_19, autoJump);

        registry.register(new BoatPassengerFluidPush_1_18_2());
    }

    private static <T extends VersionedMechanic> void registerAs(
        MovementChangeRegistry registry,
        Class<T> type,
        ParkourVersion version,
        T implementation
    ) {
        registry.register(type, version, implementation);
    }
}
