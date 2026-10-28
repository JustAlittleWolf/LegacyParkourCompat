package me.wolfii.legacyparkourcompat.change.v1_8_9;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.water.WaterBodyContactAreaAdmissionHook;

/** Restores the 1.8.0-1.8.9 client vertical admission for body-contact scans. */
@MovementChange(emulates = ParkourVersion.V1_8_9)
public final class LegacyWaterBodyContactAreaAdmission implements WaterBodyContactAreaAdmissionHook {
    public LegacyWaterBodyContactAreaAdmission() {
    }

    /*
     * Exact bounded source comparison:
     * Raw 1.8.0/1.8.1 clients mapped by Feather Gen2 build 2 Tiny and the
     * byte-identical Ornithe Feather World.java and ClientChunkCache.java files
     * for 1.8.2-1.8.9 use World.applyLiquidDrag(AABB,Material,Entity)Z with
     * allowEmpty=true. World.isAreaLoaded(IIIIII,boolean)Z admits maxY >= 0 &&
     * minY < 256; the matching 1.8.8 source is World.java:1486-1495, 205-228.
     * ClientChunkCache.hasChunk(II)Z is always true, so missing X/Z chunks read
     * through the empty-chunk path instead of rejecting. Resolver applicability
     * uses this provider through V1_8_9; flow-scan admission is independent.
     */
    @Override
    public boolean admits(int minBlockX, int minBlockY, int minBlockZ,
                          int maxBlockX, int maxBlockY, int maxBlockZ) {
        return maxBlockY >= 0 && minBlockY < 256;
    }
}
