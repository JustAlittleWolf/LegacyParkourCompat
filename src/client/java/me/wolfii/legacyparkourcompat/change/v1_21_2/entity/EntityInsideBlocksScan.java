package me.wolfii.legacyparkourcompat.change.v1_21_2.entity;

import it.unimi.dsi.fastutil.objects.ObjectLinkedOpenHashSet;
import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.entity.EntityInsideBlocksScanHook;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiConsumer;

/**
 * Source-derived 1.21.2/1.21.3 recorded-movement callback scan traversal.
 *
 * <p>Sources: 1.21.2 and 1.21.3 Mojmap Entity.applyEffectsFromBlocks and
 * Entity.checkInsideBlocks, plus BlockGetter.boxTraverseBlocks and
 * addCollisionsAlongTravel. This is the independently reviewed neutral candidate
 * from development/research/source-evidence/20261029/shared-query-box; its
 * accepted scope is candidate traversal only, not the per-position admission gate.
 */
@MovementChange(emulates = ParkourVersion.V1_21_2)
public final class EntityInsideBlocksScan implements EntityInsideBlocksScanHook {
    @Override
    public <M> void scanMovements(AABB finalEntityBox, List<M> movements, BiConsumer<M, AABB> scanMovement) {
        AABB queryBox = finalEntityBox.deflate(1.0E-5F);
        for (M movement : movements) {
            scanMovement.accept(movement, queryBox);
        }
        movements.clear();
    }

    @Override
    public Iterable<BlockPos> candidateCells(Vec3 from, Vec3 to, AABB queryBox) {
        Vec3 delta = to.subtract(from);
        Iterable<BlockPos> candidates = BlockPos.betweenClosed(queryBox);
        if (delta.lengthSqr() < Mth.square(0.99999F)) {
            return candidates;
        }

        Set<BlockPos> traversed = new ObjectLinkedOpenHashSet<>();
        Vec3 epsilon = delta.normalize().scale(1.0E-7);
        Vec3 traversalStart = queryBox.getMinPosition().subtract(delta).subtract(epsilon);
        Vec3 traversalEnd = queryBox.getMinPosition().add(epsilon);
        addCollisionsAlongTravel(traversed, traversalStart, traversalEnd, queryBox);

        for (BlockPos candidate : candidates) {
            traversed.add(candidate.immutable());
        }
        return traversed;
    }

    private static void addCollisionsAlongTravel(Set<BlockPos> candidates, Vec3 start, Vec3 end, AABB box) {
        Vec3 delta = end.subtract(start);
        int x = Mth.floor(start.x);
        int y = Mth.floor(start.y);
        int z = Mth.floor(start.z);
        int stepX = Mth.sign(delta.x);
        int stepY = Mth.sign(delta.y);
        int stepZ = Mth.sign(delta.z);
        double stepDeltaX = stepX == 0 ? Double.MAX_VALUE : stepX / delta.x;
        double stepDeltaY = stepY == 0 ? Double.MAX_VALUE : stepY / delta.y;
        double stepDeltaZ = stepZ == 0 ? Double.MAX_VALUE : stepZ / delta.z;
        double nextX = stepDeltaX * (stepX > 0 ? 1.0 - Mth.frac(start.x) : Mth.frac(start.x));
        double nextY = stepDeltaY * (stepY > 0 ? 1.0 - Mth.frac(start.y) : Mth.frac(start.y));
        double nextZ = stepDeltaZ * (stepZ > 0 ? 1.0 - Mth.frac(start.z) : Mth.frac(start.z));
        int steps = 0;

        while (nextX <= 1.0 || nextY <= 1.0 || nextZ <= 1.0) {
            if (nextX < nextY) {
                if (nextX < nextZ) {
                    x += stepX;
                    nextX += stepDeltaX;
                } else {
                    z += stepZ;
                    nextZ += stepDeltaZ;
                }
            } else if (nextY < nextZ) {
                y += stepY;
                nextY += stepDeltaY;
            } else {
                z += stepZ;
                nextZ += stepDeltaZ;
            }

            if (steps++ > 16) {
                break;
            }

            Optional<Vec3> clipped = AABB.clip(x, y, z, x + 1, y + 1, z + 1, start, end);
            if (clipped.isPresent()) {
                Vec3 intersection = clipped.get();
                double minX = Mth.clamp(intersection.x, x + 1.0E-5F, x + 1.0 - 1.0E-5F);
                double minY = Mth.clamp(intersection.y, y + 1.0E-5F, y + 1.0 - 1.0E-5F);
                double minZ = Mth.clamp(intersection.z, z + 1.0E-5F, z + 1.0 - 1.0E-5F);
                int maxX = Mth.floor(minX + box.getXsize());
                int maxY = Mth.floor(minY + box.getYsize());
                int maxZ = Mth.floor(minZ + box.getZsize());

                for (int candidateX = x; candidateX <= maxX; candidateX++) {
                    for (int candidateY = y; candidateY <= maxY; candidateY++) {
                        for (int candidateZ = z; candidateZ <= maxZ; candidateZ++) {
                            candidates.add(new BlockPos(candidateX, candidateY, candidateZ));
                        }
                    }
                }
            }
        }
    }
}
