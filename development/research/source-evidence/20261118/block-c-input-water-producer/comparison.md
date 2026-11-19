# Water-state producers for local sprint gates — bounded source comparison

## Assignment and claim

This draft is limited to the producers and tick ordering of the water-contact fields directly read by the 1.14.3 and 1.14.4 local-player sprint paths. It does not audit the already-reviewed direct call sites, broader movement differences, implementation, or runtime parity.

**Bounded result:** For a normally loaded local-player AABB, the producer scans set the compared contact fields from corresponding fluid-overlap checks, and the entity base tick updates those fields before local sprint evaluation. No reachable divergence between 1.14.3 `insideWater` and 1.14.4 `wasInWater` was found in that scope. This conclusion excludes chunk-loading edge cases and does not claim full equivalence of the separate submersion fields or the entire sprint gate.

## Exact source identities

### Minecraft 1.14.3, Yarn `1.14.3+build.13`

Canonical tree: `decompiled_minecraft/1.14.3/yarn/`.

- Provenance: `decompiled_minecraft/1.14.3/yarn/.decompile-provenance.json`, SHA-256 `81ea0021eb122d4e3340e20d47d6ecb7079365ba767a038d26a8264cddacc28a`. The provenance names the exact intermediary 1.14.3 and Yarn 1.14.3+build.13 artifacts.
- `net/minecraft/entity/Entity.java`, SHA-256 `9b204a5f9af6ca50c09db4b6185ff37c39980c1a8cd1b5fd004713ecffae5c14`.
- `net/minecraft/client/network/ClientPlayerEntity.java`, SHA-256 `28b6b4031e02af4d2e6e6b4a80e5a7ca06f16dac42ca0f92cab25e2c2dc9e14ae`.

### Minecraft 1.14.4, Mojmap

Canonical tree: `decompiled_minecraft/1.14.4/mojmap/`.

- Ready marker `mojmap.ready.json` identifies version `1.14.4`, mapping `mojmap`, and ready status. The source manifest `mojmap.sources.sha256` has SHA-256 `af98406f3d4ed31494fbdce1078d1f3df3f712501878f9a0e9ad5879bdf23c3b`.
- Provenance `mojmap.provenance.json`, SHA-256 `9d1ad405e1e787d2fca5ed1897e6797b1f3cd79527da50d713232f46e2f30d97`, identifies the exact 1.14.4 Mojmap run and mapping artifacts.
- `net/minecraft/world/entity/Entity.java`, SHA-256 `31c6be42d165102d3e6dc4295fdfef6e8971fc3aea13f938a5b3a33b91d524a7`.
- `net/minecraft/client/player/LocalPlayer.java`, SHA-256 `0795c1223198ce5acf5d2ed9e5db8435bbec4cd52b96f96b1ddf2865baaae85f`.

The four consulted Java hashes match their local provenance/source-manifest entries.

## Producer and evaluation order

### 1.14.3 Yarn

`Entity.baseTick()` calls `updateInWater()` before the rest of base-tick processing (Entity.java:354). `updateInWater()` runs `checkWaterState()`, then `updateWetState()`, then `updateSwimming()` (935–937). `checkWaterState()` sets `insideWater` false for a boat; otherwise it assigns the result of `updateMovementInFluid(FluidTags.WATER)` and, on contact, resets fall distance and extinguishes fire (949–964). `updateWetState()` separately writes `inWater = isSubmergedIn(FluidTags.WATER, true)` (967). `isInsideWater()` reads `insideWater` (905–906); `isInWater()` is `inWater && isInsideWater()` (931–932).

The client sprint checks are in `ClientPlayerEntity.tickMovement()` (607ff). Sprint start reads `(!isInsideWater() || isInWater())` (657–664); sprint maintenance reads `isInsideWater()` and `isInWater()` (667–675). The enclosing `LivingEntity.tick()` runs base-tick processing before `tickMovement()`, so the water writes above precede these reads in the same entity tick.

`updateMovementInFluid(WATER)` contracts the bounding box by `0.001`, floors minimum and ceils maximum coordinates, returns false if the area is not loaded, then scans the covered blocks. For matching fluid, it sets the contact result when fluid surface height is at least the contracted box bottom and accumulates depth (Entity.java:2554ff).

### 1.14.4 Mojmap

`Entity.baseTick()` calls `updateWaterState()` before the rest of base-tick processing (Entity.java:359). `updateWaterState()` runs `updateInWaterState()`, `updateUnderWaterState()`, then `updateSwimming()` (960–963). `updateInWaterState()` sets `wasInWater` false for a boat; otherwise it assigns the result of `checkAndHandleWater(FluidTags.WATER)` and, on contact, resets fall distance and clears fire (974–990). `updateUnderWaterState()` separately writes `wasUnderWater = isUnderLiquid(FluidTags.WATER, true)` (992). `isInWater()` returns `wasInWater` (929–930); `isUnderWater()` is `wasUnderWater && isInWater()` (955–957).

The local sprint checks are in `LocalPlayer.aiStep()` (618ff). Sprint start reads `(!isInWater() || isUnderWater())` (668–676); sprint maintenance reads `isInWater()` and `isUnderWater()` (678–686). The enclosing `LivingEntity.tick()` runs base-tick processing before `aiStep()`, so the water writes above precede these reads in the same entity tick.

`checkAndHandleWater(WATER)` deflates the bounding box by `0.001`, floors minimum and ceils maximum coordinates, returns false if the covered chunks are unavailable, then scans the covered blocks. For matching fluid, it sets the contact result when fluid surface height is at least the deflated box bottom and accumulates depth (Entity.java:2588ff).

## Comparison and limits

For normally loaded player bounds, the contact producers use corresponding operations: inset the entity box by `0.001`; derive floor/ceil scan bounds; detect matching water whose surface reaches the lower box edge; and store the resulting boolean before local sprint evaluation. Their field names and method names differ, but the checked producer logic gives the same contact bit for the same player box and water layout.

The loaded-area guards are different APIs (`World.isAreaLoaded` versus `Level.hasChunksAt`). Unloaded or partially available AABBs are explicitly outside this no-difference result. The boat branch is also excluded from the positive comparison; both implementations explicitly force their contact field false for boats.

On a false-to-true contact transition after the first entity tick, the producer invokes a version-specific splash/start callback (`onSwimmingStart()` in 1.14.3; `doWaterSplashEffect()` in 1.14.4) before storing true. This callback path is excluded from claims about callback equivalence; the comparison here concerns the contact-field write and its ordering. The distinct submersion producer (`inWater` versus `wasUnderWater`) and other sprint gates are also outside this bounded producer verdict.

No minimal reachable divergent contact-field witness was found within the stated loaded-AABB scope. No source files were edited, and no tests, builds, game launches, or runtime checks were performed.

## Previous direct-read source revision

The read-site comparison audited as the lead for this producer follow-up is the immutable revision at pinned commit `1aae7a9f`:

`development/research/source-evidence/20261118/block-c-pair-gates-and-aliases/early-input-scaling/comparison-r2.md`

SHA-256: `213b14ff38e666aa6ba7f52fe94139b7e38f0fb4c0c5119f6a5fa1923a9bc7c0`.

That revision established the direct source distinction (`insideWater`/`isInsideWater()` versus `wasInWater`/`isInWater()`) and left producer equivalence open. This draft closes only that bounded loaded-AABB producer question.

