package me.wolfii.legacyparkourcompat.mechanic.hook;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VanillaCall;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.player.Player;

/**
 * Historical swimming / sprint-swim state.
 */
@MechanicType("player.swim")
public interface SwimmingBehavior extends VersionedMechanic {
    void updateSwimming(Player player, VanillaCall vanilla);
}
