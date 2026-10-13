# F-WALL-SINGLE-ARM-GAPS — 1.13.2 wall collision unions leave corner gaps

1.12.2, ornithe-feather; feather-gen2-1.12.2+build.2-mergedv2.jar, feather-gen2-1.12.2+build.2.tiny: `1.12.2/ornithe-feather/net/minecraft/block/WallBlock.java`, `public Box getCollisionShape(BlockState state, WorldView world, BlockPos pos) {`, lines 100-103.
1.12.2, ornithe-feather; feather-gen2-1.12.2+build.2-mergedv2.jar, feather-gen2-1.12.2+build.2.tiny: `1.12.2/ornithe-feather/net/minecraft/block/WallBlock.java`, `public Box getShape(BlockState state, WorldView world, BlockPos pos) {`, lines 84-87.
1.12.2, ornithe-feather; feather-gen2-1.12.2+build.2-mergedv2.jar, feather-gen2-1.12.2+build.2.tiny: `1.12.2/ornithe-feather/net/minecraft/block/WallBlock.java`, `private boolean shouldConnectTo(WorldView world, BlockPos pos, Direction dir) {`, lines 146-152.
1.13.2, ornithe-feather; feather-gen2-1.13.2+build.2-mergedv2.jar, feather-gen2-1.13.2+build.2.tiny: `1.13.2/ornithe-feather/net/minecraft/block/WallBlock.java`, `public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos) {`, lines 37-39.
1.13.2, ornithe-feather; feather-gen2-1.13.2+build.2-mergedv2.jar, feather-gen2-1.13.2+build.2.tiny: `1.13.2/ornithe-feather/net/minecraft/block/WallBlock.java`, `public VoxelShape getShape(BlockState state, BlockView world, BlockPos pos) {`, lines 32-34.
1.13.2, ornithe-feather; feather-gen2-1.13.2+build.2-mergedv2.jar, feather-gen2-1.13.2+build.2.tiny: `1.13.2/ornithe-feather/net/minecraft/block/WallBlock.java`, `private boolean shouldConnectTo(BlockState state, FaceShape shape) {`, lines 51-55.

Difference: For a wall with only NORTH connected, both versions derive `UP=true`. 1.12.2 selects `COLLISION_SHAPES[4]`, the single Box X[4,12]×Z[0,12]×Y[0,24] in sixteenth-block coordinates. 1.13.2 selects `VoxelShapes.or(center,northArm)`: center X[4,12]×Z[4,12]×Y[0,24], north arm X[5,11]×Z[0,11]×Y[0,24].

The OR shape leaves two empty corner columns: X[4,5]×Z[0,4] and X[11,12]×Z[0,4]. 1.12.2's single Box fills both. `VoxelShapes.or` performs a Boolean OR over occupied voxels, so matching outer bounds do not make these shapes equal.

The state is reachable with a north-adjacent open east-west-facing fence gate and air on the other sides.

The gate's south face reports `MIDDLE_POLE`, which the wall connection helper accepts for fence gates; its open collision shape is empty in both versions.

For one NORTH connection, 1.12.2's and 1.13.2's placement/neighbor formulas both set `UP=true`, selecting the shapes above.

Movement meaning: The local player reaches the shared `Entity.move` resolver through the recorded player tick/travel path in `run.md` and `WORLD-COLLISIONS`. 1.12.2 gathers translated Box collisions from its swept player Box. 1.13.2 builds a swept VoxelShape query, filters block shapes by Boolean overlap, and passes the returned shape stream to the same player's Y/X/Z movement resolution.

Concrete query relative to the wall at (0,0,0): the player has width `0.6F`, height `1.8F`, center approximately x=-0.06,z=-0.31,y=0, giving a Box approximately X[-0.36,0.24], Z[-0.61,-0.01].

Request `dx=0.07`, `dy=0`, `dz=0.25`. 1.12.2's expanded Box reaches X≈0.31,Z≈0.24 and intersects its wall Box.

X resolves fully because the current player Box is still north of the wall's z=0 face; after X, the player overlaps the wall in X, so 1.12.2 clips Z at approximately 0.01.


## Current evidence and remaining work

Source decisions, implementation coverage and runtime parity are separate. Source paths are relative to `decompiled_minecraft/`; immutable Git evidence can be read with `git show <revision>:<path>`.

