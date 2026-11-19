package me.wolfii.legacyparkourcompat.change.v1_16_2.player;

import java.util.function.BooleanSupplier;
import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.player.LocalPlayerShiftDismountHook;
import net.minecraft.client.player.LocalPlayer;

/**
 * 1.16 through 1.16.5 Mojmap {@code Player.rideTick()} handles a passenger's
 * shift input on the client, detaches it and clears the shared shift flag
 * before the local player can forward directions to the boat. The branch
 * skips the inherited ride tick. {@code LocalPlayer.isShiftKeyDown()} reads
 * input independently of the shared flag written by {@code Entity.setShiftKeyDown()}.
 * 1.15.2 and 1.17 Mojmap {@code Player.rideTick()} require the logical server
 * for this branch, as does 26.2 unobfuscated {@code Player.rideTick()}.
 */
@MovementChange(emulates = ParkourVersion.V1_16_2)
public final class LocalPlayerShiftDismount implements LocalPlayerShiftDismountHook {
    @Override
    public boolean dismountIfRequested(LocalPlayer player, BooleanSupplier wantsToStopRiding) {
        if (!wantsToStopRiding.getAsBoolean() || !player.isPassenger()) {
            return false;
        }
        player.stopRiding();
        player.setShiftKeyDown(false);
        return true;
    }
}
