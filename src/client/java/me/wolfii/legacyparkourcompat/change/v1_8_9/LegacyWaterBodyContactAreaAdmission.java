package me.wolfii.legacyparkourcompat.change.v1_8_9;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.water.WaterBodyContactAreaAdmissionHook;

/** Restores the 1.8.9 client vertical admission for the body-contact scan. */
@MovementChange(emulates = ParkourVersion.V1_8_9)
public final class LegacyWaterBodyContactAreaAdmission implements WaterBodyContactAreaAdmissionHook {
    public LegacyWaterBodyContactAreaAdmission() {
    }

    /*
     * Exact source comparison:
     * 1.8.9 Ornithe Feather / Feather Gen2 build 2,
     * World.applyLiquidDrag (World.java:1486-1494) passes its bounds to
     * World.isAreaLoaded (World.java:205-224), which admits maxY >= 0 &&
     * minY < 256. ClientChunkCache.hasChunk (ClientChunkCache.java:31) always
     * returns true, and allowEmpty reads use the empty-chunk path
     * (ClientChunkCache.java:54-57), so X/Z do not reject. The 1.9 Feather
     * implementation retains this World.isAreaLoaded gate (World.java:210-229).
     * This policy is separate from flow-scan admission.
     */
    @Override
    public boolean admits(int minBlockX, int minBlockY, int minBlockZ,
                          int maxBlockX, int maxBlockY, int maxBlockZ) {
        return maxBlockY >= 0 && minBlockY < 256;
    }
}
