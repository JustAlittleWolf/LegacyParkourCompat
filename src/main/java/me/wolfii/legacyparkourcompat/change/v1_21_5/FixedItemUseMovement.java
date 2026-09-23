package me.wolfii.legacyparkourcompat.change.v1_21_5;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.ItemUseMovementBehavior;
import net.minecraft.world.entity.player.Player;

/** Through 1.21.10, every used item has the same input slowdown and sprint restriction. */
@MovementChange(emulates = ParkourVersion.V1_21_5)
public final class FixedItemUseMovement implements ItemUseMovementBehavior {
    @Override
    public float speedMultiplier(Player player, float vanilla) {
        return 0.2F;
    }

    @Override
    public boolean slowsSprinting(Player player, boolean vanilla) {
        return player.isUsingItem();
    }
}
