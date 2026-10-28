package me.wolfii.legacyparkourcompat.change.v1_8_9;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.world.FlowScanAreaAdmissionHook;

/**
 * Reproduces 1.8.9's client-side vertical admission for the liquid flow scan.
 *
 * <p>In the Ornithe Feather (Feather Gen2 build 2) 1.8.9 sources,
 * {@code net.minecraft.world.World.applyLiquidDrag(Box,
 * Material, Entity)} passes the computed integer bounds to
 * {@code World.isAreaLoaded(int, int, int, int, int, int, boolean)}; the latter
 * rejects only {@code maxY < 0 || minY >= 256} before its horizontal chunk loop
 * (World.java:1486-1494, 205-224). Client {@code ClientChunkCache.hasChunk(int,
 * int)} returns true unconditionally and {@code getChunk(int, int, boolean)}
 * returns the empty chunk when absent, so that loop cannot reject horizontal
 * coordinates (ClientChunkCache.java:31, 54-57).
 *
 * <p>The adjacent 1.9.4 Ornithe Feather sources retain the same Y-overlap test
 * in {@code World.isAreaLoaded} and pass it from {@code World.applyLiquidDrag}
 * (World.java:216-235, 1560-1602). In current 26.2 unobfuscated sources,
 * {@code EntityFluidInteraction.hasFluidAndLoaded} instead requires FULL chunks
 * and fluid in a scanned section (EntityFluidInteraction.java:91-113), while
 * {@code ClientChunkCache.getChunk} supplies an empty chunk only on its loading
 * path (ClientChunkCache.java:69-80). Integration must preserve the old client
 * empty-chunk reads and must not make historical flow application depend on the
 * current tracker preflight or water-contact gate. Apply the already-computed
 * historical flow result independently of both current gates.
 */
@MovementChange(emulates = ParkourVersion.V1_8_9)
public final class LegacyFlowScanAreaAdmission implements FlowScanAreaAdmissionHook {
    public LegacyFlowScanAreaAdmission() {
    }

    @Override
    public boolean admits(int minBlockX, int minBlockY, int minBlockZ,
                          int maxBlockX, int maxBlockY, int maxBlockZ) {
        return maxBlockY >= 0 && minBlockY < 256;
    }
}
