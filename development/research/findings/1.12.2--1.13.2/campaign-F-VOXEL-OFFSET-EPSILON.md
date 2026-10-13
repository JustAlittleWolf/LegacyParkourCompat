# F-VOXEL-OFFSET-EPSILON — repeated voxel offsets stop below 1e-7

1.12.2, ornithe-feather; feather-gen2-1.12.2+build.2-mergedv2.jar, feather-gen2-1.12.2+build.2.tiny: `1.12.2/ornithe-feather/net/minecraft/util/math/Box.java`, `public double intersectX(Box box, double limit) {`, lines 185-203.
1.13.2, ornithe-feather; feather-gen2-1.13.2+build.2-mergedv2.jar, feather-gen2-1.13.2+build.2.tiny: `1.13.2/ornithe-feather/net/minecraft/block/shape/VoxelShapes.java`, `public static double calculateMaxOffset(Direction.Axis axis, Box box, Stream<VoxelShape> shapes, double maxDist) {`, lines 194-206.

Difference: - In 1.12.2, `Box.intersectX` clips a positive requested displacement to the nearest gap with no small-distance cutoff.

With a positive gap below `1.0E-7`, a farther collision box leaves that clipped gap unchanged.

- In 1.13.2, `VoxelShapes.calculateMaxOffset` iterates the candidate shapes.

It returns `0.0` at the start of an iteration whenever `Math.abs(maxDist) < 1.0E-7`. `VoxelShape.calculateMaxDistance` can first reduce a larger displacement to a positive gap below that threshold; processing a later shape then replaces that gap with zero.

- Concrete paired preconditions: the player is airborne, has no sneak-edge probe or step candidate, moves in +X with `dx = 2.0`, and has no other nearby entity or collision shape.

Its X max is less than `1.0E-7` from the next block face, and two collision full cubes lie in the swept volume along the same Y/Z interval in a loaded, in-border area.

Use corresponding Stone blocks at (2,64,0) and (3,64,0), with player `box.maxX` about `1.99999995`, `box.minY` above 64, and Z bounds inside (0,1). 1.12.2 and 1.13.2 Stone inherit equal unit-cube collision shapes from their base Block implementations.

Both collectors include both blocks. 1.12.2 visits increasing X candidates and keeps the small positive gap after the farther block; 1.13.2's X-fastest region iterator returns the nearer Stone before the farther one, and its stream cache retains that order.

The first 1.13.2 shape clips to the gap; the second shape triggers the `1.0E-7` early return.

The paired player-correction handler accepts absolute packet position coordinates, and the paired velocity handler writes packet velocity divided by 8000, so this precise position and a 2.0 displacement are representable through existing player movement inputs.

Movement meaning: - Both `LivingEntity.travel` bodies pass the player's velocity to `Entity.move(MoverType.SELF, ...)`; the local client player reaches `LivingEntity.travel` through its movement tick.

The existing player-correction handler writes packet coordinates to the player, and the existing velocity-packet handler can supply nonzero motion; those bounded 1.12.2/1.13.2 paths are recorded in `run.md` as `PLAYER-CORRECTION` and `VELOCITY-PACKETS`.

- 1.12.2 Stone uses the base 1.12.2 `Block.getShape` full-cube Box and base `getCollisionShape`/`addCollisions` path.

Original evidence: [F-VOXEL-OFFSET-EPSILON.md](../../archive/legacy-workflows/source-campaign-2026-10-07/1.12.2--1.13.2/findings/F-VOXEL-OFFSET-EPSILON.md).
