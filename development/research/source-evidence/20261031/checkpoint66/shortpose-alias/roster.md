# Frozen applicability roster — short-pose expanded block query

Task route: verify release applicability and operation routing for the accepted displacement-expanded short-pose query, including exact 1.16.0–1.16.5 and 1.17.0–1.17.1 source call paths. This roster was fixed before reading the additional source bodies for the assigned patch-range audit. The source root is the preparation owner's read-only `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/`.

## Releases and source files

- Older patch line: `1.16`, `1.16.1`, `1.16.2`, `1.16.3`, `1.16.4`, `1.16.5`; each uses its `mojmap` source tree.
- First newer releases: `1.17`, `1.17.1`; each uses its `mojmap` source tree.
- Core call route in every release: `net/minecraft/world/entity/Entity.java` (`collide`, `collideBoundingBoxHeuristically`, and axis resolver); `net/minecraft/world/level/CollisionGetter.java` (`getBlockCollisions` overloads); `net/minecraft/world/level/CollisionSpliterator.java` (constructor, candidate admission and full-cube exception); `net/minecraft/world/phys/shapes/Shapes.java` (`create(AABB)`, `findBits`, shape join); `net/minecraft/world/phys/shapes/IndirectMerger.java` (coordinate coalescing and index-pair emission).
- Inputs and provider route in every release: `net/minecraft/world/entity/EntityDimensions.java` (`makeBoundingBox`); `net/minecraft/world/entity/player/Player.java` (swimming dimensions); `net/minecraft/world/level/block/MovingPistonBlock.java` (`getCollisionShape`); `net/minecraft/world/level/block/piston/PistonMovingBlockEntity.java` (`getCollisionShape`, movement direction, extended progress and raw progress load).
- Identity metadata in every release: `mojmap.ready.json`, `mojmap.provenance.json`, `mojmap.sources.sha256`, `artifacts.sha256`.

## Question bound

Determine whether all six old patch releases preserve the same three-axis SELF displacement → expanded block query → `Shapes.create(AABB)` encoding behavior and collision-spliterator admission applicable to the accepted equal-input moving-piston witness, and determine the first new release whose queried-shape encoding / admission differs. Do not assess unrelated single-axis ShulkerBox direct block scans, any other finding, ordinary tick production/reachability of the persisted progress, or runtime parity.
