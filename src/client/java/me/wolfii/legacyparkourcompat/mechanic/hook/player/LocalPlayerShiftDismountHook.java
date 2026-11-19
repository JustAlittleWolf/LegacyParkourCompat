package me.wolfii.legacyparkourcompat.mechanic.hook.player;

import java.util.function.BooleanSupplier;
import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.client.player.LocalPlayer;

/** Returns whether a historical local dismount consumed the player ride tick. */
@MechanicType("player.shift_dismount")
public interface LocalPlayerShiftDismountHook extends VersionedMechanic {
    boolean dismountIfRequested(LocalPlayer player, BooleanSupplier wantsToStopRiding);
}
