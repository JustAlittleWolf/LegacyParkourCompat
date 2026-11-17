# Independent bounded source audit: ACCEPT

## Immutable input

Follow-up report: `development/research/source-evidence/20261117/checkpoint04/source-189-19-20261117/patches/1.9.2--1.9.3/world-query-limit-followup.md`  
SHA-256: `5BFBA153A7AC91BE980B3126FA38FA59F1056116F02C0F22F2260A1B003F1CE8`

## Audit result

ACCEPT for the report's bounded, conditional source observation: the 1.9.3 client world can read block states from an already-cached chunk at an out-of-horizontal-world-limit coordinate during player collision queries, whereas the corresponding 1.9.2 query rejects the coordinate before consulting the client chunk cache. This review does not establish ordinary-session population of such a chunk, reachability of the required player/query state, a normal-session trajectory difference, or implementation eligibility. The report states these gates explicitly; pair-wide coverage remains separate and partial.

The provenance file hashes match the declared identities for both releases (`1.9.2`: `9928b467deb35c7e7f891b4fee1627a68feecdf233c2076909fd4834b0380510`; `1.9.3`: `43325cdd53ca34b88ee9af8464782b323a948b98c8a556c4761f1e5737b84c3a`). Each provenance has 1,819 source rows. I independently checked the provenance-listed hashes against the nine cited source files in each release: `World.java`, `ClientWorld.java`, `ClientChunkCache.java`, `EmptyChunk.java`, `WorldChunk.java`, `ChunkPos.java`, `Entity.java`, `LivingEntity.java`, and `LocalClientPlayerEntity.java`; all matched.

The decisive gate and read differ as reported. In 1.9.2, `World.isChunkLoaded(BlockPos, boolean)` rejects positions failing `contains`, and `World.getBlockState` returns air for positions failing the same world-coordinate and height check. In 1.9.3, `isChunkLoaded` passes the shifted x/z directly to `isChunkLoadedAt`, and `getBlockState` rejects only out-of-height positions before reading the chunk. Both `World.getCollisions` implementations first call `isChunkLoaded` for scanned coordinates and then conditionally read block state; for an entity outside the world border, `!bl2` makes that read occur. `ClientWorld.isChunkLoadedAt` supplies the same allow-empty result in both versions; the 1.9.3 `ClientChunkCache` returns a cached chunk when present and its `EmptyChunk` otherwise. `EmptyChunk.getBlockState` returns air. The client cache's `loadChunk` and map key construction accept the supplied coordinates without a world-limit check. Thus the report correctly conditions any collision result on the relevant non-empty chunk already being cached; an absent chunk alone returns air.

The player-caller bound is also supported: `LocalClientPlayerEntity` inherits the client player/entity chain, and inherited `Entity.move` calls `World.getCollisions` for support checks, ordinary movement, and step-up resolution. The audited report is therefore a valid source-level observation about a player movement query under its stated preconditions, not evidence that those preconditions occur in an ordinary session.

## Limits

No packet, chunk-loading route, runtime behavior, ordinary-session reachability, or concrete trajectory was reviewed or established. Do not promote this conditional observation to a general player movement finding or use it to close pair coverage without separately resolving those gates.