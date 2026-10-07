package me.wolfii.legacyparkourcompat.mechanic.hook;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.player.Player;

/** Whether shallow lava selects a ground jump. */
@FunctionalInterface
@MechanicType("player.jump.lava_ground")
public interface LavaGroundJumpBehavior extends VersionedMechanic {
    boolean isShallowLavaForGroundJump(Player player, boolean vanillaShallow);
}
