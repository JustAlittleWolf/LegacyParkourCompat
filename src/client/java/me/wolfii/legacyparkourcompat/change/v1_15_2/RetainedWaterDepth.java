package me.wolfii.legacyparkourcompat.change.v1_15_2;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.fluid.RetainedWaterDepthHook;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.AABB;

/**
 * Minecraft 1.15.2 Mojmap, Entity.checkAndHandleWater writes waterHeight only
 * after a completed scan; LevelReader.hasChunksAt rejects ceil(maxY) < 0 or
 * floor(minY) >= 256 after Entity's bounding-box deflation by 0.001.
 * Minecraft 1.16 Mojmap, Entity.updateInWaterStateAndDoFluidPushing clears
 * fluidHeight before updateFluidHeightAndDoFluidPushing can reject the query.
 * This bounded delta retains the old scalar for that fixed vertical rejection.
 * ClientLevel.hasChunk in Minecraft 1.15.2 Mojmap returns true, so this is not
 * an unloaded-neighbor query. The literal bounds are not modern dimension bounds.
 * Other fluid scan arithmetic and fluid pushing remain native.
 * Minecraft 26.2 unobfuscated, Entity.updateFluidInteraction completes the
 * native update and publishes wasTouchingWater; Entity.getFluidHeight supplies
 * the accepted WATER depth. Read that value after the original refresh returns.
 */
@MovementChange(emulates = ParkourVersion.V1_15_2)
public final class RetainedWaterDepth implements RetainedWaterDepthHook {
    @Override
    public double afterWaterRefresh(ParkourVersion selectedVersion, boolean passenger,
                                    AABB rawBox, double previousDepth, double nativeWaterDepth) {
        if (selectedVersion.olderThan(ParkourVersion.V1_13)
            || selectedVersion.newerThan(ParkourVersion.V1_15_2) || passenger) {
            return nativeWaterDepth;
        }
        AABB queryBox = rawBox.deflate(0.001);
        int minY = Mth.floor(queryBox.minY);
        int maxY = Mth.ceil(queryBox.maxY);
        if (maxY >= 0 && minY < 256) {
            return nativeWaterDepth;
        }
        return previousDepth;
    }
}
