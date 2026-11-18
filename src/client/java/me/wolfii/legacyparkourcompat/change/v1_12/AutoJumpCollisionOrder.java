package me.wolfii.legacyparkourcompat.change.v1_12;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.change.common.AutoJumpCollisionCandidates;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.MovementRuntime;
import me.wolfii.legacyparkourcompat.mechanic.VanillaFn;
import me.wolfii.legacyparkourcompat.mechanic.hook.block.BlockCollisionShapeHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.jump.AutoJumpCollisionOrderHook;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.CollisionGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.level.border.BorderStatus;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Feather 1.12.2 World.getCollisions visits X/Z/Y, skips horizontal corners and
 * the top cell of edge columns, then calls each state's addCollisions. Its
 * LocalClientPlayerEntity.autoJump stops at the first intersecting box.
 * Feather 1.13 WorldView.getBlockCollisions unions shapes; VoxelShape.getBoundingBoxes /
 * VoxelSet.forEachBox changes this first-hit order. First new release: 1.13.
 *
 * The unconnected-fence witness admits air and bottom smooth-stone slabs
 * inside the border with loaded surroundings.
 * Feather 1.12.2 FenceBlock.addCollisions emits one post for an unconnected fence;
 * SlabBlock.getShape supplies the bottom half box. Unobfuscated 26.2 FenceBlock /
 * CrossCollisionBlock and SlabBlock preserve those single-box geometries. Query
 * each actual state with the player CollisionContext.
 *
 * The separate single-Brewing-Stand fixture needs the ordered components of
 * Ornithe Feather 1.12–1.12.2 BrewingStandBlock.addCollisions: stem, then full
 * plate. The Feather 1.13 BrewingStandBlock.SHAPE union and VoxelSet.forEachBox
 * expose the plate first. The active 26.2 historical collision union retains
 * the old occupied volume but likewise flattens it plate first. Reuse its old
 * ordered provider components on the audited slab-floor AutoJump path.
 *
 * For the separate east-border fixture, Feather 1.12–1.12.2
 * World.isWithinBorder is true for either prior outside flag when the
 * player's center is inside the eroded border. World.getCollisions and
 * WorldBorder.contains append outside cells in place. Feather 1.13.2
 * WorldView.getBlockCollisions accumulates them in an IntVoxelShape instead.
 * Include only individual east cells for the supported Stone-floor probe.
 * Other providers, entities' historical geometry and runtime parity remain open.
 */
