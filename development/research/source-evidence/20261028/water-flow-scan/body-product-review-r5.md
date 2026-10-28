# Independent product review: 1.8.9 water body contact integration

**Verdict: REVISE.** Review target is product branch tip `23a788ce59c2595ec5e286ce06528fe212dd1bfc` (`a4de73e9ef0e9c1a6c984b3dc69dba78572ada14` product commit), merged with main `a0448a5d12d6fb714da8a14562eab5db05a8affe`. The review is bounded to the accepted 1.8.9 body-contact query and its integration with the existing shared water scan. It does not assess runtime parity.

The source candidate and its independent candidate review accept the 1.8.9 transformed query, `floor(max + 1.0)` exclusive upper bound, material/surface predicate, and client area-admission behavior. The supplemental family audit confirms the relevant Feather `World`, `Entity`, `LiquidBlock`, `ClientWorld`, and `ClientChunkCache` files are identical across 1.8.2–1.8.9; 1.8 and 1.8.1 remain outside the verified source range. Product comments correctly describe the V1_8 providers as native fallback scope, not as a historical-equivalence claim.

## Finding: contact membership includes cells outside the historical build height

The 1.8.9 `World.applyLiquidDrag` computes raw contact bounds, passes them to `isAreaLoaded`, and scans the half-open Y range using the same exclusive upper `l` in the surface comparison (`World.java:1486–1527`). The client area gate admits any vertical overlap (`maxY >= 0 && minY < 256`); it does not require the whole query to be inside 0–255. However, 1.8.9 `World.getBlockState` first calls `contains` and returns `Blocks.AIR` when the block position is outside the world bounds (`World.java:612–618`); `contains` restricts Y to `0 <= y < 256` (`World.java:162–164`).

The product retains the raw bounds for both traversal and contact membership: `WaterScan.java:93–100` uses `contactBounds` as `scannedContactBounds` and includes its raw `y0` in the union; `WaterScan.java:122–136` iterates that union and treats a water cell as contact whenever its Y lies in the untrimmed contact bounds. The new area hook only applies the old overlap gate (`LegacyWaterBodyContactAreaAdmission.java`); it does not constrain queried block positions to the old world height.

For example, at feet Y `-0.5`, the legacy transformed/contracted body query is approximately `[-0.1, 0.9]`, producing `y0=-1`, exclusive `y1=1`. Its area gate passes. The old loop reads y `-1` as air, while a current level whose build height includes that position can return its actual fluid block. Since the product tests the raw bounds, a water `LiquidBlock` at y `-1` can set `contactFound=true`; the historical implementation cannot. The analogous mismatch exists for scanned cells at y `>=256` when they are valid in the current dimension.

**Narrow correction:** intersect only the contact scan/membership Y interval with `[0, 256)`. Continue applying `WaterBodyContactAreaAdmissionHook` to the untrimmed bounds, and keep the original contact `exclusiveMaxY` for the historical liquid-surface test. An empty clipped interval reports no historical contact. Leave flow bounds, flow admission, and flow Y handling independent.

## Finding: unsupported flow state falls back contact before domain membership

There is a second way the contact result can escape the accepted source query. In the union loop, `WaterScan.java:127–129` returns `Optional.empty()` for any WATER state that is not a `LiquidBlock` or whose `LEVEL >= 8`; this guard runs before contact/flow domain membership at `:132–142`. The caller treats an empty scan as native fallback for both operations (`EntityWaterMixin.java:63–70`). However, the accepted contact contract explicitly supports falling levels: 1.8.9 `LiquidBlock.getHeightLoss` maps `state >= 8` to zero and returns `(state + 1) / 9.0F` (`LiquidBlock.java:41–46`), and `LegacyWaterBodyContact.includes` implements the same mapping. Thus a contact candidate at level 8 or above is never evaluated.

A concrete mismatch is feet Y `0.5` with default 1.8-high bounds: the legacy contact AABB is approximately `[0.899, 1.899]`, so the 1.8.9 bound yields exclusive Y `2` and scans only cells 0 and 1. The 26.2 native fluid box is the full entity box deflated by `0.001`, approximately `[0.501, 2.299]`; its tracker scans through Y `2`. A level-8 WATER cell at Y `2` is outside both historical contact and flow domains, but is visited in the union through the native box. The early return discards the resolved historical contact result and lets native contact decide; the old query would return false for that cell. Even after contact Y is clipped, this out-of-domain cell can trigger that global fallback.

**Narrow correction:** keep per-operation fallback independent. Compute accepted contact membership for supported historical water cells, including level `>=8`, before applying a current-specific fallback. A state outside the active historical contact/flow domains must not make contact unresolved merely because the shared union traverses it. If flow cannot resolve a falling/current state, retain native fallback for flow without discarding an independently resolved contact result; preserve native modern-only feature behavior on the operation/domain where it actually applies.

## Other reviewed integration properties

- `WaterScan` retains a single union traversal; the new contact providers do not add a second block loop.
- Contact and flow bounds/admission are distinct; rejected contact admission resolves contact false while allowing an independently admitted flow domain to proceed. The current global unsupported-fluid early return still couples fallback for both operations, as described above.
- The changed union uses explicit absent-domain sentinels (`Integer.MAX_VALUE`/`MIN_VALUE`) rather than zero, avoiding the previously identified world-origin scan expansion.
- `EntityWaterMixin` dispatches the scan at the native fluid refresh and retains the result for consumers within the same entity tick/level. The body-contact result is applied through the first water-presence expression, preserving the surrounding native refresh effects. No separate timing defect was confirmed in this review.
- The V1_8 native fallback, V1_8_9 historical providers, V1_9 native body-contact provider, and pre-existing V1_11_2 fence preserve the bounded supported range.

The target source SHA-256s inspected for these findings are 1.8.9 `World.java`: `3C04F5B874FBB6692039164C882A3C47FE28C7ECA864E91C9AC57798153888EE`; 1.8.9 `LiquidBlock.java`: `1537FD6B1BCA65E0F910F9A12BCD3BB27F5A86BE7F13A1CAA380C5E66544F1D5`; 26.2 `Level.java`: `1AE4F565AD909F4E90786089B5B4F125E8A7D18E2EF7D8C376172A5787FC5639`; and 26.2 `LevelHeightAccessor.java`: `EF140633AD909F4E90786089B5B4F125E8A7D18E2EF7D8C376172A5787FC5639`.

`git diff --check a0448a5d12d6fb714da8a14562eab5db05a8affe...23a788ce59c2595ec5e286ce06528fe212dd1bfc` passed. No code was changed by this reviewer; no build, tests, or runtime checks were run.
