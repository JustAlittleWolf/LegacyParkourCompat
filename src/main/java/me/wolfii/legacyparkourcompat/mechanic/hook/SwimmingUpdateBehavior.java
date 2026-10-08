package me.wolfii.legacyparkourcompat.mechanic.hook;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.player.Player;

/** Computes the swimming flag at the shared Entity.updateSwimming() hook. */
@FunctionalInterface
@MechanicType("player.swimming.update")
public interface SwimmingUpdateBehavior extends VersionedMechanic {
    boolean isSwimmingAfterUpdate(Player player, ParkourVersion selected);
}
