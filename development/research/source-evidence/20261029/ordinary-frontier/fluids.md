# Fluid movement dependency closure: 1.17.1 → 1.18.2

## Scope and inputs

This is a bounded, source-only supplement to the existing 1.17.1 → 1.18.2 Mojmap inventory. It reuses the full-path ledger and audits the open fluid/current/bubble-column dependencies named by the earlier independent audit. It does not close the pair. Canonical sources were read-only under `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\<release>\mojmap`. No product, history, backlog, wiki, test, build, runtime, or source-generation inputs were used.

- Reused author inventory: `.native-source/20261028/frontier-c/1171-1182-full-inventory/inventory.md`, SHA-256 `8379db16e7e252f6c140d2dce33bb8560493e4279be98abdcbcb284c302f5e1a`.
- Reused exact Java path/hash ledger: `.native-source/20261028/frontier-c/1171-1182-full-inventory/java-path-classification.tsv`, SHA-256 `11f7bc1d88b5947b0de13962dee93be8339028165f2d083b1448078f1bbae3ff`.
- Reused independent full-inventory audit: `.native-source/20261028/frontier-c/1171-1182-full-audit/audit.md`, SHA-256 `20ca97e8716a612227bb596c3ced26815bb5232257e97b4cf394a8c5a3e42f52`; its verdict is not ready to freeze, and it explicitly leaves fluid dependencies open.
- Both exact source manifests/readiness identities are recorded in the reused inventory: 1.17.1 Mojmap has 4,142 Java files, source-manifest SHA-256 `93270d229acfb751bf56daf1e7be26ce3dcff26b29e94aa157de621405a3463b`; 1.18.2 Mojmap has 4,236 Java files, source-manifest SHA-256 `aea0cb9c6fc8f7a46f0eb82b0388ad58a4659f513be6c0a2be06c0df0c1eb07a`. The inventory reports exact successful decompilation and matching source/artifact manifests for both.

## Checked terminal slices

### Entity fluid-height/current scan and tick phase — no numeric or ordering difference

`net/minecraft/world/entity/Entity.java`:

- 1.17.1 `baseTick()` lines 415–417 and 1.18.2 lines 424–426 call `updateInWaterStateAndDoFluidPushing()`, then `updateFluidOnEyes()`, then `updateSwimming()` in that order.
- `updateInWaterStateAndDoFluidPushing()` clears the per-tag fluid-height map, updates water contact/current first, computes the lava push scale from `dimensionType().ultraWarm()` (`0.007` or `0.0023333333333333335`), updates lava height/current, and returns water-contact-or-lava-contact in both versions.
- `updateInWaterStateAndDoWaterCurrentPushing()` preserves the boat gate, calls water scan with `0.014`, retains first-contact splash gate `!wasTouchingWater && !firstTick`, resets fall distance, sets `wasTouchingWater`, and clears fire. The helper substitution in 1.18.2 is equivalent for this path: `Entity.resetFallDistance()` directly assigns `fallDistance = 0.0F`.
- `updateFluidHeightAndDoFluidPushing(Tag<Fluid>, double)` (1.17.1 line 2773) and the `TagKey<Fluid>` version (1.18.2 line 2769) perform the same algorithm: unloaded-chunk early return; bounding box `deflate(0.001)`; floor minimum/ceil maximum coordinates; nested X, Y, Z loops; fluid tag test; surface check `fluidY + getHeight(...) >= box.minY`; maximum immersion-height accumulation; optional flow collection when `isPushedByFluid`; scale each sample by accumulated height only below `0.4`; divide sum by sample count; normalize only non-`Player`; apply passed coefficient; if horizontal velocity components are each below `0.003` and the scaled current length is below `0.0045000000000000005`, normalize and raise its magnitude to that threshold; add to delta movement; store the tag height and return contact. The two bodies differ in local names and tag API types, not operation order or constants.
- `isInLava()` remains `!firstTick && fluidHeight[LAVA] > 0.0`; `isInWater()` uses the corresponding water height. `isUnderWater()` remains prior eye-in-water state plus current water contact. `updateSwimming()` preserves its sprint/water/passenger/underwater and block-position WATER checks.

These conclusions concern direct local movement when the paired clients have the same received block/fluid state, bounding box, current velocity, dimension flag, and unloaded-chunk result. This source review does not establish that those states or world-query results are equal.

