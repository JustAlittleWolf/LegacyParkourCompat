package me.wolfii.legacyparkourcompat.change.v1_21_4;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.input.SprintTriggerHook;
import net.minecraft.world.entity.player.Player;

/**
 * Mojmap 1.16 and 1.21.4 LocalPlayer.aiStep reset the trigger for item use
 * without a passenger, and separately for the shift state saved before input
 * polling. Mojmap 1.21.5 adds current backward input to the reset condition;
 * 26.2 retains it. Remove only that added cause. The caller supplies the
 * saved shift local, not current input, and keeps modern custom item-use
 * sprint permission in the native item-use admission path.
 */
@MovementChange(emulates = ParkourVersion.V1_21_4)
public final class SprintTrigger implements SprintTriggerHook {
    @Override
    public boolean shouldReset(Player player, boolean priorShift, boolean itemUseReset) {
        return priorShift || itemUseReset;
    }
}
