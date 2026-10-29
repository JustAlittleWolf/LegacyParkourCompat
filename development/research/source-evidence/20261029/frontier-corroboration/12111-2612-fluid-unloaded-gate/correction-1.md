# Correction 1 — client chunk dispatch and fluid fallback

## Immutable binding and review status

This correction supplements, and does not edit, `report.md` (SHA-256 `3b4beaf7f7fb5009032431790c454c2a078063f661a0ce31eb8ec5160107ddd2`). It withdraws the original report's claim that 1.21.11 `touchingUnloadedChunk()` can early-return on a local-client tick and withdraws any impact statement dependent on that skip.

Triggering reviewer message in this task, verbatim:

> The auditor confirmed the 1.21.11 `ClientLevel.hasChunk` override returns true and `LevelReader.hasChunksAt` dispatches to it, so the old `touchingUnloadedChunk()` broad gate cannot fail on ordinary local-client ticks. Please add an immutable correction supplement binding the original report and pending audit, withdraw any old early-return claim, and state the exact narrower contrast only after tracing old height/current reads, eye-fluid read and their fallback behavior against the new collector's missing-FULL-chunk preflight. Preserve the original report and include the locally loaded-state assumptions and all affected fields. No runtime claim.

Review target: `.native-source/20261028/frontier-c/12111-2612-fluid-unloaded-audit/audit.md`. Its final immutable audit revision and digest are pending/unavailable for this correction, so no audit digest or verdict binding is asserted here. The supplement is bound to the reviewer trigger above; attach a later binding supplement if the reviewer publishes a final audit revision.

## Corrected client dispatch and fallback

The old call chain is not a reliable loaded-chunk test on the local client:

1. `Entity.updateFluidHeightAndDoFluidPushing` calls `touchingUnloadedChunk()` before its scan (1.21.11 Mojmap `Entity.java:3509–3512`). That method calls `level.hasChunksAt(...)` over the X/Z bounds of the entity box inflated by one block (`Entity.java:3576–3583`).
2. `LevelReader.hasChunksAt(int x0, int z0, int x1, int z1)` iterates chunk coordinates inclusively and calls `this.hasChunk(...)` (`LevelReader.java:197–209`). This virtual call reaches `ClientLevel.hasChunk(int,int)`, which unconditionally returns `true` (`ClientLevel.java:392–394`). Thus the old unloaded-chunk early return cannot trigger through this gate for a local player whose level is `ClientLevel`, even if the client chunk cache lacks neighboring chunks.
3. The old water and lava samplers proceed over the deflated entity box (`Entity.java:3514–3573`). `Level.getFluidState` gets the position's chunk (`Level.java:346–352`; `getChunkAt` and `getChunk` at `:179–185`). The `LevelReader.getChunk(int,int,ChunkStatus)` default requests `loadOrGenerate=true` (`LevelReader.java:128–133`). `ClientChunkCache.getChunk` returns its shared `EmptyLevelChunk` when the requested chunk is absent and loading is requested (`ClientChunkCache.java:33–40, 69–77`); that fallback's `getFluidState` returns `Fluids.EMPTY.defaultFluidState()` (`EmptyLevelChunk.java:36–38`). Consequently old sampling can still collect fluid from installed chunks in the actual entity box, while coordinates in absent chunks contribute empty fluid. The same fallback applies to the old one-block eye query.
4. The old eye updater runs after the water/lava sampler and independently queries `BlockPos.containing(x, eyeY, z)` (`Entity.java:1524–1535`). It has no separate loaded-neighbor gate. If that eye position is in an installed chunk containing water, the old eye tag is set even when another chunk is absent.
5. The new `EntityFluidInteraction.update` first resets its trackers, obtains the base non-passenger interaction box, and runs a preflight over the fluid-box block bounds expanded by one block in X/Z (`EntityFluidInteraction.java:32–43`). `hasFluidAndLoaded` asks `Level.getChunk(chunkX, chunkZ, ChunkStatus.FULL, false)` for every expanded-envelope chunk (`:91–106`). `Level` returns the chunk-source result directly unless the null result was requested with `loadOrGenerate=true` (`26.1.2 Level.java:202–207`). The client cache returns `null` for an absent chunk when `loadOrGenerate=false` (`ClientChunkCache.java:69–78`). Any such null aborts the collector; all reset trackers retain height `0`, `eyesInside=false`, zero current and count `0` (`EntityFluidInteraction.java:32–33, 42, 100–118, 155–163`). The scan and integrated eye test never run. The `hasFluid` section test is an additional no-fluid short circuit; when it returns false with all chunks present, the scanned fluid outputs would also be empty.

The distinction is therefore between the old actual-box reads with empty-chunk fallback and the new all-or-nothing FULL-chunk preflight over a one-block horizontal halo. It is not an old-side skipped-sampling effect.

