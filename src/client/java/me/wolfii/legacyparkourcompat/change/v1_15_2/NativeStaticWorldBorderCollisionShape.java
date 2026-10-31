package me.wolfii.legacyparkourcompat.change.v1_15_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.worldborder.StaticWorldBorderCollisionShapeHook;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Optional;

/** Keeps native static-border geometry for profiles below the accepted 1.16 range. */
@MovementChange(emulates = ParkourVersion.V1_15_2)
public final class NativeStaticWorldBorderCollisionShape implements StaticWorldBorderCollisionShapeHook {
    @Override
    public Optional<VoxelShape> collisionShape(double centerX, double centerZ, double size, int absoluteMaxSize) {
        return Optional.empty();
    }
}
