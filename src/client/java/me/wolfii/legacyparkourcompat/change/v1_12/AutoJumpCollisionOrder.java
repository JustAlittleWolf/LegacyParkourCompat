package me.wolfii.legacyparkourcompat.change.v1_12;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.change.common.AutoJumpCollisionCandidates;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.VanillaFn;
import me.wolfii.legacyparkourcompat.mechanic.hook.jump.AutoJumpCollisionOrderHook;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.CollisionGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.ArrayList;
import java.util.List;

/**
 * Feather 1.12.2 World.getCollisions visits X/Z/Y, skips horizontal corners and
 * the top cell of edge columns, then calls each state's addCollisions. Its
 * LocalClientPlayerEntity.autoJump stops at the first intersecting box.
 * Feather 1.13 WorldView.getBlockCollisions unions shapes; VoxelShape.getBoundingBoxes /
 * VoxelSet.forEachBox changes this first-hit order. First new release: 1.13.
 *
 * Admission is deliberately limited to the accepted unconnected-fence witness,
 * air and bottom smooth-stone slabs, inside the border with loaded surroundings.
 * Feather 1.12.2 FenceBlock.addCollisions emits one post for an unconnected fence;
 * SlabBlock.getShape supplies the bottom half box. Unobfuscated 26.2 FenceBlock /
 * CrossCollisionBlock and SlabBlock preserve those single-box geometries. Query
 * each actual state with the player CollisionContext; never sort decomposed unions
 * or assume that a multi-part provider's optimized order is its historical order.
 * Other providers, entities' historical geometry and runtime parity remain open.
 */
@MovementChange(emulates = ParkourVersion.V1_12)
public final class AutoJumpCollisionOrder implements AutoJumpCollisionOrderHook {
    private record Cell(BlockPos pos, BlockState state) {}

    @Override
    public Iterable<VoxelShape> autoJumpCollisions(Player player, CollisionGetter level, AABB query,
        ParkourVersion selected, VanillaFn<Iterable<VoxelShape>> vanilla) {
        int x0 = Mth.floor(query.minX) - 1;
        int x1 = Mth.ceil(query.maxX) + 1;
        int y0 = Mth.floor(query.minY) - 1;
        int y1 = Mth.ceil(query.maxY) + 1;
        int z0 = Mth.floor(query.minZ) - 1;
        int z1 = Mth.ceil(query.maxZ) + 1;
        var border = level.getWorldBorder();
        // Include native epsilon-expanded cells in admission, even if the old scan skips them.
        int minX = Math.min(x0, Mth.floor(query.minX - 1.0E-7) - 1);
        int maxX = Math.max(x1 - 1, Mth.floor(query.maxX + 1.0E-7) + 1);
        int minY = Math.min(y0, Mth.floor(query.minY - 1.0E-7) - 1);
        int maxY = Math.max(y1 - 1, Mth.floor(query.maxY + 1.0E-7) + 1);
        int minZ = Math.min(z0, Mth.floor(query.minZ - 1.0E-7) - 1);
        int maxZ = Math.max(z1 - 1, Mth.floor(query.maxZ + 1.0E-7) + 1);
        if (minX < border.getMinX() || maxX + 1.0 > border.getMaxX()
            || minZ < border.getMinZ() || maxZ + 1.0 > border.getMaxZ()) {
            return AutoJumpCollisionCandidates.blocksBeforeEntities(player, level, query, selected, vanilla);
        }
        List<Cell> cells = new ArrayList<>();
        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
        for (int x = minX; x <= maxX; x++) {
            for (int z = minZ; z <= maxZ; z++) {
                var chunk = level.getChunkForCollisions(SectionPos.blockToSectionCoord(x), SectionPos.blockToSectionCoord(z));
                if (chunk == null) {
                    return AutoJumpCollisionCandidates.blocksBeforeEntities(player, level, query, selected, vanilla);
                }
                for (int y = minY; y <= maxY; y++) {
                    pos.set(x, y, z);
                    BlockState state = chunk.getBlockState(pos);
                    if (!supported(state, level, pos)) {
                        return AutoJumpCollisionCandidates.blocksBeforeEntities(player, level, query, selected, vanilla);
                    }
                    boolean edgeX = x == x0 || x == x1 - 1;
                    boolean edgeZ = z == z0 || z == z1 - 1;
                    if (!state.isAir() && x >= x0 && x < x1 && y >= y0 && y < y1 && z >= z0 && z < z1
                        && !(edgeX && edgeZ) && (!(edgeX || edgeZ) || y != y1 - 1)) {
                        cells.add(new Cell(pos.immutable(), state));
                    }
                }
            }
        }
        CollisionContext context = CollisionContext.of(player);
        // The provider still sees its original state/world/player context. Each admitted
        // provider has one box, so emitting boxes separately retains its source order.
        Iterable<VoxelShape> blocks = () -> cells.stream().map(cell -> {
            List<AABB> parts = context.getCollisionShape(cell.state(), level, cell.pos()).move(cell.pos()).toAabbs();
            if (parts.size() != 1) {
                throw new IllegalStateException("Admitted auto-jump provider must supply one collision box: " + cell);
            }
            return parts.getFirst();
        })
            .filter(query::intersects).map(Shapes::create).iterator();
        return com.google.common.collect.Iterables.concat(blocks, level.getEntityCollisions(player, query));
    }

    private static boolean supported(BlockState state, CollisionGetter level, BlockPos pos) {
        if (state.isAir()) {
            return true;
        }
        if (state.is(Blocks.SMOOTH_STONE_SLAB)) {
            return state.getValue(SlabBlock.TYPE) == SlabType.BOTTOM && !state.getValue(SlabBlock.WATERLOGGED);
        }
        if (!(state.is(Blocks.OAK_FENCE) || state.is(Blocks.SPRUCE_FENCE) || state.is(Blocks.BIRCH_FENCE)
            || state.is(Blocks.JUNGLE_FENCE) || state.is(Blocks.ACACIA_FENCE) || state.is(Blocks.DARK_OAK_FENCE)
            || state.is(Blocks.NETHER_BRICK_FENCE))) {
            return false;
        }
        // Old fences resolve virtual connections from neighbors; stored modern flags alone
        // are insufficient. Air neighbors close that dependency without a new connection rule.
        return !state.getValue(FenceBlock.NORTH) && !state.getValue(FenceBlock.EAST)
            && !state.getValue(FenceBlock.SOUTH) && !state.getValue(FenceBlock.WEST)
            && !state.getValue(FenceBlock.WATERLOGGED)
            && loadedAir(level, pos.north()) && loadedAir(level, pos.east())
            && loadedAir(level, pos.south()) && loadedAir(level, pos.west());
    }

    private static boolean loadedAir(CollisionGetter level, BlockPos pos) {
        var chunk = level.getChunkForCollisions(SectionPos.blockToSectionCoord(pos.getX()),
            SectionPos.blockToSectionCoord(pos.getZ()));
        return chunk != null && chunk.getBlockState(pos).isAir();
    }
}
