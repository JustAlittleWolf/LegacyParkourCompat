package me.wolfii.legacyparkourcompat.change.v1_15_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.player.LocalPlayerShiftDismountHook;
import net.minecraft.client.player.LocalPlayer;

/** 1.15.2 Mojmap Player.rideTick retains the server-only dismount gate removed in 1.16 Mojmap. */
@MovementChange(emulates = ParkourVersion.V1_15_2)
public final class NativeLocalPlayerShiftDismount implements LocalPlayerShiftDismountHook {
    @Override
    public boolean dismountIfShifting(LocalPlayer player, ParkourVersion selected) {
        return false;
    }
}
