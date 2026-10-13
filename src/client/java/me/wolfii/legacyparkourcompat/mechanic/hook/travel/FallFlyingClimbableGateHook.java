package me.wolfii.legacyparkourcompat.mechanic.hook.travel;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.player.Player;

/** The climbable predicate read before local glide initiation. */
@MechanicType("player.elytra.climbableGate")
public interface FallFlyingClimbableGateHook extends VersionedMechanic {
    boolean isOnClimbableForFallFlyingStart(Player player, ParkourVersion selected, boolean vanilla);
}
