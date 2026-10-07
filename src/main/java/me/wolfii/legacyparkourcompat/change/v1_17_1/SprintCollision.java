package me.wolfii.legacyparkourcompat.change.v1_17_1;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.SprintCollisionBehavior;
import net.minecraft.world.entity.player.Player;

/** Restores stopping sprint on every horizontal collision. */
@MovementChange(emulates = ParkourVersion.V1_17_1)
public final class SprintCollision implements SprintCollisionBehavior {
    @Override
    public boolean shouldStopRunSprinting(Player player, boolean vanilla) {
        return vanilla || player.horizontalCollision;
    }
}
