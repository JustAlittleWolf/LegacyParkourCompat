package me.wolfii.legacyparkourcompat.mechanic.hook.collision;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;

/** Resolves collision for one horizontal sneak-edge candidate box. */
@FunctionalInterface
@MechanicType("player.sneak.edge.collision-query")
public interface SneakEdgeCollisionQueryHook extends VersionedMechanic {
    boolean noCollision(Player player, AABB candidate);
}
