package me.wolfii.legacyparkourcompat.mechanic.hook.bed;

import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;
import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.CollisionGetter;
import net.minecraft.world.phys.Vec3;

import java.util.Optional;

/** Selects the stand-up point used when the local player wakes from a bed. */
@MechanicType("player.bed_wake_position")
public interface BedWakePositionHook extends VersionedMechanic {
    /**
     * Returns the profile's stand-up point and marks whether the historical
     * algorithm was supported. The MovementRuntime caller performs whole-query
     * native fallback after restoring its scoped entity context.
     */
    Result findStandUpPosition(
        Entity entity,
        EntityType<?> entityType,
        CollisionGetter level,
        BlockPos bedPosition
    );

    record Result(boolean supported, Optional<Vec3> position) {
        public static Result unsupported() {
            return new Result(false, Optional.empty());
        }

        public static Result of(Optional<Vec3> position) {
            return new Result(true, position);
        }
    }
}