## Narrow conditional comparison and affected state

Assumptions for the source comparison: ordinary logical-client `LocalPlayer` tick passes its existing `connection.hasClientLoaded()` caller gate; its base, non-passenger fluid box is the deflated player bounding box; at least one chunk in the new collector's expanded X/Z halo is absent from the client chunk cache; and some fluid-relevant position in the actual old scan/eye query is in an installed chunk. These are conditional loaded-state assumptions, not a claim about when runtime chunk availability occurs.

Under those assumptions, old water/lava sampling continues. A present water/lava fluid position within the entity box can still set its corresponding `fluidHeight` and contribute current; an absent position contributes empty fluid through `EmptyLevelChunk`. The old eye query independently reads its one block and can report eye fluid when that block is installed and contains fluid. New preflight aborts the entire collection because of the absent halo chunk, so it clears both water/lava tracker heights, eye state and current accumulators, including values obtainable from other installed chunks in the actual box.

Source fields and downstream direct reads that can differ when the required positive fluid sample is present:

- water/lava fluid height (`getFluidHeight`); water occupancy (`wasTouchingWater` / `isInWater`) and lava occupancy (`isInLava`);
- collected/applied fluid current and any resulting velocity addition;
- water-triggered fall-distance reset and the entering-water splash call;
- current eye-fluid tag/predicate (`isEyeInFluid`), when the eye block itself is installed and fluid-filled.

The base tick snapshots `wasEyeInWater` before it refreshes current eye state in both endpoints (old `Entity.java:1525`; new `:523–524`). Thus a current-eye difference is immediately visible through the refreshed `isEyeInFluid` result, while `wasEyeInWater` receives that value only on a later base tick. `isUnderWater` uses the snapshot plus water occupancy; it is not asserted as an immediate consequence of the just-completed eye refresh. The one-tick and field effects above remain conditional on the state continuing into the corresponding later tick where applicable.

If no fluid is present in the old actual-box/eye queries, both sides produce zero fluid heights and no current; the new `hasFluid` fast path does not itself establish a delta. If the eye position itself is absent, old `EmptyLevelChunk` fallback returns empty fluid there, so this correction makes no eye-fluid contrast for that case. If missing chunks overlap the old entity-box scan, old reads from those coordinates are empty, but positive results from other installed positions in the box can still survive; the new preflight suppresses all positions together.

## Hash-bound evidence for this correction

The exact release/mapping identities and ready/source/artifact-manifest digests are inherited unchanged from the original report. The source files below were re-hashed from the canonical roots; their digests match the named entries in the source manifests.

| Endpoint | Source file | SHA-256 |
|---|---|---|
| 1.21.11 Mojmap | `net/minecraft/client/multiplayer/ClientLevel.java` | `e76d09de5acad450062d5acb98e76ff032ecb179fef0f7af434e4148f84183a2` |
| 1.21.11 Mojmap | `net/minecraft/client/multiplayer/ClientChunkCache.java` | `d260b64a3985638cd38408ddf1c46a1f0c21746764fcad17e773f5e89b5f861a` |
| 1.21.11 Mojmap | `net/minecraft/world/level/chunk/EmptyLevelChunk.java` | `e7056828062c7c088d0b60a103fab25bcf157b65480a0140a35b5d7c1eb56e80` |
| 1.21.11 Mojmap | `net/minecraft/world/level/chunk/ChunkSource.java` | `7fcb1d419ea8b19e1d488e621c973df22ac68692094a351ec2f6fda1727209a3` |
| 1.21.11 Mojmap | `net/minecraft/world/level/Level.java` | `de329fdbeca632a1b63074e1735890b2624abfc57b7a579f43eef36caefef98d` |
| 1.21.11 Mojmap | `net/minecraft/world/level/LevelReader.java` | `562cd72343484c0e48f55dfe1ff50e5bb10516135d601aff264867fac9d9ff09` |
| 1.21.11 Mojmap | `net/minecraft/world/entity/Entity.java` | `32314478c6036fa9f3f3cc409c61c622eefc5a282d60e1011a1a33cc29cf18a3` |
| 26.1.2 unobfuscated | `net/minecraft/world/entity/EntityFluidInteraction.java` | `5264ff4f1fddebc3fa9d63ad2edbe2eaf617392ff946a867a6817a78b478ee62` |
| 26.1.2 unobfuscated | `net/minecraft/world/level/Level.java` | `77cc835fc1a79cd970bc3f4897fe5f398958fcbc874400c297f465f0c7819f01` |
| 26.1.2 unobfuscated | `net/minecraft/client/multiplayer/ClientChunkCache.java` | `1a35ecd0c18d989bed9ef883028e197e29f98aadc78d909727722faba9a912a5` |

No runtime chunk-loading behavior or trajectory was tested or inferred.