### Fluid-state height and flow — checked algorithm bodies, no numeric difference found

`net/minecraft/world/level/material/FlowingFluid.java`:

- `getFlow(BlockGetter, BlockPos, FluidState)` lines 53 onward has the same horizontal-neighbor iteration and accumulation. Both releases use `affectsFlow` on horizontal and below neighbors, compare `getOwnHeight()` values, use `0.8888889F` in the below-neighbor term, accumulate direction step X/Z times the height delta, and for a falling state inspect horizontal side faces (same-level and above) before adding `(0,-6,0)` to a normalized vector. The return is normalized in both versions. Changed local names/API layout do not alter the displayed math/order.
- `getHeight(FluidState, BlockGetter, BlockPos)` returns `1.0F` when `hasSameAbove` succeeds, otherwise own height in both versions. `getOwnHeight` and amount/falling-derived height formulas match in the compared bodies.
- Remaining direct inputs to current calculation include neighbor fluid state, neighbor block material `blocksMotion()`, and `isSolidFace` queries. Their exact state data is supplied by the world; the face/block query implementation and collision/provider internals are outside this bounded assignment and remain unresolved dependencies.

`FluidState` keeps direct delegation for `isSource`, `getHeight`, `getOwnHeight`, `getFlow`, and tag membership. The tag-membership API changes from `Fluid.getType().is(Tag<Fluid>)` to `TagKey<Fluid>`/holder membership. This is an API/data representation change; it does not by itself establish changed vanilla fluid values or membership.

### Bubble-column player response — no formula difference found

`net/minecraft/world/level/block/BubbleColumnBlock.java` `entityInside` uses the block above as the selector in both versions: air calls `Entity.onAboveBubbleCol(DRAG_DOWN)`, otherwise calls `onInsideBubbleColumn(DRAG_DOWN)`. The callback executes before the client-side check that only gates server particle production. Thus a local entity’s direct response is not itself server-only.

`Entity.onAboveBubbleCol(boolean)` retains `max(-0.9, vy - 0.03)` for drag-down and `min(1.8, vy + 0.1)` for upward columns. `onInsideBubbleColumn(boolean)` retains `max(-0.3, vy - 0.03)` or `min(0.7, vy + 0.06)`, then resets fall distance. In 1.18.2 the reset helper is the direct `fallDistance = 0.0F` assignment, matching 1.17.1. X/Z velocity is preserved. Bubble-column state creation/conversion is a separate producer and is not claimed as a client movement delta.

### Fluid-dependent travel and stand-on-fluid call sites — no player override delta found; provider gate open

The prior full inventory inspected the complete `LivingEntity.travel` bodies and found matching water/lava branch order, modifiers, jump-out checks, friction/gravity ordering and damping, apart from the direct-zero/reset-helper substitution already checked here. `LivingEntity.canStandOnFluid` changes parameter from `Fluid` to `FluidState` but returns `false` in both implementations. The 1.17.1 world-source search found the only override in `Strider`, a non-player path; local player has no override in that source tree. The liquid-block collision-context query and world collision/provider behavior are excluded from this closure and still open in the full-pair audit.

### Eye-fluid tag set — vanilla source membership equivalent; data-driven membership remains unresolved

The old `Entity.updateFluidOnEyes()` saves `wasEyeInWater` from the previously recorded eye tag, clears the single stored tag, checks the eye sample/boat gate, scans static fluid tags, stores the first matching tag whose fluid surface is above the sample, then returns. The new method saves the same previous-state predicate, clears a set, uses the same eye sample and boat gate, checks the fluid surface once, then adds all tags from the fluid state. `isEyeInFluid` changes from identity comparison to set membership.

Both exact `FluidTagsProvider.addTags()` bodies declare WATER as `{Fluids.WATER, Fluids.FLOWING_WATER}` and LAVA as `{Fluids.LAVA, Fluids.FLOWING_LAVA}`. With those vanilla memberships and the same supplied fluid state, water eye detection is unchanged. The source-only trees contain Java, not runtime tag-resource payloads. Therefore this review does not close the behavior for externally supplied/data-driven fluid states carrying multiple tags or other resource-defined memberships; a result requiring such membership is not asserted.

## Status and remaining dependencies

