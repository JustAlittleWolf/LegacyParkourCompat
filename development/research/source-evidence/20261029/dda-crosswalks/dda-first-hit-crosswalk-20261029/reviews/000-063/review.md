# Independent review: first-hit crosswalk, IDs 0–63

## Verdict: ACCEPT (bounded source-only crosswalk)

Reviewed the 1.8.9 first-hit eligibility crosswalk and its cited source/evidence artifacts. The table has exactly 64 rows and 64 distinct IDs, covering 0 through 63 without gaps or duplicates. All 16 columns are populated. The 58 path/hash pairs in `range-000-063/artifact-hashes.json` resolve, and each recomputed SHA-256 matches. This includes the old 1.8.9 registry/class/provider sources, the 26.2 `BlockStateData` source, the two reviewed metadata-projection partitions, accepted provider inventory/contract, and referenced bounded family findings/reviews.

## Source checks

The exact 1.8.9 source confirms `Block.canRayTrace(BlockState, boolean)` delegates to the default `canRayTrace()` which returns true (`Block.java:356–362`). The searched `net/minecraft/block` tree has only the expected overrides: `AirBlock` rejects (`AirBlock.java:30–32`); `FireBlock` rejects (`FireBlock.java:297–299`); `LiquidBlock` requires `allowLiquids` and source level (`LiquidBlock.java:69–71`); `StairsBlock` delegates to its wrapped base block (`StairsBlock.java:442–449`). For the only stair registration in this range, ID 53 `oak_stairs`, `Block.java:941` constructs the wrapper from the oak-planks state, so the table's positive camera eligibility note is supported. The ordinary camera uses `allowLiquids=false`; the accepted provider inventory's caller/flag and first-hit statements are hash-bound in the referenced artifacts.

Registration IDs/keys and constructor anchors agree with the cited `Block.java` source and the earlier registry crosswalk. The table points each row to an existing 16-metadata projection rule, and candidate modern keys remain explicitly unproven. I checked representative low, boundary, and high rows plus the registration blocks across the range; this is a bounded crosswalk review, not a re-audit of all provider findings.

## Limits retained

The review accepts this as an eligibility/registration/projection index only. It does not close exact old-state-to-26.2 `BlockStateData` conversion, current provider or neighbor/block-entity reconstruction, full mutable-bound cache lifecycle, traversal/first-hit adapter composition, or runtime parity. The all-rows `OPEN` conversion status is appropriate; no candidate alias is promoted to a proven mapping. Accepted per-family report references remain bounded to their reviewed claims.

No product implementation or product history was inspected. No tests, build, game, or runtime were run.
