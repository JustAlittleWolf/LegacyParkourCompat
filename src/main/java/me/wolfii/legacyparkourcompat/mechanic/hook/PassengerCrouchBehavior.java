package me.wolfii.legacyparkourcompat.mechanic.hook;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.player.Player;

/** Whether riding prevents the local crouch decision. */
@MechanicType("player.crouch.passenger")
public interface PassengerCrouchBehavior extends VersionedMechanic {
    boolean blocksCrouching(Player player, boolean vanilla);
}
