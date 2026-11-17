package me.wolfii.legacyparkourcompat.change.common;

import com.google.common.collect.Iterables;
import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.VanillaFn;
import me.wolfii.legacyparkourcompat.mechanic.WorldBlockQueryBoundsContext;
import me.wolfii.legacyparkourcompat.mechanic.hook.world.BlockQueryBounds;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.CollisionGetter;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Iterator;
import java.util.function.Supplier;

/** Candidate assembly for the local-player auto-jump query. */
public final class AutoJumpCollisionCandidates {
    private AutoJumpCollisionCandidates() {}

    public static Iterable<VoxelShape> blocksBeforeEntities(Player player, CollisionGetter level, AABB query,
        ParkourVersion selected, VanillaFn<Iterable<VoxelShape>> vanilla) {
        return selected.olderThan(ParkourVersion.V1_10) ? vanilla.get()
            : Iterables.concat(level.getBlockCollisions(player, query), level.getEntityCollisions(player, query));
    }

    public static Iterable<VoxelShape> withQueryBounds(Player player, CollisionGetter level, AABB query,
        BlockQueryBounds bounds, Supplier<Iterable<VoxelShape>> resolveCandidates) {
        Iterable<VoxelShape> candidates =
            WorldBlockQueryBoundsContext.with(level, player, query, bounds, resolveCandidates);

        // Vanilla constructs BlockCollisions in iterator(). Concatenation can defer that
        // inner iterator until an outer hasNext() or next() call.
        return () -> {
            Iterator<VoxelShape> iterator =
                WorldBlockQueryBoundsContext.with(level, player, query, bounds, candidates::iterator);
            return new Iterator<>() {
                @Override
                public boolean hasNext() {
                    return WorldBlockQueryBoundsContext.with(level, player, query, bounds, iterator::hasNext);
                }

                @Override
                public VoxelShape next() {
                    return WorldBlockQueryBoundsContext.with(level, player, query, bounds, iterator::next);
                }
            };
        };
    }
}
