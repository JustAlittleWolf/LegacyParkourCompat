package me.wolfii.legacyparkourcompat.mechanic.hook;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.player.Player;

/** Historical shift-to-descend input gate while the local player is in water. */
@MechanicType("player.water.descent")
public interface WaterDescentBehavior extends VersionedMechanic {
    boolean mayDescend(Player player, boolean vanillaAffectedByFluids);
}
