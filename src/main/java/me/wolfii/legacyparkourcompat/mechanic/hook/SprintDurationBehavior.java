package me.wolfii.legacyparkourcompat.mechanic.hook;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.player.Player;

/** Historical client sprint duration and timeout. */
@MechanicType("player.sprint.duration")
public interface SprintDurationBehavior extends VersionedMechanic {
    void onSetSprinting(Player player, boolean sprinting);

    void tick(Player player);
}
