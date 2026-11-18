# Fluid contact and first travel response — 1.20 to 1.20.1

## Bounded comparison

**Result: scoped Java equivalence.** No difference was found in the four assigned operations under the same effective fluid tags, world/fluid states, player state, and collision outcomes. This is an endpoint comparison of 1.20 and 1.20.1 only; it does not establish intermediate patch boundaries or runtime parity.

The reachable client path is `LocalPlayer.tick` (`client/player/LocalPlayer.java:189-192`) → `Player.tick` (`world/entity/player/Player.java:228-255`) → `LivingEntity.tick` / `baseTick` (`world/entity/LivingEntity.java:2238-2240, 335-345`) → `Entity.baseTick` (`world/entity/Entity.java:409-411, 413-437`). The local-player travel path enters `LivingEntity.travel` from its ordinary tick travel dispatch (`LivingEntity.java:2559-2573`) and then `Player.travel` wraps it (`Player.java:1445-1473`). The cited call and operation bodies are byte-identical in the paired sources.

### 1. Body-fluid admission and stored height

Both releases clear `fluidHeight`, update water contact/current first, then scan lava (`Entity.java:1176-1181`). Water contact sets `wasTouchingWater` from the water scan; `isInWater()` directly returns that field (`Entity.java:1137-1139, 1184-1197`). Lava height is retained by the same scan and `isInLava()` reads it only when `!firstTick` (`Entity.java:1280-1282, 3001-3065`). The scan rejects touching-unloaded-chunk cases, deflates the box by `0.001`, uses floor/ceil bounds, iterates x/y/z, admits tagged fluid whose computed surface is at least the deflated box minimum, and stores the maximum surface depth under the queried tag. All gates, bounds, numeric operations, state writes, and return values match.

### 2. Current accumulation and normalization

For an admitted cell, `updateFluidHeightAndDoFluidPushing` obtains `FluidState.getFlow`; while the current accumulated depth is below `0.4`, it scales that cell's flow by the depth, then adds it and increments the count (`Entity.java:3020-3043`). A nonzero sum is averaged by count. The `Player` override permits pushing only when not flying (`Player.java:1873-1876`); for a `Player`, the summed vector is not normalized at the general-entity normalization site (`Entity.java:3045-3053`). The result is scaled by the operation's supplied push amount. When horizontal velocity is below `0.003` on both axes and the resulting push length is below `0.0045000000000000005`, the normalized push receives that minimum magnitude before being added to velocity (`Entity.java:3054-3065`). This order and all constants are unchanged.

### 3. Eye-fluid writer and read order

`Entity.baseTick` updates body contact before calling `updateFluidOnEyes` (`Entity.java:413-437`). The eye writer first copies the previous `isEyeInFluid(WATER)` result into `wasEyeInWater`, then clears `fluidOnEyes`; it samples `getEyeY() - 0.11111111F`, gets the fluid at the containing position, and adds all of that fluid state's tags only when its surface is strictly above the sample (`Entity.java:1200-1212`). `isEyeInFluid(tag)` is direct set membership (`Entity.java:1276-1278`); `Player.tick` reads `isEyeInFluid(WATER)` into `wasUnderwater` before `super.tick()` (`Player.java:254-255, 313-316`); `Entity.baseTick` then refreshes body contact before refreshing eye tags. `isUnderWater()` combines the stored prior eye result with current body water contact (`Entity.java:1162-1164`). Writer order, strict comparison, float literal, and direct readers match.

### 4. First ordinary water/lava movement response

For a controlled, non-riding, non-flying local player on the ordinary travel branch, `Player.travel` performs its swimming vertical adjustment before calling `super.travel` (`Player.java:1445-1473`). `LivingEntity.travel` uses the local-control gate and `0.08` gravity (or `0.01` for downward slow falling); it samples the fluid at `blockPosition()`. The water branch requires body-water contact, fluid effects enabled, and no standable fluid. It applies sprint/water slowdown, caps Depth Strider at `3.0F` and halves it off ground, adjusts input speed, applies Dolphin's Grace slowdown, moves, handles climbing collision, applies `(slowdown, 0.8F, slowdown)`, then fluid-falling adjustment and the horizontal-collision free-space lift (`LivingEntity.java:2004-2047`). The lava branch has the same fluid-effect/standable gates; it moves first, chooses shallow-depth `(0.5, 0.8F, 0.5)` plus falling adjustment or deep-depth `0.5` scaling at the jump threshold, then applies `-gravity / 4.0` when gravity is enabled and the same lift gate (`LivingEntity.java:2048-2067`). `getFluidFallingAdjustedMovement` preserves its sprint/no-gravity bypass and the `0.003` comparisons/`gravity / 16.0` arithmetic (`LivingEntity.java:2184-2197`). These branch and operation bodies are unchanged.

