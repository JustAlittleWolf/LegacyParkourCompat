package me.wolfii.legacyparkourcompat.mechanic.hook.collision;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VanillaFn;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;

/** Occupancy query used by the client escape probe. */
@FunctionalInterface
@MechanicType("player.client_unstuck.suffocation")
public interface SuffocationProbeHook extends VersionedMechanic {
    boolean suffocatesAt(Player player, ParkourVersion target, BlockPos pos, VanillaFn<Boolean> vanilla);

    /** Applies this predicate to an explicitly selected vertical probe range. */
    default boolean suffocatesAt(Player player, ParkourVersion target, BlockPos pos,
                                 int minY, int maxYExclusive, VanillaFn<Boolean> vanilla) {
        return this.suffocatesAt(player, target, pos, vanilla);
    }
}
