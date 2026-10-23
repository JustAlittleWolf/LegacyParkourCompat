package me.wolfii.legacyparkourcompat.mechanic.hook.collision;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import net.minecraft.world.phys.Vec3;
import java.util.Optional;

/** Adjusts the otherwise-empty native solver after historical border admission. */
@MechanicType("collision.empty_shape_movement")
public interface EmptyShapeMovementHook extends VersionedMechanic {
    Optional<Vec3> resolve(Vec3 requested);
}
