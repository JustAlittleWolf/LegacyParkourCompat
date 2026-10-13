package me.wolfii.legacyparkourcompat.mechanic.hook.jump;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.player.Player;

/** Whether a player may jump in fluid. */
@FunctionalInterface
@MechanicType("player.jump.fluid")
public interface FluidJumpHook extends VersionedMechanic {
    boolean mayJumpInFluid(Player player, boolean vanillaAffectedByFluids);
}
