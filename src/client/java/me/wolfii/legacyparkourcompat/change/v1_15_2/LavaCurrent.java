package me.wolfii.legacyparkourcompat.change.v1_15_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.fluid.LavaCurrentHook;
import net.minecraft.world.entity.Entity;

/**
 * Source evidence (paths are relative to decompiled_minecraft/):
 * 1.15.2/mojmap/net/minecraft/world/entity/Entity.java:
 *   baseTick, updateWaterState.
 * 1.16.5/mojmap/net/minecraft/world/entity/Entity.java:
 *   baseTick, updateInWaterStateAndDoFluidPushing.
 *   updateInWaterStateAndDoFluidPushing.
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
