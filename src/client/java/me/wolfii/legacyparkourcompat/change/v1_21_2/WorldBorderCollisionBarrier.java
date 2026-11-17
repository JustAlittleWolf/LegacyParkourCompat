package me.wolfii.legacyparkourcompat.change.v1_21_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.border.WorldBorderCollisionHook;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.phys.AABB;
import org.jspecify.annotations.Nullable;

/** Keeps the earlier native fallback below the verified 1.21.4 elapsed-time range. */
@MovementChange(emulates = ParkourVersion.V1_21_2)
public final class WorldBorderCollisionBarrier implements WorldBorderCollisionHook {
    @Override
    public @Nullable Collision collision(WorldBorder border, Entity entity, AABB box) {
        return null;
    }
}
