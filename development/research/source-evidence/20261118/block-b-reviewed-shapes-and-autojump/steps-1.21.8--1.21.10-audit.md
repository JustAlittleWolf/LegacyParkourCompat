# Independent source audit: ordinary dry step collision, 1.21.8–1.21.10

Status: COMPLETED

## Verdicts

- 1.21.8 → 1.21.9: NO CHANGE in ordinary axis collision, step candidate selection, or selected-vector position settlement for a dry local player move.
- 1.21.9 → 1.21.10: NO CHANGE in the same bounded operation.

No eligible witness was found. The 1.21.8→1.21.9 axis-order helper was relocated from `Entity` to `Direction`; its predicate and returned order are identical. The candidate and collision procedures are unchanged. The three control collision geometries (full cube, bottom slab, inside stair) are unchanged.

## Evidence identity and integrity

All sources use Mojmap. Source identity pins supplied with the assignment were verified:

- 1.21.8 `decompiled_minecraft/1.21.8/mojmap.sources.sha256`: SHA-256 `8ffb76cea647a2ba4fe58e000678f751bea2c40ea6358bae492e962ed1d9d008` (match).
- 1.21.9 `decompiled_minecraft/1.21.9/mojmap/.decompile-provenance.json`: SHA-256 `37d3049478585a43ffcace14fc8f2ee814b35ceedd8c9bad8b60adaed9b72c4f` (match).
- 1.21.10 `decompiled_minecraft/1.21.10/mojmap.sources.sha256`: SHA-256 `4be26049350c1b314a0b198022cb1e7ab1e745e104047de7a5c7d099a9d7b7e1` (match).

For each version, SHA-256 of the examined source files matched that version’s source manifest/provenance. In particular, `Entity.java`, `Direction.java` (or 1.21.8’s local helper in `Entity.java`), `Shapes.java`, `VoxelShape.java`, `CollisionGetter.java`, `BlockCollisions.java`, `Block.java`, `SlabBlock.java`, `StairBlock.java`, `BlockBehaviour.java`, `EntityCollisionContext.java`, and `LivingEntity.java` were checked. Method bodies cited below are intact in those pinned files.

## Reachable local-player path

The ordinary client path reaches the same inherited movement operation: `LocalPlayer.aiStep` delegates through `Player.aiStep` to `LivingEntity.aiStep`; the movement-capable branch calls `travel`, whose dry ordinary branch invokes `this.move(MoverType.SELF, this.getDeltaMovement())`.

- 1.21.8: `client/player/LocalPlayer.java:683,830`; `world/entity/player/Player.java:564,576`; `world/entity/LivingEntity.java:2721,2817–2819,2245–2271`.
- 1.21.9 and 1.21.10: `client/player/LocalPlayer.java:687,834`; `world/entity/player/Player.java:455,466`; `world/entity/LivingEntity.java:2751,2845–2849,2275–2301`.

This confirms the real local move dispatch; travel-vector generation and other travel modes are outside this bounded audit.

## Ordinary collision and step selection

For all three releases, `Entity.move` sends the requested vector to `collide`, then settles the returned vector by adding it to the current position and calling `setPos` (`Entity.java:663–750` in 1.21.8, `670–763` in 1.21.9, `670–766` in 1.21.10). On the normal SELF path, the ordinary vector is not modified by the piston-only limit. The 1.21.10 stuck-multiplier piston condition also leaves SELF movement unchanged.

The `Entity.collide` body is the same operation on every side (`1.21.8 Entity.java:1002–1052`; `1.21.9:1022–1072`; `1.21.10:1025–1075`):

1. Compute ordinary per-axis clipping first.
2. Consider stepping only when `maxUpStep() > 0`, a downward move was clipped or the entity is on ground, and X or Z was clipped.
3. For downward clipping, use the normally clipped Y position as the step base; otherwise use the original box and extend the candidate box downward by `1.0E-5F`.
4. Collect candidate heights from collider Y coordinates as floats relative to the step base; reject negative heights and the ordinary clipped Y height, stop above `maxUpStep`, deduplicate, and sort ascending.
5. Clip the requested X/Y/Z against those colliders for each candidate. A candidate replaces normal movement only on strict `candidate.horizontalDistanceSqr() > ordinary.horizontalDistanceSqr()`. Equality therefore keeps ordinary movement. The loop returns the first strictly better candidate in ascending-height order, rather than comparing later candidates against that accepted candidate. The returned vector is settled with the same `originalBox.minY - stepBase.minY` adjustment on every side.

Per-axis settlement is also unchanged. 1.21.8 `Entity.java:1075–1095` iterates Y-X-Z when `abs(x) >= abs(z)`, otherwise Y-Z-X. 1.21.9 and 1.21.10 call `Direction.axisStepOrder` (`Entity.java:1100–1117` and `1103–1119`; `core/Direction.java:375–376`), which applies the same strict `<` test and the same axis sequences. Thus equal absolute X/Z displacement selects Y-X-Z in all three versions. Each nonzero axis is clipped against the box already offset by previously settled axes. `Shapes.collide` (`world/phys/shapes/Shapes.java:211–220`) and `VoxelShape.collide` (`world/phys/shapes/VoxelShape.java:246–305`) have identical source hashes across the three versions; they retain the same shape iteration, epsilon gates and min/max clipping math.

The normal selected vector and step-selected vector are therefore settled to the same position for equal inputs, box, collider list and max step height. `LivingEntity.maxUpStep` has the same attribute lookup and passenger-player floor in all versions (`LivingEntity.java:3651–3654` in 1.21.8; `3677–3680` in 1.21.9/1.21.10).

## Collision geometry controls and dependencies

- Full cube: block collision uses `Shapes.block()`; the common block-shape helpers retain their same construction math (`Block.java:151–175` in 1.21.8, `155–179` in 1.21.9/1.21.10). A full-cube shape clips the same axes and contributes the same Y candidate coordinate in each release.
- Bottom slab: `SlabBlock.SHAPE_BOTTOM` remains `Block.column(16.0, 0.0, 8.0)`, and `getShape` selects it for `SlabType.BOTTOM` (`SlabBlock.java:35–36,59–65` in all three versions). Its code hash matches across all three.
- Inside stair: `StairBlock` uses identical outer/straight/inner unions and orientation maps (`StairBlock.java:38–48`) and selects the inner map for `INNER_LEFT`/`INNER_RIGHT` at `77–91` in all versions. Its code hash matches across all three.
- Block-shape collection: `CollisionGetter.getBlockCollisions` still creates the entity collision context and delegates to `BlockCollisions`; the block iteration still obtains each state’s collision shape and offsets it. `BlockCollisions.java` is byte-identical across versions. `CollisionGetter.java` changed elsewhere, and `EntityCollisionContext.java` changed from 1.21.8, but the specified cube, slab, and stair shape providers do not branch on that context, so those differences do not affect these controls.

## Limits

Static source review only; no runtime launch or tests. This is a bounded comparison of dry ordinary local-player axis/step selection, returned-vector settlement and the three requested geometry controls. Crouch-edge handling, AutoJump, piston and other movers, movement callbacks/effects, collision-state side effects, and other source lanes are not verdicts here. The 1.21.8→1.21.9 changes to fall-damage clipping and movement-history/callback bookkeeping in `Entity.move` are outside this selection/settlement slice; the final selected-vector position write remains the same.

