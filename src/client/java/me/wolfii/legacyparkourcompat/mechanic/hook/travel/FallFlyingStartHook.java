package me.wolfii.legacyparkourcompat.mechanic.hook.travel;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.client.player.LocalPlayer;
import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.VanillaFn;

/** Local jump-input gate that requests fall-flying. */
@MechanicType("player.elytra.jumpStart")
public interface FallFlyingStartHook extends VersionedMechanic {
    boolean tryToStartFallFlying(LocalPlayer player, ParkourVersion selected, VanillaFn<Boolean> vanilla);
}
