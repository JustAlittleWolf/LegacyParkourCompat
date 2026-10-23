package me.wolfii.legacyparkourcompat.mechanic.hook.position;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.phys.AABB;

import java.util.Optional;

@MechanicType("player.pose_box")
public interface PoseBoxHook extends VersionedMechanic {
    default Optional<AABB> poseBox(Entity entity, EntityDimensions previous, EntityDimensions next) {
        return Optional.empty();
    }
}
