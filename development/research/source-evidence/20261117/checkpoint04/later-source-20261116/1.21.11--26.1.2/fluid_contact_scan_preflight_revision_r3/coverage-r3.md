# Fluid-contact scan preflight and sampling domain — bounded source packet

## Scope

Pair: Minecraft 1.21.11 Mojmap → 26.1.2 unobfuscated. This packet closes only the source-level chunk/section preflight and scan-domain question left open by the accepted fluid-contact r3 report. It does not reopen r3's same-cell height/current arithmetic, inspect resource payloads, establish a particular streamed-world state, or freeze the pair.

## Exact source identities

Canonical source trees: `decompiled_minecraft/1.21.11/mojmap/` and `decompiled_minecraft/26.1.2/unobfuscated/`. Actual root provenance sidecars (not dot-prefixed):

- 1.21.11 `mojmap.provenance.json`, SHA-256 `99c9fb741eb5e4ac8fb5780e2158da324d7c8b6afc179d494e5986f78fe29f72`; `mojmap.ready.json`, SHA-256 `0ad98d0ebd654650492c97eb58bc324d33864787f0ce10d99faec3ae2f5b804b`; source manifest `mojmap.sources.sha256`, SHA-256 `0c4d83fef84c101d9db88f331acec7960f3c8346228c28995e6cf57bbfcae555`.
- 26.1.2 `unobfuscated.provenance.json`, SHA-256 `8d727f880cc91f4a297e148c5b75976303d051457334bc689268fbdf631a012a`; `unobfuscated.ready.json`, SHA-256 `f24af0f4d38543c2bd9c19b50f347d4e89d6027050e4b21ae070734843946bb7`; source manifest `unobfuscated.sources.sha256`, SHA-256 `54ae320660ca911a6d20514c965c33eb414fbedd1ad6727bbaa95fe906aa23c0`.

These are the same pinned identities recorded in the accepted r3 packet; the available actual path forms and sidecar hashes were rechecked. I did not rehash either full source tree. Each Java file below was rehashed and matched its ready-bound source-manifest row.

| Version / namespace | Relative Java path | SHA-256 |
| --- | --- | --- |
| 1.21.11 Mojmap | `net/minecraft/world/entity/Entity.java` | `32314478c6036fa9f3f3cc409c61c622eefc5a282d60e1011a1a33cc29cf18a3` |
| 1.21.11 Mojmap | `net/minecraft/world/level/Level.java` | `de329fdbeca632a1b63074e1735890b2624abfc57b7a579f43eef36caefef98d` |
| 1.21.11 Mojmap | `net/minecraft/world/level/LevelReader.java` | `562cd72343484c0e48f55dfe1ff50e5bb10516135d601aff264867fac9d9ff09` |
| 1.21.11 Mojmap | `net/minecraft/client/multiplayer/ClientLevel.java` | `e76d09de5acad450062d5acb98e76ff032ecb179fef0f7af434e4148f84183a2` |
| 1.21.11 Mojmap | `net/minecraft/client/multiplayer/ClientChunkCache.java` | `d260b64a3985638cd38408ddf1c46a1f0c21746764fcad17e773f5e89b5f861a` |
| 1.21.11 Mojmap | `net/minecraft/world/level/chunk/ChunkSource.java` | `7fcb1d419ea8b19e1d488e621c973df22ac68692094a351ec2f6fda1727209a3` |
| 26.1.2 unobfuscated | `net/minecraft/world/entity/Entity.java` | `8b83b1f036aabbd13d990897c540c993f7120f02955486cfcf229517d4097ccf` |
| 26.1.2 unobfuscated | `net/minecraft/world/entity/EntityFluidInteraction.java` | `5264ff4f1fddebc3fa9d63ad2edbe2eaf617392ff946a867a6817a78b478ee62` |
| 26.1.2 unobfuscated | `net/minecraft/world/level/Level.java` | `77cc835fc1a79cd970bc3f4897fe5f398958fcbc874400c297f465f0c7819f01` |
| 26.1.2 unobfuscated | `net/minecraft/world/level/LevelReader.java` | `8fbfb7cf6d6ff8a19e784198d6bf75a1eae346cd8371d3d23eb3c8af546621a1` |
| 26.1.2 unobfuscated | `net/minecraft/client/multiplayer/ClientLevel.java` | `6e46db964229bf53bb88c33140cfa9acb5423dbed11a7c721b5b8bf9a7424de7` |
| 26.1.2 unobfuscated | `net/minecraft/client/multiplayer/ClientChunkCache.java` | `1a35ecd0c18d989bed9ef883028e197e29f98aadc78d909727722faba9a912a5` |
| 26.1.2 unobfuscated | `net/minecraft/world/level/chunk/ChunkSource.java` | `fb8b10fa62abe58e3a8efdff1d2213c6c6034c4da13ddd8f84b72254600dff7f` |
| 26.1.2 unobfuscated | `net/minecraft/world/level/chunk/LevelChunkSection.java` | `63cbada78f72dfdaee7dd7c0f973b5171f8c9084b94698e8249eec226dad9859` |

The new class `EntityFluidInteraction.java` has no matching 1.21.11 source path; its old-era scan is the method in `Entity.java` listed above.

## Paired scan domain and order

For an ordinary non-passenger entity, 26.1.2 `Entity.getFluidInteractionBox()` returns `getBoundingBox().deflate(0.001)` (`Entity.java:4015–4024`). The 1.21.11 scanner uses the same deflated box (`Entity.java:3514`). Define this shared box as D.

