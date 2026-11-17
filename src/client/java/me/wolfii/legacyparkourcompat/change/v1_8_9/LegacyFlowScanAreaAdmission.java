package me.wolfii.legacyparkourcompat.change.v1_8_9;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.world.FlowScanAreaAdmissionHook;

/**
 * Reproduces the 1.8–1.9.4 client-side vertical admission for the liquid flow scan.
 *
 * <p>In raw 1.8.0/1.8.1 clients mapped with Feather Gen2 build 2 Tiny and the
 * byte-identical Ornithe Feather sources from 1.8.2 through 1.8.9,
 * {@code net.minecraft.world.World.applyLiquidDrag(Box,
 * Material, Entity)} passes the computed integer bounds to
 * {@code World.isAreaLoaded(int, int, int, int, int, int, boolean)}; the latter
 * rejects only {@code maxY < 0 || minY >= 256} before its horizontal chunk loop
 * (World.java:1486-1494, 205-224). Client {@code ClientChunkCache.hasChunk(int,
 * int)} returns true unconditionally and {@code getChunk(int, int, boolean)}
 * returns the empty chunk when absent, so that loop cannot reject horizontal
 * coordinates (ClientChunkCache.java:31, 54-57).
 *
 * <p>Ornithe Feather 1.9 through 1.9.4 {@code World.applyLiquidDrag} still
 * passes {@code allowEmpty = true} to {@code World.isAreaLoaded}, and
 * {@code ClientWorld.isChunkLoadedAt} accepts every column with that flag.
 * The same Y-overlap rule therefore applies to the 1.9 scans after their
 * exclusive upper bounds switch to ceil. In 26.2 unobfuscated sources,
 * {@code EntityFluidInteraction.hasFluidAndLoaded} instead requires FULL chunks
 * and fluid in a scanned section (EntityFluidInteraction.java:91-113), while
 * {@code ClientChunkCache.getChunk} supplies an empty chunk only on its loading
 * path (ClientChunkCache.java:69-80). Integration must preserve the old client
 * empty-chunk reads and must not make historical flow application depend on the
 * current tracker preflight or water-contact gate. Apply the already-computed
 * historical flow result independently of both current gates.
 */
@MovementChange(emulates = ParkourVersion.V1_9_3)
public final class LegacyFlowScanAreaAdmission implements FlowScanAreaAdmissionHook {
    public LegacyFlowScanAreaAdmission() {
    }

    @Override
    public boolean admits(int minBlockX, int minBlockY, int minBlockZ,
                          int maxBlockX, int maxBlockY, int maxBlockZ) {
        return maxBlockY >= 0 && minBlockY < 256;
    }
}
