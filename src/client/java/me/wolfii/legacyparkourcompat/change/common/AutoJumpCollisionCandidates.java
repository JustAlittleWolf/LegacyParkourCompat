package me.wolfii.legacyparkourcompat.change.common;

import com.google.common.collect.Iterables;
import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.VanillaFn;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.CollisionGetter;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.VoxelShape;

/** Shared block-before-entity assembly; no block-provider order is inferred here. */
public final class AutoJumpCollisionCandidates {
    private AutoJumpCollisionCandidates() {}

    public static Iterable<VoxelShape> blocksBeforeEntities(Player player, CollisionGetter level, AABB query,
        ParkourVersion selected, VanillaFn<Iterable<VoxelShape>> vanilla) {
        return selected.olderThan(ParkourVersion.V1_10) ? vanilla.get()
            : Iterables.concat(level.getBlockCollisions(player, query), level.getEntityCollisions(player, query));
    }
}