- Checked/no numeric difference: fluid height/current scan, water/lava push coefficients, scan order and gates, water contact/reset path, flow vector/height formulas, bubble-column callback selection and local vertical response, base `canStandOnFluid` result, vanilla WATER/LAVA membership, vanilla eye-in-water outcome.
- Source/API change, vanilla-equivalent under checked tags: single eye tag to tag set and `Tag` to `TagKey`/holder membership. Data-driven multi-tag/resource behavior is unresolved.
- Unresolved outside this assignment: exact `BlockGetter`/`Level`/chunk query providers and their unloaded-chunk results; `isSolidFace`, neighbor material/block tests and collision-context stand-on-fluid provider; received block/chunk/fluid state update applicability/order; runtime tag/resource contents; block-shape/collision provider; other open dependencies in the reused full-pair audit.
- No finding, implementation candidate, pair freeze, or runtime trajectory conclusion is proposed by this closure.

## Exact reviewed file hashes

SHA-256 hashes below were computed directly from the canonical exact-version Mojmap Java files. Identical full-file hashes establish equality; differing full-file hashes establish only that a file contains other changes and are not themselves evidence of movement behavior.

| Relative source path | 1.17.1 SHA-256 | 1.18.2 SHA-256 |
|---|---|---|
| `net/minecraft/world/entity/Entity.java` | `ab28e1fba924771ec048140dfd293ee5a46a7dfe81f71a1a0b1aecc1927232de` | `2228fdaca5793171cbd94038306d571a6ada78ca96f5734efb4cada5b744c10a` |
| `net/minecraft/world/entity/LivingEntity.java` | `33fd081aadb2b6fdc9ebf487db6da5b38c54f4b8676572790ee2203690d15e6f` | `db4168d531caf18f22e3fefd073365e776da4075ce01452bb9f7671d9b458782` |
| `net/minecraft/world/level/block/BubbleColumnBlock.java` | `70af7c91f54eab13b32b75ba87c72374e03b51572a175440e8cd166ceff97394` | `61990e718c04229d0cf786072e586a3655c513f4d51d9b789e6ff4f56d712d7b` |
| `net/minecraft/world/level/material/FluidState.java` | `0ee09bede0d44264009a810a6493897c0ce8ee9893fa275d3ec87d782a5ad754` | `a39d52cdaebcff391abd094d17c0d8c190df27af7aeb5ac3810370eca017c459` |
| `net/minecraft/world/level/material/FlowingFluid.java` | `93fac0aa0b44dcd42a9e05783eede16e6e1e72cbc7cbf00d34f6e5a55b67f4c6` | `bbfb661b524ac92f74579cd4b61c1a25df00ecc4bfe775a5516f7e4a7c8768f3` |
| `net/minecraft/world/level/material/Fluid.java` | `7b8953fae5769ceebef1d950947460e29d95e74b93d313dd57a6a09c910dc8a8` | `441ddda17005a8d40e2a9b1cc7a6a449d811cb25c9df2628133fec5e4d6ffb62` |
| `net/minecraft/world/level/material/Fluids.java` | `7ee6840d600b06d6c803c8c741e39efdf5add797879f656a9c3abd9348fc7c64` | `4888bd1fa4cf05bf79721ecd4d37f1c23837efd0a7b9289bfe880cdee8a9a411` |
| `net/minecraft/world/level/material/WaterFluid.java` | `2e45a3d180d8f8978bcc2abefdc0c1475f3500cc6bbbaa88a1d6632e801ed672` | `4ff811fb14b0ae0f67a9a9068b286bc0dd7667ac0bc6a4964507b63d538dda50` |
| `net/minecraft/world/level/material/LavaFluid.java` | `24f2e3d50c29a6e9b9cceee05caa5254788772e390c31a6628d5431322d6dc17` | `117b1ae75b42432963928458503c87649a351f6c577a2b417075a8cb99c44f50` |
| `net/minecraft/tags/FluidTags.java` | `b328dac2531d48d462c14a58aa8e86e47510cbf20fd8002bc37a2cbdf1072bd7` | `c37eaf0dab3b4bcb3af89e3d24808a9edf9764127fa3c7876ec64cd5d501885e` |
| `net/minecraft/data/tags/FluidTagsProvider.java` | `05b6b133203d2fa546d8c024ba68754a739e8c01f1466d24f2c30a5026f0585f` | `10e8a8e8847259a30544dcc1088ab1bb7aa45455227a678ea52e54b6742b8a52` |


