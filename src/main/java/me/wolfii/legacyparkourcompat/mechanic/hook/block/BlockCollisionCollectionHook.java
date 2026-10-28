package me.wolfii.legacyparkourcompat.mechanic.hook.block;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.Optional;

/**
 * Supplies a historical block-collision stream for a movement sweep.
 * An empty result means that the caller must keep the native collection.
 * Entity and world-border shapes are deliberately outside this operation.
 */
@MechanicType("world.block-collision-collection")
public interface BlockCollisionCollectionHook extends VersionedMechanic {
    StreamOrder streamOrder();

    Optional<List<VoxelShape>> collect(CollectorQuery query, BlockCollisionProvider provider);

    enum StreamOrder {
        NATIVE,
        AGGREGATE_X_Z_Y,
        ORDERED_X_Y_Z
    }

    /** Historical inputs plus explicit proofs that the current query has the same meaning. */
    record CollectorQuery(
        VoxelShape bounds,
        VoxelShape exclusion,
        boolean boundsEquivalent,
        boolean exclusionEquivalent,
        boolean blockProviderEquivalent,
        boolean shapeAggregationEquivalent,
        boolean entityFree,
        boolean borderClear,
        boolean stepUpFree,
        boolean borderCheck,
        boolean onlyFirst
    ) {
    }

    /** Current-world adapter for raw state, local-shape, and historical chunk checks. */
    interface BlockCollisionProvider {
        boolean isChunkLoaded(BlockPos oldProbe);

        CollisionCell get(BlockPos position);

        boolean supportsCompleteEnvelope(VoxelShape bounds, StreamOrder streamOrder);
    }

    /**
     * Shape result for one block position. {@code canonicalFullShape} is the old
     * reference-identity classification, not a fullness/occlusion predicate.
     */
    record CollisionCell(
        BlockState currentState,
        @Nullable String historicalStateKey,
        VoxelShape localCollisionShape,
        boolean canonicalFullShape,
        boolean stateAndProviderEquivalent
    ) {
    }
}
