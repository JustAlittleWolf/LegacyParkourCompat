package me.wolfii.legacyparkourcompat.change.v1_14;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.change.common.AutoJumpCollisionCandidates;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.VanillaFn;
import me.wolfii.legacyparkourcompat.mechanic.hook.jump.AutoJumpCollisionOrderHook;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.SectionPos;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.CollisionGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.border.BorderStatus;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

/**
 * Reproduces the old AutoJump full-cube grid aggregate only when its complete
 * query envelope is loaded, entity-free, and contains only supported cells.
 * Most cases are interior and use only ordinary full-cube blocks or air.
 * Cells where the 1.13 and 1.13.1 pruning rules disagree must be air.
 * Other queries retain the native iterable and object identities.
 *
 * <p>For the supported block providers, exact 1.14 Ornithe Feather Gen2 build 2,
 * {@code net.minecraft.block.Block.getShape} returns the shared
 * {@code VoxelShapes.block()} singleton and {@code Block.getCollisionShape}
 * returns that shape for collidable blocks. {@code Blocks} registers the
 * supported providers as ordinary full-cube blocks. Exact 1.14.1
 * Yarn build 10, {@code Block.getOutlineShape} and {@code Block.getCollisionShape}
 * use the shared {@code VoxelShapes.fullCube()} singleton, and
 * {@code Blocks} registers the same providers. Sandstone, stone bricks,
 * obsidian, iron, gold and diamond blocks, white wool and white concrete
 * also have these default providers in the exact 1.13 through 1.13.2
 * Ornithe Feather Gen2 build 2 {@code Block} registrations and
 * {@code Block.getCollisionShape}. Runtime checks require the canonical
 * {@code Shapes.block()} identity. Exact 1.13,
 * 1.13.1 and 1.13.2 Ornithe Feather Gen2 build 2,
 * {@code net.minecraft.world.WorldView.getBlockCollisions}, and exact 1.14
 * Ornithe Feather Gen2 build 2, {@code net.minecraft.world.WorldView.getCollisions};
 * each accumulates full cubes on the padded integer grid and emits an
 * {@code IntVoxelShape}. The matching {@code VoxelSet.forEachBox} implementation
 * defines the X/Y/Z scan and merge order. Exact 1.14.1 Yarn build 10,
 * {@code ViewableWorld.getCollisionShapes} and {@code CuboidBlockIterator.step},
 * emits translated per-block shapes instead. The checked cutover is 1.14.1.
 *
 * <p>For the bounded border cases, Ornithe Feather 1.13, 1.13.1 and 1.13.2
 * {@code WorldView.getBlockCollisions} put full Stone support and synthetic
 * border cells in the same grid before {@code VoxelSet.forEachBox}.
 * Original 1.13 skips X/Z corners and skips the high-Y row only at an
 * X or Z edge. 1.13.1 and 1.13.2 instead skip cells on two scan edges.
 * The supporting floor touches the probe's lower face but changes which
 * box the AutoJump ray encounters first.
 */
@MovementChange(emulates = ParkourVersion.V1_14)
public final class AutoJumpCollisionOrder implements AutoJumpCollisionOrderHook {
    private record Cell(int x, int y, int z) { }

    @Override
    public Iterable<VoxelShape> autoJumpCollisions(Player player, CollisionGetter level, AABB query,
                                                   ParkourVersion selected,
                                                   VanillaFn<Iterable<VoxelShape>> vanilla) {
        List<VoxelShape> aggregate = aggregate(player, level, query, selected);
        return aggregate == null
            ? AutoJumpCollisionCandidates.blocksBeforeEntities(player, level, query, selected, vanilla)
            : aggregate;
    }

