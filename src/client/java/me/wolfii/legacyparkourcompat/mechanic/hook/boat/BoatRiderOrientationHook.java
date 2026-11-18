package me.wolfii.legacyparkourcompat.mechanic.hook.boat;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.vehicle.boat.Boat;

/** Resolves whether the modern boat rider yaw adjustment and clamp apply to this rider. */
@MechanicType("boat.rider_orientation")
public interface BoatRiderOrientationHook extends VersionedMechanic {
    boolean preserveRiderOrientation(Boat boat, LocalPlayer rider);
}
