package me.wolfii.legacyparkourcompat.change.v1_9;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.change.common.StrictAxisClipping;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.collision.ComponentCollisionShape;
import me.wolfii.legacyparkourcompat.mechanic.hook.collision.BlockComponentClipHook;
import me.wolfii.legacyparkourcompat.mechanic.hook.collision.BlockMovementCollisionHook;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * 1.8.9 and 1.9 through 1.9.4, Ornithe Feather: {@code PistonHeadBlock.addCollisions}
 * submits the plate then the arm. {@code Block.addCollisions} in 1.8.9 and
 * {@code Block.addCollision} from 1.9 test each against the world-space query.
 * {@code Entity.move} clips those boxes with {@code Box.intersectX/Y/Z}, whose
 * strict transverse overlap keeps contacts smaller than 26.2's voxel tolerance.
 * The facing-dependent boxes are supplied by the resolved piston-head shape provider.
 */
@MovementChange(emulates = ParkourVersion.V1_9_3)
public final class PistonHeadMovementComponents implements BlockMovementCollisionHook, BlockComponentClipHook {
    @Override
    public String variant() {
        return "minecraft:piston_head";
    }

    @Override
    public VoxelShape collect(Entity player, BlockPos pos, AABB query, VoxelShape nativeShape,
                              List<VoxelShape> providerParts, @Nullable BlockComponentClipHook componentClip) {
        if (providerParts.size() != 2 || componentClip == null) {
            throw new IllegalStateException("Piston head movement requires a plate, an arm, and a component clip hook");
        }
        List<AABB> collected = new ArrayList<>(providerParts.size());
        for (VoxelShape part : providerParts) {
            List<AABB> boxes = part.toAabbs();
            if (boxes.size() != 1) {
                throw new IllegalStateException("A piston head movement component must supply one box");
            }
            AABB component = boxes.getFirst();
            if (component.move(pos).intersects(query)) {
                collected.add(component);
            }
        }
        if (collected.isEmpty()) {
            return Shapes.empty();
        }
        return new ComponentCollisionShape(collected, componentClip);
    }

    @Override
    public double clip(AABB component, Direction.Axis axis, AABB moving, double distance) {
        return StrictAxisClipping.clipBox(component, axis, moving, distance);
    }
}