    private static List<VoxelShape> aggregate(Player player, CollisionGetter level, AABB query,
                                               ParkourVersion selected) {
        if (query.getXsize() <= 0.0 || query.getYsize() <= 0.0 || query.getZsize() <= 0.0
            || !Double.isFinite(query.minX) || !Double.isFinite(query.minY) || !Double.isFinite(query.minZ)
            || !Double.isFinite(query.maxX) || !Double.isFinite(query.maxY) || !Double.isFinite(query.maxZ)) {
            return null;
        }
        if (!axisBoundsRepresentedExactly(query.minX, query.maxX)
            || !axisBoundsRepresentedExactly(query.minY, query.maxY)
            || !axisBoundsRepresentedExactly(query.minZ, query.maxZ)) {
            return null;
        }
        if (!intSafe(query.minX) || !intSafe(query.maxX)
            || !intSafe(query.minY) || !intSafe(query.maxY)
            || !intSafe(query.minZ) || !intSafe(query.maxZ)) {
            return null;
        }
        AABB entityEnvelope = query.inflate(0.25);
        if (!(level instanceof Level world)
            || !world.getEntities(player, entityEnvelope, entity -> entity != player).isEmpty()) {
            return null;
        }

        int minX = Mth.floor(query.minX) - 1;
        int minY = Mth.floor(query.minY) - 1;
        int minZ = Mth.floor(query.minZ) - 1;
        int maxX = Mth.ceil(query.maxX) + 1;
        int maxY = Mth.ceil(query.maxY) + 1;
        int maxZ = Mth.ceil(query.maxZ) + 1;
        long sizeXLong = (long) maxX - minX;
        long sizeYLong = (long) maxY - minY;
        long sizeZLong = (long) maxZ - minZ;
        if (minY < 0 || maxY > 256 || sizeXLong <= 0 || sizeYLong <= 0 || sizeZLong <= 0
            || sizeXLong > 16_384L || sizeYLong > 16_384L || sizeZLong > 16_384L
            || sizeXLong * sizeYLong * sizeZLong > 16_384L) {
            return null;
        }
        int sizeX = (int) sizeXLong;
        int sizeY = (int) sizeYLong;
        int sizeZ = (int) sizeZLong;
        var border = level.getWorldBorder();
        boolean west = minX <= border.getMinX();
        boolean east = maxX >= border.getMaxX();
        boolean north = minZ <= border.getMinZ();
        boolean south = maxZ >= border.getMaxZ();
        int borderFaces = (west ? 1 : 0) + (east ? 1 : 0) + (north ? 1 : 0) + (south ? 1 : 0);
        if (borderFaces > 1) {
            return null;
        }
        BorderProbe borderProbe = null;
        if (borderFaces == 1) {
            Direction face = west ? Direction.WEST : east ? Direction.EAST : north ? Direction.NORTH : Direction.SOUTH;
            borderProbe = admitBorder(player, world, query, selected, border, face, minX, maxX, minZ, maxZ);
            if (borderProbe == null || minY != query.minY - 1.0 || sizeY != 4) {
                return null;
            }
        }

        List<Cell> fullCells = new ArrayList<>();
        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
        CollisionContext context = CollisionContext.of(player);
        for (int x = minX; x < maxX; x++) {
            boolean edgeX = x == minX || x == maxX - 1;
            for (int z = minZ; z < maxZ; z++) {
                boolean edgeZ = z == minZ || z == maxZ - 1;
                for (int y = minY; y < maxY; y++) {
                    pos.set(x, y, z);
                    BlockGetter chunk = level.getChunkForCollisions(
                        SectionPos.blockToSectionCoord(x), SectionPos.blockToSectionCoord(z));
                    if (chunk == null) {
                        return null;
                    }
                    var state = chunk.getBlockState(pos);
                    boolean edgeY = y == minY || y == maxY - 1;
                    boolean includesV1_13 = !(edgeX && edgeZ) && (!(edgeX || edgeZ) || y != maxY - 1);
                    boolean includesV1_13_1 = !(edgeX && edgeY || edgeY && edgeZ || edgeZ && edgeX);
                    boolean includedForSelected = selected == ParkourVersion.V1_13
                        ? includesV1_13 : includesV1_13_1;
                    if (borderProbe != null) {
                        if (y == minY && borderProbe.isSupport(x, z)) {
                            if (!state.is(Blocks.STONE) || state.getCollisionShape(level, pos, context) != Shapes.block()) {
                                return null;
                            }
                            fullCells.add(new Cell(x, y, z));
                        } else {
                            if (!state.is(Blocks.AIR)) {
                                return null;
                            }
                            if (includedForSelected && borderProbe.outwardCell(x, z) >= borderProbe.edge()) {
                                fullCells.add(new Cell(x, y, z));
                            }
                        }
                        continue;
                    }
                    if (state.isAir()) {
                        continue;
                    }
                    if (!isSupportedBlock(state)
                        || state.getCollisionShape(level, pos, context) != Shapes.block()) {
                        return null;
                    }
                    if (includesV1_13 != includesV1_13_1) {
                        return null;
                    }
                    if (includedForSelected) {
                        fullCells.add(new Cell(x, y, z));
                    }
                }
            }
        }
        if (borderProbe != null) {
            // The scan already certified physical AIR below maxY. The extra head read is at
            // maxY. The first-hit center in original 1.13 west/north/south is one block lower,
            // so both of its cap samples are in the scan.
            boolean capExtendsAboveScan = selected != ParkourVersion.V1_13 || borderProbe.face() == Direction.EAST;
            if (!loadedAir(level, BlockPos.containing(player.getX(), maxY, player.getZ()))
                || (capExtendsAboveScan && !loadedAir(level, borderProbe.capSample(maxY)))) {
                return null;
            }
        }

        BitSet cells = new BitSet(sizeX * sizeY * sizeZ);
        for (Cell cell : fullCells) {
            int x = cell.x - minX;
            int y = cell.y - minY;
            int z = cell.z - minZ;
            cells.set((x * sizeY + y) * sizeZ + z);
        }
        List<VoxelShape> result = new ArrayList<>();
        for (int x = 0; x <= sizeX; x++) {
            for (int y = 0; y <= sizeY; y++) {
                int runStart = -1;
                for (int z = 0; z <= sizeZ; z++) {
                    if (contains(cells, sizeX, sizeY, sizeZ, x, y, z)) {
                        if (runStart < 0) runStart = z;
                    } else if (runStart >= 0) {
                        int lowX = x, highX = x, lowY = y, highY = y;
                        clear(cells, sizeY, sizeZ, x, y, runStart, z);
                        while (containsRun(cells, sizeX, sizeY, sizeZ, lowX - 1, lowY, runStart, z)) {
                            clear(cells, sizeY, sizeZ, --lowX, lowY, runStart, z);
                        }
                        while (containsRun(cells, sizeX, sizeY, sizeZ, highX + 1, lowY, runStart, z)) {
                            clear(cells, sizeY, sizeZ, ++highX, lowY, runStart, z);
                        }
                        while (containsLayer(cells, sizeX, sizeY, sizeZ, lowX, highX, lowY - 1, runStart, z)) {
                            lowY--;
                            for (int clearX = lowX; clearX <= highX; clearX++) {
                                clear(cells, sizeY, sizeZ, clearX, lowY, runStart, z);
                            }
                        }
                        while (containsLayer(cells, sizeX, sizeY, sizeZ, lowX, highX, highY + 1, runStart, z)) {
                            highY++;
                            for (int clearX = lowX; clearX <= highX; clearX++) {
                                clear(cells, sizeY, sizeZ, clearX, highY, runStart, z);
                            }
                        }
                        result.add(Shapes.create(new AABB(minX + lowX, minY + lowY, minZ + runStart,
                            minX + highX + 1.0, minY + highY + 1.0, minZ + z)));
                        runStart = -1;
                    }
                }
            }
        }
        if (borderProbe != null) {
            VoxelShape probe = Shapes.create(query);
            // Check overlap of the whole aggregate. Keep every box in greedy order
            // once any part of the union overlaps, including the floor run.
            if (result.stream().noneMatch(box -> Shapes.joinIsNotEmpty(probe, box, BooleanOp.AND))) {
                return List.of();
            }
        }
        return List.copyOf(result);
    }

