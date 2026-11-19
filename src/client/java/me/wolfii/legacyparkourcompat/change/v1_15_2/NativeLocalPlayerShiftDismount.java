package me.wolfii.legacyparkourcompat.change.v1_15_2;

import java.util.function.BooleanSupplier;
import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.player.LocalPlayerShiftDismountHook;
import net.minecraft.client.player.LocalPlayer;

/**
 * 1.15 through 1.15.2 Mojmap {@code Player.rideTick()} handles shift
 * dismount on the server. 1.16 Mojmap {@code Player.rideTick()} also
 * handles it on the client.
 */
@MovementChange(emulates = ParkourVersion.V1_15_2)
public final class NativeLocalPlayerShiftDismount implements LocalPlayerShiftDismountHook {
    @Override
    public boolean dismountIfRequested(LocalPlayer player, BooleanSupplier wantsToStopRiding) {
        return false;
    }
}
