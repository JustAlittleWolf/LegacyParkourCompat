# Independent source audit: 1.17.1 → 1.18.2 fluid movement closure

## Verdict

Independent review confirms the bounded source conclusions in `.native-source/20261028/frontier-c/1171-1182-fluid-movement-closure/report.md` (SHA-256 `D058C0FD29350A9735AD43F40262A8071F4114996B79997074DB0CD040322C7F`) for the fluid-height/current scan, its constants and operation order, the `FlowingFluid` neighbor-flow and height formula, the bubble-column local vertical response, and vanilla WATER/LAVA tag declarations. All 22 selected source-file SHA-256 values in the closure report match independently recomputed canonical bytes. The source manifest hashes also match the exact inventory identities.

This is a bounded independent audit, not a pair freeze or runtime finding. Equal received fluid/block state, fluid heights, entity box/velocity, dimension warmth, and world/chunk query answers are preconditions. Resource-defined tag membership, world-query providers, and collision/block-face provider behavior remain unresolved.

## Inputs and exact identities

- Closure report: `.native-source/20261028/frontier-c/1171-1182-fluid-movement-closure/report.md`, SHA-256 `D058C0FD29350A9735AD43F40262A8071F4114996B79997074DB0CD040322C7F`.
- 1.17.1 Mojmap source manifest: `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.17.1\mojmap.sources.sha256`, SHA-256 `93270D229ACFB751BF56DAF1E7BE26CE3DCFF26B29E94AA157DE621405A3463B`.
- 1.18.2 Mojmap source manifest: `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.18.2\mojmap.sources.sha256`, SHA-256 `AEA0CB9C6FC8F7A46F0EB82B0388AD58A4659F513BE6C0A2BE06C0DF0C1EB07A`.
- Exact compared Java source hashes are in `source-hashes.tsv` beside this report. They were recomputed from canonical sources and match every corresponding table entry in the closure report.

## Independently checked source operations

### Entity fluid scan and tick ordering

- `Entity.baseTick()`: 1.17.1 lines 395–417; 1.18.2 lines 403–426. Both call fluid pushing, eye-fluid update, then swimming update, in that order. 1.18.2 also clears cached `feetBlockState` earlier in the method; that additional cache initialization is not a difference in the checked fluid formulas and is not claimed equivalent for all world-query consumers.
- `updateInWaterStateAndDoFluidPushing()` and `updateInWaterStateAndDoWaterCurrentPushing()`: 1.17.1 lines 1066–1087; 1.18.2 lines 1047–1068. Both clear the height map, process water before lava, use water scale `0.014`, and choose lava scale `0.007` for ultra-warm dimensions or `0.0023333333333333335` otherwise. They retain the boat gate, first-contact splash condition, water-contact assignment and fire clear. Water resets fall distance directly in 1.17.1 and through `resetFallDistance()` in 1.18.2; that helper assigns `0.0F`.
- `updateFluidHeightAndDoFluidPushing(...)`: 1.17.1 lines 2773–2837; 1.18.2 lines 2769–2833. Both have the unloaded-chunk early return, deflate the box by `0.001`, use floor minima and ceil maxima with half-open nested X/Y/Z loops, query each fluid cell, test tag membership, and require `cellY + fluidHeight >= deflatedBox.minY`. Both maintain the maximum immersion height, accumulate flow only when pushed by fluid, scale each flow sample by the running maximum only while it is `< 0.4`, average by sample count, normalize only non-players, and apply the caller coefficient. The minimum-current gate checks `abs(currentVelocity.x) < 0.003` and `abs(currentVelocity.z) < 0.003`, then checks scaled-flow length `< 0.0045000000000000005`; both normalize and scale to that threshold before adding movement. The `double 0.003` local is unused in both bodies; the actual gate uses the same literal in both.
- `isInLava()`, `isInWater()` and `isUnderWater()` retain the same cached height/contact semantics in the inspected sources. `updateSwimming()` keeps the same sprint, passenger, underwater, and WATER block-position predicates.

### Fluid flow and surface-height math

- `FlowingFluid.getFlow(...)`: both releases iterate horizontal directions in the same order and use the same neighbor eligibility, empty-neighbor/material-motion gate, below-neighbor fallback, height subtraction, `0.8888889F` constant, direction-step accumulation, falling-side face checks, `(0.0, -6.0, 0.0)` addition, and normalization order. References: 1.17.1 lines 53–97; 1.18.2 lines 53–97.
- `affectsFlow(...)` and `isSolidFace(...)`: 1.17.1 lines 100–113; 1.18.2 lines 100–113. Bodies match in the checked gate ordering: empty or same fluid; same-fluid side suppresses solid face; upward side returns true; ice is excluded before sturdy-face lookup.
- `getHeight(...)` and `getOwnHeight(...)`: 1.17.1 lines 469–476; 1.18.2 lines 434–441. Both return `1.0F` when the same fluid type is above and otherwise use amount divided by `9.0F`.
- Exact block/fluid neighbor state, `blocksMotion()`, and sturdy-face query results are inputs from the world/provider. Their equality and implementation are not closed here.

### Bubble-column callback

- `BubbleColumnBlock.entityInside(...)`: 1.17.1 lines 40–75; 1.18.2 lines 40–67. In each, the block above selects the callback: air invokes `onAboveBubbleCol`, otherwise `onInsideBubbleColumn`. In the air branch, the direct entity callback precedes the `!isClientSide` particle gate. Thus the response invocation itself is not restricted to the server.
- `Entity.onAboveBubbleCol(...)`: 1.17.1 lines 2091–2101; 1.18.2 lines 2086–2096. Both set Y velocity to `max(-0.9, vy - 0.03)` for drag-down, otherwise `min(1.8, vy + 0.1)`, preserving X/Z.
- `Entity.onInsideBubbleColumn(...)`: 1.17.1 lines 2103–2114; 1.18.2 lines 2098–2109. Both set Y velocity to `max(-0.3, vy - 0.03)` for drag-down, otherwise `min(0.7, vy + 0.06)`, preserve X/Z, then reset fall distance to `0.0F` (direct assignment versus equivalent helper).

### Tag membership

- `FluidTagsProvider.addTags()`: 1.17.1 lines 17–19; 1.18.2 lines 15–17. Both vanilla providers put still and flowing water in WATER and still and flowing lava in LAVA.
- 1.17.1 stores a single matching eye-fluid tag; 1.18.2 stores all tags from the sampled state. Under the checked vanilla WATER/LAVA declarations and equal vanilla fluid states, the water predicate has the same result. The Java trees do not prove datapack/resource tag membership or cases where a supplied fluid state belongs to multiple tags. Those cases remain open; no generalized tag-equivalence claim is made.

## Remaining gates

- Equality and applicability of received block/fluid state, entity box and current velocity, dimension type, loaded-chunk result, and block/fluid world queries.
- `BlockGetter`/`Level` query provider behavior, collision-context/shape sources, neighbor material checks, and sturdy-face results.
- Runtime resource/datapack tag contents and multiple-tag fluid states.
- Other source dependencies left open by the reused full-pair audit. This review does not prove complete 1.17.1→1.18.2 coverage, pair freeze, client/server runtime parity, or a trajectory result.
