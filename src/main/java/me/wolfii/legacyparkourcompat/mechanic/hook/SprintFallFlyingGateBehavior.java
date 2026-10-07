package me.wolfii.legacyparkourcompat.mechanic.hook;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.player.Player;

/** Fall-flying state used by the sprint-start eligibility check. */
@FunctionalInterface
@MechanicType("player.sprint.fallFlyingForSprintGate")
public interface SprintFallFlyingGateBehavior extends VersionedMechanic {
    boolean fallFlyingForSprintGate(Player player, ParkourVersion selected, boolean vanilla);
}
