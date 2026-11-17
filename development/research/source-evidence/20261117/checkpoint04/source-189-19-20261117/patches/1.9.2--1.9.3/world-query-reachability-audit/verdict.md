# Independent bounded source audit: ACCEPT

## Immutable input

Report: `build/source-189-19-20261117/patches/1.9.2--1.9.3/world-query-reachability-followup.md`  
SHA-256: `76A2C86AA12365AEB2754367EA340B83B67A2B95C993FEBA05B24CCC6B785046`

Pair: Minecraft 1.9.2 → 1.9.3, `ornithe-feather`. Provenance identities verified: 1.9.2 `9928b467deb35c7e7f891b4fee1627a68feecdf233c2076909fd4834b0380510`; 1.9.3 `43325cdd53ca34b88ee9af8464782b323a948b98c8a556c4761f1e5737b84c3a`.

## Audit result

ACCEPT for the bounded cache-population/query-reachability analysis. I parsed the 14 paired source-file rows in the report and independently checked all 28 file hashes against both the report table and their exact provenance `sources` rows; all match. Both provenance-file hashes also match the supplied identities.

The report's cache-population path is supported. `ChunkMap.addPlayer` iterates the square around the server player's chunk without a world-border or coordinate-limit condition in both releases (`ChunkMap.java:202` in 1.9.2; `:205` in 1.9.3); `updateViewDistance` clamps to 3–32 and uses the same coordinate fan-out. `getOrAddChunk`, `ChunkHolder.load/populate/sendChunk`, and `ServerChunkCache.getChunk` forward those coordinates without a horizontal-world limit check. A populated chunk is sent in `WorldChunkS2CPacket`. On the client, `handleWorldChunk` forwards packet coordinates to `ClientWorld.updateChunk`; full-chunk update calls `ClientChunkCache.loadChunk`, which inserts the exact `ChunkPos.toLong` key. That key packs the supplied coordinates and does not clamp them. Thus, conditional on a server player at the hard-capped border and a populated neighboring chunk, the ordinary view-distance path can load a client cache entry beyond the ±30,000,000 block-coordinate limit. This establishes cache-entry population only; it does not establish a collision-bearing block state there.

The report correctly keeps the movement-query gate open. In 1.9.2, `World.contains` applies the horizontal ±30,000,000 and vertical bounds; both `isChunkLoaded` and `getBlockState` reject a position outside them. In 1.9.3, `isChunkLoaded` forwards the chunk coordinates to `isChunkLoadedAt`, while `getBlockState` checks only height before querying the chunk. When no client chunk is cached, `ClientChunkCache.getChunk` returns `EmptyChunk`, whose `getBlockState` is air; an absent chunk therefore does not yield a collision.

Both `World.getCollisions(Entity, Box)` implementations scan `floor(min)-1` through exclusive `ceil(max)+1`. They read the actual block state outside the border only when the entity is not within the border; an entity still within the border uses the synthetic stone state for exterior scan cells. `WorldBorder.maxSize` is 29,999,984, at least 16 blocks inside the horizontal coordinate limit. The queried movement state therefore requires an outside-border player/entity plus a scan that reaches a coordinate at or beyond ±30,000,000. `Entity.move` invokes this entity-aware query for support checks, ordinary movement and step-up handling, but the report does not claim that an ordinary client tick reaches that player/query state. `PlayerEntity.tick` applies its ±29,999,999 x/z clamp only after `super.tick`, which does not prove ordinary ingress. A player pose query is a separate call: `PlayerEntity.updatePlayerPose` calls `World.getCollisions(Box)` without an entity argument; it is not evidence that the entity-aware `Entity.move` query reads the same exterior state.

The report's distinction is precise: ordinary server streaming can populate out-of-limit cache coordinates, while ordinary local-player reachability of an out-of-border query and a non-air collision state remain unproven. Trajectory significance and implementation eligibility are not claimed. No further correction is required to this bounded report.

## Status boundary

This verdict accepts only the bounded cache-population and query-gate analysis. It does not establish a normal-session movement difference or implementation-ready finding. The exact 1.9.2→1.9.3 pair remains PARTIAL and unfrozen; the separate accepted source coverage and unresolved reachability/trajectory gates are unchanged.