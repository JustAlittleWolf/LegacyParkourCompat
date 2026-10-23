package me.wolfii.legacyparkourcompat.mechanic.hook.push;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.player.Player;

/** Eligibility of a client player to receive an entity push. */
@MechanicType("player.pushability")
public interface PlayerPushabilityHook extends VersionedMechanic {
    boolean isPushable(Player player, boolean vanilla);
}
