package me.wolfii.legacyparkourcompat.mechanic.hook.travel;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.player.Player;

/** Whether a same-tick creative-flight toggle prevents local glide initiation. */
@MechanicType("player.elytra.flightToggleGate")
public interface FallFlyingFlightToggleGateHook extends VersionedMechanic {
    boolean justToggledCreativeFlightForStart(Player player, ParkourVersion selected, boolean vanilla);
}
