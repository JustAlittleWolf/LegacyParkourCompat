package me.wolfii.legacyparkourcompat.mechanic.hook;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.player.Player;

/** Whether a player may jump in fluid. */
@FunctionalInterface
@MechanicType("player.jump.fluid")
public interface FluidJumpBehavior extends VersionedMechanic {
    boolean mayJumpInFluid(Player player, boolean vanillaAffectedByFluids);
}
