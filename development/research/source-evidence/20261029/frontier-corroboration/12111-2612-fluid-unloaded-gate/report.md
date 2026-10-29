# 1.21.11 → 26.1.2 fluid unloaded-neighbor gate

## Bounded result

On the ordinary direct local-player tick path, the new `EntityFluidInteraction` collector can early-return when any full chunk in its expanded fluid-query envelope is unavailable. This does not require the local player to be a passenger. The old fluid-height/current sampler had an unloaded-chunk early return too, but the old eye-fluid update was a separate single-block query performed afterward and was not covered by that early return. Therefore, if the eye block itself is loaded and contains water while a different chunk in the new collector's envelope is missing, the old path can retain the eye-fluid result while the new collector leaves its eye tracker false. This is a conditional source-level effect; it does not assert that a particular runtime trajectory enters that state.

Vehicle simulation is excluded. No historical movement outcome beyond the source-controlled query/result difference is inferred.

## Exact source identity and hashes

Read-only canonical roots:

- `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.21.11\mojmap` — 1.21.11, Mojmap.
- `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\26.1.2\unobfuscated` — 26.1.2, published unobfuscated names.

The ready markers report `status=ready`, exact `versionId` and mapping values above, Vineflower, Java 25, and their provenance sidecars agree on the same `versionId`/namespace and exact-run mapping option. Their source-manifest SHA-256 values match recomputed sidecar bytes:

| Endpoint | Source manifest SHA-256 | Artifact manifest SHA-256 | Client input SHA-256 |
|---|---|---|---|
| 1.21.11 Mojmap | `0c4d83fef84c101d9db88f331acec7960f3c8346228c28995e6cf57bbfcae555` | `c8210b15012dcc4e109c2f73015b4117abec1f8ab04fdd375dbc11419e9e246c` | `1473c9489ac50fda3c435049a76a70d61a10b8610db27f5ba9d8756b686cd3bd` |
| 26.1.2 unobfuscated | `54ae320660ca911a6d20514c965c33eb414fbedd1ad6727bbaa95fe906aa23c0` | `89f648229de83b0109460695c9f1f3bed7ef36efb84ede9224e80cce87118f92` | `b1b3158572666445eff01e82fad8c7de2e4953db6d354f311730d77a8359d0b0` |

The checked source-file digests match the source-manifest rows:

| Source file | SHA-256 |
|---|---|
| 1.21.11 `net/minecraft/world/entity/Entity.java` | `32314478c6036fa9f3f3cc409c61c622eefc5a282d60e1011a1a33cc29cf18a3` |
| 1.21.11 `net/minecraft/world/level/LevelReader.java` | `562cd72343484c0e48f55dfe1ff50e5bb10516135d601aff264867fac9d9ff09` |
| 1.21.11 `net/minecraft/client/player/LocalPlayer.java` | `948e94f8e874b2f72e9a689e6e3ba1933f5728ec381d64f3bb5e2388718bd477` |
| 1.21.11 `net/minecraft/world/entity/player/Player.java` | `8e97167350a91741d0aa10d3b0d92a33150ed6dccdc94cd5b37d9c7ca22bcc81` |
| 26.1.2 `net/minecraft/world/entity/Entity.java` | `8b83b1f036aabbd13d990897c540c993f7120f02955486cfcf229517d4097ccf` |
| 26.1.2 `net/minecraft/world/entity/EntityFluidInteraction.java` | `5264ff4f1fddebc3fa9d63ad2edbe2eaf617392ff946a867a6817a78b478ee62` |
| 26.1.2 `net/minecraft/world/level/LevelReader.java` | `8fbfb7cf6d6ff8a19e784198d6bf75a1eae346cd8371d3d23eb3c8af546621a1` |
| 26.1.2 `net/minecraft/client/player/LocalPlayer.java` | `433fd995ad317af0f6ef0e50c1e8e3483cb8f00e0e327d4edf27a4dd99666ebe` |
| 26.1.2 `net/minecraft/world/entity/player/Player.java` | `44cf28e0c64e78d39fd13368e9991381dbebab67029070cb9ddc43f09d45d14d` |

## Caller and ordinary-player gates

In both endpoints `LocalPlayer.tick` calls `super.tick()` under `connection.hasClientLoaded()` (old `LocalPlayer.java:211–214`; new `:227–230`). `Player.tick` and `LivingEntity.tick` continue through `super.tick()` (old `Player.java:238–266`, `LivingEntity.java:2610–2611`; new `Player.java:231–259`, `LivingEntity.java:2699–2700`), reaching `Entity.tick` → `baseTick` (old `Entity.java:480–481`; new `:499–500`). The fluid update in `baseTick` is unconditional after that caller gate (old `Entity.java:484–506`; new `:503–524`); there is no passenger exclusion around it.

For a non-passenger player, the 26.1.2 base `getFluidInteractionBox` returns the bounding box deflated by `0.001`; it only calls `vehicle.modifyPassengerFluidInteractionBox` when a vehicle exists (`Entity.java:4015–4027`). The player implementation does not override that method in the checked pair. `Player.isPushedByFluid()` is `!abilities.flying` in both releases (old `Player.java:1677–1680`; new `:1671–1674`). Thus a regular non-flying local player collects and applies fluid current; a flying player still samples height and eye state, but the new collector receives `ignoreCurrent=true`, matching the old scan's current-collection guard. The old boat-specific water-state gate is false for a non-passenger.

## Paired query and gate behavior

### 1.21.11 Mojmap

