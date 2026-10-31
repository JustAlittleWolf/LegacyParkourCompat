# A tiny closing shulker-box lid retains its collision strip

1.16.5, Mojmap: `net/minecraft/world/level/block/ShulkerBoxBlock.java`, `getShape`, lines 218–220; `net/minecraft/world/level/block/entity/ShulkerBoxBlockEntity.java`, `getBoundingBox`, lines 93–100; `net/minecraft/world/phys/shapes/Shapes.java`, `create` and `findBits`; `net/minecraft/world/entity/Entity.java`, `collide` and `collideBoundingBox`.

1.17 and 1.17.1, Mojmap: `ShulkerBoxBlock.getShape`, lines 223–225; `ShulkerBoxBlockEntity.getBoundingBox`, lines 100–101; `net/minecraft/world/entity/monster/Shulker.java`, `getProgressAabb`, lines 244–253; `Shapes.create` and `findBits`.

At the equal closing progress `1.4901161193847656e-8F`, a WEST-, DOWN- or NORTH-facing box has a tiny negative-facing extension. The old `Shapes.create(AABB)` encoding retains this outward strip; the new grid-alignment test rounds its endpoint to the unit cube. The underlying bounding-box formula is equal for this progress. The progress is reachable by three opening ticks followed by three closing ticks with the historical float additions and subtractions.

For a standing local player beside the WEST-facing box, the accepted X-only SELF request `0.049999999` clips to `0.0499999925494194` on the old strip while the new cube leaves the request unchanged. This route is the direct one-axis `Shapes.collide` state-provider scan, rather than the three-axis `CollisionSpliterator` query. It reads the same vanilla block state and block-entity progress and does not change either input.
