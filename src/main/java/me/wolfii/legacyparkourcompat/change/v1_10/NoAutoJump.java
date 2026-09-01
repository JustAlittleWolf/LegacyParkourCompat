package me.wolfii.legacyparkourcompat.change.v1_10;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.AutoJumpBehavior;
import net.minecraft.world.entity.player.Player;

/**
 * Auto-jump was added in 1.10. Versions through 1.9 keep it off regardless of
 * the controls option.
 */
@MovementChange(emulates = ParkourVersion.V1_9)
public final class NoAutoJump implements AutoJumpBehavior {
    @Override
    public boolean isAutoJumpEnabled(Player player, boolean vanilla) {
        return false;
    }
}
