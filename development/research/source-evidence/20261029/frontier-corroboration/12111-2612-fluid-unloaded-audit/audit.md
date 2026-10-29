# Independent source audit: 1.21.11 → 26.1.2 fluid unloaded-neighbor gate

## Verdict

The exact source identities and all source-file hashes cited by `report.md` match the canonical ready markers and source manifests. The new 26.1.2 collector can fail its full-chunk preflight on the client when an expanded-envelope chunk is absent, and its reset trackers then make the current eye-water predicate false. A narrowly bounded old-versus-new eye-predicate difference is source-supported.

The report's stated old-side unloaded early return is not reachable on a 1.21.11 local client: `ClientLevel.hasChunk(int, int)` unconditionally returns `true`, and the inherited `LevelReader.hasChunksAt` preflight dispatches to that override. Therefore the old water/lava height/current sampler does not skip on account of a missing client chunk. The report's stronger eye-difference scenario that depends on old height/current sampling being skipped, and its same-next-tick `isUnderWater` consequence, need correction. This audit does not establish any runtime occurrence or trajectory.

## Source identity and hashes

Canonical mapping roots checked read-only:

- `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.21.11\mojmap`
- `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\26.1.2\unobfuscated`

Their release-parent sidecars identify `1.21.11` / `mojmap` and `26.1.2` / `unobfuscated`, both ready, Vineflower, Java 25. The matching provenance sidecars have the same version and namespace and exact-run mapping options `mojmap` and `unobfuscated`. The recorded source-manifest hashes match the actual sidecar bytes; the artifact-manifest hashes also match. Ready-marker client hashes are `1473c9489ac50fda3c435049a76a70d61a10b8610db27f5ba9d8756b686cd3bd` and `b1b3158572666445eff01e82fad8c7de2e4953db6d354f311730d77a8359d0b0`, respectively.

| Endpoint | Ready source-manifest SHA-256 = recomputed | Ready artifact-manifest SHA-256 = recomputed |
|---|---|---|
| 1.21.11 Mojmap | `0c4d83fef84c101d9db88f331acec7960f3c8346228c28995e6cf57bbfcae555` | `c8210b15012dcc4e109c2f73015b4117abec1f8ab04fdd375dbc11419e9e246c` |
| 26.1.2 unobfuscated | `54ae320660ca911a6d20514c965c33eb414fbedd1ad6727bbaa95fe906aa23c0` | `89f648229de83b0109460695c9f1f3bed7ef36efb84ede9224e80cce87118f92` |

Every source path and digest cited by the report was recomputed and matched its corresponding release-parent source-manifest row. Additional files needed to resolve client dispatch were also verified against those manifests:

