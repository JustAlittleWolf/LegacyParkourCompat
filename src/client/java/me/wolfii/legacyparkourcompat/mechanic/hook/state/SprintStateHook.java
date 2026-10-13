package me.wolfii.legacyparkourcompat.mechanic.hook.state;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.player.Player;

/** Notification when the local player sprint state is assigned. */
@FunctionalInterface
@MechanicType("player.sprint.state")
public interface SprintStateHook extends VersionedMechanic {
    void onSetSprinting(Player player, boolean sprinting);
}
