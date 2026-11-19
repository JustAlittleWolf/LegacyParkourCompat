package me.wolfii.legacyparkourcompat.mechanic.hook.border;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jspecify.annotations.Nullable;

import java.util.function.Supplier;

/** Resolves the moving-border clock for admission and for the later shape read. */
@MechanicType("border.collision_clock")
public interface WorldBorderCollisionHook extends VersionedMechanic {
    @Nullable Collision collision(WorldBorder border, Entity entity, AABB box);

    record Collision(boolean insideCloseToBorder, Supplier<VoxelShape> shape) {
    }
}
