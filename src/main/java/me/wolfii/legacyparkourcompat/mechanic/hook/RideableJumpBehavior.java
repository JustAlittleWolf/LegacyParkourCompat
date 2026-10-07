package me.wolfii.legacyparkourcompat.mechanic.hook;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VanillaCall;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.PlayerRideableJumping;
import net.minecraft.world.entity.player.Player;

/** Local-player charge application to a rideable jump mount. */
@FunctionalInterface
@MechanicType("player.rideable_jump")
public interface RideableJumpBehavior extends VersionedMechanic {
    void onPlayerJump(Player player, PlayerRideableJumping mount, int strength, VanillaCall vanilla);
}
