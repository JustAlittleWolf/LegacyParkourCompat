package me.wolfii.legacyparkourcompat.change.v1_21_4;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.block.BubbleColumnAboveContactHook;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Ornithe Feather 1.13.2 net.minecraft.block.BubbleColumnBlock.onEntityCollision
 * has bubble columns and uses an air-only overhead check; the same check is
 * continuous through Mojmap 1.21.4. Mojmap 1.21.4
 * net.minecraft.world.level.block.BubbleColumnBlock.entityInside classifies
 * only air overhead as above contact, then calls
 * net.minecraft.world.entity.Entity.onAboveBubbleCol; other blocks call
 * net.minecraft.world.entity.Entity.onInsideBubbleColumn. Mojmap 1.21.5
 * net.minecraft.world.level.block.BubbleColumnBlock.entityInside uses empty
 * collision and fluid states and calls
 * net.minecraft.world.entity.Entity.onAboveBubbleColumn or
 * net.minecraft.world.entity.Entity.onInsideBubbleColumn. The corresponding
 * net.minecraft.world.entity.player.Player overrides in both versions retain
 * the flying-player gate. Player has no bubble callback overrides in 1.21.1;
 * Mojmap 1.21.2 adds flying-gated overrides, so this classification change's
 * demonstrated player response applies to non-flying players across its range.
 */
@MovementChange(emulates = ParkourVersion.V1_21_4)
public final class BubbleColumnAboveContact implements BubbleColumnAboveContactHook {
    @Override
    public boolean isAboveContact(ParkourVersion selected, BlockState stateAbove, boolean vanillaAboveContact) {
        return selected.olderThan(ParkourVersion.V1_13) ? vanillaAboveContact : stateAbove.isAir();
    }
}
