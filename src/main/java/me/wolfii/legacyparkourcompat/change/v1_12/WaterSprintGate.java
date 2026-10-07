package me.wolfii.legacyparkourcompat.change.v1_12;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.WaterSprintGateBehavior;
import net.minecraft.world.entity.player.Player;

@MovementChange(emulates = ParkourVersion.V1_12)
public final class WaterSprintGate implements WaterSprintGateBehavior {
    @Override
    public boolean allowShallowWaterSprint(Player player, boolean vanilla) {
        return true;
    }
}
