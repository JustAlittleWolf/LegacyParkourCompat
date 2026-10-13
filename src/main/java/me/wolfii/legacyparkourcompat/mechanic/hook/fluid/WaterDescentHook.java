package me.wolfii.legacyparkourcompat.mechanic.hook.fluid;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.player.Player;

/** Historical shift-to-descend input gate while the local player is in water. */
@FunctionalInterface
@MechanicType("player.water.descent")
public interface WaterDescentHook extends VersionedMechanic {
    boolean mayDescend(Player player, boolean vanillaAffectedByFluids);
}
