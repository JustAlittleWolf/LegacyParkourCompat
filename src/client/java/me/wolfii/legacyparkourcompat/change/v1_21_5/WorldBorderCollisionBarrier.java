package me.wolfii.legacyparkourcompat.change.v1_21_5;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.border.WorldBorderCollisionHook;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.border.WorldBorder;
import net.minecraft.world.phys.AABB;
import org.jspecify.annotations.Nullable;

/** Keeps native border collision for 1.21.5; elapsed-time emulation starts in 1.21.6. */
@MovementChange(emulates = ParkourVersion.V1_21_5)
public final class WorldBorderCollisionBarrier implements WorldBorderCollisionHook {
    @Override
    public @Nullable Collision collision(WorldBorder border, Entity entity, AABB box) {
        return null;
    }
}
