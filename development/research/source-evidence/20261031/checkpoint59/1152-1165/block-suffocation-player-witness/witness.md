# Conditional local-player witness: partial-height suffocation registrations

## Scope and claim boundary

This is a concrete source-level state/input witness for the already reviewed 1.15.2→1.16.5 provider residual. It closes the reviewer’s missing box/escape-direction calculation, without claiming that normal walking or a particular server correction produces the starting embedded position. Equal sampled block state and pre-tick player state are assumed; runtime trajectory and producer proof are outside this source-only claim.

## Pair evidence

The 1.15.2 `Block.isSuffocating` default returns `material.blocksMotion() && state.isCollisionShapeFullBlock(...)` (`Block.java:285–286`). Farmland does not override this predicate in the old `Blocks` registration; its collision shape is `Block.box(0,0,0,16,15,16)` (`FarmBlock.java:27–29`). Thus the same farmland state at `(0,64,0)` is not suffocating under the old provider.

In 1.16.5, `Blocks.FARMLAND` registers `.isSuffocating(Blocks::always)` (`Blocks.java:494–503`); `FarmBlock.SHAPE` remains the 15/16-high collision shape (`FarmBlock.java:27–29`). The exact 1.16.5 `LocalPlayer.suffocatesAt` builds a unit X/Z AABB with the player's vertical extent, deflates it by `1.0E-7`, and calls `level.noBlockCollision` with `BlockState.isSuffocating` (`LocalPlayer.java:439–447`). `CollisionGetter.noBlockCollision` is `getBlockCollisions(...).allMatch(VoxelShape::isEmpty)` (`CollisionGetter.java:62–64`), so a predicate-accepted farmland collision shape intersecting the query makes the method return true.

The prior independent registration review’s consumer source trace remains accurate: old `LocalPlayer.aiStep` has four `!noPhysics` `checkInBlock` probes at `LocalPlayer.java:650–655`; new `aiStep` has four `!noPhysics` `moveTowardsClosestSpace` probes (`LocalPlayer.java:656–661`). Old `blocked/freeAt` reaches `isSuffocating` (`LocalPlayer.java:431–443`, `Player.java:1489–1491`). New `moveTowardsClosestSpace` checks four horizontal candidate cells in WEST, EAST, NORTH, SOUTH order and writes exactly one horizontal delta component to `±0.1` when it finds a clear candidate (`LocalPlayer.java:414–437`).

## Concrete sampled state

Use the same farmland state at `(0,64,0)`, with air in every other block cell intersecting the player box and every cell intersecting the four horizontal candidate columns. Set a non-noPhysics LocalPlayer pre-tick bounding box to `[0.2, 64.8, 0.2]–[0.8, 66.6, 0.8]`, with center `(0.5,64.8,0.5)`, and any starting delta movement. This box overlaps farmland’s collision volume by `0.1375` vertically (`64.9375 - 64.8`), stays strictly inside its X/Z footprint, and stays inside the world cell; it is a specific valid AABB input to the source consumer.

For each new probe, `d/e` are `0.29` or `0.71`, so `new BlockPos(d, getY(), e)` is `(0,64,0)`. Its suffocation query intersects the farmland shape and accepts the state because the new registration predicate returns true. Each horizontally adjacent candidate query is disjoint from the farmland cell (only boundary-touching after the `1.0E-7` deflation) and contains air, so `suffocatesAt` is false there. For the four probes in source order, the nearest clear directions are WEST, WEST, EAST, EAST: the coordinate fractions are `(.29,.71)`, `(.29,.29)`, `(.71,.29)`, and `(.71,.71)`, with the implementation retaining the first direction on equal-distance ties. Each writes the corresponding X impulse; after the fourth probe the final result is `deltaMovement.x = +0.1` (Y and Z preserved).

For the paired old source on the same block state and box, every sampled block position is `(0,64,0)` and `freeAt` sees old `isSuffocating == false`; therefore `blocked` is false, no check-in-block call writes a horizontal escape impulse, and this suffocation route leaves the initial delta movement unchanged.

## Verdict

The registration delta is movement-reachable through a named LocalPlayer tick response and has a concrete same-input witness: with `deltaMovement.x = 0` at tick entry, the new result is `+0.1` while the old result remains `0`. This is conditional on the stated sampled state and embedded pre-tick box, and does not assert ordinary-grounded reachability or state-production frequency. Soul Sand and Grass Path share the same changed `isSuffocating` registration pattern and partial-height provider class; this witness uses Farmland only and does not claim independent behavior for those blocks.

## Source fingerprints

- 1.15.2 `LocalPlayer.java`: `3A9019BD7B860E251C23FD8D0CD70B7F5B38566D34470C4E29B1014EF689CCBD`
- 1.16.5 `LocalPlayer.java`: `6011569E766BB1568609147BE9AA14E9C08C51948E3D3A60FD066E848F6A8C2B`
- 1.16.5 `FarmBlock.java`: `1298367814491EF92FE8C0F43F1C14E173E54182859C3A68FA9422B60D23C810`
- 1.16.5 `Blocks.java`: `3B39D5CC4CD22F146ED3195AA30CBB9FDFCA49F63783FABF9924A6CE7795FA12`
- 1.16.5 `CollisionGetter.java` and 1.15.2 `Block.java` are cited by exact method/line above; their source files must be included in the reviewer’s rehash.
