package me.wolfii.legacyparkourcompat.mechanic.hook.collision;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;

/** Movement types for which the player's sneak-edge guard runs. */
@FunctionalInterface
@MechanicType("player.sneak.edge.mover")
public interface SneakEdgeMoverHook extends VersionedMechanic {
    MoverType edgeMoverType(Player player, MoverType vanilla);
}
