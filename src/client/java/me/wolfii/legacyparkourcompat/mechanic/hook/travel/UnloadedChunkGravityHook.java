package me.wolfii.legacyparkourcompat.mechanic.hook.travel;

import me.wolfii.legacyparkourcompat.mechanic.MechanicType;
import me.wolfii.legacyparkourcompat.mechanic.VersionedMechanic;

@MechanicType("player.travel.unloaded_chunk_gravity")
public interface UnloadedChunkGravityHook extends VersionedMechanic {
    boolean chunkIsLoaded(boolean nativeHasChunk, boolean clientChunkIsLoaded);
}