| Endpoint | Source file | SHA-256 |
|---|---|---|
| 1.21.11 Mojmap | `net/minecraft/world/entity/Entity.java` | `32314478c6036fa9f3f3cc409c61c622eefc5a282d60e1011a1a33cc29cf18a3` |
| 1.21.11 Mojmap | `net/minecraft/world/level/LevelReader.java` | `562cd72343484c0e48f55dfe1ff50e5bb10516135d601aff264867fac9d9ff09` |
| 1.21.11 Mojmap | `net/minecraft/client/player/LocalPlayer.java` | `948e94f8e874b2f72e9a689e6e3ba1933f5728ec381d64f3bb5e2388718bd477` |
| 1.21.11 Mojmap | `net/minecraft/world/entity/player/Player.java` | `8e97167350a91741d0aa10d3b0d92a33150ed6dccdc94cd5b37d9c7ca22bcc81` |
| 1.21.11 Mojmap | `net/minecraft/world/entity/LivingEntity.java` | `19ed2d565858c401c69a06750b054a633a20ea864cab3a753b5c767f0bdd60e8` |
| 1.21.11 Mojmap | `net/minecraft/client/multiplayer/ClientLevel.java` | `e76d09de5acad450062d5acb98e76ff032ecb179fef0f7af434e4148f84183a2` |
| 1.21.11 Mojmap | `net/minecraft/client/multiplayer/ClientChunkCache.java` | `d260b64a3985638cd38408ddf1c46a1f0c21746764fcad17e773f5e89b5f861a` |
| 1.21.11 Mojmap | `net/minecraft/world/level/Level.java` | `de329fdbeca632a1b63074e1735890b2624abfc57b7a579f43eef36caefef98d` |
| 26.1.2 unobfuscated | `net/minecraft/world/entity/Entity.java` | `8b83b1f036aabbd13d990897c540c993f7120f02955486cfcf229517d4097ccf` |
| 26.1.2 unobfuscated | `net/minecraft/world/entity/EntityFluidInteraction.java` | `5264ff4f1fddebc3fa9d63ad2edbe2eaf617392ff946a867a6817a78b478ee62` |
| 26.1.2 unobfuscated | `net/minecraft/world/level/LevelReader.java` | `8fbfb7cf6d6ff8a19e784198d6bf75a1eae346cd8371d3d23eb3c8af546621a1` |
| 26.1.2 unobfuscated | `net/minecraft/client/player/LocalPlayer.java` | `433fd995ad317af0f6ef0e50c1e8e3483cb8f00e0e327d4edf27a4dd99666ebe` |
| 26.1.2 unobfuscated | `net/minecraft/world/entity/player/Player.java` | `44cf28e0c64e78d39fd13368e9991381dbebab67029070cb9ddc43f09d45d14d` |
| 26.1.2 unobfuscated | `net/minecraft/world/entity/LivingEntity.java` | `c3b64de8dbaba8ad8a7ccd4f33255346d8206e66f12ca91260971bf5e5ac93bd` |
| 26.1.2 unobfuscated | `net/minecraft/client/multiplayer/ClientLevel.java` | `6e46db964229bf53bb88c33140cfa9acb5423dbed11a7c721b5b8bf9a7424de7` |
| 26.1.2 unobfuscated | `net/minecraft/client/multiplayer/ClientChunkCache.java` | `1a35ecd0c18d989bed9ef883028e197e29f98aadc78d909727722faba9a912a5` |
| 26.1.2 unobfuscated | `net/minecraft/world/level/Level.java` | `77cc835fc1a79cd970bc3f4897fe5f398958fcbc874400c297f465f0c7819f01` |

## Dispatch and ordinary local-player path

In each release, `LocalPlayer.tick()` calls `super.tick()` inside `connection.hasClientLoaded()` (`1.21.11 LocalPlayer.java:211–214`; `26.1.2 LocalPlayer.java:227–230`). The inherited player/living-entity tick chain reaches `Entity.tick()` and `Entity.baseTick()`. The base-tick fluid calls have no passenger exclusion (`1.21.11 Entity.java:480–506`; `26.1.2 Entity.java:499–525`). The report's ordinary-path caller sequence is supported.

### Old endpoint: client override makes the old broad gate ineffective

`Entity.updateInWaterStateAndDoFluidPushing()` clears `fluidHeight`, then samples water and lava; each `updateFluidHeightAndDoFluidPushing` tests `touchingUnloadedChunk()` before scanning (`Entity.java:1501–1521, 3509–3573`). `touchingUnloadedChunk()` queries `level().hasChunksAt(...)` for the bounding box inflated by one block (`Entity.java:3576–3583`). The inherited `LevelReader.hasChunksAt` converts the inclusive X/Z endpoints to chunk coordinates and iterates `this.hasChunk(...)` (`LevelReader.java:197–211`).

The virtual call is decisive on a client. `ClientLevel` overrides `hasChunk(int, int)` as `return true` (`ClientLevel.java:392–394`). Thus for `LocalPlayer.level()` backed by `ClientLevel`, that inherited `LevelReader` loop returns true even when the client chunk cache lacks a neighbor. The old `touchingUnloadedChunk()` therefore returns false and does not abort the old sampler for this condition. This is not merely a difference in the generic interface's implementation; it is the method dispatched on the local-player client path.

