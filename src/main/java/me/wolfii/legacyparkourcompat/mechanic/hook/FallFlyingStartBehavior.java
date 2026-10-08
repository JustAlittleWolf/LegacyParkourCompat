package me.wolfii.legacyparkourcompat.mechanic.hook;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.player.Player;

/** Local jump-input gate that requests fall-flying. */
@MechanicType("player.elytra.jumpStart")
public interface FallFlyingStartBehavior extends VersionedMechanic {
    boolean shouldStartFallFlying(Player player);

    boolean isOnClimbableForFallFlyingStart(Player player, boolean vanilla);

    boolean justToggledCreativeFlightForStart(Player player, boolean vanilla);
}
