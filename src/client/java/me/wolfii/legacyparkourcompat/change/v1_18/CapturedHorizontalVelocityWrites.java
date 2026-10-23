package me.wolfii.legacyparkourcompat.change.v1_18;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.collision.HorizontalVelocityWriteHook;
import net.minecraft.world.phys.Vec3;
import java.util.Optional;

/**
 * 1.17.1, 1.18 and 1.18.1 Mojmap Entity.move capture velocity once, then write
 * X and Z separately in that order. The Z write restores captured X, including
 * when the previous write cleared X. 1.18.2 Mojmap Entity.move combines the
 * flags into one write. V1_18 includes both inspected 1.18 and 1.18.1.
 */
@MovementChange(emulates = ParkourVersion.V1_18)
public final class CapturedHorizontalVelocityWrites implements HorizontalVelocityWriteHook {
    @Override
    public Optional<Vec3> clear(Vec3 capturedVelocity, boolean clearX, boolean clearZ) {
        Vec3 result = capturedVelocity;
        if (clearX) {
            result = new Vec3(0.0, capturedVelocity.y, capturedVelocity.z);
        }
        if (clearZ) {
            result = new Vec3(capturedVelocity.x, capturedVelocity.y, 0.0);
        }
        return Optional.of(result);
    }
}
