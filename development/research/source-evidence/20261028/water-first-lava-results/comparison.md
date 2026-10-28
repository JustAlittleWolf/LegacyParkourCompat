# Source closure r2: legacy water-first gate and tick lifetime

## Bound inputs

This revision supplements but does not edit `REPORT.md` (SHA-256 `d95cf93450707328f7579b09fcbc7bf4493ea969bf688bf651ad6d72f35126cc`). It uses the same canonical read-only source root and source identities documented there: Mojmap 1.16 / 1.16.1 / 1.16.2 and published unobfuscated 26.2. Recomputed hashes for newly examined `LivingEntity.java` files match their source manifests; 1.16 and 1.16.1 are byte-identical for Entity, LivingEntity, LevelReader, FluidState and FlowingFluid in the examined slices.

Additional source digests (SHA-256):

| Version/mapping | `LivingEntity.java` | `LevelReader.java` | `FluidState.java` | `FlowingFluid.java` |
|---|---|---|---|---|
| 1.16 / Mojmap | `b6ea2b9b037b79fc3ea6e1f6bcd2c4c1d89d4a96315968a8f247641f6c335f26` | `d301b992d71b05306a3139e69f3f1529190f82404791ec60f9a693188d29577a` | `665b4024a32f659d92ce1259b25f529616e44b6f3df8a6aab4a4a2c57c7bc26d` | `a85a5cd6625b1b4a047bd693399e1f78c2978ae207af927d4abfac3b34229552` |
| 1.16.1 / Mojmap | same as 1.16 | same as 1.16 | same as 1.16 | same as 1.16 |
| 1.16.2 / Mojmap | `b5d8a1a3c80f85d5d545b5a777e9e2a915dc002a7e31b5f5ad12bf7e285d7a88` | same as 1.16 | same as 1.16 | same as 1.16 |
| 26.2 / unobfuscated | `7ffd9c70966edc50c9cb4d9a8fe17a518e2678ff44c8026e763d0b94ac0ae51a` | `8fbfb7cf6d6ff8a19e784198d6bf75a1eae346cd8371d3d23eb3c8af546621a1` | `e35608d5775013c67653ea350309b5150a97f36aa65c27f1c6247ae2aa840f18` | `f8c0c4430e81efdf39b221968272ed8e3a4d030fa5192e04e04d34c9fbd37b28` |

## Exact legacy WATER-presence probe

In Mojmap 1.16 / 1.16.1 `Entity.updateInWaterStateAndDoFluidPushing()` clears `fluidHeight`, calls `updateInWaterStateAndDoWaterCurrentPushing()`, then tests the newly set `isInWater()` value (`Entity.java:947-956`). The helper's branch is exact: Boat passenger ⇒ set `wasTouchingWater=false` without a water scan; otherwise call `updateFluidHeightAndDoFluidPushing(WATER, 0.014)` and set `wasTouchingWater` to that result (`Entity.java:958-972`). Thus the water-first / skipped-LAVA predicate for a non-Boat entity is the Boolean return of that exact WATER query, not 26.2 `isInWater()`, prior `wasTouchingWater`, or `getFluidHeight(WATER)>0`.

The old WATER query contract, from `Entity.java:2633-2697`, is:

1. Use `getBoundingBox().deflate(0.001)`; it does not call `getFluidInteractionBox()`.
2. Set block bounds to `floor(minX/minY/minZ)` and `ceil(maxX/maxY/maxZ)`. `hasChunksAt(i,k,m,j,l,n)` runs before any cell reads; if false, the query returns false. In 1.16/.1/.2 `LevelReader.hasChunksAt(int i,int j,int k,int l,int m,int n)` accepts only when `m >= 0 && j < 256`, then checks every X/Z chunk from `i >> 4` through `l >> 4` and `k >> 4` through `n >> 4` using `hasChunk`; it is an any-intersection vertical gate, not a requirement that the whole AABB remain in 0..255. 26.2 `LevelReader.hasChunksAt` instead clips against the world's dynamic `getMinY()/getMaxY()` (`LevelReader.java:193-212`), so calling that current overload alone would not reproduce the old vertical bound in every world.
3. Visit exactly `p=i; p<j`, `q=k; q<l`, `r=m; r<n` (max indices exclusive). Read `FluidState` for each cell.
4. Test `fluidState.is(FluidTags.WATER)`—a fluid-tag predicate. There is no `BlockState.getMaterial()`/water-block predicate or other material gate.
5. For tagged cells calculate `f = q + fluidState.getHeight(level, mutableBlockPos)`. A hit is `f >= deflatedAabb.minY` (inclusive); equality counts even when the recorded maximum depth is zero. It is not equivalent to requiring positive stored depth.
6. `isPushedByFluid()` controls only whether flow is read/accumulated, not the hit Boolean. The presence probe must not query flow, change WATER current/height, clear/write the map, mutate `wasTouchingWater`, reset fall distance, splash or clear fire. Those are separate effects of the historical helper/full update.

