# Independent AF patch alias audit: Mojmap 1.16 sequence

## Scope and verdict

Compared the exact Mojmap sources for 1.16 → 1.16.1 → 1.16.2 → 1.16.3 → 1.16.4 → 1.16.5, limited to ordinary `LocalPlayer` suffocation response and its suffocating block inputs, Soul Speed support lookup, and entity water/lava current sampling plus the related lava-presence query.

**Earliest observed source change in this sequence: 1.16.2.** The assigned slices match between 1.16 and 1.16.1; the relevant 1.16.2 source is then byte-identical in the 1.16.3, 1.16.4 and 1.16.5 decompilations. The independently audited slices are source-confirmed, but this bounded audit does not freeze the whole 1.16.1--1.16.2 movement pair: the complete reachable player-movement inventory and all other terminal slices are outside this assignment.

## Exact source identities

Mapping family is Mojmap/official client mappings in every release. For 1.16, 1.16.1, 1.16.2 and 1.16.5, the `mojmap.sources.sha256` file's hash matches the corresponding ready marker's `sourceManifestSha256`; the `artifacts.sha256` hash likewise matches `artifactManifestSha256`. The audited source-file hashes below match those source manifests.

For 1.16.3 and 1.16.4, there are no release-root ready, root provenance, source-manifest or artifact-manifest files. Each `mojmap/` instead contains `.decompile-provenance.json` with an embedded source-hash map and exact client, client-mapping, metadata and tool-input hashes. The six audited source files match those embedded hashes. Cached `version.json`, `client.jar` and `client_mappings.txt` exist for both releases; their current bytes match the three `.pin.json` SHA-256 values and the input hashes in the embedded provenance. These are exact source identities without ready publication markers.

The root ready/provenance records and source/artifact manifest hashes for 1.16, 1.16.1, 1.16.2 and 1.16.5 are present and internally consistent. Their recorded campaign cache/staging paths are no longer present, so the client, mapping and metadata binaries could not be re-hashed from those cache locations in this audit. The ready markers and artifact manifests preserve their recorded SHA-256 values. 1.16's provenance records a complete stdout/stderr success log; 1.16.1 and 1.16.2 record full Gradle output; 1.16.5 records only a summary excerpt. No hidden `.*mojmap.previous-*` directory was present beside any of the six release mapping trees.

| Release | LocalPlayer.java | Entity.java | LivingEntity.java | Blocks.java | BlockBehaviour.java | BlockTagsProvider.java |
| --- | --- | --- | --- | --- | --- | --- |
| 1.16 | `ee20523a…20dfb8b` | `27e3bb01…616576` | `b6ea2b9b…335f26` | `61f18f90…1313a9` | `1b524b5c…18ef7f` | `bba0bac8…34df7b` |
| 1.16.1 | `ee20523a…20dfb8b` | `27e3bb01…616576` | `b6ea2b9b…335f26` | `61f18f90…1313a9` | `1b524b5c…18ef7f` | `bba0bac8…34df7b` |
| 1.16.2 | `6011569e…f6a8c2b` | `f9a9a073…5ee2666` | `b5d8a1a3…285d7a88` | `3b39d5cc…7795fa12` | `c62e2f07…472def8b` | `9f084139…ca4c239` |
| 1.16.3 | same as 1.16.2 | same | same | same | same | same |
| 1.16.4 | same as 1.16.2 | same | same | same | same | same |
| 1.16.5 | same as 1.16.2 | same | same | same | same | same |

Abbreviated hashes identify the full audited-file SHA-256 values recorded in the applicable source manifest or embedded provenance. All six files were present and hash-verified for each release.

## Findings by operation

### Ordinary client-player suffocation response and block predicates

**1.16 and 1.16.1:** `client/player/LocalPlayer.java`, `checkInBlock` (around lines 414–483), is called at the four horizontal offsets `±getBbWidth()*0.35` with `y + 0.5` in `aiStep` (lines 682–685). The response samples a block column from each point. `blocked` walks integer y from `floor(boundingBox.minY)` to `ceil(maxY)` and calls inherited `Player.freeAt`; `Player.java:1421–1423` defines `freeAt` as the negation of the block state's `isSuffocating(level,pos)`. When a sampled column blocks, the code tests its four horizontal neighbors and writes a fixed `0.1` horizontal velocity toward the closest free side, preserving the other velocity components.