## Remaining input gates

The Java equivalence is conditional on matching the effective fluid-tag membership resolved by the registry/data resources; `FluidState.is(tag)` and its tag stream read holder tags (`FluidState.java:104-106, 132-134`). It also depends on matching fluid states and heights in the scanned body box, eye sample, and horizontal/above/below neighbor reads used by `FlowingFluid.getFlow` and `getHeight` (`FluidState.java:49-50, 91-93; FlowingFluid.java:54-99, 429-437`). Dimension `ultraWarm`, chunk availability, player flying/sprinting/ground/effects, velocity, and collision/free-space results remain caller/world inputs. These resources and runtime inputs were not independently compared here. Custom datapacks, collision-shape behavior, non-fluid responses, and full-tick/runtime parity are outside this slice.

## Exact source identity and body-integrity check

Both inputs are pinned Mojmap decompilations. The 1.20 embedded `.decompile-provenance.json` pins Mojang 1.20 metadata (`1.20.json` SHA-256 `24f4700f86471b6f3d3e4d68fefcd4177820dade5ce003c095fcf0e429abbc5f`), client JAR (`5ac9ead135746f5f27048f2e01d8d5bdc2dabc4d91422f73ef2435f4c3d24aff`), and official client mappings (`client.txt`, SHA-256 `fdd921d6bcc7657850f78514d36bb57af0b8a340786d12c46ec18d9c46c039de`). The 1.20.1 ready marker pins release `1.20.1`, Mojmap, client JAR (`56b71336d2b4fdffd197f56595b0da93e32a946f78f382a299b8f4b92758bb0f`), and source-manifest SHA-256 `858b56764113e591c60d09bc63e4d90f9c1d67a705645f0c793e8b28f2378465`; its artifact manifest pins `client_mappings.txt` at SHA-256 `67bfc33ef0c7103f002d6ac134f8eddd3d67e2ce45d266e83816d1a2e2c9b01d`.

For each of the seven paired source files below, the actual SHA-256 matched 1.20's embedded source map and 1.20.1's `mojmap.sources.sha256`; the complete files are byte-identical across releases, so the cited method bodies and enclosing call sites are identical as well.

| Mojmap source file | SHA-256 in both releases |
| --- | --- |
| `net/minecraft/world/entity/Entity.java` | `94b9c3656715de2d61fa9a02ccef164261e50eb13d6090a6f07e58fe9c5b0759` |
| `net/minecraft/world/entity/LivingEntity.java` | `decf8cd70d194098ad51d0c82e5a0087e0687881e0a2dc66456f2ba6ea76676f` |
| `net/minecraft/world/entity/player/Player.java` | `873d82c6f5471d06812929e471d970fd973bfbe21638e4e348a6fcde45637dac` |
| `net/minecraft/client/player/LocalPlayer.java` | `69a2d043d2c0595bd364d625a5445e4934d9e07fef020cd79fafb42f59952ac2` |
| `net/minecraft/world/level/material/FluidState.java` | `0d6d1d24deb58f056163acc3ae8976d2c7b3394aca2ae37fa43bc560c9d33b7c` |
| `net/minecraft/world/level/material/Fluid.java` | `50ea2dadff56228aff11147c6da9fab224c7241d3b54bc4025b4d38d3d470546` |
| `net/minecraft/world/level/material/FlowingFluid.java` | `ba1dff6ccb28b62760c74d66832d8fdbe7a1cdc37b9c5a0ff476bd200215c904` |

Reviewed slices: body contact/storage and readers, fluid-current accumulation and player normalization gate, eye-fluid writer/order/readers, LocalPlayer→Player→LivingEntity tick/travel call path, ordinary water/lava travel branches and falling adjustment, plus FluidState tag/height/flow delegates and FlowingFluid height/flow bodies. No Java difference found in this bounded assignment; no positive finding is drafted.
