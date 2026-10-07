package me.wolfii.legacyparkourcompat.change.v1_15_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.SprintTriggerBehavior;
import net.minecraft.world.entity.player.Player;

@MovementChange(emulates = ParkourVersion.V1_15_2)
public final class SprintTrigger implements SprintTriggerBehavior {
    @Override
    public boolean shouldReset(Player player, boolean anotherResetCause) {
        return anotherResetCause;
    }
}
