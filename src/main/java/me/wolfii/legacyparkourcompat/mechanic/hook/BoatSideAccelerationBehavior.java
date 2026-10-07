package me.wolfii.legacyparkourcompat.mechanic.hook;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.Entity;

/** Acceleration applied by boat input when turning without forward or backward input. */
@FunctionalInterface
@MechanicType("player.boat.side_acceleration")
public interface BoatSideAccelerationBehavior extends VersionedMechanic {
    float sideOnlyAcceleration(
        Entity boat,
        ParkourVersion selected,
        float vanilla,
        boolean left,
        boolean right,
        boolean forward,
        boolean backward
    );
}
