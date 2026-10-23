package me.wolfii.legacyparkourcompat.change.v1_13;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.collision.HorizontalVelocityWriteHook;
import net.minecraft.world.phys.Vec3;
import java.util.Optional;

/**
 * 1.13.2 Ornithe Feather Entity.move clears velocityX and velocityZ independently.
 * 1.14 Ornithe Feather Entity.move and 1.14.4 Mojmap Entity.move capture the
 * velocity once; a later Z-clear restores the captured X. Override that later
 * operation for the old profiles, including when both axes are clipped.
 */
@MovementChange(emulates = ParkourVersion.V1_13)
public final class IndependentHorizontalVelocityWrites implements HorizontalVelocityWriteHook {
    @Override
    public Optional<Vec3> clear(Vec3 captured, boolean clearX, boolean clearZ) {
        return Optional.of(new Vec3(clearX ? 0.0 : captured.x, captured.y, clearZ ? 0.0 : captured.z));
    }
}
