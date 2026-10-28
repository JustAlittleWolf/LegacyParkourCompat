package me.wolfii.legacyparkourcompat.change.v1_12;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.change.common.PaneCollisionComponents;
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
 * Reproduces 1.12 Block.addCollision's strict per-component overlap admission
 * and Box.intersectX/Y/Z clipping for the ordered pane components. Axis
 * resolution uses the existing ComponentCollisionShape/AxisClippingHook path.
 */
@MovementChange(emulates = ParkourVersion.V1_12)
public final class PaneMovementComponents implements BlockMovementCollisionHook, BlockComponentClipHook {
    private final String blockId;

    @Override
    public String variant() {
        return blockId;
    }

    @MovementChange.Factory
    public static List<PaneMovementComponents> variants() {
        return PaneCollisionComponents.BLOCK_IDS.stream().map(PaneMovementComponents::new).toList();
    }

    public PaneMovementComponents(String blockId) {
        this.blockId = blockId;
    }

    @Override
    public VoxelShape collect(Entity player, BlockPos pos, AABB query, VoxelShape nativeShape,
                              List<VoxelShape> providerParts, @Nullable BlockComponentClipHook componentClip) {
        List<AABB> collected = new ArrayList<>();
        for (VoxelShape part : providerParts) {
            var boxes = part.toAabbs();
            if (boxes.size() != 1) {
                throw new IllegalStateException("Pane provider must supply individual components");
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
            throw new IllegalStateException("Collected pane components require a component clip hook");
        }
        return new ComponentCollisionShape(collected, componentClip);
    }

    @Override
    public double clip(AABB component, Direction.Axis axis, AABB moving, double distance) {
        return StrictAxisClipping.clipBox(component, axis, moving, distance);
    }
}
