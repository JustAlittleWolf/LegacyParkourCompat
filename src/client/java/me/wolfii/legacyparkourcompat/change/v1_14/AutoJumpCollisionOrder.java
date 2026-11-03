package me.wolfii.legacyparkourcompat.change.v1_14;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.change.common.AutoJumpCollisionCandidates;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.VanillaFn;
import me.wolfii.legacyparkourcompat.mechanic.hook.jump.AutoJumpCollisionOrderHook;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.CollisionGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

/**
 * Reproduces the old AutoJump full-cube grid aggregate only when its complete
 * query envelope is loaded, interior, entity-free, and contains only Stone/air.
 * Cells where the 1.13 and 1.13.1 pruning rules disagree must be air.
 * Other queries retain the native iterable and object identities.
 *
 * <p>Source: exact 1.13, 1.13.1 and 1.13.2 Ornithe Feather Gen2 build 2,
 * {@code net.minecraft.world.WorldView.getBlockCollisions}, and exact 1.14
 * Ornithe Feather Gen2 build 2, {@code net.minecraft.world.WorldView.getCollisions};
 * each accumulates full cubes on the padded integer grid and emits an
 * {@code IntVoxelShape}. The matching {@code VoxelSet.forEachBox} implementation
 * defines the X/Y/Z scan and merge order. Exact 1.14.1 Yarn build 10,
 * {@code ViewableWorld.getCollisionShapes} and {@code CuboidBlockIterator.step},
 * emits translated per-block shapes instead. The checked cutover is 1.14.1.
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
        if (!level.getEntities(player, entityEnvelope, entity -> entity != player).isEmpty()) {
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
        if (minX <= border.getMinX() || maxX >= border.getMaxX()
            || minZ <= border.getMinZ() || maxZ >= border.getMaxZ()) {
            return null;
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
                    if (state.isAir()) {
                        continue;
                    }
                    if (!state.is(Blocks.STONE)
                        || state.getCollisionShape(level, pos, context) != Shapes.block()) {
                        return null;
                    }
                    boolean edgeY = y == minY || y == maxY - 1;
                    boolean includesV1_13 = !(edgeX && edgeZ) && (!(edgeX || edgeZ) || y != maxY - 1);
                    boolean includesV1_13_1 = !(edgeX && edgeY || edgeY && edgeZ || edgeZ && edgeX);
                    if (includesV1_13 != includesV1_13_1) {
                        return null;
                    }
                    boolean includedForSelected = selected == ParkourVersion.V1_13
                        ? includesV1_13 : includesV1_13_1;
                    if (includedForSelected) {
                        fullCells.add(new Cell(x, y, z));
                    }
                }
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
        return List.copyOf(result);
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