With a non-passenger local player, the old boat-only water gate is false. The old sampler then scans the deflated player bounding box and samples matching fluid states, computes height, and may accumulate/apply current. `baseTick` next calls `updateFluidOnEyes()`, which snapshots the previous eye-water predicate, clears eye tags, directly reads the block at `(x, eyeY, z)`, and adds its fluid tags when the fluid top is strictly above eye Y (`Entity.java:1524–1535`). This eye-block read is separate from the old height/current scan. Under the client override, however, it is inaccurate to say the missing-neighbor condition caused that earlier scan to skip.

### New endpoint: collector preflight can fail on the client

`Entity.baseTick()` snapshots the prior `isEyeInFluid(WATER)` result, then calls `updateFluidInteraction()` (`Entity.java:503–525`). For a non-passenger, base `getFluidInteractionBox()` returns the bounding box deflated by `0.001`; the vehicle modification hook only runs when a vehicle exists (`Entity.java:4015–4027`). `Player` does not override `getFluidInteractionBox` or the local-player subclasses in this pair.

`EntityFluidInteraction.update()` resets every tracker before querying that box. Its integer bounds are `floor(min)` through `ceil(max)-1`; the preflight expands those bounds by one block in X/Z, keeping Y unchanged (`EntityFluidInteraction.java:32–42`). `hasFluidAndLoaded` converts those limits to section/chunk coordinates and calls `level.getChunk(..., ChunkStatus.FULL, false)` for each X/Z chunk; the first null chunk aborts (`:91–119`). The call reaches `Level.getChunk`, which delegates through virtual `getChunkSource()` (`Level.java:201–208`); `ClientLevel.getChunkSource()` supplies the client cache (`ClientLevel.java:694–696`). `ClientChunkCache.getChunk(..., false)` returns null when no valid cached chunk is present or in range (`ClientChunkCache.java:69–78`). This collector gate can therefore fail on the local client, despite `ClientLevel.hasChunk` returning true.

On a false preflight, tracker reset remains in force: height `0.0`, `eyesInside=false`, accumulated current `Vec3.ZERO`, count `0` (`EntityFluidInteraction.java:153–164`). The later eye test is inside the admitted scan (`:43–89`); `isEyeInFluid` and `getFluidHeight` read these trackers (`:139–151`). `Entity.updateFluidInteraction()` consumes water/lava trackers and applies current only if the corresponding sampled height is positive and the player is pushed by fluid (`Entity.java:1558–1581`). Player `isPushedByFluid()` is `!abilities.flying` in both versions (`Player.java:1677–1680` and `:1671–1674`); a flying state suppresses current, but does not disable height/eye sampling. This gate is not a passenger gate.

## Bounded comparison and corrections

A sufficient source-level condition for an immediate eye-predicate difference is: the ordinary non-passenger local player's old unexpanded scan blocks are in loaded chunks; the eye block is loaded and its water-fluid surface is above eye Y; and a different chunk is absent but is included only by the new collector's one-block X/Z expanded preflight envelope. The old sampler is not stopped by `touchingUnloadedChunk()` on `ClientLevel`, and its direct eye-block read can set water eye state. The new preflight returns false, leaving `eyesInside=false`. Exact chunk availability/position co-occurrence is untested and unclaimed.

The report should be corrected to remove the claim that this client-side missing neighbor makes the old height/current scan skip. Its old sampler gate exists in general source but is neutralized on `ClientLevel` by the `hasChunk` override. Also narrow “eye block contains water” to a fluid surface above eye Y (the old test is strict `fluidTop > eyeY`; the new in-scan test is inclusive at the top).

The report's claimed same-next-tick `isUnderWater` consequence is not supported under the stated repeated-missing-neighbor condition. `isUnderWater()` is `wasEyeInWater && isInWater()` (`Entity.java:1527–1528`); when the new preflight aborts on that tick, the just-reset water height makes `isInWater()` false. A later tick could expose a carried eye-snapshot difference if its preflight succeeds, its water height is positive, and the prior eye trackers differed. That is only a conditional source-level possibility, not a runtime result.

No other files were changed. No tests, builds, launches, or Git operations were performed.
