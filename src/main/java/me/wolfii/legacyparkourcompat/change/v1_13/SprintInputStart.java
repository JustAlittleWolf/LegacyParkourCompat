package me.wolfii.legacyparkourcompat.change.v1_13;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.hook.input.DoubleTapSprintStartHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.input.ShallowWaterSprintStartHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.input.SprintKeyStartHook;
import net.minecraft.world.entity.player.Player;

@MovementChange(emulates = ParkourVersion.V1_13)
public final class SprintInputStart implements ShallowWaterSprintStartHook, DoubleTapSprintStartHook, SprintKeyStartHook {
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
