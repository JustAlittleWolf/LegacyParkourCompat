package me.wolfii.legacyparkourcompat.change.v1_17;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.SprintCollisionBehavior;
import net.minecraft.world.entity.player.Player;

/** Through 1.17, every horizontal collision stopped sprinting. */
@MovementChange(emulates = ParkourVersion.V1_17_1)
public final class StrictCollisionSprintStop implements SprintCollisionBehavior {
    @Override
    public boolean shouldStopRunSprinting(Player player, boolean vanilla) {
        return player.horizontalCollision || vanilla;
    }
}
