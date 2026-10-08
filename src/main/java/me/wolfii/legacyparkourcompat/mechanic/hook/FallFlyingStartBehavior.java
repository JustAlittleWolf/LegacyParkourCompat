package me.wolfii.legacyparkourcompat.mechanic.hook;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VanillaFn;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.player.Player;

/** Local jump-input path that starts fall-flying. */
@FunctionalInterface
@MechanicType("player.elytra.jumpStart")
public interface FallFlyingStartBehavior extends VersionedMechanic {
    boolean tryStartFallFlying(Player player, VanillaFn<Boolean> vanilla);
}