```json
{
  "implementation": {
    "status": "open",
    "changes": [],
    "hooks": [],
    "limits": [
      "Complete current Change/catalog inventory inspected; no dedicated operation implements F-WALL-SINGLE-ARM-GAPS — 1.13.2 wall collision unions leave corner gaps; later broadly related hooks do not establish this claimed delta."
    ]
  },
  "applicability": [],
  "sources": [
    {
      "version": "1.12.2",
      "mapping": "ornithe-feather; feather-gen2-1.12.2+build.2-mergedv2.jar, feather-gen2-1.12.2+build.2.tiny",
      "path": "1.12.2/ornithe-feather/net/minecraft/block/WallBlock.java",
      "member": "public Box getCollisionShape(BlockState state, WorldView world, BlockPos pos) {",
      "lines": "100-103",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 7e4378c46e367c32deca14475e5161b6e4223947d83153ce3e7ba5c8a0d18e50; matches an archived source-file digest. Checked full declaration/body; snippet SHA-256 266ff2876e15fb737e8205e2f002c6ddfb8939ce3b581d258b9d186dcfafe88b."
    },
    {
      "version": "1.12.2",
      "mapping": "ornithe-feather; feather-gen2-1.12.2+build.2-mergedv2.jar, feather-gen2-1.12.2+build.2.tiny",
      "path": "1.12.2/ornithe-feather/net/minecraft/block/WallBlock.java",
      "member": "public Box getShape(BlockState state, WorldView world, BlockPos pos) {",
      "lines": "84-87",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 7e4378c46e367c32deca14475e5161b6e4223947d83153ce3e7ba5c8a0d18e50; matches an archived source-file digest. Checked full declaration/body; snippet SHA-256 56611e62db27a31cfe65fae756a0640f9f87a30765d729b6d05b92727c1f8bd3."
    },
    {
      "version": "1.12.2",
      "mapping": "ornithe-feather; feather-gen2-1.12.2+build.2-mergedv2.jar, feather-gen2-1.12.2+build.2.tiny",
      "path": "1.12.2/ornithe-feather/net/minecraft/block/WallBlock.java",
      "member": "private boolean shouldConnectTo(WorldView world, BlockPos pos, Direction dir) {",
      "lines": "146-152",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 7e4378c46e367c32deca14475e5161b6e4223947d83153ce3e7ba5c8a0d18e50; matches an archived source-file digest. Checked full declaration/body; snippet SHA-256 5cca374718da430edc73bd1998caceba1de051d2e30b3ce4f7c6d21f9b3adf66."
    },
    {
      "version": "1.13.2",
      "mapping": "ornithe-feather; feather-gen2-1.13.2+build.2-mergedv2.jar, feather-gen2-1.13.2+build.2.tiny",
      "path": "1.13.2/ornithe-feather/net/minecraft/block/WallBlock.java",
      "member": "public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos) {",
      "lines": "37-39",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 aeb67adf292a3b750e72a7d2fdc13f7b42f8633c6183964d00dfe8d6960e07bf; matches an archived source-file digest. Checked full declaration/body; snippet SHA-256 0810e25f0c2d172415bfeb4872a233a62d573856b5b528dcf7809f17f91690f7."
    },
    {
      "version": "1.13.2",
      "mapping": "ornithe-feather; feather-gen2-1.13.2+build.2-mergedv2.jar, feather-gen2-1.13.2+build.2.tiny",
      "path": "1.13.2/ornithe-feather/net/minecraft/block/WallBlock.java",
      "member": "public VoxelShape getShape(BlockState state, BlockView world, BlockPos pos) {",
      "lines": "32-34",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 aeb67adf292a3b750e72a7d2fdc13f7b42f8633c6183964d00dfe8d6960e07bf; matches an archived source-file digest. Checked full declaration/body; snippet SHA-256 8daa9dfc187eef1ce473b7e956d1769c1dd30edd2e8f625460df1bd88847a166."
    },
    {
      "version": "1.13.2",
      "mapping": "ornithe-feather; feather-gen2-1.13.2+build.2-mergedv2.jar, feather-gen2-1.13.2+build.2.tiny",
      "path": "1.13.2/ornithe-feather/net/minecraft/block/WallBlock.java",
      "member": "private boolean shouldConnectTo(BlockState state, FaceShape shape) {",
      "lines": "51-55",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 aeb67adf292a3b750e72a7d2fdc13f7b42f8633c6183964d00dfe8d6960e07bf; matches an archived source-file digest. Checked full declaration/body; snippet SHA-256 3952b4d23cea7bbcdb71038771fac876e60802963d99e7069d0a6f83b3f9bb36."
    }
  ],
  "sourceReviews": [],
  "openIssues": [
    {
      "missing": "Later applicability gate: exact introduction of campaign-F-WALL-SINGLE-ARM-GAPS is unknown within (1.12.2, 1.13.2]",
      "next_action": "Preserve any exact historical verdict for its bounded assertion; verify intervening releases separately before assigning a new implementation range. Formatting alone requires no renewed acceptance."
    },
    {
      "missing": "Current implementation of campaign-F-WALL-SINGLE-ARM-GAPS",
      "next_action": "After expert scope review and independent source acceptance, assign this specific operation to the separate implementation phase."
    },
    {
      "missing": "Exact independent source verdict/snapshot identity for F-WALL-SINGLE-ARM-GAPS",
      "next_action": "Retain original source-confirmed status only; source-acceptance owner must resolve a preserved independent verdict or review this exact bounded assertion in the later campaign."
    }
  ],
  "runtime": "unverified"
}
```
