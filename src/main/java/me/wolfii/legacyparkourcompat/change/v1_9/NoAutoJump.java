package me.wolfii.legacyparkourcompat.change.v1_9;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.AutoJumpBehavior;
import net.minecraft.world.entity.player.Player;

/** Auto-jump did not exist in the 1.9 movement profile. */
@MovementChange(emulates = ParkourVersion.V1_9)
public final class NoAutoJump implements AutoJumpBehavior {
    @Override
    public boolean isAutoJumpEnabled(Player player, boolean vanilla) {
        return false;
    }
}
