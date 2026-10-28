package me.wolfii.legacyparkourcompat.change.v1_21_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.blockcallback.PistonCallbackScanOriginHook;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

/** Keeps native piston callback scan origins through 1.21.3. */
@MovementChange(emulates = ParkourVersion.V1_21_2)
public final class NativePistonCallbackScanOrigin implements PistonCallbackScanOriginHook {
    @Override
    public Vec3 scanOrigin(Entity entity, Vec3 currentOrigin) {
        return currentOrigin;
    }
}
