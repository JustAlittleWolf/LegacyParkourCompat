package me.wolfii.legacyparkourcompat.change.v1_14_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.collision.FullCubeOverlapHook;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;

/**
 * Preserves the product's previous query-shape operation for selections at or
 * before V1_14_2. This is a product preservation entry, not a source-parity
 * claim for every lower profile. Exact 1.14.4 Mojmap
 * LevelReader.getBlockCollisions also uses Shapes.create and joinIsNotEmpty(AND).
 */
@MovementChange(emulates = ParkourVersion.V1_14_2)
public final class PreservedFullCubeOverlap implements FullCubeOverlapHook {
    @Override
    public boolean overlaps(AABB query, int blockX, int blockY, int blockZ) {
        return Shapes.joinIsNotEmpty(
            Shapes.create(query),
            Shapes.block().move(blockX, blockY, blockZ),
            BooleanOp.AND
        );
    }
}