@MovementChange(emulates = ParkourVersion.V1_12)
public final class AutoJumpCollisionOrder implements AutoJumpCollisionOrderHook {
    private static final AABB BREWING_STAND_STEM =
        new AABB(7.0 / 16.0, 0.0, 7.0 / 16.0, 9.0 / 16.0, 14.0 / 16.0, 9.0 / 16.0);
    private static final AABB BREWING_STAND_PLATE = new AABB(0.0, 0.0, 0.0, 1.0, 2.0 / 16.0, 1.0);

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
        if (minX < border.getMinX() || minZ < border.getMinZ() || maxZ + 1.0 > border.getMaxZ()) {
            return AutoJumpCollisionCandidates.blocksBeforeEntities(player, level, query, selected, vanilla);
        }
        double east = border.getMaxX();
        boolean eastBorder = maxX + 1.0 > east;
        if (eastBorder && !admitsEastBorder(player, level, query, selected, border, east, minY, maxY)) {
            return AutoJumpCollisionCandidates.blocksBeforeEntities(player, level, query, selected, vanilla);
        }
        int floorY = Mth.floor(query.minY) - 1;
        int floorZ = Mth.floor(player.getZ());
        int stoneSupportCells = 0;
        List<Cell> cells = new ArrayList<>();
        BlockPos brewingStand = null;
        boolean brewingStandSurroundings = true;
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
                    boolean edgeX = x == x0 || x == x1 - 1;
                    boolean edgeZ = z == z0 || z == z1 - 1;
                    boolean visited = x >= x0 && x < x1 && y >= y0 && y < y1 && z >= z0 && z < z1
                        && !(edgeX && edgeZ) && (!(edgeX || edgeZ) || y != y1 - 1);
                    if (eastBorder) {
                        // Exactly the two full-cube floor cells support the whole footprint.
                        if (y == floorY && z == floorZ && (x == east - 2.0 || x == east - 1.0)) {
                            if (!state.is(Blocks.STONE)) {
                                return AutoJumpCollisionCandidates.blocksBeforeEntities(player, level, query, selected, vanilla);
                            }
                            stoneSupportCells++;
                        } else if (!state.is(Blocks.AIR)) {
                            return AutoJumpCollisionCandidates.blocksBeforeEntities(player, level, query, selected, vanilla);
                        }
                        if (visited && x >= east) {
                            cells.add(new Cell(pos.immutable(), state));
                        }
                        continue;
                    }
                    if (state.is(Blocks.BREWING_STAND)) {
                        if (brewingStand != null || !visited) {
                            return AutoJumpCollisionCandidates.blocksBeforeEntities(player, level, query, selected, vanilla);
                        }
                        brewingStand = pos.immutable();
                    } else {
                        if (!supported(state, level, pos)) {
                            return AutoJumpCollisionCandidates.blocksBeforeEntities(player, level, query, selected, vanilla);
                        }
                        brewingStandSurroundings &= state.is(Blocks.AIR)
                            || isDryBottomSlab(state) && y + 0.5 <= query.minY;
                    }
                    if (!state.isAir() && visited) {
                        cells.add(new Cell(pos.immutable(), state));
                    }
                }
            }
        }
        if (eastBorder) {
            if (stoneSupportCells != 2 || !loadedState(level, BlockPos.containing(player.getX(),
                    player.getBoundingBox().maxY, player.getZ()).above()).is(Blocks.AIR)) {
                return AutoJumpCollisionCandidates.blocksBeforeEntities(player, level, query, selected, vanilla);
            }
            return () -> cells.stream().map(cell -> new AABB(cell.pos()))
                .filter(query::intersects).map(Shapes::create).iterator();
        }
        CollisionContext context = CollisionContext.of(player);
        List<VoxelShape> standParts = brewingStand == null || !brewingStandSurroundings ? List.of()
            : brewingStandParts(player, level, query, context, brewingStand, selected);
        if (brewingStand != null && standParts.isEmpty()) {
            return AutoJumpCollisionCandidates.blocksBeforeEntities(player, level, query, selected, vanilla);
        }

        Iterable<VoxelShape> blocks = () -> cells.stream().flatMap(cell -> {
            if (cell.state().is(Blocks.BREWING_STAND)) {
                return standParts.stream().map(shape -> shape.move(cell.pos()).bounds());
            }
            List<AABB> parts = context.getCollisionShape(cell.state(), level, cell.pos()).move(cell.pos()).toAabbs();
            if (parts.size() != 1) {
                throw new IllegalStateException("Admitted auto-jump provider must supply one collision box: " + cell);
            }
            return Stream.of(parts.getFirst());
        })
            .filter(query::intersects).map(Shapes::create).iterator();
        return com.google.common.collect.Iterables.concat(blocks, level.getEntityCollisions(player, query));
    }

    private static boolean admitsEastBorder(Player player, CollisionGetter level, AABB query, ParkourVersion selected,
        WorldBorder border, double east, int minY, int maxY) {
        if (selected != ParkourVersion.V1_12 || !(player instanceof LocalPlayer)
            || !(level instanceof Level clientLevel) || clientLevel != player.level() || !clientLevel.isClientSide()
            || border.getStatus() != BorderStatus.STATIONARY || east != Math.rint(east)
            || border.getMinX() != Math.rint(border.getMinX()) || border.getMinZ() != Math.rint(border.getMinZ())
            || border.getMaxZ() != Math.rint(border.getMaxZ()) || minY < level.getMinY() || maxY > level.getMaxY()) {
            return false;
        }

        double x = player.getX();
        double y = player.getY();
        double z = player.getZ();
        double width = player.getBbWidth();
        double endX = query.maxX - width;
        AABB box = player.getBoundingBox();
        int row = Mth.floor(z);
        var speed = player.getEffect(MobEffects.SPEED);
        return x > border.getMinX() + 1.0 && x < east - 1.0
            && z > border.getMinZ() + 1.0 && z < border.getMaxZ() - 1.0
            && width == 0.6F && player.getBbHeight() == 1.8F
            && box.minX >= east - 2.0 && box.maxX < east && box.minZ >= row && box.maxZ <= row + 1.0
            && y == Math.rint(y) && query.minY == y && query.maxY == y + player.getBbHeight()
            && query.minX == x - width && query.minZ == z - width && query.maxZ == z + width
            && endX > east && endX < east + 1.0
            && speed != null && speed.getAmplifier() == 1 && !player.hasEffect(MobEffects.JUMP_BOOST)
            && clientLevel.getEntities(player, query.inflate(0.25), entity -> true).isEmpty();
    }

    private static List<VoxelShape> brewingStandParts(Player player, CollisionGetter level, AABB query,
        CollisionContext context, BlockPos stand, ParkourVersion selected) {
        BlockPos playerSupport = BlockPos.containing(player.getX(), player.getY() - 0.5, player.getZ());
        if (selected != ParkourVersion.V1_12 || !(level instanceof Level clientLevel)
            || clientLevel != player.level() || !clientLevel.isClientSide()
            || query.minY != player.getY() || query.minY != stand.getY() - 0.5
            || playerSupport.getY() != stand.getY() - 1
            || !query.intersects(new AABB(stand))
            || !isDryBottomSlab(loadedState(level, stand.below()))
            || !isDryBottomSlab(loadedState(level, playerSupport))
            || !loadedState(level, stand.above()).is(Blocks.AIR)
            || !clientLevel.getEntities(player, query.inflate(0.25), entity -> true).isEmpty()) {
            return List.of();
        }

        BlockState state = loadedState(level, stand);
        if (!state.is(Blocks.BREWING_STAND)) {
            return List.of();
        }
        List<VoxelShape> parts = MovementRuntime.find(BlockCollisionShapeHook.class, state.getBlock(), player)
            .filter(hook -> hook.supports(selected))
            .flatMap(hook -> hook.collisionShapes(state, level, stand, context))
            .orElseGet(List::of);
        if (parts.size() != 2 || !parts.get(0).toAabbs().equals(List.of(BREWING_STAND_STEM))
            || !parts.get(1).toAabbs().equals(List.of(BREWING_STAND_PLATE))) {
            return List.of();
        }
        VoxelShape occupied = Shapes.or(parts.get(0), parts.get(1));
        VoxelShape playerShape = context.getCollisionShape(state, level, stand);
        return Shapes.joinIsNotEmpty(occupied, playerShape, BooleanOp.NOT_SAME) ? List.of() : parts;
    }

    private static boolean supported(BlockState state, CollisionGetter level, BlockPos pos) {
        if (state.isAir()) {
            return true;
        }
        if (state.is(Blocks.SMOOTH_STONE_SLAB)) {
            return isDryBottomSlab(state);
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

    private static boolean isDryBottomSlab(BlockState state) {
        return state.is(Blocks.SMOOTH_STONE_SLAB) && state.getValue(SlabBlock.TYPE) == SlabType.BOTTOM
            && !state.getValue(SlabBlock.WATERLOGGED);
    }

    private static BlockState loadedState(CollisionGetter level, BlockPos pos) {
        var chunk = level.getChunkForCollisions(SectionPos.blockToSectionCoord(pos.getX()),
            SectionPos.blockToSectionCoord(pos.getZ()));
        return chunk == null ? Blocks.VOID_AIR.defaultBlockState() : chunk.getBlockState(pos);
    }

    private static boolean loadedAir(CollisionGetter level, BlockPos pos) {
        var chunk = level.getChunkForCollisions(SectionPos.blockToSectionCoord(pos.getX()),
            SectionPos.blockToSectionCoord(pos.getZ()));
        return chunk != null && chunk.getBlockState(pos).isAir();
    }
}
