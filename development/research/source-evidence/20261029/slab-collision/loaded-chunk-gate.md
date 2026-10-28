# Client collision chunk availability for the historical slab scan

## Finding

For the project-supported logical-client movement path, source establishes a safe non-loading custom-scan policy for chunk absence, but **not** equality of the old and modern loaded-chunk sets across different game sessions. Do not use `Level.getBlockState` to probe the modern world: its ordinary `getChunk` route can request a chunk with `loadOrGenerate=true`. Use `CollisionGetter.getChunkForCollisions(chunkX, chunkZ)` for each envelope column and do not dereference a null result.

In 1.13, `WorldView.getBlockCollisions` tests `isChunkLoaded(pos)` at Y=64 for each eligible X/Z column. Its default overload calls `isChunkLoaded(x,z,true)`. `ClientWorld.isChunkLoaded(x,z,allowEmpty)` returns `allowEmpty || !getChunkSource().getChunk(x,z).isEmpty()`, so this call is always true. `World.getBlockState` then calls the client chunk cache. For an absent client chunk, `ClientChunkCache.getChunk` returns its shared `EmptyChunk`, whose `getBlockState` returns `VOID_AIR`. The 1.13 `VOID_AIR` registration uses `Block.Properties.noCollision`; the base collision provider therefore returns an empty shape.

In 26.2, `ClientLevel` inherits `Level.getChunkForCollisions`. That override requests `ChunkStatus.FULL` with `loadOrGenerate=false`. `ClientChunkCache.getChunk(x,z,status,false)` returns the valid in-range `LevelChunk` or `null`; it does not synthesize/load a chunk on that route. `BlockCollisions` skips a null provider. If the chunk exists, the returned `LevelChunk.getBlockState(pos)` reads the current state. Thus, for collision geometry, a null current collision chunk can safely stand for the absent old-client `VOID_AIR` provider, while an available current collision chunk can be read through its returned `BlockGetter` without loading. The 26.2 `VOID_AIR` registration is also `.noCollision`, though the null path skips it rather than materializing that state.

This yields an implementation predicate for the **client-only** adapter: obtain the collision chunk with `getChunkForCollisions`; if null, treat that column as empty (equivalent to old client missing-chunk `VOID_AIR` for collision shape); if non-null, read every scanned state from that returned chunk. Never fall back to `level.getBlockState` for this scan. Continue to enforce border/entity/step-up/query and slab-roster gates independently. This predicate proves non-loading collision-state access, not that the 1.13 client and 26.2 client have identical chunk residency or received block data. If parity requires the exact same resident-world contents rather than current received-state witnesses, residency/data equivalence remains unproved and that route must stay native.

The old server's `isChunkLoaded` implementation is separate; this report does not use it because historical movement runs only on the logical client. Nor does it establish behavior for arbitrary `WorldView` implementations, `PathNavigationRegion`, server worlds, or future provider adapters.

## Exact source evidence

- 1.13 `WorldView.java:57, 131, 343-348`: `isChunkLoaded(BlockPos)` selects `allowEmpty=true`; collector checks it per X/Z at Y=64.
- 1.13 `ClientWorld.java:112-114`: with `allowEmpty=true`, client chunk availability test returns true without requiring a loaded chunk.
- 1.13 `World.java:508-515`, `ClientChunkCache.java:61-64`, `EmptyChunk.java:40-42`: world read obtains a chunk from the cache; absent client entries yield the shared empty chunk; that chunk returns `VOID_AIR`.
- 1.13 `Blocks.java:1880`, `Block.java:409-410`: `VOID_AIR` is registered as collisionless and base collision provider returns empty when `hasCollision` is false.
- 26.2 `ClientLevel.java`: no `getChunkForCollisions` override exists; the inherited implementation is `Level.java:769-771`.
- 26.2 `Level.java:207-214, 769-771`, `ClientChunkCache.java:69-77`: the collision method requests `FULL,false`; client cache returns a valid resident chunk or null; no chunk is generated on that path.
- 26.2 `BlockCollisions.java:64-73, 79-91`: chunk provider is cached per chunk position, null is skipped, and block state is read from the returned `BlockGetter` only.
- 26.2 `Blocks.java:4371-4373`, `BlockBehaviour.java:327-328`: modern `VOID_AIR` is also registered with `.noCollision` and base collision shape is empty.

## Source identity

Exact source roots are `1.13/ornithe-feather` and `26.2/unobfuscated`. Their ready markers identify those version/mapping pairs and their `sources.sha256` digests. The per-file hashes and ready/provenance/source-manifest hashes are in `source-hashes.sha256`; every cited Java file hash was recomputed and matched its canonical source manifest.
