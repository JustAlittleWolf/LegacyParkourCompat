package me.wolfii.legacyparkourcompat.mechanic.hook.player;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.player.Player;

/** Selects whether the non-flying Player.updateSwimming branch delegates. */
@MechanicType("player.swimming_update_delegate")
public interface SwimmingUpdateHook extends VersionedMechanic {
    boolean shouldDelegate(Player player);
}
