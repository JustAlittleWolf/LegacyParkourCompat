# Level query residual audit — 1.15.2 → 1.16.5 Mojmap

## Result

The examined `Level`, `LevelAccessor`, and `LevelReader` members do not establish an independent ordinary LocalPlayer movement difference beyond separately owned `EntityGetter` / `CollisionGetter` routes. Exact movement-query wrappers are either same-form or delegate to the lower interface providers; those lower providers are listed below as the precise remaining dependency, not as a whole-Level gap. This audit does not claim full pair freeze or runtime parity.

All sources were read-only from the exact Mojmap trees. Each compared source SHA-256 matched its release's `mojmap.sources.sha256` manifest (the checked rows are listed in `source-hashes.tsv`). The 1.15.2 and 1.16.5 manifest parent pins are the already verified `cb7fd93f8730d8b2813857744f8572060535c7de44fcee86222acd1ceb6f43e7` and `9499f2611d0e6dde37cb635f1a28a591416382d6bc188b37d5f99d2b2a20823b`.

## Exact movement query members

### `LevelReader.containsAnyLiquid(AABB)` — CLOSED EQUIVALENT for the reached player movement check

The old and new defaults enumerate the same integer prism: floors of all minima, ceilings of all maxima, and three half-open loops over `i < j`, `k < l`, `m < n`. Each reads the same `BlockState.getFluidState().isEmpty()` result and returns on the first nonempty fluid. The only source-body change is storage of the cursor: 1.15.2 acquires one `PooledMutableBlockPos` in try-with-resources and reuses it; 1.16.5 allocates one `MutableBlockPos` before the loops and reuses it. Both pass the current coordinates to `getBlockState` and retain no cursor reference.

This query is reached from `Entity.isFree(AABB)`; in the local player's `LivingEntity.travel` branches, `horizontalCollision && isFree(...)` conditionally selects the `0.3F` vertical escape velocity. Under the same box, sampled block/fluid states, and thread-confined client read, the query visits the same cells and returns the same Boolean. The movement consequence for this LevelReader member is therefore closed equivalent. Entity collision admission and block-query geometry in the separate `noCollision` portion belong to their own source routes, not to `containsAnyLiquid`.

### `Level.getChunkForCollisions` and `Level.getEntities` query wrappers — same wrappers; lower collision routes remain separate

`Level.getChunkForCollisions(x,z)` returns `getChunk(x,z,ChunkStatus.FULL,false)` at both endpoints. The field-to-accessor change (`chunkSource` versus `getChunkSource()`) does not change the chunk request. The collision collector behavior layered above that chunk getter is covered by the existing CollisionGetter/CollisionSpliterator audits.

The paired `Level.getEntities(@Nullable Entity,AABB,Predicate)` wrappers both increment the same profiler counter, compute the same `floor((min-2)/16)` through `floor((max+2)/16)` chunk range on X/Z, request each chunk without creating it, skip null chunks, and pass the same query/entity/predicate to `LevelChunk.getEntities`. The newer body caches `getChunkSource()` in a local variable; this does not change the queried chunk set or per-chunk call. The corresponding `LevelChunk` method retains its vertical section range, entity-box intersection, query-entity exclusion, optional predicate, and EnderDragon-part path. Thus the Level/LevelChunk broad-phase wrapper itself is closed for equal chunk contents and method inputs.

Level.getBlockEntity(BlockPos) is also the same read path at both endpoints: build-height guard, non-client off-thread null gate, pending-entity lookup when updating, chunk getBlockEntity(..., IMMEDIATE), then pending fallback. The changed field/accessor form for ChunkSource does not change the lookup. This preserves the Level read used by the fixed-source piston collision provider for the same sampled chunk/block-entity input; its BE-presence/order condition remains separately bounded by the piston report.

