package me.wolfii.legacyparkourcompat.change.v1_13;

import me.wolfii.legacyparkourcompat.api.ParkourVersion;
import me.wolfii.legacyparkourcompat.mechanic.MovementChange;
import me.wolfii.legacyparkourcompat.mechanic.hook.travel.UnloadedChunkGravityHook;

/**
 * Ornithe Feather 1.13.2 LivingEntity#travel checks the chunk at (x, 0, z)
 * with WorldView#isChunkLoaded and WorldChunk#isLoaded before applying normal
 * gravity; a missing chunk instead sets vertical velocity to -0.1 above Y=0
 * or zero at/below it. Mojmap 1.14.4 LivingEntity#travel sees
 * MultiPlayerLevel#hasChunk, which always returns true. Unobfuscated 26.2
 * LivingEntity#travelInAir has the same always-true client hasChunkAt gate;
 * Level#isLoaded reaches ChunkSource#hasChunk, and
 * ClientChunkCache#getChunk(..., false) returns null when that client chunk is
 * absent. This restores only the reachable gravity fallback; it does not alter
 * player tick or chunk-registration gates. For an already registered player
 * with inChunk still true while entering a missing neighbor, the 1.13.2
 * World#tickEntity requireLoaded gate still invokes tick before travel; the
 * empty chunk does not remove that player or clear the existing registration.
 */
@MovementChange(emulates = ParkourVersion.V1_13)
public final class UnloadedChunkGravity implements UnloadedChunkGravityHook {
    @Override
    public boolean chunkIsLoaded(boolean nativeHasChunk, boolean clientChunkIsLoaded) {
        return nativeHasChunk && clientChunkIsLoaded;
    }
}
