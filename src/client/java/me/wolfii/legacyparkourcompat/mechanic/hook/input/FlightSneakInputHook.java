package me.wolfii.legacyparkourcompat.mechanic.hook.input;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec2;

/** Compensation of stored keyboard axes in the local flight/camera/sneak branch. */
@FunctionalInterface
@MechanicType("player.input.flight_sneak")
public interface FlightSneakInputHook extends VersionedMechanic {
    Vec2 compensate(Player player, ParkourVersion selected, Vec2 storedInput);
}
