package me.wolfii.legacyparkourcompat.mechanic.collision;

import me.wolfii.legacyparkourcompat.mixin.collision.VoxelShapeAccessor;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.shapes.DiscreteVoxelShape;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.BitSet;

/** Historical VoxelSet#forEachBox traversal shared by the 1.13 and 1.14 AutoJump providers. */
public final class LegacyVoxelShapeBoxes {
    private LegacyVoxelShapeBoxes() {}

    public static void forEachBox(VoxelShape shape, Shapes.DoubleLineConsumer consumer) {
        if (shape.isEmpty()) {
            return;
        }

        DiscreteVoxelShape voxels = ((VoxelShapeAccessor) (Object) shape).legacyparkourcompat$discreteShape();
        int sizeX = voxels.getXSize();
        int sizeY = voxels.getYSize();
        int sizeZ = voxels.getZSize();
        BitSet cells = new BitSet(sizeX * sizeY * sizeZ);
        for (int x = 0; x < sizeX; x++) {
            for (int y = 0; y < sizeY; y++) {
                for (int z = 0; z < sizeZ; z++) {
                    if (voxels.isFull(x, y, z)) {
                        cells.set((x * sizeY + y) * sizeZ + z);
                    }
                }
            }
        }

        var xs = shape.getCoords(Direction.Axis.X);
        var ys = shape.getCoords(Direction.Axis.Y);
        var zs = shape.getCoords(Direction.Axis.Z);
        forEachBox(cells, sizeX, sizeY, sizeZ, (x1, y1, z1, x2, y2, z2) -> consumer.consume(
            xs.getDouble(x1), ys.getDouble(y1), zs.getDouble(z1),
            xs.getDouble(x2), ys.getDouble(y2), zs.getDouble(z2)));
    }

    /** Consumes the occupied cells in the supplied grid in historical box order. */
    public static void forEachBox(BitSet cells, int sizeX, int sizeY, int sizeZ,
                                  DiscreteVoxelShape.IntLineConsumer consumer) {
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
                        consumer.consume(lowX, lowY, runStart, highX + 1, highY + 1, z);
                        runStart = -1;
                    }
                }
            }
        }
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
}
