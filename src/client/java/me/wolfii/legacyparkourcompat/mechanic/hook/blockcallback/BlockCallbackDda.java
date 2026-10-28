package me.wolfii.legacyparkourcompat.mechanic.hook.blockcallback;

import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.Optional;

/** Shared implementation of the source-closed swept block-position traversal. */
public final class BlockCallbackDda {
    private BlockCallbackDda() {}

    public static Optional<Boolean> visitSegment(Vec3 from, Vec3 to, AABB target, BlockGetter.BlockStepVisitor visitor) {
        Vec3 travel = to.subtract(from);
        if (travel.lengthSqr() < Mth.square(0.99999F)) {
            for (BlockPos pos : BlockPos.betweenClosed(target)) {
                if (!visitor.visit(pos, 0)) return Optional.of(false);
            }
            return Optional.of(true);
        }
        LongSet visited = new LongOpenHashSet();
        Vec3 minTo = target.getMinPosition();
        Vec3 minFrom = minTo.subtract(travel);
        int attempts = alongTravel(visited, minFrom, minTo, target, visitor);
        if (attempts < 0) return Optional.of(false);
        for (BlockPos pos : BlockPos.betweenClosed(target)) {
            if (!visited.contains(pos.asLong()) && !visitor.visit(pos, attempts + 1)) return Optional.of(false);
        }
        return Optional.of(true);
    }

    private static int alongTravel(LongSet visited, Vec3 from, Vec3 to, AABB target, BlockGetter.BlockStepVisitor visitor) {
        Vec3 travel = to.subtract(from);
        int x = Mth.floor(from.x), y = Mth.floor(from.y), z = Mth.floor(from.z);
        int signX = Mth.sign(travel.x), signY = Mth.sign(travel.y), signZ = Mth.sign(travel.z);
        double dx = signX == 0 ? Double.MAX_VALUE : signX / travel.x;
        double dy = signY == 0 ? Double.MAX_VALUE : signY / travel.y;
        double dz = signZ == 0 ? Double.MAX_VALUE : signZ / travel.z;
        double tx = dx * (signX > 0 ? 1.0 - Mth.frac(from.x) : Mth.frac(from.x));
        double ty = dy * (signY > 0 ? 1.0 - Mth.frac(from.y) : Mth.frac(from.y));
        double tz = dz * (signZ > 0 ? 1.0 - Mth.frac(from.z) : Mth.frac(from.z));
        int attempts = 0;
        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
        while (tx <= 1.0 || ty <= 1.0 || tz <= 1.0) {
            if (tx < ty) {
                if (tx < tz) { x += signX; tx += dx; }
                else { z += signZ; tz += dz; }
            } else if (ty < tz) { y += signY; ty += dy; }
            else { z += signZ; tz += dz; }
            if (attempts++ > 16) break;
            Optional<Vec3> hit = AABB.clip(x, y, z, x + 1, y + 1, z + 1, from, to);
            if (hit.isPresent()) {
                Vec3 point = hit.get();
                double px = Mth.clamp(point.x, x + 1.0E-5F, x + 1.0 - 1.0E-5F);
                double py = Mth.clamp(point.y, y + 1.0E-5F, y + 1.0 - 1.0E-5F);
                double pz = Mth.clamp(point.z, z + 1.0E-5F, z + 1.0 - 1.0E-5F);
                int maxX = Mth.floor(px + target.getXsize());
                int maxY = Mth.floor(py + target.getYsize());
                int maxZ = Mth.floor(pz + target.getZsize());
                for (int bx = x; bx <= maxX; bx++) {
                    for (int by = y; by <= maxY; by++) {
                        for (int bz = z; bz <= maxZ; bz++) {
                            if (visited.add(BlockPos.asLong(bx, by, bz)) && !visitor.visit(pos.set(bx, by, bz), attempts)) return -1;
                        }
                    }
                }
            }
        }
        return attempts;
    }
}
