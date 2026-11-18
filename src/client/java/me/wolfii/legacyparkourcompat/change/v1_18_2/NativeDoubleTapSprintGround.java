package me.wolfii.legacyparkourcompat.change.v1_18_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.input.SprintDoubleTapGroundHook;
import net.minecraft.world.entity.player.Player;

/**
 * Keeps the existing 26.2 unobfuscated LocalPlayer#aiStep double-tap admission
 * for profiles earlier than the 1.19 implementation range.
 */
@MovementChange(emulates = ParkourVersion.V1_18_2)
public final class NativeDoubleTapSprintGround implements SprintDoubleTapGroundHook {
    @Override
    public boolean admits(Player player) {
        return true;
    }
}