    private record BorderProbe(Direction face, int edge, int row) {
        int outwardCell(int x, int z) {
            int coordinate = face.getAxis() == Direction.Axis.X ? x : z;
            return face.getAxisDirection() == Direction.AxisDirection.POSITIVE ? coordinate : -coordinate - 1;
        }

        boolean isSupport(int x, int z) {
            int outward = outwardCell(x, z);
            int tangent = face.getAxis() == Direction.Axis.X ? z : x;
            return tangent == row && (outward == edge - 2 || outward == edge - 1);
        }

        BlockPos capSample(int y) {
            // Use the floored center of the first greedy ray box in world coordinates.
            return switch (face) {
                case EAST -> new BlockPos(edge + 1, y, row);
                case WEST -> new BlockPos(-edge - 1, y, row);
                case NORTH -> new BlockPos(row, y, -edge - 1);
                case SOUTH -> new BlockPos(row, y, edge);
                default -> throw new IllegalStateException("Expected a horizontal AutoJump border face");
            };
        }
    }

    private static BorderProbe admitBorder(Player player, Level world, AABB query, ParkourVersion selected,
        WorldBorder border, Direction face, int minX, int maxX, int minZ, int maxZ) {
        boolean original = selected == ParkourVersion.V1_13;
        if ((!original && selected != ParkourVersion.V1_13_1)
            || !(player instanceof LocalPlayer) || world != player.level() || !world.isClientSide()
            || border.getStatus() != BorderStatus.STATIONARY) {
            return null;
        }
        double west = border.getMinX(), east = border.getMaxX();
        double north = border.getMinZ(), south = border.getMaxZ();
        double x = player.getX(), y = player.getY(), z = player.getZ();
        double width = player.getBbWidth(), height = player.getBbHeight();
        var speed = player.getEffect(MobEffects.SPEED);
        var jump = player.getEffect(MobEffects.JUMP_BOOST);
        if (!(intSafe(west) && intSafe(east) && intSafe(north) && intSafe(south)
            && west == Math.rint(west) && east == Math.rint(east) && north == Math.rint(north) && south == Math.rint(south)
            && x > west + 1.0 && x < east - 1.0 && z > north + 1.0 && z < south - 1.0
            && width == 0.6F && height == 1.8F && y == Math.rint(y) && query.minY == y
            && speed != null && speed.getAmplifier() == 1 && jump != null && jump.getAmplifier() == 1)) {
            return null;
        }

        Direction.Axis normal = face.getAxis();
        boolean onX = normal == Direction.Axis.X;
        boolean positive = face.getAxisDirection() == Direction.AxisDirection.POSITIVE;
        Direction.Axis tangent = onX ? Direction.Axis.Z : Direction.Axis.X;
        double along = onX ? x : z, across = onX ? z : x;
        int row = Mth.floor(across);
        int edge = (int) (positive ? (onX ? east : south) : -(onX ? west : north));
        int scanMin = onX ? minX : minZ, scanMax = onX ? maxX : maxZ;
        int outwardMin = positive ? scanMin : -scanMax, outwardMax = positive ? scanMax : -scanMin;
        int acrossMin = onX ? minZ : minX, acrossMax = onX ? maxZ : maxX;
        AABB box = player.getBoundingBox();
        double bodyInward = positive ? box.min(normal) : -box.max(normal);
        double bodyOutward = positive ? box.max(normal) : -box.min(normal);
        double queryInward = positive ? query.min(normal) : -query.max(normal);
        double rayEnd = (positive ? query.max(normal) : -query.min(normal)) - width;
        // Recover the axial segment from the actual inflated probe; keep the original east gate exact.
        double tolerance = original && face == Direction.EAST ? 0.0 : 1.0E-7;
        if (outwardMin != edge - 3 || outwardMax != edge + 2 || acrossMin != row - 2 || acrossMax != row + 3
            || bodyInward < edge - 2.0 || bodyOutward <= edge - 1.0 || bodyOutward >= edge
            || box.min(tangent) < row || box.max(tangent) > row + 1.0
            || Math.abs(query.maxY - (y + height)) > tolerance
            || Math.abs(queryInward - ((positive ? along : -along) - width)) > tolerance
            || Math.abs(query.min(tangent) - (across - width)) > tolerance
            || Math.abs(query.max(tangent) - (across + width)) > tolerance
            || rayEnd <= edge || rayEnd >= edge + 1.0) {
            return null;
        }
        return new BorderProbe(face, edge, row);
    }

