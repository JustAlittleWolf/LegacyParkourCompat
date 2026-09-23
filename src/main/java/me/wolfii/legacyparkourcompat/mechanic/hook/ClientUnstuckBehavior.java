package me.wolfii.legacyparkourcompat.mechanic.hook;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VanillaCall;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.player.Player;

/** Local-player horizontal impulse when stuck inside a suffocating block. */
@MechanicType("player.client_unstuck")
public interface ClientUnstuckBehavior extends VersionedMechanic {
    void moveTowardsClosestSpace(Player player, double x, double z, VanillaCall vanilla);
}