**Exact lower dependency still open elsewhere:** `EntityGetter.getEntityCollisions` changes the entity-candidate admission/shape route (legacy `getCollideBox`/`getCollideAgainstBox` plus same-vehicle exclusion versus predicate/can-collide plus bounding-box geometry). The changed wrapper is called by ordinary `Entity.move` and `LivingEntity.pushEntities`, including local-player collision queries. The Level wrapper does not neutralize that changed lower filter. Keep the exact `EntityGetter`/entity-subclass applicability question open under that row; do not leave the Level row described as “unexamined query methods.”

### Inherited `noCollision`, `getEntityCollisions`, `isUnobstructed`, and heightmap dispatch

Old `LevelAccessor` explicitly re-delegates `getEntityCollisions` and `isUnobstructed` to `EntityGetter.super`, and `getHeightmapPos` to `LevelReader.super`. New `LevelAccessor` inherits `CommonLevelAccessor`, which contains those same three default-delegation forms under the newer predicate signature. `LevelReader`'s `getHeightmapPos` body is unchanged. Therefore no distinct LevelAccessor implementation delta is introduced by moving these defaults to the common superinterface.

The receiver's subsequent lower-provider semantics remain split by scope: `EntityGetter.getEntityCollisions` is the open entity-candidate dependency above; `noCollision` reaches `CollisionGetter` and its same-world query path. The already accepted pose-fit/suffocation and CollisionSpliterator reports close their named witnesses only. This report does not transfer an accepted block-collision witness to the distinct auto-jump query or claim every collision context/input route closed.

## Other changed members classified against movement scope

- `LevelReader.getBlockStatesIfLoaded(AABB)` is newly added and directly used by `Entity.baseTick` to scan FIRE-tag/LAVA states before updating fire ticks. This is fire/health/damage state handling, excluded from player movement; it is not a collision or travel query. `BubbleColumnAmbientSoundHandler` is a separate sound consumer.
- `LevelReader.getBrightness` renames the dimension API used for a brightness value; no LocalPlayer movement reader consumes it. The biome, tint, sky visibility and heightmap helpers either retain their method bodies or have no ordinary LocalPlayer movement consumer.
- `Level.isInWorldBounds` and spawnable-bounds helpers feed block placement/command bounds, not the local-player tick. The changed `loadedAndEntityCanStandOnFace`/`loadedAndEntityCanStandOn` calls are used by Shulker/Guardian behavior, not LocalPlayer movement.
- The new `Level` constructor's border initialization matches vanilla endpoint behavior. Old `Dimension.createWorldBorder()` returns a plain border for the Overworld/End and `NetherDimension.createWorldBorder()` overrides its center getters to divide by `8.0`. New `Level` creates a plain border for scale `1.0` and divides center getters by `DimensionType.coordinateScale()` otherwise; the 1.16.5 default Nether scale is `8.0`. Thus default Overworld, Nether, and End movement border inputs preserve the same initializer math. Custom dimension/resource production is not claimed.
- Old `Level.blockUpdated` calls `updateNeighborsAt` only through the server-only `setBlock` branch. New `LevelAccessor.blockUpdated` is a no-op default (there is no Level/ClientLevel override); new `Level.setBlock` reaches it from a flag-only branch. For the logical client, old `setBlock` never called this hook and new call is a no-op. Server-side neighbor production is outside the client movement implementation scope; equal sampled client block states remain the premise for the bounded provider findings.

## Exact remaining source routes from these files

1. `Level` collision wrappers are closed for equal chunk/input state; the `EntityGetter.getEntityCollisions` candidate filter/shape semantics are still the actual lower movement dependency. Existing pose-fit, chunk-cache, collision fast-path, border, and fixed-provider reports retain their own bounded scope.
2. `LevelAccessor` contains no separate changed movement body after mapping the removed defaults to `CommonLevelAccessor`/`LevelReader`; track the changed delegated `EntityGetter` algorithm at that exact lower row.
3. `LevelReader.containsAnyLiquid` is closed equivalent for `Entity.isFree` / the movement branch above; `getBlockStatesIfLoaded` is excluded fire/sound handling.

No server-side movement, packet cadence, or source state producer claim is used to close the movement consumer. No test, build, launch, decompilation, product edit, wiki edit, or backlog write was performed.
