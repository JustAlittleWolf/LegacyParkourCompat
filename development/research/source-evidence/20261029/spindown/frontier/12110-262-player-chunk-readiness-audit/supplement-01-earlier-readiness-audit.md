# Independent audit: earlier 1.21 player-section readiness supplements

## Verdict

**Accept the corrected source claims in Supplements 02 and 03.** The direct compiled-only readiness predicate is present at 1.21.3 and is independently verified again in 1.21.6, .7, .8, .9, and .10. Supplement 01's initial statement that .6/.9 were unavailable for behavior review is superseded by Supplement 02's source-map closure; it should not be retained as the current evidence disposition. The absence of modern ready markers for .6, .7, and .9 is accurate, but does not negate their complete legacy mapping-root source maps.

The earliest positive endpoint demonstrated here is 1.21.3; the evidence does not establish when the predicate first appeared before that release. Readiness and local-player loaded-state/tick gates are separate source paths. This audit makes no render-output, scheduling, packet-arrival, or movement-trajectory claim.

## Bound supplements and source identity

I recomputed and matched the supplement sidecars:

- Supplement 01, `supplement-01-earlier-121-readiness.md`: SHA-256 `283a413da6b69fa6b812d7ff2ed8b6711c8c9a5acc10d339cee8d3f2a5e21cd4`.
- Supplement 02, `supplement-02-legacy-manifest-1216-1219.md`: SHA-256 `0fba410019738740705517ba4d01489078257ad245ded84c6024abda4eca3b23`.
- Supplement 03, `supplement-03-1217-exact-readiness.md`: SHA-256 `f217eac75a09f66548da55423da5309d9f9acedd31b15db4295410baa6ec01bd`.

The bound primary report remains SHA-256 `ff75dfeed8bc8a50a0771c7fdfb00ee68d6a2b33cc47e95828cddfb58c83faa1`.

### Ready Mojmap endpoints

For 1.21.3, .4, .5, and .8, I independently checked the ready state, exact version and metadata IDs, mapping family, file counts, source-manifest SHA-256 against the ready marker, and cited file digests against their source-manifest rows. The marker values match Supplement 01: manifests `d673bb5464853e3a2a92ed9b1ffe1789d4e62c097bb884f67c336fcbb3b178ce`, `f90b61197928632e061ea877955a19055c92ae6f357c2daf1bc646172c6f51f0`, `365cc2d22446ceba0e36f46aa1c95ce4cc31514dbd657505680fb660f3adefd9`, and `8ffb76cea647a2ba4fe58e000678f751bea2c40ea6358bae492e962ed1d9d008`, respectively. Their client hashes and mapping-batch identities also match the ready/provenance metadata reported in Supplement 01.

### Legacy mapping-root source maps

For .6, .7, and .9, I verified the SHA-256 of each mapping-root `.decompile-provenance.json`; read the embedded source map; recomputed every declared Java-file digest against its path under that root; and confirmed the source-map counts equal the physical `.java` file counts:

| Release | Provenance SHA-256 | Entries checked | Verified | Missing / mismatched |
|---|---|---:|---:|---:|
| 1.21.6 | `56b036c61094d1d936882bdf5b2b59044512189cb33adbb075880c727da9b19e` | 6,103 | 6,103 | 0 / 0 |
| 1.21.7 | `45bf296d921b2239fd0aedb2684e2c1cb9b7d7532b4106b8a2fd9b09309ca82d` | 6,104 | 6,104 | 0 / 0 |
| 1.21.9 | `37d3049478585a43ffcace14fc8f2ee814b35ceedd8c9bad8b60adaed9b72c4f` | 6,386 | 6,386 | 0 / 0 |

I matched the metadata JSON, client JAR, and client mapping URLs and declared SHA-256 values in Supplements 02/03 to the `inputs` properties in those exact hashed sidecars. .7 has 87 inputs, as reported. The sidecars bind the declarations; the downloaded input payloads were not separately rehashed, which the supplements disclose.

For .6, all five cited source hashes in Supplement 02 match both the files and the embedded source map. For .9, all five cited hashes do likewise. For .7, all five selected source hashes in Supplement 03 match both the files and the embedded source map. The shared manager hash is `2d871f1c73e8391e3fe049552702210566d49857d7d02d20b781f3148d75dbec`; .6 and .7's listener/player/renderer hashes match .8's ready-source hashes. The .9 tracker, listener, local-player, and renderer hashes match .10's ready-source hashes. Thus the selected caller-chain details are supported by the exact source bytes, not inferred from names or nearby versions.

