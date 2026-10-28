package me.wolfii.legacyparkourcompat.change.v1_13;

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
 * Reconstructs the block-only 1.13.0 collector only where the adapter proves
 * equal historical inputs, provider results, and exact shape aggregation. It
 * does not implement axis clipping.
 *
 * <p>Source: Minecraft 1.13.0, {@code 1.13/ornithe-feather},
 * {@code net.minecraft.world.WorldView.getBlockCollisions} (region, loaded-cell,
 * identity, exclusion and X/Z/Y joins), {@code WorldView.getCollisions}
 * (movement sweep and entity/border composition), and
 * {@code net.minecraft.block.shape.VoxelShapes.overlap/joinUnoptimized}.
 * Compared against Minecraft 1.13.1 and 1.13.2,
 * {@code 1.13.1/ornithe-feather} and {@code 1.13.2/ornithe-feather},
 * {@code WorldView.getBlockCollisions}; and Minecraft 26.2,
 * {@code 26.2/unobfuscated}, {@code net.minecraft.world.level.BlockCollisions.computeNext}
 * and {@code net.minecraft.world.phys.shapes.Shapes.joinUnoptimized}.
 * The only non-slab states admitted as empty are air, cave air, and void air:
 * 1.13 {@code Block.init} and 26.2 {@code Blocks} register those keys with
 * {@code noCollision}; absent client chunks map to the source's void air.
 */
@MovementChange(emulates = ParkourVersion.V1_13)
public final class BlockCollisionCollection implements BlockCollisionCollectionHook {
    @Override
    public StreamOrder streamOrder() {
        return StreamOrder.AGGREGATE_X_Z_Y;
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

        VoxelShape regionShape = Shapes.empty();
        List<BlockPos> canonicalFullCells = new ArrayList<>();
        for (int x = minX; x < maxX; x++) {
            VoxelShape xShape = Shapes.empty();
            for (int z = minZ; z < maxZ; z++) {
                boolean xPerimeter = x == minX || x == maxX - 1;
                boolean zPerimeter = z == minZ || z == maxZ - 1;
                if ((xPerimeter && zPerimeter)
                    || !provider.isChunkLoaded(new BlockPos(x, 64, z))) {
                    continue;
                }

                VoxelShape columnShape = Shapes.empty();
                for (int y = minY; y < maxY; y++) {
                    if (xPerimeter && zPerimeter || (xPerimeter || zPerimeter) && y == maxY - 1) {
                        continue;
                    }
                    BlockPos position = new BlockPos(x, y, z);
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

                    if (cell.canonicalFullShape()) {
                        // 1.13.0 uses reference identity with VoxelShapes.block(), not isFullBlock().
                        canonicalFullCells.add(position);
                        continue;
                    }

                    VoxelShape worldShape = localShape.move(x, y, z);
                    if (Shapes.joinIsNotEmpty(exclusion, worldShape, BooleanOp.AND)
                        || !Shapes.joinIsNotEmpty(bounds, worldShape, BooleanOp.AND)) {
                        continue;
                    }
                    columnShape = Shapes.joinUnoptimized(columnShape, worldShape, BooleanOp.OR);
                }
                xShape = Shapes.joinUnoptimized(xShape, columnShape, BooleanOp.OR);
            }
            regionShape = Shapes.joinUnoptimized(regionShape, xShape, BooleanOp.OR);
        }

        // 1.13.0 optimizes the joined partial accumulator before the single
        // integer full-cell shape is OR-joined. Fold only positions marked by
        // canonical identity into that separate tail.
        regionShape = regionShape.optimize();
        VoxelShape fullCells = Shapes.empty();
        for (BlockPos position : canonicalFullCells) {
            fullCells = Shapes.joinUnoptimized(fullCells,
                Shapes.block().move(position.getX(), position.getY(), position.getZ()), BooleanOp.OR);
        }
        return Optional.of(List.of(Shapes.joinUnoptimized(regionShape, fullCells, BooleanOp.OR)));
    }

    private static boolean finite(double min, double max) {
        return Double.isFinite(min) && Double.isFinite(max);
    }
}
