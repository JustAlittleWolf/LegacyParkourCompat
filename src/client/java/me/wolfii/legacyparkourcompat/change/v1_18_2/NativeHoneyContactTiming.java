package me.wolfii.legacyparkourcompat.change.v1_18_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.block.HoneyContactTimingHook;
import net.minecraft.client.player.LocalPlayer;

/**
 * Retains native Honey dispatch below the bounded Mojmap 1.19–1.19.3
 * Entity.move / Entity.checkInsideBlocks integration range.
 */
@MovementChange(emulates = ParkourVersion.V1_18_2)
public final class NativeHoneyContactTiming implements HoneyContactTimingHook {
    @Override
    public boolean replay(LocalPlayer player, double inset) {
        return false;
    }
}
