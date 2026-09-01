package me.wolfii.legacyparkourcompat.change.v1_14;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.SprintingBehavior;
import net.minecraft.world.entity.player.Player;

/**
 * Sneak-sprint was added in 1.14. Earlier versions cannot start or keep a
 * ground sprint while the sneak key is held.
 */
@MovementChange(emulates = ParkourVersion.V1_13)
public final class NoSneakSprint implements SprintingBehavior {
    @Override
    public boolean canStartSprinting(Player player, boolean vanilla) {
        return !player.isShiftKeyDown() && vanilla;
    }

    @Override
    public boolean shouldStopRunSprinting(Player player, boolean vanilla) {
        return player.isShiftKeyDown() || vanilla;
    }
}
