package me.wolfii.legacyparkourcompat.change.v1_11_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.change.common.AutoJumpCollisionCandidates;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.VanillaFn;
import me.wolfii.legacyparkourcompat.mechanic.hook.jump.AutoJumpCollisionOrderHook;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.CollisionGetter;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.VoxelShape;

/**
 * The accepted Feather 1.12.2/1.13 World.getCollisions / WorldView.getBlockCollisions
 * first-height witness does not close earlier releases' provider order. Preserve the
 * existing block-before-entity assembly there instead of extrapolating the V1_12 scan.
 */
@MovementChange(emulates = ParkourVersion.V1_11_2)
public final class UnprovedEarlierAutoJumpCollisionOrder implements AutoJumpCollisionOrderHook {
    @Override
    public Iterable<VoxelShape> autoJumpCollisions(Player player, CollisionGetter level, AABB query,
        ParkourVersion selected, VanillaFn<Iterable<VoxelShape>> vanilla) {
        return AutoJumpCollisionCandidates.blocksBeforeEntities(player, level, query, selected, vanilla);
    }
}
