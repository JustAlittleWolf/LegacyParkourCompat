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


## Current evidence and remaining work

Source decisions, implementation coverage and runtime parity are separate. Source paths are relative to `decompiled_minecraft/`; immutable Git evidence can be read with `git show <revision>:<path>`.

```json
{
  "implementation": {
    "status": "open",
    "changes": [],
    "hooks": [],
    "limits": [
      "Complete current Change/catalog inventory inspected; no dedicated operation implements F-COLLISION-REPRESENTATION — 1.13.2 changes player collision resolution representation; later broadly related hooks do not establish this claimed delta.",
      "Complete current Change/catalog inventory inspected; no dedicated operation implements F-VOXEL-OFFSET-EPSILON — repeated voxel offsets stop below 1e-7; later broadly related hooks do not establish this claimed delta."
    ]
  },
  "applicability": [],
  "sources": [
    {
      "version": "1.12.2",
      "mapping": "ornithe-feather; feather-gen2-1.12.2+build.2-mergedv2.jar, feather-gen2-1.12.2+build.2.tiny",
      "path": "1.12.2/ornithe-feather/net/minecraft/entity/Entity.java",
      "member": "public void move(MoverType moverType, double x, double y, double z) {",
      "lines": "462-795",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a; matches an archived source-file digest. Checked full declaration/body; snippet SHA-256 2a74faa4005108a1dbda32b0799522b2feb786f19fa9ad3b3bba9d40e1ff4687."
    },
    {
      "version": "1.12.2",
      "mapping": "ornithe-feather; feather-gen2-1.12.2+build.2-mergedv2.jar, feather-gen2-1.12.2+build.2.tiny",
      "path": "1.12.2/ornithe-feather/net/minecraft/world/World.java",
      "member": "private boolean getCollisions(@Nullable Entity entity, Box shape, boolean enforceWorldBorder, @Nullable List<Box> collisions) {",
      "lines": "964-1015",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 e9fa9b8d6d31ad57a5b876f5f63a5e3c554a23437decf1845a493daf48233594; matches an archived source-file digest. Checked full declaration/body; snippet SHA-256 7e6fc52e791970a1434bf0384cce5fafea6fab8e419852f612b517c8432fae55."
    },
    {
      "version": "1.12.2",
      "mapping": "ornithe-feather; feather-gen2-1.12.2+build.2-mergedv2.jar, feather-gen2-1.12.2+build.2.tiny",
      "path": "1.12.2/ornithe-feather/net/minecraft/world/World.java",
      "member": "public List<Box> getCollisions(@Nullable Entity entity, Box shape) {",
      "lines": "1017-1040",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 e9fa9b8d6d31ad57a5b876f5f63a5e3c554a23437decf1845a493daf48233594; matches an archived source-file digest. Checked full declaration/body; snippet SHA-256 629c744b573939adc1cd2f676fcec026d55a13e6762adc01eb2d6ce2f36cf25f."
    },
    {
      "version": "1.12.2",
      "mapping": "ornithe-feather; feather-gen2-1.12.2+build.2-mergedv2.jar, feather-gen2-1.12.2+build.2.tiny",
      "path": "1.12.2/ornithe-feather/net/minecraft/world/World.java",
      "member": "public boolean getCollisions(Box shape) {",
      "lines": "1062-1064",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 e9fa9b8d6d31ad57a5b876f5f63a5e3c554a23437decf1845a493daf48233594; matches an archived source-file digest. Checked full declaration/body; snippet SHA-256 0a28414321edc341d45591b03ad761b1f7519dc1440b32293f1c49e6ac2e7324."
    },
    {
      "version": "1.12.2",
      "mapping": "ornithe-feather; feather-gen2-1.12.2+build.2-mergedv2.jar, feather-gen2-1.12.2+build.2.tiny",
      "path": "1.12.2/ornithe-feather/net/minecraft/world/WorldView.java",
      "member": "WorldView#getBlockCollisions",
      "lines": null,
      "status": "different",
      "detail": "Checked complete canonical file SHA-256 628ce75d2c76ed025b62e905da8030024e1b89b145445fd6971bfef798bba7cc (not bound to a source-file digest in this record); named declaration absent. Named declaration is absent in this checked canonical file; paired existing declaration/range is retained separately, so this is an explicit addition/removal or mapping correspondence limit, not an invented line."
    },
    {
      "version": "1.12.2",
      "mapping": "ornithe-feather",
      "path": "1.12.2/ornithe-feather/VoxelShapes.java",
      "member": "VoxelShapes#calculateMaxOffset",
      "lines": null,
      "status": "missing",
      "detail": "Canonical version/file unavailable; historical symbol retained only as lookup target."
    },
    {
      "version": "1.13.2",
      "mapping": "ornithe-feather; feather-gen2-1.13.2+build.2-mergedv2.jar, feather-gen2-1.13.2+build.2.tiny",
      "path": "1.13.2/ornithe-feather/net/minecraft/entity/Entity.java",
      "member": "public void move(MoverType moverType, double x, double y, double z) {",
      "lines": "473-776",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269; matches an archived source-file digest. Checked full declaration/body; snippet SHA-256 4a2270fbb434f7bde4f2f15062993063560bcda5c372723ee5cdf081fc63cff8."
    },
    {
      "version": "1.13.2",
      "mapping": "ornithe-feather; feather-gen2-1.13.2+build.2-mergedv2.jar, feather-gen2-1.13.2+build.2.tiny",
      "path": "1.13.2/ornithe-feather/net/minecraft/world/World.java",
      "member": "public Stream<VoxelShape> getCollisions(@Nullable Entity entity, VoxelShape shape, VoxelShape excludeBlock, Set<Entity> excludeEntities) {",
      "lines": "1755-1758",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 0023de30da608db73c6c6188334d2bb19039d86fe4d6f5e4ad79d732a5d2cd1a; matches an archived source-file digest. Checked full declaration/body; snippet SHA-256 c47ed7a5dceea59f2d27209c9123378b31b25a69be66a37f30284904f2251294."
    },
    {
      "version": "1.13.2",
      "mapping": "ornithe-feather; feather-gen2-1.13.2+build.2-mergedv2.jar, feather-gen2-1.13.2+build.2.tiny",
      "path": "1.13.2/ornithe-feather/net/minecraft/world/WorldView.java",
      "member": "default Stream<VoxelShape> getBlockCollisions(VoxelShape shape, VoxelShape excludeBlock, boolean checkWorldBorder) {",
      "lines": "112-152",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 9577360b1a1bdde9280f0b72593e5e6dde45d9a54719cd7ff933f3ecd31b971f; matches an archived source-file digest. Checked full declaration/body; snippet SHA-256 ff887bf9c999974aa07f28e9273830f2e4cdf620b6c1ac1ae0e4b73de12cafb1."
    },
    {
      "version": "1.13.2",
      "mapping": "ornithe-feather; feather-gen2-1.13.2+build.2-mergedv2.jar, feather-gen2-1.13.2+build.2.tiny",
      "path": "1.13.2/ornithe-feather/net/minecraft/block/shape/VoxelShapes.java",
      "member": "public static double calculateMaxOffset(Direction.Axis axis, Box box, Stream<VoxelShape> shapes, double maxDist) {",
      "lines": "194-206",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 313968d4e5855b5ec380272b6ababcc5849d478ab771926355912a8cb6aa9c95; not bound to a source-file digest in this record. Checked full declaration/body; snippet SHA-256 18215f5bcc9774b93ec9b17056402184c8b8e59aa67e6d516f41e5010443a2b6."
    },
    {
      "version": "1.12.2",
      "mapping": "ornithe-feather; feather-gen2-1.12.2+build.2-mergedv2.jar, feather-gen2-1.12.2+build.2.tiny",
      "path": "1.12.2/ornithe-feather/net/minecraft/util/math/Box.java",
      "member": "public double intersectX(Box box, double limit) {",
      "lines": "185-203",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 f788b8146b14f299ccb58ea0854609f845c298a963295d503de2e30e15d66f3a; not bound to a source-file digest in this record. Checked full declaration/body; snippet SHA-256 33e1cf4f78413e9986450d7c8a77ef25a4e35e7ac1b703a2a973a0136670179a."
    },
    {
      "version": "1.13.2",
      "mapping": "ornithe-feather; feather-gen2-1.13.2+build.2-mergedv2.jar, feather-gen2-1.13.2+build.2.tiny",
      "path": "1.13.2/ornithe-feather/net/minecraft/block/shape/VoxelShapes.java",
      "member": "public static double calculateMaxOffset(Direction.Axis axis, Box box, Stream<VoxelShape> shapes, double maxDist) {",
      "lines": "194-206",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 313968d4e5855b5ec380272b6ababcc5849d478ab771926355912a8cb6aa9c95; matches an archived source-file digest. Checked full declaration/body; snippet SHA-256 18215f5bcc9774b93ec9b17056402184c8b8e59aa67e6d516f41e5010443a2b6."
    },
    {
      "version": "1.13.2",
      "mapping": "ornithe-feather; feather-gen2-1.13.2+build.2-mergedv2.jar, feather-gen2-1.13.2+build.2.tiny",
      "path": "1.13.2/ornithe-feather/net/minecraft/util/math/Box.java",
      "member": "Box#intersectX",
      "lines": null,
      "status": "different",
      "detail": "Checked complete canonical file SHA-256 0f573d00cc10386f94cf51671c62815979973053c9e67a806e9fee98e55a4d8d (not bound to a source-file digest in this record); named declaration absent. Named declaration is absent in this checked canonical file; paired existing declaration/range is retained separately, so this is an explicit addition/removal or mapping correspondence limit, not an invented line."
    }
  ],
  "sourceReviews": [],
  "openIssues": [
    {
      "missing": "Later applicability gate: exact introduction of campaign-F-COLLISION-REPRESENTATION is unknown within (1.12.2, 1.13.2]",
      "next_action": "Preserve any exact historical verdict for its bounded assertion; verify intervening releases separately before assigning a new implementation range. Formatting alone requires no renewed acceptance."
    },
    {
      "missing": "Current implementation of campaign-F-COLLISION-REPRESENTATION",
      "next_action": "After expert scope review and independent source acceptance, assign this specific operation to the separate implementation phase."
    },
    {
      "missing": "Exact independent source verdict/snapshot identity for F-COLLISION-REPRESENTATION",
      "next_action": "Retain original source-confirmed status only; source-acceptance owner must resolve a preserved independent verdict or review this exact bounded assertion in the later campaign."
    },
    {
      "missing": "Later applicability gate: exact introduction of campaign-F-VOXEL-OFFSET-EPSILON is unknown within (1.12.2, 1.13.2]",
      "next_action": "Preserve any exact historical verdict for its bounded assertion; verify intervening releases separately before assigning a new implementation range. Formatting alone requires no renewed acceptance."
    },
    {
      "missing": "Current implementation of campaign-F-VOXEL-OFFSET-EPSILON",
      "next_action": "After expert scope review and independent source acceptance, assign this specific operation to the separate implementation phase."
    },
    {
      "missing": "Exact independent source verdict/snapshot identity for F-VOXEL-OFFSET-EPSILON",
      "next_action": "Retain original source-confirmed status only; source-acceptance owner must resolve a preserved independent verdict or review this exact bounded assertion in the later campaign."
    }
  ],
  "runtime": "unverified"
}
```
