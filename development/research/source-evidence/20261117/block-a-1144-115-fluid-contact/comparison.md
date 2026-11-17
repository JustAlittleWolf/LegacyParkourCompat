# 1.14.4 → 1.15 Mojmap: fluid contact and travel comparison

## Authority and integrity

Read-only comparison authority: `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.14.4\mojmap` and `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.15\mojmap`. These are the published local trees used for every source citation below; provenance-referenced staging/cache paths were not used as source authority.

- 1.14.4 ready marker reports 3,250 files / 17,080,207 bytes and source-manifest SHA-256 `af98406f3d4ed31494fbdce1078d1f3df3f712501878f9a0e9ad5879bdf23c3b`. The actual manifest file has that digest, and all 3,250 listed files in the published tree match their per-file SHA-256 entries and byte total. Artifact-manifest digest `308cc33ef6dffc047432ade6affc88eccc9de94a736ee9a98571fd92915c2970` and diagnostics digest `d404e46059270c9283f6767edb6050959548253d2725443c95b6ab9cb49b1a61` also match the ready marker.
- 1.15 ready marker reports 3,329 files / 17,522,461 bytes and source-manifest SHA-256 `251a3019ccc3fd067f66365f3545b69a983383a28b19a1038debf1791be29340`. The actual manifest file has that digest, and all 3,329 listed files match their per-file SHA-256 entries and byte total. The ready marker's provenance digest `de4a538ebbaf82d1615683b08ecae3cd76553345f3079436764eb095be343949` matches `mojmap.provenance.json`; artifact-manifest digest `acc29974470e4f4864c695e1f1d084e746874663c47a365ca1fa41ff9fcc0cae` and diagnostics digest `419967c4867731bb36e93a729008f2f1543f8348ac5ed60f4de4e0c85f891c55` also match.
- The 1.14.4 provenance file's local SHA-256 is `9d1ad405e1e787d2fca5ed1897e6797b1f3cd79527da50d713232f46e2f30d97`, but its ready marker does not bind that file by digest. Its provenance is a summary excerpt and records mapping artifact names without their content hashes. The ready-bound source/artifact/diagnostics manifests still validate the published tree; do not treat the provenance JSON itself as marker-authenticated.

Relevant manifest-pinned source files: `net/minecraft/world/entity/Entity.java` (1.14.4 `31c6be42…d524a7`; 1.15 `191b3ad3…c0a10e`), `LivingEntity.java` (`42876217…d9ef681`; `139c3866…8e28bf79`), `LevelReader.java` (`1f86a029…50fdbc9`; `d09aa8ea…5241d37`), and `FlowingFluid.java` (`e8a39555…22dccdf` on both sides). The full digests are in the source manifests; every listed source file was verified against them.

## 1. Body-water contact/flow state and tick phase — no difference

Sources: 1.14.4 `Entity.java` lines 341–364, 960–994, 2588–2647; `LivingEntity.java` lines 237–241, 1782–1924. 1.15 `Entity.java` lines 348–374, 943–977, 2593–2652; `LivingEntity.java` lines 244–248, 1831–1973. The LocalPlayer tick entry is 1.14.4 `LocalPlayer.java` 177–195 and 1.15 `LocalPlayer.java` 178–196.

Both base ticks call `updateWaterState()` after sprint-state update and before the living AI/travel phase. That updater calls `updateInWaterState()`, then eye immersion, then swimming. `updateInWaterState()` clears the latch when riding a boat; otherwise it calls `checkAndHandleWater(WATER)`, conditionally splashes on entry after first tick, resets fall distance, sets `wasInWater`, and clears fire. The water query deflates the player AABB by `0.001`, floors minima and ceils maxima, iterates each axis with an exclusive upper bound, and returns false without sampling unless every covered chunk is loaded. For matching water whose surface reaches the deflated box minimum, it takes maximum depth, accumulates flow when `isPushedByWater`, scales each sampled flow by depth only while current maximum depth is below `0.4`, averages by sample count, normalizes only non-Players, adds `flow * 0.014` to velocity, stores water height and returns contact. `FlowingFluid.getFlow`'s neighbor order, height deltas, falling-fluid downward term and final normalization are identical; the `FluidState` delegation is identical.

