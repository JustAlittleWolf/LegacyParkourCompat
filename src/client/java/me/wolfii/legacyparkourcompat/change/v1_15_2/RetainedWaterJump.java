package me.wolfii.legacyparkourcompat.change.v1_15_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.fluid.RetainedWaterJumpHook;

/**
 * Minecraft 1.15.2 Mojmap, LivingEntity.aiStep reads waterHeight without an
 * isInWater gate. Its water branch negates the exact ground/raw-depth predicate
 * below. LivingEntity.jumpInLiquid adds 0.04F to vertical velocity and does not
 * test or change noJumpDelay; the float is promoted to double by Vec3.add.
 * Minecraft 1.16 Mojmap, LivingEntity.aiStep requires isInWater and selects a
 * tagged depth instead. Entity.updateInWaterStateAndDoFluidPushing clears that
 * depth before refresh, unlike the retained 1.15.2 scalar.
 * Only the missing stale-depth branch is supplied here. Native water, lava,
 * passengers and selections outside the accepted releases remain native.
 * Minecraft 26.2 unobfuscated, LivingEntity.aiStep has the corresponding jump
 * selection phase; LivingEntity.jumpInLiquid still adds the same 0.04F.
 */
@MovementChange(emulates = ParkourVersion.V1_15_2)
public final class RetainedWaterJump implements RetainedWaterJumpHook {
    @Override
    public boolean selectsWaterJump(ParkourVersion selectedVersion, boolean passenger,
                                    boolean inWater, boolean inLava, boolean jumping,
                                    boolean onGround, double retainedWaterDepth) {
        if (selectedVersion.olderThan(ParkourVersion.V1_13)
            || selectedVersion.newerThan(ParkourVersion.V1_15_2)
            || passenger || inWater || inLava || !jumping) {
            return false;
        }
        return !(!(retainedWaterDepth > 0.0)
            || onGround && !(retainedWaterDepth > 0.4));
    }
}
