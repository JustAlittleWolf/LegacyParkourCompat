package me.wolfii.legacyparkourcompat.mechanic.hook.player;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.client.player.LocalPlayer;

/** Controls the local player's passenger shift-to-dismount gate. */
@MechanicType("player.shift_dismount")
public interface LocalPlayerShiftDismountHook extends VersionedMechanic {
    boolean dismountIfShifting(LocalPlayer player, ParkourVersion selected);
}