**1.16.2 through 1.16.5:** `LocalPlayer.java`, `moveTowardsClosestSpace` and `suffocatesAt` (lines 414–449 in 1.16.2; same source hash afterward), replaces that operation. The same four horizontal offsets are called in `aiStep` (1.16.2 lines 657–660), gated by `!noPhysics`. The probe uses the player's current y in `BlockPos`, forms a box from that block's x/z cell and the player's full bounding-box vertical extent, deflates it by `1.0E-7`, and calls `level.noBlockCollision` with `BlockState.isSuffocating` as a filter. The collision helper in `world/level/CollisionGetter.java` reduces to `getBlockCollisions(...).allMatch(VoxelShape::isEmpty)`: the changed probe therefore requires a nonempty block collision shape intersecting the test box as well as a true suffocation predicate. A selected open neighbor gets horizontal velocity `0.1 * direction.stepX/stepZ`, with the other components preserved. The player consequence is a changed push-out test when the box-column predicate and actual collision-shape intersection differ.

The 1.16.2 `Blocks.java` registrations explicitly add `.isSuffocating(Blocks::always)` to **farmland** (lines 494–503), **soul sand** (730–741), and **grass path** (1965–1969). Those explicit values are absent in 1.16/1.16.1 and all three registrations retain them through 1.16.5. `BlockBehaviour.Properties` in both sides defaults suffocation to `material.blocksMotion() && state.isCollisionShapeFullBlock(...)` (1.16.1 lines 784–789; 1.16.2 lines 804–809); its state method invokes the stored predicate. Exact shape dependencies were checked: `FarmBlock.SHAPE` is `(0,0,0)–(16,15,16)` (`FarmBlock.java:29`), `GrassPathBlock` returns that shape (`GrassPathBlock.java:59–61`), and `SoulSandBlock.SHAPE` is `(0,0,0)–(16,14,16)` (`SoulSandBlock.java:18,25–27`). `BlockBehaviour` supplies the state collision shape through the block shape when collision is enabled. These non-full shapes had a false default suffocation predicate, so the 1.16.2 registrations make them eligible for the new predicate-filtered collision probe when their actual shape intersects the player's test box. The registered block state remains vanilla.

Dependency closure checked for this response: `LocalPlayer.aiStep` call sites; old `blocked` → `Player.freeAt` → `BlockState.isSuffocating`; new `suffocatesAt` → `CollisionGetter.noBlockCollision/getBlockCollisions` → collision-shape intersection and the same predicate; registration values, the default predicate, and the concrete shapes from `FarmBlock`, `GrassPathBlock`, and `SoulSandBlock`. `Entity.checkInsideBlocks`'s similarly named suffocation query is a separate operation and is not this LocalPlayer push-out response.

Concrete precondition: local client player, physics enabled, and one of the four sampled horizontal columns produces a suffocation result. For the new path, a suffocating block collision shape must also intersect the near-cell test box. If a safe neighbor is found, the horizontal velocity is rewritten to the selected fixed 0.1 step. No server-side historical movement claim is implicated.

### Soul Speed support-block lookup

**1.16 and 1.16.1:** `world/entity/LivingEntity.java:onSoulSpeedBlock` (lines 424–426) reads `getBlockStateOn()`, which resolves through `Entity.getOnPos()` to `floor(position.y - 0.2F)` and has a lower-block fallback for fences, walls and fence gates (`Entity.java:579–592`).