## Predicate, caller, and version-boundary audit

### 1.21.3–1.21.8: manager predicate and separate local gate

The byte-identical `LevelLoadStatusManager.tick()` bodies in .3, .4, .5, .6, .7, and .8 transition the manager from `WAITING_FOR_SERVER` to `WAITING_FOR_PLAYER_CHUNK` in `loadingPacketsReceived()`. In that state, `tick()` obtains `player.blockPosition()`, evaluates outside-build-height, then uses the short-circuit expression `outsideHeight || levelRenderer.isSectionCompiled(pos) || player.isSpectator() || !player.isAlive()` to mark the level ready (`LevelLoadStatusManager.java:19–40`). Therefore renderer compilation is tested at the player's position only after the outside-height check and before the spectator/dead bypasses; these bypasses are real early exits, not renderer calls. `isSectionCompiled(pos)` accepts an existing section whose compiled mesh is not `UNCOMPILED`; none of these manager call paths applies the later visibility threshold.

The shared manager file hash is identical in all those endpoints. Supplement 01's per-release renderer references check the matching operation in .3/.4/.5/.8; Supplements 02/03 and their complete legacy source-map checks bind the same manager/renderer implementation for .6/.7. The client listener starts/polls this manager and, beginning in .4, uses its ready state to send the loaded packet and set the player flag. That listener/notification path does not replace the manager's section predicate.

`LocalPlayer.tick()` is a separate consumer. In .3, it has no `hasClientLoaded()` guard, and the examined Player source has no loaded-state flag or timeout. From .4 through .10 in the checked endpoints, `LocalPlayer.tick()` ticks the client-load timeout and gates its ordinary tick body on `hasClientLoaded()`; the Player-side fallback is the flag OR an exhausted 60-tick timer. The .7 exact source shows that sequence at `LocalPlayer.java:197–201`; the client listener's manager-to-loaded-state transition is at `ClientPacketListener.java:2589–2594`, and the Player fallback methods are at `Player.java:2053–2066`. Beginning with .11, the local tick gate instead calls `connection.hasClientLoaded()` as documented in the bound primary report. These gates are separate from each release's compiled-section readiness predicate.

### 1.21.9–1.21.10: tracker timeout, same compiled-only query

The old manager class is replaced by `LevelLoadTracker`. In both exact endpoints, `WaitingForPlayerChunk.isReady()` first accepts after its 30-second deadline. Otherwise it reads `player.blockPosition()` and checks the player build-height, spectator, and alive gates before calling `isSectionCompiled(pos)` on the ordinary branch. This reverses the old manager's query ordering: the compiled-section call occurs only after those eligibility checks, and timeout is a new earlier bypass. The .9 `LevelLoadTracker.java` is byte-identical to the ready .10 file; its hash and the linked listener/local-player/renderer hashes match the canonical ready .10 source manifest. This remains a direct player-position compiled-only query, with no visibility threshold.

### Visibility boundary at 1.21.11 through 26.1.2

The original report's exact ready-source audit shows that the 30-second fallback remains while the ordinary player-position branch changes to `isSectionCompiledAndVisible(pos)` in 1.21.11, and through 26.1.2. The renderer predicate adds evaluated visibility `>= 0.3F` to the compiled mesh requirement. Thus timeout and visibility are concurrent predicate features: visibility is added at .11; it does not replace the timeout.

The accepted bounded timeline from these records is: direct compiled-only/player-position query at .3–.10 endpoints checked, including the now source-bound .6/.7/.9 gaps; a compiled-and-visible/player-position query beginning at .11 and continuing through 26.1.2. The later 26.2 callback producer/render path is outside this earlier-source audit and remains as independently audited in the primary report review.

## Correction status and limits

Supplement 01's initial .6/.9 “not ready” disposition accurately described the absence of modern ready markers and standalone `*.sources.sha256` files, but its broader decision not to make source claims is superseded. Supplement 02 supplies complete, independently verified legacy source maps and exact input declarations, and the readiness claims for both releases check out. Supplement 03 separately binds .7 with the same legacy-map method. These corrections do not assert modern-preparation ready markers or independently rehash the external source artifacts.

No source was generated or modified. No product, wiki, backlog, build, test, launch, or Git operation was performed. No finding is made about rendered output or runtime scheduling.
