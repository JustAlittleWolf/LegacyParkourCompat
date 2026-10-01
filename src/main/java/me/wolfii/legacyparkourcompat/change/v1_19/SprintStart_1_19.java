package me.wolfii.legacyparkourcompat.change.v1_19;
import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.SprintStartBehavior;
import net.minecraft.world.entity.player.Player;
/** Before 1.19.4 there is no extra post-query fall-flying veto. */
@MovementChange(emulates = ParkourVersion.V1_19)
public final class SprintStart_1_19 implements SprintStartBehavior {
    @Override public boolean canStartSprinting(Player player, boolean vanilla) { return vanilla; }
}