For vanilla water, the inspected 1.16/.1/.2 `FlowingFluid.getHeight` is `1.0F` when the same fluid is above, otherwise `getAmount()/9.0F`; the inspected 26.2 method has the same formula. This supports using `FluidState.getHeight` in a target-side presence probe, subject to the exact level/tag inputs. It does not make the full modern fluid scan equivalent: modern scanning uses its adjusted interaction box, modern cell/load optimization, and tracker-positive depth predicate.

A faithful *presence-only* probe can short-circuit after the first qualifying cell because only the Boolean hit is needed; it must keep the old bounds, chunk gate, tag test and inclusive top-vs-minY comparison. If preserving exact source read order is required, finish the old nested scan, but do not reproduce its flow/current/depth writes as part of this gate.

## Old and modern call order / state lifetime

### 1.16 / 1.16.1 / 1.16.2 Player path

- `Entity.tick()` calls `baseTick()` (`Entity.java:346-352`, 1.16/.1; `354-360`, 1.16.2). `Entity.baseTick()` performs the full body-fluid update before eye-fluid update and swimming (`Entity.java:372-374`, 1.16/.1; `380-382`, 1.16.2). For 1.16/.1, compute the gate at this pre-movement base-tick point, after the same entity/vehicle position is established and before player movement for the tick.
- On the normal Player movement path, a later `checkFallDamage()` may run from `Entity.move()`, after the post-collision position update (`Entity.java:502` in 1.16/.1, `518` in 1.16.2). `LivingEntity.checkFallDamage()` invokes only `updateInWaterStateAndDoWaterCurrentPushing()` and only when `!isInWater()` (`LivingEntity.java:256-259` in 1.16/.1/.2). This follow-up may refresh WATER height/current and `wasTouchingWater`; it neither clears `fluidHeight` nor scans LAVA. The base-tick LAVA map value—absent/default zero in the water-first branch—therefore remains the LAVA body-height state through this later water-only refresh. The base-tick gate must be retained; it must not be recomputed from the later water helper result.
- Other old full-update callers are `ItemEntity`, `PrimedTnt`, and `AbstractMinecart`; they are outside the Player-only movement projection. The direct full-update caller for a Player is `Entity.baseTick()`. `ServerPlayer.doCheckFallDamage()` is also a server-side caller of the LivingEntity fall-damage path in these sources; it is outside historical logical-client movement.
- 1.16.2 always measures LAVA after the water helper (`Entity.java:957-963`), so this skipped-LAVA projection is specifically the 1.16/.1 behavior; it must not be applied to .2 as if .2 retained the early return.

### 26.2 Player path

- `Entity.tick()` calls `baseTick()` (`Entity.java:506-508`); `Entity.baseTick()` calls `updateFluidInteraction()` (`Entity.java:530-532`). `LivingEntity.tick()` proceeds through `super.tick()` and then `aiStep()` (`LivingEntity.java:2765-2805`), so this unified scan is the pre-movement/base-tick scan.
- `Entity.move()` updates position at `Entity.java:751-755`, then, for a local-authoritative instance, invokes `checkFallDamage()` at `777-778`. `LivingEntity.checkFallDamage()` calls `updateFluidInteraction()` again only if current `isInWater()` is false (`LivingEntity.java:363-366`). This is a post-move scan and it resets/replaces the private trackers before it applies current.
- For a base-tick-dry Player that enters water during movement, that post-move scan may see water and set modern `wasTouchingWater`; a marker recomputed from this call's modern water result would incorrectly turn on the historical skip-LAVA projection mid-tick. Conversely, if modern water-presence disagrees with the old probe at the base-tick pose, a base-tick-wet old marker must remain latched even if the later call happens. The correct lifetime for this narrow gate is a base-tick decision retained through all later fluid refreshes until the next base-tick recomputes it.
- `Entity.doCheckFallDamage()` can also dispatch to `LivingEntity.checkFallDamage()` (`Entity.java:1544-1550`); vanilla source callers are server packet handling. It is not the local-authoritative Player movement caller and historical movement is client-only, but any future client-side caller would need the same retained decision rather than making a new gate from that invocation.

## Narrow candidate boundary and assessment

