package me.wolfii.legacyparkourcompat.mechanic.hook;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.Entity;

/** Scaling of a boat input acceleration while its controlling player sneaks. */
@FunctionalInterface
@MechanicType("player.boat.sneak_acceleration")
public interface BoatSneakAccelerationBehavior extends VersionedMechanic {
    float sneakingAcceleration(Entity boat, ParkourVersion selected, float vanilla, boolean sneaking);
}
