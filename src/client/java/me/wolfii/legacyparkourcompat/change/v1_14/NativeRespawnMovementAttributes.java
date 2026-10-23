package me.wolfii.legacyparkourcompat.change.v1_14;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.player.RespawnMovementAttributeHook;
import net.minecraft.client.player.LocalPlayer;

/**
 * Keeps the native respawn response for profiles before the accepted 1.14.2 boundary.
 * The 1.14.4 Mojmap ClientPacketListener.handleRespawn path supports the reset delta;
 * earlier source states are not yet proven, so they retain native attribute copying.
 */
@MovementChange(emulates = ParkourVersion.V1_14)
public final class NativeRespawnMovementAttributes implements RespawnMovementAttributeHook {
    @Override
    public void afterRespawn(LocalPlayer replacementPlayer) {
        // Native attribute copying is the supported response before the 1.14.2 boundary.
    }
}