    private static boolean loadedAir(CollisionGetter level, BlockPos pos) {
        BlockGetter chunk = level.getChunkForCollisions(SectionPos.blockToSectionCoord(pos.getX()),
            SectionPos.blockToSectionCoord(pos.getZ()));
        return chunk != null && chunk.getBlockState(pos).is(Blocks.AIR);
    }

    private static boolean isSupportedBlock(BlockState state) {
        return state.is(Blocks.STONE) || state.is(Blocks.DIRT)
            || state.is(Blocks.COBBLESTONE) || state.is(Blocks.OAK_PLANKS)
            || state.is(Blocks.STONE_BRICKS) || state.is(Blocks.SANDSTONE) || state.is(Blocks.OBSIDIAN)
            || state.is(Blocks.GOLD_BLOCK) || state.is(Blocks.IRON_BLOCK) || state.is(Blocks.DIAMOND_BLOCK)
            || state.is(Blocks.WOOL.pick(DyeColor.WHITE)) || state.is(Blocks.CONCRETE.pick(DyeColor.WHITE));
    }

    private static boolean contains(BitSet cells, int sx, int sy, int sz, int x, int y, int z) {
        return x >= 0 && x < sx && y >= 0 && y < sy && z >= 0 && z < sz
            && cells.get((x * sy + y) * sz + z);
    }

