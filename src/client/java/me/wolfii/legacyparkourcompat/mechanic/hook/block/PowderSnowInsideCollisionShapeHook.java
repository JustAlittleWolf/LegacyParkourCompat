package me.wolfii.legacyparkourcompat.mechanic.hook.block;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.phys.shapes.VoxelShape;

@MechanicType("block.powder_snow.inside_collision_shape")
public interface PowderSnowInsideCollisionShapeHook extends VersionedMechanic {
    VoxelShape entityInsideCollisionShape(ParkourVersion selected, VoxelShape vanilla);
}
