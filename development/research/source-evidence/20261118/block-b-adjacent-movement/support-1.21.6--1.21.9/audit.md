# COMPLETED — Blind source audit: support and retreat edges

## Identity and scope

Independent source-only audit of the exact adjacent edges **1.21.6→1.21.7**, **1.21.7→1.21.8**, and the bounded crouch/support slice of **1.21.8→1.21.9**. All sources are read-only `mojmap` decompilations under `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/<version>/mojmap/`.

Compared operation scope: dry player movement's sneak-triggered edge retreat and its solid support predicate; movement collision and step candidate selection for the first two edges; full-cube, slab, stair and iron-bars/pane shape controls for support and collision boundaries. AutoJump and unrelated movement changes are excluded.

## Source identity and integrity

- 1.21.6 `.decompile-provenance.json` SHA-256: `56b036c61094d1d936882bdf5b2b59044512189cb33adbb075880c727da9b19e` — matches assignment.
- 1.21.7 `.decompile-provenance.json` SHA-256: `45bf296d921b2239fd0aedb2684e2c1cb9b7d7532b4106b8a2fd9b09309ca82d` — matches assignment.
- 1.21.8 `mojmap.sources.sha256` SHA-256: `8ffb76cea647a2ba4fe58e000678f751bea2c40ea6358bae492e962ed1d9d008` — matches ready metadata; `mojmap.ready.json` identifies version `1.21.8`, mapping `mojmap`, status `ready`.
- 1.21.9 `.decompile-provenance.json` SHA-256: `37d3049478585a43ffcace14fc8f2ee814b35ceedd8c9bad8b60adaed9b72c4f` — matches assignment.
- The source SHA-256 entries were checked against actual files for `LocalPlayer.java`, `Player.java`, `Entity.java`, `CollisionGetter.java`, `BlockBehaviour.java`, `Block.java`, `SlabBlock.java`, `StairBlock.java`, `IronBarsBlock.java`, `CrossCollisionBlock.java`, `Shapes.java`, and `VoxelShape.java` in all four version trees; every present checked entry matched.

## Per-edge verdicts

### 1.21.6→1.21.7 — no assigned movement delta found

**Evidence:** In both `net/minecraft/world/entity/Entity.java`, `move` routes the attempted movement through `maybeBackOffFromEdge` before `collide` (1.21.6 lines 682–683; 1.21.7 lines 682–683). In `net/minecraft/world/entity/player/Player.java`, `isStayingOnGroundSurface()` is `isShiftKeyDown()` (lines 373–375) and the `maybeBackOffFromEdge` guard and retreat loops are unchanged (lines 1038–1099). The guard requires not flying, nonpositive vertical request, SELF/PLAYER mover, shift held, and `isAboveGround(maxUpStep)`. Horizontal X and Z are tested separately, then diagonally; each retreat subtracts signed `0.05` steps until the inset support probe becomes safe or the component is zeroed. `canFallAtLeast` calls solid-only `level().noCollision` on the player box inset by `1.0E-7` and extended down by the requested depth plus `1.0E-7`.

Solid collision/step selection in `Entity.java` is also unchanged: the collision result and blocked-axis flags gate step-up; the probe expands by `maxUpStep` and by `-1.0E-5F` when not descending; candidate heights cast each Y coordinate difference to `float`, reject negative/equal-to-current-Y values, stop above max step, then use `FloatArrays.unstableSort`; candidate acceptance uses strict `horizontalDistanceSqr() >`.

**Movement meaning:** Under the same grounded/shift/mover gates and same block support shapes, the requested horizontal movement is shortened in the same 0.05 increments; solid obstruction and step candidates are considered in the same order and with the same float/strict-comparison rules.

### 1.21.7→1.21.8 — no assigned movement delta found

**Evidence:** The same caller order and the same Player retreat, `isAboveGround`, and `canFallAtLeast` bodies remain in `Entity.java` and `Player.java` (same line locations as 1.21.7). The `Entity.collide`, `collectCandidateStepUpHeights`, `collectColliders`, `collideWithShapes`, and `axisStepOrder` bodies checked for 1.21.7 and 1.21.8 match in the scoped algorithm: world-border collider then block colliders; Y-coordinate-to-float candidate generation, filters and unstable sort; axis-wise `Shapes.collide`; strict horizontal-distance improvement.

**Movement meaning:** No source change was found in the bounded retreat/support operation or in the solid collision/step candidate selection; the same request and same surrounding shapes produce the same scoped movement decision.

### 1.21.8→1.21.9 — no crouch/support-gate or control-shape delta found

**Evidence:** `Player.maybeBackOffFromEdge`, `isAboveGround`, `canFallAtLeast`, and `isStayingOnGroundSurface` retain the same logic; only line locations shift (`maybeBackOffFromEdge` begins at line 891 in 1.21.9 versus 1038 in 1.21.8). Its shift-key gate, mover and vertical conditions, 0.05 retreat loop, `1.0E-7` inset, solid-only no-collision probe and `maxUpStep` fall check are unchanged. The compared support/collision controls are unchanged: `BlockBehaviour` defaults full-block outline/collision to `Shapes.block()` and collision delegates to the state's shape when collision is enabled; `SlabBlock` keeps bottom/top half-height shapes and double as `Shapes.block()`; `StairBlock` retains its half/facing/inner/outer shape table; `IronBarsBlock` retains inherited `CrossCollisionBlock` collision-shape behavior. No difference affecting the full-versus-partial support boundary was found in these controls.

**Movement meaning:** For crouch retreat's support check and the assigned full/partial controls, support presence and the retreat decision are unchanged.

## Gaps and boundaries

- This is not a full movement-tick inventory or pair freeze; verdicts apply only to the stated operations and controls.
- The 1.21.8→1.21.9 solid-collision/step algorithm was not audited as a standalone operation, per the bounded assignment. That version has broader collision-code differences outside this verdict; no claim of parity is made for those changes.
- No runtime trajectory, recording, game launch, tests, or implementation was performed. Block-state generation and every block variant were not exhaustively enumerated; the stated control classes were checked at source level.
- No exact patch-introduction claim beyond the three adjacent comparisons is made.
