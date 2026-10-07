package me.wolfii.legacyparkourcompat.mechanic.hook;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VanillaFn;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;

/** Occupancy query used by the client escape probe. */
@FunctionalInterface
@MechanicType("player.client_unstuck.suffocation")
public interface SuffocationProbeBehavior extends VersionedMechanic {
    boolean suffocatesAt(Player player, BlockPos pos, VanillaFn<Boolean> vanilla);
}
