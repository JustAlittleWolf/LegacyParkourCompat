package me.wolfii.legacyparkourcompat.change.v1_13;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.hook.DoubleTapSprintStartBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.ShallowWaterSprintStartBehavior;
import me.wolfii.legacyparkourcompat.mechanic.hook.SprintKeyStartBehavior;
import net.minecraft.world.entity.player.Player;

@MovementChange(emulates = ParkourVersion.V1_13)
public final class SprintInputStart implements ShallowWaterSprintStartBehavior, DoubleTapSprintStartBehavior, SprintKeyStartBehavior {
    @Override
    public boolean allowShallowWaterStart(Player player, boolean vanilla) {
        return vanilla || MovementRuntime.profile(player).target() == ParkourVersion.V1_13;
    }

    @Override
    public boolean allowDoubleTapStart(Player player, boolean vanilla) {
        if (MovementRuntime.profile(player).target() != ParkourVersion.V1_13) {
            return vanilla;
        }
        return vanilla && (player.onGround() || player.isUnderWater());
    }

    @Override
    public boolean allowSprintKeyStart(Player player, boolean vanilla) {
        if (MovementRuntime.profile(player).target() != ParkourVersion.V1_13) {
            return vanilla;
        }
        return vanilla && (!player.isInWater() || player.isUnderWater());
    }
}
