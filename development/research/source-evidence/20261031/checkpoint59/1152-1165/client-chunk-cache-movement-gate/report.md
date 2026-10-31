# ClientChunkCache movement route closure — 1.15.2 → 1.16.5

## Result

**Movement-reachable client delta confirmed, conditional on a full-chunk packet being applied to an already populated client chunk.** With the same full-chunk packet payload, block/fluid states and tracked entities, 1.15.2 mutates the existing `LevelChunk` and then re-adds every tracked entity into its existing entity section. The section collection accepts duplicate references and does not clear them in the packet-data method. A repeated full-chunk packet can therefore multiply entity entries returned by local entity queries. In 1.16.5 a full-chunk packet replaces the old chunk object before the same entity re-add pass, so the new section receives each tracked entity once.

A reachable movement consumer is `LivingEntity.pushEntities`: when the local player overlaps a pushable entity in the affected chunk, duplicate query entries cause repeated `doPush`/`Entity.push` calls; `Entity.push` adds the horizontal impulse to both participants' delta movement. This gives a concrete conditional player velocity difference from client cache replacement policy, without relying on changed block/fluid state or asserting that packet scheduling changed. The existing full-pair collision/entity geometry dependencies remain separate; this result is independently supported by the push path.

## Exact paired source facts

- `ClientChunkCache.getChunk`, `isTickingChunk`, both `isEntityTickingChunk` overloads, ring index/range checks, and `calculateStorageRange` have the same bodies at 1.15.2 `ClientChunkCache.java:65-74, 168-179, 195-215` and 1.16.5 `ClientChunkCache.java:65-74, 167-178, 194-214`. For equal cache contents/view center/radius, loaded lookup and readiness results match.
- Changed gate: 1.15.2 `ClientChunkCache.replaceWithPacketData:82-119` updates any valid resident chunk in place; it creates/replaces only when no matching chunk exists. 1.16.5 `replaceWithPacketData:82-119` receives `isFullChunk`; its `!full && valid` branch updates in place, while a full packet constructs a fresh `LevelChunk` and calls `Storage.replace`. The full packet branch requires biome data, as both packet decoders supply for a full chunk.
- Both `ClientPacketListener.handleLevelChunk` implementations call `reAddEntitiesToChunk` only when the returned chunk is non-null and the packet is full: 1.15.2 `ClientPacketListener.java:748-762`; 1.16.5 `:755-770`. This is an existing client packet consumer. Server packet production/scheduling was not inspected.
- `ClientLevel.reAddEntitiesToChunk` iterates `entitiesById` and calls `levelChunk.addEntity` for each entity whose floored X/Z chunk coordinates match (1.15.2 `ClientLevel.java:314-323`; 1.16.5 `:305-314`). `ClientChunkCache.Storage.replace` uses `getAndSet`, calls `level.unload(old)`, and stores the fresh chunk; the packet handler then re-adds entities to it.
- `LevelChunk.replaceWithPacketData` updates section/block-entity data but does not clear `entitySections` (1.15.2 `LevelChunk.java:546-600`; 1.16.5 `:550-606`). `LevelChunk.addEntity` unconditionally adds the entity to its Y section (1.15.2 `:321-344`; 1.16.5 `:322-345`). The corresponding `ClassInstanceMultiMap.add` appends to backing lists without duplicate rejection (1.15.2 `ClassInstanceMultiMap.java:25-33`; 1.16.5 `:26-34`).
- `Level.getEntities` visits loaded chunks and appends their `LevelChunk.getEntities` results (1.15.2 `Level.java:760-775`; 1.16.5 `:690-706`). `LevelChunk.getEntities` scans section entries and appends each intersecting entity reference (1.15.2 `LevelChunk.java:475-497`; 1.16.5 `:476-500`). Thus duplicate entries survive into a local entity query.
- `LivingEntity.pushEntities` obtains the nearby pushable list and calls `doPush` once per returned list entry (1.15.2 `LivingEntity.java:2323-2344`; 1.16.5 `:2502-2523`). `doPush` invokes `entity.push(this)` (1.15.2 `:2370-2372`; 1.16.5 `:2549-2551`). `Entity.push` applies impulses to each non-vehicle participant (1.15.2 `Entity.java:1156-1189`; 1.16.5 `:1180-1213`), so duplicate references can change the local player's accumulated velocity when the local player is pushable and overlapping.
- The block/fluid cache consumers use unchanged retrieval semantics: `Level.getChunkForCollisions` requests `FULL,false` in both (`Level.java:755-757` / `:685-687`), and `Level.getBlockState`/`getFluidState` request the ordinary chunk getter (`:342-358` / `:368-384`). The water/lava scan reaches `Level.getFluidState`; it was not re-audited for its independent fluid arithmetic. The prior ordered tick residual already covers `ClientLevel` tick body and says the local-player chunk-presence gate is retained; I did not re-audit that body. Cache readiness methods themselves are unchanged.

