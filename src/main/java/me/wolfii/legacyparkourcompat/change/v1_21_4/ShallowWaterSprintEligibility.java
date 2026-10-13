package me.wolfii.legacyparkourcompat.change.v1_21_4;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.input.ShallowWaterSprintHook;
import net.minecraft.world.entity.player.Player;

@MovementChange(emulates = ParkourVersion.V1_21_4)
public final class ShallowWaterSprintEligibility implements ShallowWaterSprintHook {
    @Override
    public boolean isInShallowWaterForSprintEligibility(
        Player player,
        boolean sprintKeyDown,
        boolean sprinting,
        boolean vanilla
    ) {
        return (sprinting || sprintKeyDown) && vanilla;
    }
}
