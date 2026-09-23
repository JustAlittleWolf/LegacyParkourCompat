package me.wolfii.legacyparkourcompat.change.v1_15;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.hook.ElytraStartClimbBehavior;
import net.minecraft.world.entity.player.Player;

/** 1.15.2 first disallowed starting fall flight while on a ladder. */
@MovementChange(emulates = ParkourVersion.V1_15)
public final class AllowLadderElytraStart implements ElytraStartClimbBehavior {
    @Override
    public boolean onClimbable(Player player, boolean vanilla) {
        return MovementRuntime.profile(player).target().olderThan(ParkourVersion.V1_9) ? vanilla : false;
    }
}
