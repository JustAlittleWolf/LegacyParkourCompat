package me.wolfii.legacyparkourcompat.mechanic.hook;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VanillaCall;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.player.Player;

/**
 * Historical sneaking / crouch / crawl pose selection.
 */
@MechanicType("player.pose")
public interface PlayerPoseBehavior extends VersionedMechanic {
    void updatePlayerPose(Player player, VanillaCall vanilla);
}
