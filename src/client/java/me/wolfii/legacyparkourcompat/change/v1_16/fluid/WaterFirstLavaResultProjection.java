package me.wolfii.legacyparkourcompat.change.v1_16.fluid;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.fluid.LavaResultProjectionHook;

/**
 * Projects away lava movement results when the old base-tick WATER probe hit.
 *
 * <p>In Mojmap 1.16 and 1.16.1, {@code Entity.updateInWaterStateAndDoFluidPushing()}
 * returns before sampling LAVA when {@code Entity.isInWater()} is true after
 * {@code Entity.updateInWaterStateAndDoWaterCurrentPushing()} has performed
 * its WATER update. The WATER helper skips its probe for a Boat passenger.
 * Therefore the caller must pass the exact WATER presence-probe result for
 * the eligible client Player, not a modern water predicate, and retain that
 * baseTick result through later fluid refreshes until the next baseTick. The
 * projection can gate both public LAVA depth exposure and LAVA current
 * application; it does not implement either call site or the probe.
 *
 * <p>Keep tracker eye state, the internal {@code EntityFluidInteraction.isInFluid}
 * value used by the separate {@code Entity.isInLava()} path, and WATER depth
 * untouched. Mojmap 1.16.2 {@code Entity.updateInWaterStateAndDoFluidPushing()}
 * samples LAVA after WATER unconditionally, so the V1_16 boundary leaves
 * 1.16.2 and later profiles on native behavior.
 *
 * @see <a href="Mojmap 1.16 and 1.16.1: net.minecraft.world.entity.Entity#updateInWaterStateAndDoFluidPushing, Entity#updateInWaterStateAndDoWaterCurrentPushing">Mojmap Entity fluid update and WATER helper</a>
 * @see <a href="Mojmap 1.16.2: net.minecraft.world.entity.Entity#updateInWaterStateAndDoFluidPushing">Mojmap 1.16.2 Entity fluid update</a>
 */
@MovementChange(emulates = ParkourVersion.V1_16)
public final class WaterFirstLavaResultProjection implements LavaResultProjectionHook {
    @Override
    public boolean allowLavaResults(boolean legacyWaterPresenceProbeResult) {
        return !legacyWaterPresenceProbeResult;
    }
}
