# Product readiness: native fallback for fence-adjacent stair faces

**Disposition:** COVERED_NO_CODE for the accepted horizontal fence/stair face matrix, including INNER_LEFT and INNER_RIGHT. Current native fence connection state uses full-face support from the stair collision shape; for ordinarily resolved stair states, that yields the same horizontal SOLID/UNDEFINED face table accepted for 1.12. The pending four-operation patch needs no extension for these matrix rows. This is a source-level effective-fallback correspondence, not runtime parity.

## Bound evidence and frozen candidate identity

- Accepted 1.11.2→1.12 state matrix: `build/source-early-patches-20261117/1.11.2--1.12/dependencies/stairs-face-shape-state-matrix.md`, SHA-256 `96df1629a7138eaac34db2ebe8dd464d1e6734c96963459843d21851017c86ed`.
- Matrix audit ACCEPT: `build/source-early-patches-20261117/1.11.2--1.12/dependencies/stairs-face-shape-state-matrix-audit/verdict.md`, SHA-256 `50ffb75a3f39e63bea1398b6e03750796c78d87dbed87daa12adf109ed0fd35b`.
- Existing provider-readiness input: `build/product-readiness-20261117/mcpk-1112-112-provider-readiness.md`, SHA-256 `A1FEB8CA9ECB01AA9FA575EF03D95742F205EE35CC7B80E858BA716401FC3112`.
- Frozen pending patch: `build/integration-staging/early-1112-consolidation-v2.patch`, SHA-256 `49888265ad1b785023196d9bb2f997a1dfeca7b8a0445b0640f389f88af97225`.
- Frozen manifest: `build/integration-staging/early-1112-consolidation-v2.json`, SHA-256 `1f960dd8a1abc6c5c6e4e98b9c50594c482efdcc243034fe7197a4c794039209`.
- Selected target profile: aliases 1.12/1.12.1/1.12.2 resolve to `V1_12` (ordinal 11). The existing `FenceCollisionState @V1_11_2` is ordinal 10; candidate `FenceCollisionState @V1_12` is ordinal 11 and is selected for V1_12 by closest-applicable resolution.

## Native collision and connection path

When the candidate returns empty for an inner or outer stair, `CrossCollisionBlockMixin` leaves vanilla `CrossCollisionBlock.getCollisionShape` active. `CrossCollisionBlock` applies its collision-shape function to the fence state's NORTH/EAST/SOUTH/WEST bits. Those bits are set by `FenceBlock.getStateForPlacement` and `FenceBlock.updateShape`, both of which call `FenceBlock.connectsTo` with `neighborState.isFaceSturdy(..., queriedFace)`.

For an ordinary stair neighbor, `FenceBlock.connectsTo` accepts a full face because stairs are not in `Block.isExceptionForConnection`. `BlockState.isFaceSturdy` with `SupportType.FULL` checks the collision shape's requested face (`Block.isFaceFull` / `VoxelShape.getFaceShape` / full-block test). `StairBlock.getShape` selects its collision geometry from the current FACING, HALF, and SHAPE properties; `getStateForPlacement` and `updateShape` maintain SHAPE from neighboring stairs.

The current stair collision geometry produces the same horizontal full-face table as the accepted 1.12 matrix for ordinarily resolved states:

| Resolved stair shape | Native full horizontal faces | Accepted 1.12 table |
| --- | --- | --- |
| STRAIGHT | F | F |
| INNER_LEFT | F and CCW(F) | F and CCW(F) |
| INNER_RIGHT | F and CW(F) | F and CW(F) |
| OUTER_LEFT / OUTER_RIGHT | none | none |

The collision geometry's horizontal face fullness does not depend on HALF, matching the accepted matrix. The connector passes the opposite face direction to `isFaceSturdy`, so the resulting fence connection bit follows the same stair-face direction mapping. For example, the ordinary north-facing bottom straight stair south of a fence presents its NORTH face, which is full in current native geometry; the native fence path therefore connects, matching 1.12. The inner-left/right directions likewise match instead of requiring a historical override.

## Scope limits

This closes native fallback correspondence for the accepted face table and ordinary world-maintained stair states. It does not claim behavior for synthetic/stale state tuples whose stored SHAPE disagrees with neighboring stairs, broader fence state/pole rules, other connector types, trajectories, runtime parity, or pair completion. No product code, patch, manifest, primary checkout, or Git history was changed; no build/tests/runtime were run.
