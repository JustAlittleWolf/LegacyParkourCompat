package me.wolfii.legacyparkourcompat.change.v1_9;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.FallFlyingSavedStateBehavior;
import me.wolfii.legacyparkourcompat.mixin.accessor.EntityInvoker;
import net.minecraft.world.entity.player.Player;

@MovementChange(emulates = ParkourVersion.V1_9)
public final class FallFlyingSavedState implements FallFlyingSavedStateBehavior {
    @Override
    public boolean shouldPersistFallFlyingState(Player player, ParkourVersion selected) {
        return selected != ParkourVersion.V1_9;
    }

    @Override
    public void afterFallFlyingStateLoaded(Player player, ParkourVersion selected) {
        if (selected == ParkourVersion.V1_9) {
            ((EntityInvoker) player).legacyparkourcompat$setSharedFlag(7, false);
        }
    }
}