## Reachable condition and scope

Condition: a matching full-chunk packet is applied while the corresponding cache slot contains a valid chunk whose entity section already contains the local player and at least one pushable entity with intersecting bounds; the local player then runs `LivingEntity.pushEntities`. In 1.15.2, prior entity entries remain and packet re-add creates repeats; in 1.16.5, replacement starts with empty sections and packet re-add creates one entry per tracked entity. This is a client consumer difference for identical packet data/input and equal final block/fluid state. No trajectory, frequency of full-chunk resends, or changed server scheduling is claimed.

No other movement delta was found in the assigned cache read/readiness methods. The separate `CollisionGetter`/`CollisionSpliterator` and fluid/provider closures remain open as listed by the supplied source residuals; they do not block the entity-push route above. No tests, build, game launch, decompilation, product source or canonical finding/backlog were touched.

## Source identity and hashes

Canonical roots and Mojmap manifests, read-only:
- `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.15.2\mojmap`; manifest SHA-256 `cb7fd93f8730d8b2813857744f8572060535c7de44fcee86222acd1ceb6f43e7`.
- `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.16.5\mojmap`; manifest SHA-256 `9499f2611d0e6dde37cb635f1a28a591416382d6bc188b37d5f99e2b2a20823b`.

The canonical path manifests contain the following matching hashes for the examined source files:

| Mojmap source path | 1.15.2 SHA-256 | 1.16.5 SHA-256 |
|---|---|---|
| `net/minecraft/client/multiplayer/ClientChunkCache.java` | `0c86577084a9e7a67a3460a018bebc014a7cf7f6c071893321cb41e1b19bc2cc` | `a87557dae1fc7d01050e2e05154364d3b056d69ac3c596edd323abc6a87e4d54` |
| `net/minecraft/client/multiplayer/ClientPacketListener.java` | `d806d286af4b93d7c61c3c884f344a5870ce1871fb0a811506031adf78fafc30` | `a1c3ea65f40a527d92b1e4d637cad4a8293fdddd1a9d530d778bf39cc2d8670e` |
| `net/minecraft/client/multiplayer/ClientLevel.java` | `4ed37f68a8c25ab5a780c7d646e2f1f4f375e39e4591e0a3115c8d637e500835` | `96c80a10623d41f136fa70391b3920cf7f3ef15547923f7d772b78d356f16d93` |
| `net/minecraft/world/level/chunk/LevelChunk.java` | `e5f08fcc71ffb3bf480e4ce62346d1dea248f6739dbc6a5ca5393163b0becc75` | `0bfdae3558d681d8c8074ae7476c3a394239dbcca0fdf7bad5b4386eafb65f12` |
| `net/minecraft/util/ClassInstanceMultiMap.java` | `3580d6d17f3bf41bd288c3cd0497b5fac140ef1b52db83e1aeac20e643e4a0cb` | `d728d00bc31f8df765f9fa64af3e1dae132d55c8050e60e2180f7a061d45f061` |
| `net/minecraft/world/level/Level.java` | `39cbd69f51c58711a3072bce84c80adfddf19837dba3e9582b0d6315d53e62bf` | `e9a23488863fd43a7d8975efc23f4c7cf4af874554bab5b76eaa2c79f028db80` |
| `net/minecraft/world/entity/LivingEntity.java` | `46d243bb7e51f7b54404aa1d7d6e6b827682d0f5925d02847c4193306c4d5e54` | `b5d8a1a3c80f85d5d545b5a777e9e2a915dc002a7e31b5f5ad12bf7e285d7a88` |
| `net/minecraft/world/entity/Entity.java` | `191b3ad3e7348c9bac1e703fff896706d23a751bf15162aacf676f5f97c0a10e` | `f9a9a073fe3105a0aa53d0f21ec72e59084e8d21a14c1cd3be75703865ee2666` |
| `net/minecraft/world/level/CollisionGetter.java` | `63daec0623c816d53f5dbdde407a27222fa21e62680c1096b63cf9082b1c25fd` | `b507d6be11e5985a62cfeb249a99dcb5f8edaf346f12cb2487797d9e01763eac` |
| `net/minecraft/world/level/LevelReader.java` | `d09aa8eaaa7f8ca8703091facb4d7e1a26c9f4bea8b99c5c8956682875241d37` | `d301b992d71b05306a3139e69f3f1529190f82404791ec60f9a693188d29577a` |