    private static boolean containsRun(BitSet cells, int sx, int sy, int sz, int x, int y, int start, int end) {
        if (x < 0 || x >= sx || y < 0 || y >= sy || start < 0 || end > sz) return false;
        int base = (x * sy + y) * sz;
        return cells.nextClearBit(base + start) >= base + end;
    }

    private static boolean containsLayer(BitSet cells, int sx, int sy, int sz,
                                         int lowX, int highX, int y, int start, int end) {
        if (y < 0 || y >= sy) return false;
        for (int x = lowX; x <= highX; x++) {
            if (!containsRun(cells, sx, sy, sz, x, y, start, end)) return false;
        }
        return true;
    }

    private static void clear(BitSet cells, int sy, int sz, int x, int y, int start, int end) {
        int base = (x * sy + y) * sz;
        cells.clear(base + start, base + end);
    }

    private static boolean intSafe(double coordinate) {
        return coordinate >= Integer.MIN_VALUE + 2.0 && coordinate <= Integer.MAX_VALUE - 2.0;
    }

    private static boolean axisBoundsRepresentedExactly(double min, double max) {
        // VoxelShapes.block(Box) uses exact raw coordinates when an endpoint is
        // outside [-1.0E-7, 1.0000001]. Otherwise both endpoints must be exactly
        // representable at one of its 1/1, 1/2, 1/4, or 1/8 grid resolutions.
        if (min < -1.0E-7 || max > 1.0000001) {
            return true;
        }
        for (int bits = 0; bits <= 3; bits++) {
            double scale = 1 << bits;
            if (voxelAligned(min, scale) && voxelAligned(max, scale)) {
                return true;
            }
        }
        return false;
    }

    private static boolean voxelAligned(double coordinate, double scale) {
        double scaled = coordinate * scale;
        return Math.abs(scaled - Math.floor(scaled)) < 1.0E-7;
    }
}
