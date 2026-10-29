# Independent review: 1.8.9 ID 128 sandstone-stairs provider closure

Verdict: **ACCEPT within the stated primary-provider and applicability gates.**

## Input identity and hash verification

Reviewed `.native-source/20261028/collision-next/dda-sandstone-stairs-128-closure/report.md` (SHA-256 `F6C13F520A2B2A02BFA0F2493CCF703A1C0A6CC08AB66AE8D9799290E3A5CE9D`) and `sources.sha256` (SHA-256 `FB7C6826CF1215B0CB640F3ED2A926389DC64F604881FBD3A211F455CCBD8A29`). Recomputed every one of its 25 listed file hashes against canonical read-only roots; all matched. This includes exact ready marker/provenance/source-manifest identities and all selected Java sources.

## Claim review

- 1.8.9 `Block` registers ID 128 as `sandstone_stairs`; its base state is smooth sandstone. The current `BlockItemIds.SANDSTONE_STAIRS` and `Blocks.SANDSTONE_STAIRS` map to the `sandstone_stairs` key and the legacy stair implementation. The different base-state material does not affect either provider's collision shape calculation, which uses stair state and neighbor state.
- Old metadata decoding masks the low two bits for `Direction.byId(5 - bits)` and bit 2 for top/bottom. Thus all 0–15 inputs map to four horizontal facings and two halves; bit 3 is ignored. The reviewed old direction IDs yield the facing table in the closure. Current facing and half values correspond directly.
- The old movement provider adds a full-footprint half-height base, an additional state/neighbor-selected box, and a possible inner-corner quarter box, then resets shared bounds. The current provider returns a union assembled from half and quarter boxes at the same 0, 1/2 and 1 coordinates. The old behind/front tests and diagonal same-facing/same-half veto correspond to the current outer/inner tests and `canTakeShape` for mapped legacy stairs. The coordinate geometry therefore agrees under the report's explicit state/neighbor conditions.
- Applicability is properly limited: current `SHAPE` is stored while the old corner result is recomputed during collision; product use must establish that they agree. Modern-only stair neighbors must not satisfy old `instanceof StairsBlock` tests. Ray tracing uses separate mutable `isCorner`/`stairType` logic and is outside this movement-provider finding.

## Scope boundary

This acceptance covers one old stair registration's metadata/state alias and local movement collision union, conditional on equivalent neighbor inputs and current stored `SHAPE`. It does not accept all stair aliases, modern-only-neighbor behavior, ray tracing, DDA traversal, collector/list order, runtime reachability or movement parity. The report's native fallback conditions remain necessary.

No product code was inspected or changed. No build, tests, game launch or runtime check was run.
