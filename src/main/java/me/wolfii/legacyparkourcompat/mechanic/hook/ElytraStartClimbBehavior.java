package me.wolfii.legacyparkourcompat.mechanic.hook;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.player.Player;

/** Whether a climbable block prevents starting fall flight. */
@MechanicType("player.elytra_start_climb")
public interface ElytraStartClimbBehavior extends VersionedMechanic {
    boolean onClimbable(Player player, boolean vanilla);
}
