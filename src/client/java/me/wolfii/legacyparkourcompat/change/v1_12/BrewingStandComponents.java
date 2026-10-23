package me.wolfii.legacyparkourcompat.change.v1_12;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.change.common.StrictAxisClipping;
import me.wolfii.legacyparkourcompat.mechanic.hook.collision.BlockComponentClipHook;
import me.wolfii.legacyparkourcompat.mechanic.collision.ComponentCollisionShape;
import me.wolfii.legacyparkourcompat.mechanic.hook.collision.BlockMovementCollisionHook;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.ArrayList;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * 1.12.2 ornithe-feather: BrewingStandBlock.addCollisions emits COLLISION_SHAPE then SHAPE;
 * Block.addCollision/Box.intersects collect strict overlapping components, and
 * Box.intersectX/intersectY/intersectZ clip each component independently.
 * 26.2 unobfuscated: BrewingStandBlock.getShape supplies a voxel union, collected
 * by BlockCollisions.computeNext and clipped by VoxelShape.collide/collideX.
 * The union's internal planes must not replace the historical component faces.
 * This source representative backs the grouped 1.12 profile only; the 1.11.2
 * collector fence preserves the earlier geometry implementation without
 * extending this component-response evidence backwards. The accepted witness
 * is an already embedded default standing player, not a natural-entry or
 * whole-tick parity claim. Ordered parts come from BrewingStandCollisionShape.
 */
@MovementChange(emulates = ParkourVersion.V1_12)
public final class BrewingStandComponents implements BlockMovementCollisionHook, BlockComponentClipHook {
    @Override public String variant() { return "minecraft:brewing_stand"; }

    @Override
    public VoxelShape collect(Entity player, BlockPos pos, AABB query, VoxelShape nativeShape,
                              List<VoxelShape> providerParts, @Nullable BlockComponentClipHook componentClip) {
        List<AABB> collected = new ArrayList<>();
        for (VoxelShape part : providerParts) {
            var boxes = part.toAabbs();
            if (boxes.size() != 1) {
                throw new IllegalStateException("Brewing stand provider must supply individual components");
            }
            AABB component = boxes.getFirst();
            if (component.move(pos).intersects(query)) {
                collected.add(component);
            }
        }
        if (collected.isEmpty()) {
            return Shapes.empty();
        }
        if (componentClip == null) {
            throw new IllegalStateException("Collected block components require a component clip hook");
        }
        return new ComponentCollisionShape(collected, componentClip);
    }

    @Override
    public double clip(AABB component, Direction.Axis axis, AABB moving, double distance) {
        return StrictAxisClipping.clipBox(component, axis, moving, distance);
    }
}
