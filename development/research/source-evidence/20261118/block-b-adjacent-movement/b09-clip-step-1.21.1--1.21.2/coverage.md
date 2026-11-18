# B09 source coverage: 1.21.1 to 1.21.2

## Evidence identity

- 1.21.1 Mojmap source root: `decompiled_minecraft/1.21.1/mojmap`; release-ready record identifies `1.21.1`, Mojmap, 5,363 files and 25,773,546 source bytes. `mojmap.sources.sha256` SHA-256: `900f956e00f6fc1300bb3d689ea49df2b1a57bcaa54617344ef456d95d47cb48`; Mojang `client_mappings.txt`: `140c47931cccc8fc9e4c22d7603e2d714d1a953a146f51ea7397d95c955536ec`.
- 1.21.2 Mojmap source root: `decompiled_minecraft/1.21.2/mojmap`; `.decompile-provenance.json` SHA-256: `00f399407dc447737ea83e3e46562418f8c712f66e3f51cb85d46afa2ecac575`; its pinned official mappings input `client.txt`: `e37560e4889168916a73dbc4922c563002eff70fbecfe1cf5c50a7b222f451b2`.
- Hashes of every source file named below were checked against the corresponding source manifest/provenance entry. Both releases' relevant methods are intact.

## Bounded slice and verdict

No eligible difference found for dry, border-clear, entity-free ordinary entity movement through `Entity.collide`, including step candidate acquisition, rejection, trial ordering and final candidate settlement. The paired `Entity.java` bodies at 1.21.1 lines 875-983 and 1.21.2 lines 908-1016 are textually equivalent in control flow, constants, casts, comparisons, iteration and returned vectors. In both, base collision is skipped only for zero squared movement; stepping is gated by positive `maxUpStep`, downward Y clipping or `onGround`, and horizontal clipping. Candidate heights are shape Y coordinates minus `box.minY`, cast to `float`, rejected when negative or equal to the baseline clipped Y, stop scanning a shape when above max step, deduplicated, then unstable-sorted. Trial movement keeps requested X/Z, tests candidates in ascending order and takes the first whose horizontal-distance squared strictly exceeds baseline; accepted output applies the original-box/step-box minY offset. Otherwise the base clipped vector is returned.

The shared clipping primitive is also equivalent: `collideWithShapes` resolves Y first, then orders X/Z by `abs(x) < abs(z)`, moving the working AABB after each nonzero clip; `Shapes.collide` applies each shape sequentially and returns zero once the remaining absolute delta is below `1.0E-7`; `VoxelShape.collide/collideX` preserves the same axis cycle, epsilon-index bounds, direction-dependent scan, and `1.0E-7` comparisons. `AABB.expandTowards` uses the same sign-conditioned min/max updates.

Shape/world dependencies and controls are closed for this slice. `CollisionGetter.getBlockCollisions` creates the same `BlockCollisions` iterator. Its cursor bounds, inflation epsilons, block filtering, translated collision shapes and intersection checks are unchanged. The 1.21.2 refactor changes the shape lookup to `CollisionContext.getCollisionShape`; for a player, `CollisionContext.of(entity)` still creates the ordinary `EntityCollisionContext`, and that method delegates to `BlockState.getCollisionShape` with the same context. The context values for player descent, entity bottom, held item and fluid predicate match the earlier constructor. Border-clear omits the world-border shape; entity-free returns no entity shapes (`EntityGetter.getEntityCollisions` returns empty for an empty query). Default full cubes still use `Shapes.block()`. Slab collision shape remains the state-selected shape: double is full cube, top is `TOP_AABB`, bottom is `BOTTOM_AABB`; the lower slab's Y top remains 0.5. These controls yield the same candidate/clip/rejection behavior.

## Compared source members

- `net/minecraft/world/entity/Entity.java`: `collide`, `collectCandidateStepUpHeights`, `collideBoundingBox`, `collectColliders`, `collideWithShapes` (1.21.1 875-983; 1.21.2 908-1016).
- `net/minecraft/world/level/CollisionGetter.java`: `getEntityCollisions` declaration and `getBlockCollisions` (1.21.1 75-85; 1.21.2 81-91).
- `net/minecraft/world/level/EntityGetter.java`: `getEntityCollisions` (1.21.1 57-75; 1.21.2 54-72).
- `net/minecraft/world/level/BlockCollisions.java`: constructors and `computeNext` (1.21.1 32-95; 1.21.2 32-100).
- `net/minecraft/world/phys/shapes/CollisionContext.java` and `EntityCollisionContext.java`: entity-context selection, collision-shape dispatch and player-context fields (relevant members around 1.21.1 7-65; 1.21.2 11-72).
- `net/minecraft/world/level/block/state/BlockBehaviour.java`: default block/full-cube shape and collision-shape dispatch (1.21.1 304-310; 1.21.2 308-314).
- `net/minecraft/world/level/block/SlabBlock.java`: `BOTTOM_AABB`, `TOP_AABB`, and state-selected `getShape` (1.21.1 32-33, 56-65; 1.21.2 35-36, 59-68).
- `net/minecraft/world/phys/shapes/Shapes.java`: `collide` (both 204-214).
- `net/minecraft/world/phys/shapes/VoxelShape.java`: `collide` and `collideX` (1.21.1 218-277; 1.21.2 241-300).
- `net/minecraft/world/phys/AABB.java`: `expandTowards` (1.21.1 153-179; 1.21.2 154-180).

## Limits

This closes only the assigned collision/step-selection slice and the stated world/shape dependencies and controls. It does not close the full movement tick or other step-triggering environment/entity cases. Review stopped at the collision result; post-collision position writes, block callbacks, AutoJump and other later state/effects were not examined.