**1.16.2 through 1.16.5:** `onSoulSpeedBlock` instead reads `level.getBlockState(getBlockPosBelowThatAffectsMyMovement())` (`LivingEntity.java:425–427). That helper (`Entity.java:627–629`) samples `position.x/z` at `boundingBox.minY - 0.5000001`. The two lookups can select different y cells while the player is near a block boundary; the concrete case is an enchanted player whose selected support cell is Soul Sand or Soul Soil for one lookup and another state for the other. `onSoulSpeedBlock` feeds the Soul Speed block-speed-factor override (`getBlockSpeedFactor` directly returns `1.0F` when the enchantment is present) and the Soul Speed eligibility/particle path. This can change the factor used for player movement under that precondition.

The data tag dependency is unchanged across the six versions: `data/tags/BlockTagsProvider.java:515` places `Blocks.SOUL_SAND` and `Blocks.SOUL_SOIL` in `BlockTags.SOUL_SPEED_BLOCKS`; their registrations persist. Thus the observed delta is the queried support coordinate and accessor path, not tag membership. `onSoulSpeedBlock` gates `tryAddSoulSpeed` (`LivingEntity.java:447–468`), which adds the level-scaled transient movement-speed modifier on a block change, and it gates `getBlockSpeedFactor` (`LivingEntity.java:429–432`), which is applied to horizontal velocity by `Entity.move` (`Entity.java:561–563` in 1.16.1; 576–578 in 1.16.2). Under the differing-support-cell condition, this can change both the Soul Speed attribute effect and the block-speed damping in player movement.

### Water/lava presence and fluid-current scan

**1.16 and 1.16.1:** `world/entity/Entity.java:updateInWaterStateAndDoFluidPushing` (lines 947–956) clears cached `fluidHeight`, calls the water-current updater, and returns immediately if `isInWater()` is true. Only when that water presence check is false does it scan lava with the dimension-dependent coefficient (`0.007` in ultra-warm dimensions, otherwise `0.0023333333333333335`). `updateInWaterStateAndDoWaterCurrentPushing` scans water with coefficient `0.014`, updates `wasTouchingWater`, fall distance and fire state. The public method `updateFluidHeightAndDoFluidPushing` scans the deflated bounding box over integer coordinates `[floor(min), ceil(max))`, considers tagged fluid whose surface is at or above the box minimum, stores the maximum overlap height, accumulates current, scales each contribution when overlap is below `0.4`, averages over contributing cells, normalizes only for non-players, applies its coefficient and minimum-current threshold, then adds the vector to movement. The scanner body is identical in the inspected source after the wrapper change.

**1.16.2 through 1.16.5:** the wrapper no longer returns early after water. It updates water, computes the same lava coefficient, scans lava into the fluid-height cache, then returns `isInWater() || lavaScanResult` (`Entity.java:957–978`). Since `Entity.baseTick` calls this wrapper before `updateFluidOnEyes` (`Entity.java:380–381` in 1.16.2), the side effects are reachable for the ordinary LocalPlayer tick. A concrete changed case is a loaded-chunk player bounding box spanning water and lava tagged fluid blocks, with nonzero current in the lava sample and fluid pushing enabled: 1.16.1's water-positive return skips the lava current contribution; 1.16.2 samples and applies it after the water sample. The per-fluid scan math itself did not change in this slice.

### Separate related lava-presence predicate

Do not fold the following predicate delta into the mixed-current scan finding. In 1.16/1.16.1, `Entity.isInLava()` reads `isTouchingLava`; `Entity.checkInsideBlocks` resets it before its block scan, and `LiquidBlock.entityInside` sets it with `Entity.setInLava()` (`LiquidBlock.java:183`, `Entity.java:552–553, 1065–1070`). In 1.16.2–1.16.5, that flag and setter/callback path are absent from these sources; `isInLava()` reads `!firstTick && fluidHeight.getDouble(FluidTags.LAVA) > 0.0` (`Entity.java:1071–1073), so its evidence comes from the preceding fluid-height scan. `LivingEntity.travel` uses `isInLava()` to select its lava branch and `getFluidHeight(LAVA)` (1.16.1 lines 1921–1961; 1.16.2 lines 1923–1963); the jumping branch also selects lava vs water height (1.16.1 lines 2425–2438; 1.16.2 lines 2427–2440). The `!firstTick` gate is part of the newer query. Damage handling in `Entity.baseTick` also reads the predicate but damage calculation is outside this movement audit.

This predicate change is same-boundary evidence at 1.16.2, with a different upstream data source and different call sites than the mixed-fluid scanner. The scan's older early return is specifically a current-sampling control-flow issue; it does not prove what `isInLava()` returns in every mixed-fluid state, because the older predicate is set by the separate `LiquidBlock.entityInside`/`checkInsideBlocks` lifecycle.

Fluid input identity was also checked: `FluidTagsProvider.java` is byte-identical across all six releases and still assigns the still and flowing variants to `WATER` and `LAVA`; `FluidState.java`, `FlowingFluid.java`, `Fluid.java`, and `LiquidBlock.java` hashes were verified against each release's source manifest or embedded provenance. Their contents are byte-identical across the six versions. Thus the observed mixed-fluid current delta comes from the entity wrapper's call order, while the separate lava-presence delta changes where the query obtains its value.

## Per-release disposition

- **1.16:** baseline old LocalPlayer push-out, old Soul Speed lookup, old water-first/early-return wrapper and callback-backed `isInLava`.
- **1.16.1:** assigned source files match 1.16 byte-for-byte; no change in these operations.
- **1.16.2:** earliest observed changes in all three assigned operations, including the three block suffocation registrations and the separate lava-presence query.
- **1.16.3:** assigned source content matches 1.16.2; embedded identity only, with no ready publication marker.
- **1.16.4:** assigned source content matches 1.16.2; embedded identity only, with no ready publication marker.
- **1.16.5:** assigned source content matches 1.16.2; ready publication markers are present.

No intermediate release between these six IDs was omitted. This exact interval establishes 1.16.2 as the earliest observed patch among the requested releases; it does not make a pair-wide freeze verdict.

