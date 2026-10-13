package me.wolfii.legacyparkourcompat.change.v1_15_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.fluid.LavaCurrentHook;
import net.minecraft.world.entity.Entity;

/**
 * Source evidence (paths are relative to decompiled_minecraft/):
 * 1.15.2/mojmap/net/minecraft/world/entity/Entity.java:
 *   baseTick, updateWaterState; lines [[361, 363], [943, 947]].
 *   Source SHA-256: 191b3ad3e7348c9bac1e703fff896706d23a751bf15162aacf676f5f97c0a10e.
 * 1.16.5/mojmap/net/minecraft/world/entity/Entity.java:
 *   baseTick, updateInWaterStateAndDoFluidPushing; lines [[380, 380], [957, 979]].
 *   updateInWaterStateAndDoFluidPushing; lines [[957, 979]].
 *   Source SHA-256: f9a9a073fe3105a0aa53d0f21ec72e59084e8d21a14c1cd3be75703865ee2666.
 * No exact independently accepted source binding is established for this operation.
 * Source-content correspondence and compilation do not establish tick-level runtime parity.
 */
@MovementChange(emulates = ParkourVersion.V1_15_2)
public final class LavaCurrent implements LavaCurrentHook {
    @Override
    public boolean allowLavaCurrent(Entity entity, boolean vanilla) {
        return false;
    }
}
