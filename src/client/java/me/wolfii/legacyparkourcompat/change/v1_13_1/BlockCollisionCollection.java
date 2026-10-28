package me.wolfii.legacyparkourcompat.change.v1_13_1;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.block.BlockCollisionCollectionHook;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Reconstructs the ordered 1.13.1/.2 slab stream from raw block candidates.
 * Partial slabs stay in X/Y/Z stream order; canonical full slabs form one tail.
 *
 * <p>Source: Minecraft 1.13.1 and 1.13.2 ({@code 1.13.1/ornithe-feather}
 * and {@code 1.13.2/ornithe-feather}), {@code WorldView.getBlockCollisions}
 * and {@code VoxelShapes.calculateMaxOffset}; compared against Minecraft 26.2
 * ({@code 26.2/unobfuscated}), {@code BlockCollisions.computeNext} and
 * {@code Shapes.collide}. Current axis clipping preserves the old strict
 * pre-next threshold for this ordered, grid-aligned slab stream.
 * The only non-slab states admitted as empty are air, cave air, and void air:
 * 1.13 {@code Block.init} and 26.2 {@code Blocks} register those keys with
 * {@code noCollision}; absent client chunks map to the source's void air.
 * 1.13 {@code Block.init} registers {@code stone_slab} at line 1574; 26.2
 * {@code DataFixers} applies {@code BlockRenameFix} at lines 751-765 to map it
 * to {@code smooth_stone_slab}, registered by {@code Blocks} at lines 3301-3305.
 * Current {@code stone_slab} is outside this historical roster.
 */
@MovementChange(emulates = ParkourVersion.V1_13_1)
public final class BlockCollisionCollection implements BlockCollisionCollectionHook {
    @Override
    public StreamOrder streamOrder() {
        return StreamOrder.ORDERED_X_Y_Z;
    }

    @Override
    public Optional<List<VoxelShape>> collect(CollectorQuery query, BlockCollisionProvider provider) {
        Objects.requireNonNull(query, "query");
        Objects.requireNonNull(provider, "provider");
        if (!query.boundsEquivalent()
            || !query.exclusionEquivalent()
            || !query.blockProviderEquivalent()
            || !query.shapeAggregationEquivalent()
            || !query.entityFree()
            || !query.borderClear()
            || !query.stepUpFree()
            || query.borderCheck()
            || query.onlyFirst()) {
            return Optional.empty();
        }

        VoxelShape bounds = query.bounds();
        VoxelShape exclusion = query.exclusion();
        if (bounds.isEmpty()
            || !finite(bounds.min(Direction.Axis.X), bounds.max(Direction.Axis.X))
            || !finite(bounds.min(Direction.Axis.Y), bounds.max(Direction.Axis.Y))
            || !finite(bounds.min(Direction.Axis.Z), bounds.max(Direction.Axis.Z))) {
            return Optional.empty();
        }

        int minX = Mth.floor(bounds.min(Direction.Axis.X)) - 1;
        int maxX = Mth.ceil(bounds.max(Direction.Axis.X)) + 1;
        int minY = Mth.floor(bounds.min(Direction.Axis.Y)) - 1;
        int maxY = Mth.ceil(bounds.max(Direction.Axis.Y)) + 1;
        int minZ = Mth.floor(bounds.min(Direction.Axis.Z)) - 1;
        int maxZ = Mth.ceil(bounds.max(Direction.Axis.Z)) + 1;
        List<VoxelShape> partialStream = new ArrayList<>();
        List<BlockPos> canonicalFullCells = new ArrayList<>();

        // BlockPos.Mutable.iterateRegionMutable uses X-fastest, then Y, then Z.
        for (int z = minZ; z < maxZ; z++) {
            boolean zPerimeter = z == minZ || z == maxZ - 1;
            for (int y = minY; y < maxY; y++) {
                boolean yPerimeter = y == minY || y == maxY - 1;
                for (int x = minX; x < maxX; x++) {
                    boolean xPerimeter = x == minX || x == maxX - 1;
                    if ((!xPerimeter || !yPerimeter) && (!yPerimeter || !zPerimeter)
                        && (!zPerimeter || !xPerimeter)) {
                        BlockPos position = new BlockPos(x, y, z);
                        if (!provider.isChunkLoaded(position)) {
                            continue;
                        }
                        CollisionCell cell = Objects.requireNonNull(provider.get(position),
                            "Loaded block collision provider returned no cell at " + position);
                        if (!cell.stateAndProviderEquivalent()) {
                            return Optional.empty();
                        }
                        Objects.requireNonNull(cell.currentState(),
                            "Block collision provider returned no current state at " + position);
                        VoxelShape localShape = Objects.requireNonNull(cell.localCollisionShape(),
                            "Block collision provider returned no shape at " + position);
                        if (cell.canonicalFullShape() && localShape != Shapes.block()) {
                            return Optional.empty();
                        }
                        if (localShape.isEmpty()) {
                            continue;
                        }

                        VoxelShape worldShape = localShape.move(x, y, z);
                        if (Shapes.joinIsNotEmpty(exclusion, worldShape, BooleanOp.AND)) {
                            continue;
                        }
                        if (cell.canonicalFullShape()) {
                            // 1.13.1/.2 place the single full-cell shape after partial stream elements.
                            canonicalFullCells.add(position);
                        } else if (Shapes.joinIsNotEmpty(bounds, worldShape, BooleanOp.AND)) {
                            partialStream.add(worldShape);
                        }
                    }
                }
            }
        }

        VoxelShape fullCells = Shapes.empty();
        for (BlockPos position : canonicalFullCells) {
            fullCells = Shapes.joinUnoptimized(fullCells,
                Shapes.block().move(position.getX(), position.getY(), position.getZ()), BooleanOp.OR);
        }
        if (!fullCells.isEmpty() && Shapes.joinIsNotEmpty(bounds, fullCells, BooleanOp.AND)) {
            partialStream.add(fullCells);
        }
        return Optional.of(List.copyOf(partialStream));
    }

    private static boolean finite(double min, double max) {
        return Double.isFinite(min) && Double.isFinite(max);
    }
}
