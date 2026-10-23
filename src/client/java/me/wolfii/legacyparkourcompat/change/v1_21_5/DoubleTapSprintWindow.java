package me.wolfii.legacyparkourcompat.change.v1_21_5;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.input.SprintWindowHook;
import net.minecraft.world.entity.player.Player;

/**
 * Mojmap 1.21.5, 1.21.6, 1.21.7 and 1.21.8 LocalPlayer.aiStep arm the
 * double-tap sprint timer with seven ticks. Mojmap 1.21.9 and 1.21.10 instead
 * read Options.sprintWindow. Keep this endpoint at 1.21.8 when splitting the
 * later profile alias; the newer profiles retain their native option value.
 * The 1.21.6 profile represents 1.21.6 through 1.21.8; anchoring here
 * preserves this operation through 1.21.8 after splitting the former alias.
 */
@MovementChange(emulates = ParkourVersion.V1_21_6)
public final class DoubleTapSprintWindow implements SprintWindowHook {
    @Override
    public int doubleTapWindow(Player player, int vanilla) {
        return 7;
    }
}
