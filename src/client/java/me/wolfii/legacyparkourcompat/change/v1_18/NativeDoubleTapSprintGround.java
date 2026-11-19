package me.wolfii.legacyparkourcompat.change.v1_18;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.input.SprintDoubleTapGroundHook;
import net.minecraft.world.entity.player.Player;

/**
 * Keeps the existing 26.2 unobfuscated LocalPlayer#aiStep double-tap admission
 * through the 1.18 and 1.18.1 profiles.
 */
@MovementChange(emulates = ParkourVersion.V1_18)
public final class NativeDoubleTapSprintGround implements SprintDoubleTapGroundHook {
    @Override
    public boolean admits(Player player) {
        return true;
    }
}
