package me.wolfii.legacyparkourcompat.change.v1_21_4;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.sprint.HeldKeyTapStartHook;
import net.minecraft.client.Minecraft;

/**
 * Mojmap 1.21.4 LocalPlayer.aiStep reads the sprint key binding when an
 * eligible forward tap finds an expired timer. A held key starts sprint
 * immediately and skips the timer write. Mojmap 1.21.5 and unobfuscated
 * 26.2 LocalPlayer.aiStep arm on an expired timer regardless of that key,
 * then evaluate the separate sampled sprint input.
 */
@MovementChange(emulates = ParkourVersion.V1_21_4)
public final class HeldSprintTapStart implements HeldKeyTapStartHook {
    @Override
    public boolean startOnExpiredTimer() {
        return Minecraft.getInstance().options.keySprint.isDown();
    }
}
