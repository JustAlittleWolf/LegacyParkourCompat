package me.wolfii.legacyparkourcompat.change.v1_13_1;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.fluid.BodyWaterScanAdmissionHook;

/**
 * 1.13, 1.13.1 and 1.13.2 Ornithe Feather: Entity.m_69693160 scans half-open
 * bounds after WorldView.isAreaLoaded invokes ClientWorld.isChunkLoaded with
 * allowEmpty=true. A neighboring chunk touched only by the area guard does
 * not reject the loaded WATER cell. In 26.2 unobfuscated EntityFluidInteraction.update,
 * hasFluidAndLoaded can reject that cell because its horizontal query is padded.
 * Restore only the proven loaded-column body query inside the old build height.
 */
@MovementChange(emulates = ParkourVersion.V1_13_1)
public final class BodyWaterScanAdmission implements BodyWaterScanAdmissionHook {
    @Override
    public boolean admitsColumn(int minY, int maxY) {
        return minY >= 0 && maxY < 256;
    }
}
