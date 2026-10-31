package me.wolfii.legacyparkourcompat.mechanic.hook.border;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import java.util.Optional;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.VoxelShape;

@MechanicType("border.collision_admission")
public interface BorderCollisionAdmissionHook extends VersionedMechanic {
    /**
     * Evaluates the admission gate against the collision geometry selected by
     * the runtime; the same historical static shape is carried to collection.
     */
    Optional<Boolean> includeBorder(ParkourVersion selected, WorldBorder border, AABB currentBox,
                                    VoxelShape collisionShape);
}
