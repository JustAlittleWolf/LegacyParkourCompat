package me.wolfii.legacyparkourcompat.change.v1_13;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.change.common.AutoJumpCollisionCandidates;
import me.wolfii.legacyparkourcompat.change.common.AutoJumpFullCubeCandidates;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.VanillaFn;
import me.wolfii.legacyparkourcompat.mechanic.collision.LegacyVoxelShapeBoxes;
import me.wolfii.legacyparkourcompat.mechanic.collision.RawArrayVoxelShape;
import me.wolfii.legacyparkourcompat.mechanic.hook.jump.AutoJumpCollisionOrderHook;
import me.wolfii.legacyparkourcompat.mixin.collision.ArrayVoxelShapeInvoker;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.SectionPos;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.CollisionGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.EndPortalFrameBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.BitSetDiscreteVoxelShape;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.ArrayList;
import java.util.List;

/**
 * Exact 1.13 (Ornithe Feather gen2 build.2) WorldView#getBlockCollisions translates
 * and OR-joins admitted partial providers within Y, Z and X loops. It optimizes
 * that union before joining the separate integer-grid full-cell shape.
 * VoxelSet#forEachBox determines both optimization and first-hit box order.
 * Exact 1.13.1 (same mapping family) WorldView#getBlockCollisions instead streams
 * the individual partial shapes in BlockPos#iterateRegionMutable order.
 * LocalClientPlayerEntity#autoJump in both versions uses the first ray hit and
 * probes the block above its floored center. Adjacent top slabs can merge in
 * 1.13 and move that probe into the column of a no-eye end portal frame.
 *
 * <p>Admission covers the entire loaded, border-clear query, with no entities
 * and only air, dry static top slabs, no-eye end portal frames, or verified
 * full-cube supports. The query must include at least two admitted top slabs
 * and an admitted no-eye frame. Other queries use the existing full-cell and
 * border collector. Vanilla still consumes the boxes and updates the timer.
 */
@MovementChange(emulates = ParkourVersion.V1_13)
public final class AutoJumpCollisionAggregation implements AutoJumpCollisionOrderHook {
    private static final int MAX_QUERY_CELLS = 16_384;
    private static final double JOIN_EPSILON = 1.0E-7;
    private static final AABB TOP_SLAB = new AABB(0.0, 0.5, 0.0, 1.0, 1.0, 1.0);
    private static final AABB EMPTY_FRAME = new AABB(0.0, 0.0, 0.0, 1.0, 13.0 / 16.0, 1.0);

    @Override
    public Iterable<VoxelShape> autoJumpCollisions(Player player, CollisionGetter level, AABB query,
                                                   ParkourVersion selected,
                                                   VanillaFn<Iterable<VoxelShape>> vanilla) {
        List<VoxelShape> candidates = aggregate(player, level, query);
        return candidates == null
            ? AutoJumpFullCubeCandidates.collect(player, level, query, selected, vanilla)
            : candidates;
    }

