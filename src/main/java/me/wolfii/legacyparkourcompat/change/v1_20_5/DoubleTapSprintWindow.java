package me.wolfii.legacyparkourcompat.change.v1_20_5;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.SprintWindowBehavior;
import net.minecraft.world.entity.player.Player;

@MovementChange(emulates = ParkourVersion.V1_20_5)
public final class DoubleTapSprintWindow implements SprintWindowBehavior {
    @Override
    public int doubleTapWindow(Player player, int vanilla) {
        return 7;
    }
}
