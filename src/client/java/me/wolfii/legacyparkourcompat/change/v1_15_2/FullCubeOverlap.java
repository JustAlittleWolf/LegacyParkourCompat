package me.wolfii.legacyparkourcompat.change.v1_15_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.VanillaFn;
import me.wolfii.legacyparkourcompat.mechanic.hook.collision.FullCubeOverlapHook;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;

/**
 * Mojmap 1.15.2 CollisionGetter#getBlockCollisions intersects each translated block
 * shape with Shapes.create(query) via Shapes.joinIsNotEmpty(AND). Mojmap 1.16.5
 * CollisionSpliterator#collisionCheck takes the AABB.intersects fast path when the
 * shape is Shapes.block(); 26.2 unobfuscated BlockCollisions#computeNext retains
 * that strict full-cube branch. The query shape remains the first BooleanOp operand.
 */
@MovementChange(emulates = ParkourVersion.V1_15_2)
public final class FullCubeOverlap implements FullCubeOverlapHook {
    @Override
    public boolean overlaps(AABB query, double x, double y, double z, double maxX, double maxY, double maxZ,
                            VanillaFn<Boolean> vanilla) {
        return Shapes.joinIsNotEmpty(
            Shapes.create(query),
            Shapes.block().move(x, y, z),
            BooleanOp.AND
        );
    }
}
