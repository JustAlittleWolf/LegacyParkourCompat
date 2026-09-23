package me.wolfii.legacyparkourcompat.mechanic.hook;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.player.Player;

/** Client input and sprint restrictions while the player uses an item. */
@MechanicType("player.item_use_movement")
public interface ItemUseMovementBehavior extends VersionedMechanic {
    float speedMultiplier(Player player, float vanilla);

    boolean slowsSprinting(Player player, boolean vanilla);
}
