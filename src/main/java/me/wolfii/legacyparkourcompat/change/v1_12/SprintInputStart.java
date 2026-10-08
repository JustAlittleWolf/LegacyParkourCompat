package me.wolfii.legacyparkourcompat.change.v1_12;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.hook.SprintInputStartBehavior;
import net.minecraft.world.entity.player.Player;

@MovementChange(emulates = ParkourVersion.V1_12)
public final class SprintInputStart implements SprintInputStartBehavior {
    @Override
    public boolean allowShallowWaterStart(Player player, boolean vanilla) {
        return vanilla;
    }

    @Override
    public boolean allowDoubleTapStart(Player player, boolean vanilla) {
        ParkourVersion target = MovementRuntime.profile(player).target();
        if (target != ParkourVersion.V1_11_2 && target != ParkourVersion.V1_12) {
            return vanilla;
        }
        return vanilla && player.onGround();
    }

    @Override
    public boolean allowSprintKeyStart(Player player, boolean vanilla) {
        return vanilla;
    }
}
