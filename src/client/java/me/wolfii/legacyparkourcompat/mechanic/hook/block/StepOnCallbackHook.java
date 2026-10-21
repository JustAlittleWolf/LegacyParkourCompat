package me.wolfii.legacyparkourcompat.mechanic.hook.block;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.player.Player;

/** Determines whether the historical grounded step callback should run. */
@MechanicType("entity.step_on_callback")
public interface StepOnCallbackHook extends VersionedMechanic {
    boolean shouldInvoke(Player player);
}