    private static List<VoxelShape> aggregate(Player player, CollisionGetter level, AABB query) {
        // The ordinary-height witness yields a raw 1.13 VoxelShapes.block(Box)
        // because at least one Y endpoint lies outside the local unit interval.
        // At this height the original ceiling probes and the single center-column
        // read (no Jump Boost) also remain inside the padded query.
        if (!(level instanceof Level world)
            || player.getBbHeight() != 1.8F || player.getBbWidth() != 0.6F
            || player.hasEffect(MobEffects.JUMP_BOOST)
            || query.minY != player.getY()
            || query.minY != player.getBoundingBox().minY
            || query.maxY != query.minY + player.getBbHeight()
            || !safeAxis(query.minX, query.maxX)
            || !safeAxis(query.minY, query.maxY)
            || !safeAxis(query.minZ, query.maxZ)) {
            return null;
        }

        int minX = Mth.floor(query.minX) - 1;
        int minY = Mth.floor(query.minY) - 1;
        int minZ = Mth.floor(query.minZ) - 1;
        int maxX = Mth.ceil(query.maxX) + 1;
        int maxY = Mth.ceil(query.maxY) + 1;
        int maxZ = Mth.ceil(query.maxZ) + 1;
        int sizeX = maxX - minX;
        int sizeY = maxY - minY;
        int sizeZ = maxZ - minZ;
        if ((long) sizeX * sizeY * sizeZ > MAX_QUERY_CELLS
            || minY < 0 || maxY > 256
            || world.isOutsideBuildHeight(minY) || world.isOutsideBuildHeight(maxY - 1)) {
            return null;
        }

        var border = level.getWorldBorder();
        if (minX <= border.getMinX() || maxX >= border.getMaxX()
            || minZ <= border.getMinZ() || maxZ >= border.getMaxZ()
            || !world.getEntities(player, query.inflate(0.25), entity -> entity != player).isEmpty()) {
            return null;
        }

        VoxelShape bounds = RawArrayVoxelShape.oneCell(query);
        BitSetDiscreteVoxelShape fullCells = new BitSetDiscreteVoxelShape(sizeX, sizeY, sizeZ);
        CollisionContext context = CollisionContext.of(player);
        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
        VoxelShape partial = Shapes.empty();
        int topSlabs = 0;
        boolean hasEmptyFrame = false;
        for (int x = minX; x < maxX; x++) {
            boolean edgeX = x == minX || x == maxX - 1;
            VoxelShape xShapes = Shapes.empty();
            for (int z = minZ; z < maxZ; z++) {
                boolean edgeZ = z == minZ || z == maxZ - 1;
                BlockGetter chunk = level.getChunkForCollisions(
                    SectionPos.blockToSectionCoord(x), SectionPos.blockToSectionCoord(z));
                if (chunk == null) {
                    return null;
                }

                VoxelShape zShapes = Shapes.empty();
                for (int y = minY; y < maxY; y++) {
                    pos.set(x, y, z);
                    BlockState state = chunk.getBlockState(pos);
                    if (!state.getFluidState().isEmpty()) {
                        return null;
                    }
                    if (state.isAir()) {
                        continue;
                    }
                    VoxelShape shape = supportedShape(state, level, pos, context);
                    if (shape == null) {
                        return null;
                    }

                    // The gate covers pruned cells as they can still be read by
                    // AutoJump's ceiling or center-column probes.
                    if (edgeX && edgeZ || (edgeX || edgeZ) && y == maxY - 1) {
                        continue;
                    }
                    if (shape == Shapes.block()) {
                        // The 1.13 collecting branch inserts full cells without
                        // performing the partial-shape overlap check.
                        fullCells.fill(x - minX, y - minY, z - minZ);
                    } else {
                        VoxelShape moved = shape.move(x, y, z);
                        if (hasMergerTie(bounds, moved)) {
                            return null;
                        }
                        if (Shapes.joinIsNotEmpty(bounds, moved, BooleanOp.AND)) {
                            zShapes = Shapes.joinUnoptimized(zShapes, moved, BooleanOp.OR);
                            if (state.is(Blocks.END_PORTAL_FRAME)) {
                                hasEmptyFrame = true;
                            } else {
                                topSlabs++;
                            }
                        }
                    }
                }
                xShapes = Shapes.joinUnoptimized(xShapes, zShapes, BooleanOp.OR);
            }
            partial = Shapes.joinUnoptimized(partial, xShapes, BooleanOp.OR);
        }

        if (topSlabs < 2 || !hasEmptyFrame) {
            return null;
        }

        VoxelShape[] optimized = {Shapes.empty()};
        LegacyVoxelShapeBoxes.forEachBox(partial, (x1, y1, z1, x2, y2, z2) ->
            optimized[0] = Shapes.joinUnoptimized(optimized[0], Shapes.box(x1, y1, z1, x2, y2, z2), BooleanOp.OR));
        VoxelShape full = ArrayVoxelShapeInvoker.legacyparkourcompat$create(fullCells,
            integerGrid(minX, sizeX), integerGrid(minY, sizeY), integerGrid(minZ, sizeZ));
        VoxelShape aggregate = Shapes.joinUnoptimized(optimized[0], full, BooleanOp.OR);

        // The native consumer flattens each shape with the current Y/X/Z box
        // iterator. Supply the historical X/Y/Z boxes individually in its place.
        List<VoxelShape> candidates = new ArrayList<>();
        LegacyVoxelShapeBoxes.forEachBox(aggregate, (x1, y1, z1, x2, y2, z2) ->
            candidates.add(RawArrayVoxelShape.oneCell(new AABB(x1, y1, z1, x2, y2, z2))));
        return candidates;
    }