`Entity.baseTick` calls `updateInWaterStateAndDoFluidPushing()` then `updateFluidOnEyes()` (lines 504–505). The first clears `fluidHeight`, scans water, then lava; the water scale is `0.014`, and the lava scale is the FAST_LAVA dimension value ? `0.007` : `0.0023333333333333335` (lines 1501–1521). Each `updateFluidHeightAndDoFluidPushing` first returns `false` if `touchingUnloadedChunk()` (lines 3509–3512). That gate checks the X/Z range of `getBoundingBox().inflate(1.0)` via `level.hasChunksAt(floor(minX), floor(minZ), ceil(maxX), ceil(maxZ))` (lines 3575–3583); `LevelReader.hasChunksAt` converts both inclusive block endpoints to section/chunk coordinates and checks every chunk inclusively (lines 197–209). On that early return it does not write a fluid-height entry; after the parent clear, `getFluidHeight` defaults to zero.

When admitted, old sampling uses `getBoundingBox().deflate(0.001)`, loops `floor(min)` inclusive to `ceil(max)` exclusive on X/Y/Z, and tests each `FluidState` against the requested tag (lines 3514–3548). It tracks `max(fluidBlockY + height - deflatedBox.minY, 0)` where the fluid top is at least the box minimum Y; if enabled, flow vectors are scaled when current tracked height is below `0.4`, accumulated and averaged. For players the average is preserved without normalization; the existing small-current floor and velocity write follow (lines 3549–3573). Those ordinary loaded-fluid current calculations are described only to delimit the gate and are not re-proposed here.

The old eye updater first snapshots the previous `isEyeInFluid(WATER)` value into `wasEyeInWater`, clears `fluidOnEyes`, then directly reads the single block at `BlockPos.containing(x, getEyeY(), z)` and adds its tags when `blockY + fluidHeight > eyeY` (lines 1524–1535). This query has no `touchingUnloadedChunk` preflight. Therefore it can still set the current eye-fluid tags after the height/current scan returned early for an unrelated missing neighboring chunk, provided the eye block query itself yields fluid.

### 26.1.2 unobfuscated

`Entity.baseTick` snapshots `wasEyeInWater = isEyeInFluid(WATER)` and then calls `updateFluidInteraction()` (lines 523–524). The latter calls `fluidInteraction.update(this, !isPushedByFluid())`, reads water/lava height trackers, resets fall distance for water, updates `wasTouchingWater`, and applies the same water/lava current scales only when `isPushedByFluid()` (lines 1558–1580). The eye snapshot is therefore still taken from the prior interaction sample before the new sample is reset.

`EntityFluidInteraction.update` resets every tracker first, gets the entity interaction box, and for a non-null box computes scan bounds as `floor(min)` through `ceil(max)-1` inclusive for X/Y/Z (lines 32–39). It preflights `hasFluidAndLoaded` over those bounds expanded by one block in X and Z, with the same Y bounds (line 42). The preflight converts all bounds to section coordinates, enumerates X/Z chunk coordinates inclusively, and calls `level.getChunk(chunkX, chunkZ, ChunkStatus.FULL, false)`; any null chunk returns false. For loaded chunks it ORs `LevelChunkSection.hasFluid()` over in-range vertical sections; out-of-array section indices are skipped. It returns true only if all chunks exist and at least one checked section has fluid (lines 91–119). A false result leaves all reset trackers at their defaults: height `0`, `eyesInside=false`, accumulated current `Vec3.ZERO`, count `0` (lines 155–163). The collector then scans the unexpanded interaction-box bounds only when preflight succeeds; eye sampling is integrated into that scan using `x == entity.getBlockX()`, `z == entity.getBlockZ()`, and `eyeY >= fluidBottom && eyeY <= fluidTop` (lines 43–89). Flow is accumulated only when `ignoreCurrent` is false (lines 73–82). `isEyeInFluid` and `getFluidHeight` read these trackers (Entity.java:1651–1652, 3610–3611).

The newer `LevelReader.hasChunksAt` uses the same inclusive endpoint-to-chunk checks as the old method (new `LevelReader.java:198–210`). The new helper itself requests full chunks without loading/generating (`EntityFluidInteraction.java:100–106`). Its extra `hasFluid` requirement is a no-fluid fast path: when no queried section contains any fluid, the scan's fluid result would remain empty. The unloaded-neighbor case is different for eyes because the old path's single eye-block read was outside its unloaded-chunk-gated sampler, while the new eye test is inside the collector-wide preflight.

## Conditional movement meaning and open dependencies

If the eye's block is loaded and contains water, while another chunk touched by the new expanded fluid-query envelope is unavailable, the old path can produce `isEyeInFluid(WATER)=true` for the current sample even though old water/lava height/current sampling was skipped. In the new path the missing chunk makes preflight false, so the collector leaves `eyesInside=false` along with zero heights/current. This immediately alters the current `isEyeInFluid(WATER)` predicate. If the unloaded-neighbor condition also holds at the next tick, it changes the next `wasEyeInWater` snapshot, which feeds `isUnderWater` while the water-height gate is true. This is a source-level conditional only; exact runtime chunk availability and any ensuing trajectory are unverified.

No passenger box-hook or vehicle fluid behavior is included. The outstanding external dependency for runtime applicability is whether the ordinary local-player client tick can coincide with a loaded eye block and a missing neighboring chunk in the computed preflight envelope; this assignment closes only the source control-flow question. No product/history/backlog/wiki files, canonical sources, tests, builds, launches, or Git state were changed.

