# Independent candidate review — short-pose collision query

## Verdict

**Accept for integration with constraints.** Candidate commit `8af81d46919924880d7e3f62b49f26eaee7ce7af` implements the accepted old `Shapes.create(AABB)` query encoding. No candidate blocker was found for the reviewed, valid swept collision-query inputs. This accepts the encoding operation only; it does not establish runtime parity.

## Evidence binding

- Candidate repository: `.native-candidates/shortpose-query66`, branch `fix/historical-query-encoding-20261031`, clean at the reviewed commit.
- Accepted source review `.native-source/20261031/shortpose-query-alias65/review.md`: SHA-256 `b2adb0f7f4d4e72ab1fc51821cb5c59bd590ee5b0dc575c1770a3baebfe4c16e`.
- Accepted short-pose addendum `.native-source/20261031/1165-1171-finish45/collision-query-review/20261031-short-pose-addendum.md`: SHA-256 `69aa4c1584f0ffd927297493f5fa00de07b962a75fb5de2623a0b00440399f67`.
- Primary source inspected read-only at `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/1.16.5/mojmap/net/minecraft/world/phys/shapes/Shapes.java` and the paired `1.17/mojmap/.../Shapes.java`.

## Technical findings

The candidate's `findBits` preserves the 1.16.5 guard bounds, denominator order 0–3, floor comparison, and strict tolerance. The 1.16.5 source's successful path rounds endpoints to grid indices, builds a `BitSetDiscreteVoxelShape` of the corresponding dimensions, and fills the half-open cell product `[minCell,maxCell)` on X/Y/Z. The candidate records those exact grid resolutions and half-open cell limits. Its all-zero-bits branch preserves `AABB.contains(0.5, 0.5, 0.5)` and distinguishes canonical full block from canonical empty shape. On any failed axis it records exact endpoints for all three axes, matching the old `ArrayVoxelShape` built over the canonical block voxel grid. Thus the contract contains the information needed to retain historical grid planes and index-merge behavior; it is not merely an AABB approximation.

The paired 1.17 source delegates `create(AABB)` to the coordinate overload and changes the grid test to nearest-integer rounding with denominator-scaled tolerance. This supports the candidate's `@MovementChange(V1_16_2)` boundary. The reviewed old query source applies across 1.16.0–1.16.5, and the changed path persists through 1.17.1. Do not retag it earlier than 1.16.2; retain the independent 1.15.2 implementation below it.

The supplied equal-input witness passes through the three-axis `CollisionSpliterator` query and its AND admission of a partial translated piston shape. The addendum's merger replay distinguishes the retained old cell from the coalesced new boundary, and the exact source witness supports the old/new X clipping difference. The accepted applicability review limits this conclusion to the described collision query and does not claim ordinary-tick state production or runtime trajectories.

## Integration constraints

1. Materialize `DISCRETE` from `bits` and `[minCell,maxCell)` using the old discrete grid planes. Do not derive its coordinates from the record's original `min`/`max`; those are only near-grid input endpoints. Materialize `RAW` with the exact endpoint arrays on all three axes over the canonical full-block voxel grid. Preserve the canonical `EMPTY` and `FULL_CUBE` cases.
2. Keep the native full-cube AABB fast path unchanged. Apply this hook only to the reviewed client `LocalPlayer` `MoverType.SELF` block-query route, when pose is `SWIMMING`, `noPhysics` is false, all three displacement components are nonzero, edge correction leaves the displacement unchanged (the reviewed fixture has shift false), and `onGround` is false. Do not clone or replace the whole collision collector; preserve the native fallback.
3. The candidate returns native fallback for non-finite or inverted bounds. The accepted route produces a valid finite swept AABB; keep the hook gated to that route so this fallback does not widen its semantic scope.
4. Keep server movement native and ensure the new integration is inert outside those gates. The candidate contains no adapter, so adapter inertness remains an integration check, not a rejection of this encoding candidate.

## Review disposition

No code changes, builds, tests, game launches, product implementation/history inspection, or staging were performed. This review covers only the immutable candidate and the explicitly assigned accepted source evidence.
