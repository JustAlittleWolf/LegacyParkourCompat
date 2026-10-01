package me.wolfii.legacyparkourcompat.mechanic.hook;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VanillaCall;
import me.wolfii.legacyparkourcompat.mechanic.VanillaFn;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;

/** Local-player horizontal impulse when stuck inside a suffocating block. */
@MechanicType("player.client_unstuck")
public interface ClientUnstuckBehavior extends VersionedMechanic {
    void moveTowardsClosestSpace(Player player, double x, double z, VanillaCall vanilla);

    /** Versioned occupancy query used by the shared client escape probe. */
    default boolean suffocatesAt(Player player, BlockPos pos, VanillaFn<Boolean> vanilla) {
        return vanilla.get();
    }
}