There is also a same-version post-move phase: `LivingEntity.checkFallDamage()` calls `updateInWaterState()` when `!isInWater()`. `Entity.move()` calls that fall-damage hook after collision resolution and position update. Thus a move that enters water can latch contact and add flow after this tick's jump choice and initial travel-branch selection; the next tick's base-tick update precedes jump/travel. This ordering is the same in both releases. LocalPlayer's outer tick admission also remains a `hasChunkAt(BlockPos(x, 0, z))` check before `super.tick()`.

The WATER tag is a direct dependency of the body contact query and eye-immersion state (`isUnderLiquid(WATER, true)`). Eye immersion rejects boats, checks the eye block's chunk before reading fluid, and requires eye Y strictly below fluid-surface height plus `0.11111111F`; that predicate is unchanged.

## 2. Local lava input/current and velocity consumer — no difference

Sources: 1.14.4 `LivingEntity.java` lines 1770–1776, 1782–1792, 1864–1876 and 2217–2224; 1.15 `LivingEntity.java` lines 1819–1825, 1831–1841, 1913–1925 and 2282–2288. LocalPlayer inherits this travel implementation in both releases.

For an already-latched lava state, the ordinary local-player path (no flying) reaches the same lava branch after `aiStep` damps horizontal input (`xxa` and `zza`) by `0.98F` and supplies that input to `travel`. The branch applies `moveRelative(0.02F, input)`, moves using current velocity, halves resulting velocity, then adds `-d / 4.0` vertical gravity when gravity is enabled (`d` starts at `0.08`, or `0.01` under the same slow-falling gate). On horizontal collision it tests the same candidate clearance and, if clear, writes vertical velocity `0.3F`. The jump phase first calls `jumpInLiquid(LAVA)` under the same water-height/jumping conditions; `jumpInLiquid` adds `0.04F` vertically and has the same body in both versions. The supplied tag parameter is not read by that method. The earlier lava query/latch assignment is outside this slice as assigned.

The algorithms, gates, constants, casts and operation order match. The changes in many 1.15 expressions from direct `x/y/z` fields to `getX()/getY()/getZ()` do not change this behavior.

## 3. Fluid-exit clearance and loaded admission — no difference

Sources: 1.14.4 `Entity.java` lines 438–444, `LevelReader.java` lines 110–124 and 193–215, `LivingEntity.java` lines 1852–1860, 1873–1876 and 1920–1923. 1.15 `Entity.java` lines 442–448, `CollisionGetter.java` lines 43–57, `LevelReader.java` lines 112–134 and 147–175, `LivingEntity.java` lines 1901–1909, 1922–1925 and 1969–1972.

Both `isFree(dx,dy,dz)` methods translate the current player box and require `noCollision(entity, box) && !containsAnyLiquid(box)`. `containsAnyLiquid` uses the same floor-minimum / ceil-maximum, exclusive-upper-bound voxel scan and rejects any non-empty fluid state; it does not itself check loaded chunks. The collision predicate remains `getCollisions(...).allMatch(VoxelShape::isEmpty)`; 1.15 relocates this unchanged default from `LevelReader` to `CollisionGetter`. Both water and lava side-clearance responses use the same `velocity.y + 0.6F - currentY + savedY` candidate expression and set Y velocity to `0.3F` only when that `isFree` predicate succeeds.

Loaded admission is also unchanged within these paths: `checkAndHandleWater` first requires `hasChunksAt` across its sampled body box (rejecting below-zero or above-255 bounds); eye immersion with its loaded-check flag requires the eye's chunk; and the ordinary LocalPlayer tick checks the player's X/Z chunk at Y=0. In non-water/non-lava travel, the client-only missing-chunk fallback at the block below the player remains `y > 0 ? -0.1 : 0.0` before gravity handling.

`isFree` has an any-fluid dependency through `FluidState.isEmpty`, rather than a WATER- or LAVA-tag-specific test. The lava travel consumer depends on the already-computed `isInLava` latch; its only `FluidTags.LAVA` use in the covered input phase is the argument passed to the tag-agnostic `jumpInLiquid` implementation. The excluded earlier lava latch producer was not analyzed.

## Disposition

All three assigned residuals are exact-pair no-difference coverage. No candidate finding is supported for 1.14.4 → 1.15 in these slices; no canonical finding file is warranted. The comparisons above do not claim pair-wide inventory completion or independent audit/freeze.