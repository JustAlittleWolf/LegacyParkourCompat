package me.wolfii.legacyparkourcompat.change.v1_11_2;

import java.util.OptionalDouble;
import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.change.common.StrictAxisClipping;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.collision.AxisClippingHook;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.VoxelShape;

/**
 * Retains the existing stone-only strict clipping for 1.11.2 and earlier profiles.
 * The independent default-provider geometry proof compares Ornithe Feather
 * 1.12.2 Block.addCollisions/getCollisionShape/getShape with 26.2 unobfuscated
 * BlockBehaviour.getCollisionShape/getShape and Blocks registrations; it does
 * not establish earlier provider registration/state coverage. The common helper
 * preserves the existing 1.12.2 Box.intersectX/intersectY/intersectZ math here;
 * this retention fence makes no new source-fidelity claim for earlier profiles.
 */
@MovementChange(emulates = ParkourVersion.V1_11_2)
public final class AxisClipping implements AxisClippingHook {
    @Override
    public OptionalDouble clip(Direction.Axis axis, AABB moving, Iterable<VoxelShape> shapes, double distance) {
        return StrictAxisClipping.clip(axis, moving, shapes, distance, state -> state.is(Blocks.STONE));
    }
}