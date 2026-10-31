# Auto-jump collision fast-path fringe exclusion — 1.15.2 → 1.16.5

## Disposition

The `LocalPlayer.updateAutoJump` body and direct call are paired equivalent apart from the already reviewed entity-filter API migration. For the standing, crouching, fall-flying, swimming and spin-attack pose dimensions, the accepted canonical full-cube query fringe from `collision-collection-residual/report.md` cannot intersect either side segment that auto-jump actually tests. This closes the named fast-path dependency for those dimensions only. The sleeping and dying dimensions are 0.2 high and remain open because the broadphase may overlap a full block by a tiny vertical sliver even while the tested segment crosses the same block. This report does not claim a pair-wide auto-jump closure or trajectory.

## Source geometry

In 1.16.5 `LocalPlayer.updateAutoJump`, the method creates the broadphase as `new AABB(vec36, vec37.add(0.0, t, 0.0)).inflate(s, 0.0, s)` (LocalPlayer.java:917; old 1.15.2 :905), then creates the two side segments by offsetting the start and end by `±vec38.scale(s * 0.5F)` (lines 922–927), where `vec38=vec34.cross(up)`. `vec34` has zero Y because both the measured displacement vector and the input/yaw fallback vector are constructed with Y=0 (lines 878–899). The exact order and arithmetic are unchanged in 1.15.2. `Player.java` pins width 0.6 and height at least 0.6 for STANDING (1.8), CROUCHING (1.5), FALL_FLYING, SWIMMING and SPIN_ATTACK (0.6 each). SLEEPING and DYING use 0.2 by 0.2 dimensions from `LivingEntity.SLEEPING_DIMENSIONS` and `Player.POSES`; those poses are explicitly excluded from this bound.

Any side-segment point is inside the uninflated movement-endpoint AABB expanded by at most `1.01s/2` in each horizontal axis. The perpendicular vector is `vec34` crossed with the vertical unit vector, so its horizontal magnitude is at most the magnitude of `vec34`. Since `vec34` is produced by `Mth.fastInvSqrt(float)` from a finite input greater than `0.001F`, its length factor is within the conservative bound `[0.99,1.01]` for the cited one-step Newton refinement. The query AABB then adds another `s` along both X and Z. A point where a side segment intersects a unit full-block shape has at least `s - 1.01s/2 = 0.297` query overlap in each horizontal axis for `s=0.6`. Vertically, the side segment is at `playerY+0.51`; the query begins at `playerY` and extends to `playerY+t`. For a unit full-block Y interval containing that segment point, query overlap is at least `min(t-0.51,0.51,1)`: this is `0.09` or more for the listed poses with `t>=0.6`.

The accepted collector fringe is below `1.0E-7` across all four `findBits` endpoints in the old `Shapes.create(AABB)` grid path; the old grid snap can make that small positive overlap tangent while the newer raw-AABB fast path retains it. The auto-jump segment-hit condition for the listed poses forces at least `0.297` horizontal and `0.09` vertical query overlap, far above that bound. Conversely, if the collector query were in the sub-`1.0E-7` fringe, neither tested side segment can intersect the same full-cube collision shape for these pose dimensions. The witness for `Entity.move` therefore does not transfer to auto-jump, and no auto-jump response difference follows from that specific collector change.

## Scope and other dependencies

This proof applies to canonical full-block collision shapes and the named raw-AABB versus `Shapes.create(AABB)` collector optimization only, and only when current pose dimensions are one of those listed above. The sleeping and dying poses (height 0.2) remain a named unresolved auto-jump fast-path applicability dependency. `updateAutoJump` still consumes other block and entity collision shapes; those independent provider/state routes and the changed `EntityGetter` semantics remain separately scoped. No collision snapshot, movement trajectory, runtime test, or producer timing is claimed.

## Exact source hashes

- 1.15.2 `LocalPlayer.java`: 3a9019bd7b860e251c23fd8d0cd70b7f5b38566d34470c4e29b1014ef689ccbd
- 1.16.5 `LocalPlayer.java`: 6011569e766bb1568609147be9aa14e9c08c51948e3d3a60fd066e848f6a8c2b
- 1.16.5 `AABB.java`: 514558cf4827679d84a4debd4f400d7d65bd4b8982b7ae8124a0d83a916ba878
- 1.16.5 `CollisionGetter.java`: b507d6be11e5985a62cfeb249a99dcb5f8edaf346f12cb2487797d9e01763eac
- 1.16.5 `Mth.java`: 0e4424ffa12923314c309ae13e012768271dfcace4b5a84727f74e8170afe290
- 1.16.5 `Player.java`: d2e26589bdb6a20dc914266db06aa48f50811efc792d6e63b3008c9199914960
- 1.16.5 `LivingEntity.java`: b5d8a1a3c80f85d5d545b5a777e9e2a915dc002a7e31b5f5ad12bf7e285d7a88
- Existing collision residual report: `.native-source/20261030/1152-1165-finish43/collision-collection-residual/report.md` SHA-256 `3a67f32fdc97d048cd31a1ee735ff5668f486c15d2c44583dc1605cc6bd3eb7c`