    private static VoxelShape supportedShape(BlockState state, CollisionGetter level, BlockPos pos,
                                             CollisionContext context) {
        if (AutoJumpCollisionCandidates.isHistoricalFullCube(state)) {
            VoxelShape shape = state.getCollisionShape(level, pos, context);
            return shape == Shapes.block() ? shape : null;
        }

        AABB expected;
        if (isHistoricalSlab(state) && state.getValue(SlabBlock.TYPE) == SlabType.TOP) {
            expected = TOP_SLAB;
        } else if (state.is(Blocks.END_PORTAL_FRAME) && !state.getValue(EndPortalFrameBlock.HAS_EYE)) {
            expected = EMPTY_FRAME;
        } else {
            return null;
        }
        VoxelShape shape = state.getCollisionShape(level, pos, context);
        return shape.toAabbs().equals(List.of(expected)) ? shape : null;
    }

    private static boolean isHistoricalSlab(BlockState state) {
        // The original stone_slab registry entry was renamed smooth_stone_slab.
        return state.is(Blocks.SMOOTH_STONE_SLAB) || state.is(Blocks.SANDSTONE_SLAB)
            || state.is(Blocks.PETRIFIED_OAK_SLAB) || state.is(Blocks.COBBLESTONE_SLAB)
            || state.is(Blocks.BRICK_SLAB) || state.is(Blocks.STONE_BRICK_SLAB)
            || state.is(Blocks.NETHER_BRICK_SLAB) || state.is(Blocks.QUARTZ_SLAB)
            || state.is(Blocks.RED_SANDSTONE_SLAB) || state.is(Blocks.PURPUR_SLAB)
            || state.is(Blocks.PRISMARINE_SLAB) || state.is(Blocks.PRISMARINE_BRICK_SLAB)
            || state.is(Blocks.DARK_PRISMARINE_SLAB) || state.is(Blocks.OAK_SLAB)
            || state.is(Blocks.SPRUCE_SLAB) || state.is(Blocks.BIRCH_SLAB)
            || state.is(Blocks.JUNGLE_SLAB) || state.is(Blocks.ACACIA_SLAB)
            || state.is(Blocks.DARK_OAK_SLAB);
    }

    private static boolean hasMergerTie(VoxelShape query, VoxelShape partial) {
        // Feather 1.13 SimpleIndexMerger and 26.2 IndirectMerger differ at
        // equality to the epsilon-adjusted coordinate. The provider grid
        // itself uses exact binary fractions, so only the raw query can tie.
        for (Direction.Axis axis : Direction.Axis.values()) {
            var endpoints = query.getCoords(axis);
            var coordinates = partial.getCoords(axis);
            for (int i = 0; i < endpoints.size(); i++) {
                double endpoint = endpoints.getDouble(i);
                for (int j = 0; j < coordinates.size(); j++) {
                    double coordinate = coordinates.getDouble(j);
                    if (endpoint == coordinate - JOIN_EPSILON || coordinate == endpoint - JOIN_EPSILON) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private static boolean safeAxis(double min, double max) {
        return min >= Integer.MIN_VALUE + 2.0 && max <= Integer.MAX_VALUE - 2.0
            && max - min >= 1.0E-7 && max - min <= MAX_QUERY_CELLS - 3.0;
    }

    private static double[] integerGrid(int start, int size) {
        double[] coordinates = new double[size + 1];
        for (int index = 0; index <= size; index++) {
            coordinates[index] = start + index;
        }
        return coordinates;
    }
}
