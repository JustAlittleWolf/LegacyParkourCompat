package me.wolfii.legacyparkourcompat.mechanic.hook.block;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.client.player.LocalPlayer;

/** Move-local Honey callback eligibility and dispatch for a resolved air move. */
@MechanicType("player.honey_contact_timing")
public interface HoneyContactTimingHook extends VersionedMechanic {
    boolean replay(LocalPlayer player, double inset);
}
