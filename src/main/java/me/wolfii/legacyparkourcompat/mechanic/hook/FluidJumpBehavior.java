package me.wolfii.legacyparkourcompat.mechanic.hook;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.player.Player;

/** Historical player eligibility and branch choice for water and lava jumping. */
@MechanicType("player.jump.fluid")
public interface FluidJumpBehavior extends VersionedMechanic {
    boolean mayJumpInFluid(Player player, boolean vanillaAffectedByFluids);

    boolean isShallowLavaForGroundJump(Player player, boolean vanillaShallow);
}
