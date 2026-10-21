package me.wolfii.legacyparkourcompat.change.v1_21_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.input.SlowInputSprintStartHook;

// 1.21.3 Mojmap net.minecraft.client.player.LocalPlayer.canStartSprinting has no slow-state
// gate; 1.21.4 Mojmap net.minecraft.client.player.LocalPlayer.canStartSprinting adds
// (!isMovingSlowly() || isUnderWater()). In 26.2 unobfuscated
// net.minecraft.client.player.LocalPlayer.canStartSprinting retains that gate and calls
// net.minecraft.client.player.LocalPlayer.isMovingSlowly, so the mixin changes only this input.
@MovementChange(emulates = ParkourVersion.V1_21_2)
public final class SlowInputSprintStart implements SlowInputSprintStartHook {
    @Override
    public boolean allowsSprintingWhileMovingSlowly() {
        return true;
    }
}