- 1.21.11 `Entity.updateFluidHeightAndDoFluidPushing` floors each D minimum and uses `ceil(D.max)` as an exclusive upper bound, then loops x, y, z in that order (`Entity.java:3509–3559`). For ordinary non-passenger player ticks its water and lava calls are separate (`Entity.java:1501–1514`).
- 26.1.2 `EntityFluidInteraction.update` floors each D minimum, sets each inclusive maximum to `ceil(D.max)-1`, and loops x, y, z in the same order (`EntityFluidInteraction.java:34–89`). Its integer coordinates represent the same cells as the old half-open bounds: `k < ceil(D.max)` is equivalent to `k <= ceil(D.max)-1`. When its preflight admits a scan, the ordinary-box cell coordinate set and per-pass order therefore match.
- The old routine samples `getFluidState` at each cell separately for each requested tag, rejecting by tag before height/flow work (`Entity.java:3529–3556`). The new routine samples each cell once in its single scan, rejects empty states, derives fluid type/tracker, and handles registered tags (`EntityFluidInteraction.java:52–88`). This is a traversal refactor; tag/resource membership is outside this packet.

`Level.getFluidState` has the same read shape at both endpoints: return empty fluid outside valid bounds; otherwise get the position's chunk and read its fluid state (`1.21.11 Level.java:346–352`; `26.1.2 Level.java:371–377`). The preflight difference below controls whether these reads occur; no flow or height formula is reconsidered here.

## Loaded-area and section preflight

**1.21.11:** before scanning each requested fluid tag, `Entity.updateFluidHeightAndDoFluidPushing` calls `touchingUnloadedChunk()` and immediately returns false if it reports unloaded (`Entity.java:3509–3512`). That helper inflates the original bounding box by 1.0 on all axes and calls `LevelReader.hasChunksAt` with floored minima and ceiled maxima (`Entity.java:3576–3584`). The four-argument `LevelReader.hasChunksAt` called here checks only horizontal chunk columns through `hasChunk` (`LevelReader.java:197–212`); it does not inspect the vertical interval. The dimension min/max Y check is in the distinct six-argument overload (`LevelReader.java:192–194`), which this helper does not call. On the client, `ClientLevel.hasChunk` returns true unconditionally at both endpoints' corresponding method locations (`1.21.11 ClientLevel.java:392–394`; `26.1.2 ClientLevel.java:402–404`), so this old preflight does not reject a missing client-cache chunk. If scanning proceeds, `Level.getFluidState` calls `getChunkAt`; the default `LevelReader.getChunk(..., status)` passes `loadOrGenerate=true` (`1.21.11 Level.java:179–188; LevelReader.java:124–132`). The client cache returns its `emptyChunk` when a chunk is absent and that flag is true (`ClientChunkCache.java:69–80`), after which the scanner continues through its cell coordinates.

**26.1.2:** `EntityFluidInteraction.update` resets all per-fluid trackers first, computes D's scan bounds, and calls `hasFluidAndLoaded` before any per-cell `getFluidState` call (`EntityFluidInteraction.java:32–44`). That helper expands the scan block bounds by one block in X and Z (not Y), converts the resulting X/Y/Z limits to section coordinates, then requires every horizontal chunk column in that section range to return a non-null `FULL` chunk from `level.getChunk(..., false)` (`EntityFluidInteraction.java:91–108`). This direct call is not `LevelReader.hasChunk`, so `ClientLevel.hasChunk=true` does not bypass it. In a client level, `ClientChunkCache.getChunk(..., false)` returns null for a chunk miss rather than the empty chunk (`26.1.2 ClientChunkCache.java:69–79`). If any checked chunk is missing, the helper returns false and the whole scan performs zero per-cell `getFluidState` reads. If all chunks exist, it checks `LevelChunkSection.hasFluid()` for each in-range vertical section (`EntityFluidInteraction.java:108–116`; `LevelChunkSection.java:109–110`) and enters the scan only if at least one such section reports fluid.

The new `hasFluid` section test is a pre-scan optimization: a false result means no scanned-height section reports any fluid; it does not establish tag-specific membership. More materially, on a client-cache miss the new code can skip the entire scan where the old client path proceeded and read present chunks plus `emptyChunk` fallbacks. The new preflight also explicitly checks the one-block X/Z-expanded region and only the scan's Y section range; the old code's inflated-box bounds feed `hasChunksAt`, whose client override makes the horizontal loaded check vacuous. Thus the preflight's spatial predicate and the set of possible `getFluidState` calls differ before the already-bounded same-cell height calculation. This is a source-visible sampling candidate only: no particular world edge, relevant loaded fluid cell, resource membership, or resulting movement state is established here.

## Bounded disposition and remaining gates

- **Same when scanning:** for the same ordinary non-passenger D, the enumerated block coordinate set and x→y→z order match. `Level.getFluidState` reads the same cell abstraction.
- **Different before scanning:** 26.1.2 adds an all-chunks-loaded check plus section `hasFluid` gate. ClientLevel's old `hasChunk=true` does not satisfy the new direct non-null chunk lookup. A cache-miss arrangement can therefore change whether any cell is sampled.
- **Not established:** whether ordinary play reaches a particular cache-miss arrangement with a movement-relevant loaded fluid cell; the effective fluid/tag data; server-level consequences; aggregate current or trajectory. The trackers are reset before the new gate and the old update clears its height map before its per-tag scans, so this packet does not claim stale-height behavior.

This closes only the source-level scan-preflight inventory. Fluid resource membership, loaded-area state/edge-case evaluation, passenger boxes, caller/product association, intermediate onset, complete pair coverage and runtime parity remain separate gates. No artifacts, source generation, build, test, gameplay, product/wiki/backlog/history or Git state were inspected or changed.
