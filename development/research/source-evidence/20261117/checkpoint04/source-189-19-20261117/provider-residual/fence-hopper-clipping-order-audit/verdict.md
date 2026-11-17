# Fence/Hopper clipping-order audit verdict

**Verdict: ACCEPT for the bounded per-cell geometry and ordering subdependency.** This verdict is bound to report SHA-256 `5F0B6123FCB52E45D693F866F943028128815224EE801BBCEB199E8E7886D8D3`.

## Evidence checked

The report's 1.8.9 source manifest SHA-256 `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004`, 1.8.9 provenance SHA-256 `ba066fc8f1c7ceb9819d2106338d9e9f2c2b96eb5a8329eb40e171b1f9889185`, and 1.9 mapping-local provenance SHA-256 `8daacb7af8f210e716ee4b34c43b8a5d6f6b4b19831d2fd725cbe16ecf330a1a` match the assigned publication identities. All six cited class files match their 1.8.9 manifest rows and 1.9 provenance entries, and their recomputed SHA-256 values match the report.

## Findings

- For equal resolved Fence connection bits, 1.8.9's at most two 1.5-high strips and 1.9's center post plus directional arms cover the same per-cell collision union. This conclusion is limited to matching bits and the same queried cell.
- Hopper emits the same five coordinate boxes in both releases. The north/east order differs, while the remaining boxes and all coordinates match. `FACING` and `ENABLED` do not change these collision boxes.
- Both `Block.addCollisions` paths append only shapes with strict overlap against the query box. Both `Entity.move` paths clip Y, update the player box, then clip X and Z, and use the same order for the step-up alternatives. The paired `Box.intersectX/Y/Z` methods apply the same transverse-overlap gates and monotonically tighten the movement limit. For the same cell/query and matching Fence bits, neither the Fence partitioning nor Hopper box-order permutation changes this bounded clipping result.

## Limits

The report correctly leaves global collision-cell inclusion and order open: the paired `World.getCollisions` scan envelopes and border/corner gates differ. Fence connection-bit equivalence is also open because the solid-neighbor branch changes from `block.isCube()` to `blockState.isCube()`. This verdict does not close those gates, other providers or the exact pair's full coverage, and makes no trajectory or runtime claim.
