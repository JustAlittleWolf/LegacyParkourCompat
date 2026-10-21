package me.wolfii.legacyparkourcompat.change.v1_21_4;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.block.BubbleColumnAboveContactHook;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Mojmap 1.21.4
 * net.minecraft.world.level.block.BubbleColumnBlock.entityInside classifies
 * only air overhead as above contact, then calls
 * net.minecraft.world.entity.Entity.onAboveBubbleCol; other blocks call
 * net.minecraft.world.entity.Entity.onInsideBubbleColumn. Mojmap 1.21.5
 * net.minecraft.world.level.block.BubbleColumnBlock.entityInside uses empty
 * collision and fluid states and calls
 * net.minecraft.world.entity.Entity.onAboveBubbleColumn or
 * net.minecraft.world.entity.Entity.onInsideBubbleColumn. The corresponding
 * net.minecraft.world.entity.player.Player overrides in both versions retain
 * the flying-player gate.
 */
@MovementChange(emulates = ParkourVersion.V1_21_4)
public final class BubbleColumnAboveContact implements BubbleColumnAboveContactHook {
    @Override
    public boolean isAboveContact(BlockState stateAbove, boolean vanillaAboveContact) {
        return stateAbove.isAir();
    }
}
