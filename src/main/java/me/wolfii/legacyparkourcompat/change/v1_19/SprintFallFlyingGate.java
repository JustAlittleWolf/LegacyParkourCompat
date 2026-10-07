package me.wolfii.legacyparkourcompat.change.v1_19;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.SprintFallFlyingGateBehavior;
import net.minecraft.world.entity.player.Player;

@MovementChange(emulates = ParkourVersion.V1_19)
public final class SprintFallFlyingGate implements SprintFallFlyingGateBehavior {
    @Override
    public boolean fallFlyingForSprintGate(Player player, ParkourVersion selected, boolean vanilla) {
        if (selected.olderThan(ParkourVersion.V1_9)) {
            return vanilla;
        }

        return false;
    }
}
