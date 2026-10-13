package me.wolfii.legacyparkourcompat.mechanic.hook.border;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import java.util.Optional;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.phys.AABB;

@MechanicType("border.collision_admission")
public interface BorderCollisionAdmissionHook extends VersionedMechanic {
    Optional<Boolean> includeBorder(ParkourVersion selected, WorldBorder border, AABB currentBox);
}
