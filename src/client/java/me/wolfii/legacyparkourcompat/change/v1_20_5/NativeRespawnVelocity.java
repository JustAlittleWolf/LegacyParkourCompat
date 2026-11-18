package me.wolfii.legacyparkourcompat.change.v1_20_5;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.player.RespawnVelocityHook;
import net.minecraft.world.phys.Vec3;

/**
 * Keeps the 26.2 unobfuscated ClientPacketListener#handleRespawn velocity copy for
 * selections before the independently accepted 1.21 and 1.21.1 Mojmap range.
 */
@MovementChange(emulates = ParkourVersion.V1_20_5)
public final class NativeRespawnVelocity implements RespawnVelocityHook {
    @Override
    public Vec3 replacementVelocity(Vec3 vanillaVelocity) {
        return vanillaVelocity;
    }
}