A source-faithful narrow candidate is structurally possible for eligible client Players in 1.16/.1 profiles: compute the pure legacy WATER-presence predicate once at the 26.2 baseTick fluid point; retain that Boolean until the next baseTick; when true, mask only public `Entity.getFluidHeight(LAVA)` for that interval and suppress only `fluidInteraction.applyCurrentTo(LAVA, ...)` on every `updateFluidInteraction()` call in that interval. Do not reset/recompute the latch from later calls, and do not clear tracker height/current/eyes. In 26.2, private tracker height continues to feed modern `isInLava()` and private `eyesInside` remains modern-owned; public depth and applied velocity are separately routed. A later post-move reset can replace internal LAVA values without escaping the public mask or applying its current while the base-tick latch remains true.

This candidate is narrower than full old tick equivalence. It leaves the old collision-callback `isInLava()` meaning, eye cache, WATER state/current/numerics, and old non-skipped-LAVA branch untouched. It also does not address a 26.2 post-move LAVA current application when the base-tick old WATER probe was false; legacy checkFallDamage only repeats WATER work, so that is a separate non-skipped-branch refresh delta. Do not infer that skipping the second call in every circumstance or suppressing its modern `isInLava()` state follows from this narrow source contract.

## Remaining gates for reviewer

- Decide scope explicitly as 1.16/.1 only; .2 has no skip-lava gate.
- Verify the implementation's probe uses the old entity AABB and old numeric block/chunk/vertical bounds, including inclusive `fluidTop >= minY` and the Boat bypass; do not substitute modern `inWater`.
- Verify the latch is written only at baseTick and survives `checkFallDamage`/post-move `updateFluidInteraction()` until next baseTick. The post-move call may refresh modern tracker data but must not replace the decision.
- Verify public LAVA-depth masking covers the player-visible `getFluidHeight(LAVA)` consumers while not changing tracker-backed `isInLava()` or eyes. Existing source consumers include LivingEntity fluid jump/travel paths and other entity classes; the profile eligibility guard is required so non-player entities retain native behavior.
- Keep the modern 26.2 vertical world boundary and exact 1.16 tag-resource membership as explicit compatibility questions if the task expects equivalence on nonstandard dimensions or data packs; source probe logic alone does not bind external fluid-tag contents.
- No source evidence here closes current/eye parity, runtime behavior, intermediate version boundaries, or the separate false-water post-move refresh delta.

## Exact source slices

- 1.16 / 1.16.1 Mojmap `net/minecraft/world/entity/Entity.java`: `tick/baseTick` 346-374; `move` post-position `checkFallDamage` 502 and later `isTouchingLava` reset / `checkInsideBlocks` 551-559; WATER helper and early return 947-972; probe algorithm 2633-2697.
- 1.16 / 1.16.1 Mojmap `net/minecraft/world/entity/LivingEntity.java`: `checkFallDamage` water-only refresh 255-259; `baseTick` calls `super.baseTick` at 288-299.
- 1.16.2 Mojmap `net/minecraft/world/entity/Entity.java`: `tick/baseTick` 354-382; `move` checkFallDamage 518; full fluid update 957-979. `LivingEntity.java` water-only refresh 257-260.
- All 1.16 lineages Mojmap `net/minecraft/world/level/LevelReader.java`: `hasChunksAt` 163-187; `FluidState.java` `getHeight` 40-42; `FlowingFluid.java` `hasSameAbove/getHeight/getOwnHeight` 463-475.
- 26.2 unobfuscated `net/minecraft/world/entity/Entity.java`: `tick/baseTick` 506-532; move/post-move checkFallDamage 751-779; server-facing `doCheckFallDamage` 1544-1550; unified update/current 1645-1669; private tracker-backed `isInLava` 1742-1744 and public height wrapper 3683-3685.
- 26.2 unobfuscated `net/minecraft/world/entity/LivingEntity.java`: `checkFallDamage` re-scan 363-366; `tick`/`aiStep` order 2765-2805.
- 26.2 unobfuscated `net/minecraft/world/level/LevelReader.java`: modern `hasChunksAt` y gate and loaded-chunk loop 193-212; `FluidState.java` `getHeight` delegates at 95-96; `FlowingFluid.java` height formula 466-477.
- 26.2 `ServerGamePacketListenerImpl.java`: server-only `doCheckFallDamage` use sites 509, 1173 and 1195 (exact overload call paths).

No product, candidate implementation, history, backlog, wiki, or unrelated lane material was read or changed; the assigned audit was read as required. No tests, build, launch or source generation was run.

