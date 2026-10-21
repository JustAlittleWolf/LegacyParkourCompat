package me.wolfii.legacyparkourcompat.mechanic.hook.input;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.player.Player;

/** Flying-player shallow-water eligibility shared by sprint entry and continuation. */
@FunctionalInterface
@MechanicType("player.sprint.flying_shallow_water")
public interface FlightShallowWaterSprintHook extends VersionedMechanic {
    boolean isSprintingPossible(Player player, ParkourVersion selected, boolean vanilla);
}
