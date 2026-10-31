# Independent source review: client chunk cache movement gate

## Verdict

**Conditionally accepted as an in-scope direct client player movement response.** Bound to candidate report SHA-256 `61899c9aa3c4b19bafcb5d1840b9a78d56f2fafcf6372f53ccf42d129cba70ac` and the exact Mojmap source hashes below.

The repeated-full-chunk path is supported by the cited source. In 1.15.2, `ClientChunkCache.replaceWithPacketData` updates a valid resident chunk in place (82–103). `LevelChunk.replaceWithPacketData` changes section and block-entity data without clearing `entitySections` (546–581). `ClientPacketListener.handleLevelChunk` then calls `reAddEntitiesToChunk` on full packets (748–762); that method walks `entitiesById` and adds every entity whose floored X/Z coordinates match the chunk (ClientLevel 314–323). `LevelChunk.addEntity` appends to the selected entity section (321–344), and `ClassInstanceMultiMap.add` delegates to `List.add` without duplicate rejection (25–34). Thus a second same-payload full packet applied while that chunk is resident appends a second reference for each matching tracked entity.

In 1.16.5, the `!isFullChunk && valid` branch is the only in-place branch; a full chunk with biome data constructs a fresh `LevelChunk` and replaces the slot (ClientChunkCache 82–103). `Storage.replace` unloads the old chunk, and `ClientLevel.unload` only queues its block entities and disables its light sources; it does not remove tracked entities from `entitiesById`. The packet handler then re-adds matching tracked entities to the fresh chunk (ClientPacketListener 755–770; ClientLevel 305–314), so its entity section starts without the old references and receives one add per tracked entity.

The duplicate survives the consumer path. `Level.getEntities` appends chunk query results to a list without deduplication (1.15.2 760–775; 1.16.5 690–706). `LevelChunk.getEntities` iterates each section entry and appends each matching entity entry (1.15.2 475–497; 1.16.5 476–500). The backing `ClassInstanceMultiMap` accepts repeated references in both releases.

The movement response is direct: the `LivingEntity` tick path calls `pushEntities` after `travel` (1.15.2 2285–2297; 1.16.5 2457–2469); `pushEntities` calls `doPush` once per query-list entry (2323–2345; 2502–2525), and `doPush` calls `entity.push(this)` (2370–2372; 2549–2551). For an intersecting pushable entity with nonzero horizontal separation, `Entity.push(Entity)` adds an impulse to the local player and the other participant when both are non-vehicle and have physics enabled; `Entity.push(double,double,double)` adds to delta movement (1.15.2 1156–1192; 1.16.5 1180–1216). Duplicate list entries therefore repeat that direct velocity write. This meets project scope for direct player movement responses even though the external event producer was not reviewed.

## Exact applicability gates and limits

- The full packet must be applied on the client while the corresponding cache slot is in range and holds a valid chunk at those coordinates. The 1.15.2 in-place path is gated by `isValidChunk`; the 1.16.5 full replacement additionally requires non-null biome data. The verdict is limited to this accepted packet input. The report's statement that packet decoding supplies biome data for a full chunk was not independently source-checked under this bounded review.
- At least one tracked, pushable entity must be in that chunk and intersect the local player's bounding box at the `pushEntities` query. Duplicate references to the player itself do not establish the response because the query excludes the receiver.
- A concrete repeated velocity change additionally requires the entity and local player not to share a vehicle, neither to have `noPhysics`, a non-vehicle local player, and `absMax(dx,dz) >= 0.01F`. These are the gates in `Entity.push(Entity)`; “overlap” alone is not sufficient to guarantee the impulse branch.
- This review establishes the consequence of repeated full-chunk packet application. It does not establish how often, or whether under ordinary server scheduling, a producer sends another full chunk for a resident cache entry. That producer question does not block classifying the demonstrated client-side response as in scope; do not state normal resend frequency or scheduling as a finding.
- Geometry, collision-provider and fluid dependencies remain outside this bounded review, as the candidate report states.

## Evidence identity

Candidate report SHA-256: `61899c9aa3c4b19bafcb5d1840b9a78d56f2fafcf6372f53ccf42d129cba70ac`.

Exact source hashes (all matched the candidate report):

| Mojmap source | 1.15.2 SHA-256 | 1.16.5 SHA-256 |
|---|---|---|
| `net/minecraft/client/multiplayer/ClientChunkCache.java` | `0c86577084a9e7a67a3460a018bebc014a7cf7f6c071893321cb41e1b19bc2cc` | `a87557dae1fc7d01050e2e05154364d3b056d69ac3c596edd323abc6a87e4d54` |
| `net/minecraft/client/multiplayer/ClientPacketListener.java` | `d806d286af4b93d7c61c3c884f344a5870ce1871fb0a811506031adf78fafc30` | `a1c3ea65f40a527d92b1e4d637cad4a8293fdddd1a9d530d778bf39cc2d8670e` |
| `net/minecraft/client/multiplayer/ClientLevel.java` | `4ed37f68a8c25ab5a780c7d646e2f1f4f375e39e4591e0a3115c8d637e500835` | `96c80a10623d41f136fa70391b3920cf7f3ef15547923f7d772b78d356f16d93` |
| `net/minecraft/world/level/chunk/LevelChunk.java` | `e5f08fcc71ffb3bf480e4ce62346d1dea248f6739dbc6a5ca5393163b0becc75` | `0bfdae3558d681d8c8074ae7476c3a394239dbcca0fdf7bad5b4386eafb65f12` |
| `net/minecraft/util/ClassInstanceMultiMap.java` | `3580d6d17f3bf41bd288c3cd0497b5fac140ef1b52db83e1aeac20e643e4a0cb` | `d728d00bc31f8df765f9fa64af3e1dae132d55c8050e60e2180f7a061d45f061` |
| `net/minecraft/world/level/Level.java` | `39cbd69f51c58711a3072bce84c80adfddf19837dba3e9582b0d6315d53e62bf` | `e9a23488863fd43a7d8975efc23f4c7cf4af874554bab5b76eaa2c79f028db80` |
| `net/minecraft/world/entity/LivingEntity.java` | `46d243bb7e51f7b54404aa1d7d6e6b827682d0f5925d02847c4193306c4d5e54` | `b5d8a1a3c80f85d5d545b5a777e9e2a915dc002a7e31b5f5ad12bf7e285d7a88` |
| `net/minecraft/world/entity/Entity.java` | `191b3ad3e7348c9bac1e703fff896706d23a751bf15162aacf676f5f97c0a10e` | `f9a9a073fe3105a0aa53d0f21ec72e59084e8d21a14c1cd3be75703865ee2666` |
