package me.wolfii.legacyparkourcompat.change.v1_12;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.DoubleTapSprintStartBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.ShallowWaterSprintStartBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.SprintKeyStartBehavior;
import net.minecraft.world.entity.player.Player;

@MovementChange(emulates = ParkourVersion.V1_12)
public final class SprintInputStart implements ShallowWaterSprintStartBehavior, DoubleTapSprintStartBehavior, SprintKeyStartBehavior {
    @Override
    public boolean allowShallowWaterStart(Player player, boolean vanilla) {
        return vanilla;
    }

    @Override
    public boolean allowDoubleTapStart(Player player, boolean vanilla) {
        return vanilla && player.onGround();
    }

    @Override
    public boolean allowSprintKeyStart(Player player, boolean vanilla) {
        return vanilla;
    }
}
