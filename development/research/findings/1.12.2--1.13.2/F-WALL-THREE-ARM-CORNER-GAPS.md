# F-WALL-THREE-ARM-CORNER-GAPS — three-arm wall shape leaves a reachable collision corner

1.12.2, ornithe-feather; feather-gen2-1.12.2+build.2-mergedv2.jar, feather-gen2-1.12.2+build.2.tiny: `1.12.2/ornithe-feather/net/minecraft/block/WallBlock.java`, `public Box getCollisionShape(BlockState state, WorldView world, BlockPos pos) {`, lines 100-103.
1.12.2, ornithe-feather; feather-gen2-1.12.2+build.2-mergedv2.jar, feather-gen2-1.12.2+build.2.tiny: `1.12.2/ornithe-feather/net/minecraft/block/WallBlock.java`, `public Box getShape(BlockState state, WorldView world, BlockPos pos) {`, lines 84-87.
1.12.2, ornithe-feather; feather-gen2-1.12.2+build.2-mergedv2.jar, feather-gen2-1.12.2+build.2.tiny: `1.12.2/ornithe-feather/net/minecraft/block/WallBlock.java`, `private boolean shouldConnectTo(WorldView world, BlockPos pos, Direction dir) {`, lines 146-152.
1.13.2, ornithe-feather; feather-gen2-1.13.2+build.2-mergedv2.jar, feather-gen2-1.13.2+build.2.tiny: `1.13.2/ornithe-feather/net/minecraft/block/WallBlock.java`, `public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos) {`, lines 37-39.
1.13.2, ornithe-feather; feather-gen2-1.13.2+build.2-mergedv2.jar, feather-gen2-1.13.2+build.2.tiny: `1.13.2/ornithe-feather/net/minecraft/block/WallBlock.java`, `public VoxelShape getShape(BlockState state, BlockView world, BlockPos pos) {`, lines 32-34.
1.13.2, ornithe-feather; feather-gen2-1.13.2+build.2-mergedv2.jar, feather-gen2-1.13.2+build.2.tiny: `1.13.2/ornithe-feather/net/minecraft/block/WallBlock.java`, `private boolean shouldConnectTo(BlockState state, FaceShape shape) {`, lines 51-55.

Difference: For NORTH+EAST+SOUTH connected and WEST open, both neighbor formulas produce those three connection bits and `UP=true` when the block above is air. 1.12.2's horizontal direction bits select shape index 13.

Its collision Box is X[4,16] × Z[0,16] × Y[0,24] in sixteenth-block coordinates (the collision table raises the outline's 16/16 height to 24/16).

1.13.2's `UP=true` shape is the OR of the center X[4,12] × Z[4,12], north arm X[5,11] × Z[0,11], south arm X[5,11] × Z[5,16], and east arm X[5,16] × Z[5,11], all with Y[0,24].

This leaves the northwest corner cell region X[4,5] × Z[0,4] empty, while 1.12.2's single Box fills it.

Coordinates above are inclusive shape boundaries; collision requires positive-volume overlap.

Movement meaning: Place open fence gates immediately NORTH, EAST, and SOUTH of the wall, with each facing chosen so its face toward the wall is `MIDDLE_POLE`; leave WEST and the block above air.

Both versions' wall helper accepts `MIDDLE_POLE` from a fence gate, and the open gate collision getters return empty shapes.

With three connections and air above, the 1.12.2 virtual-property formula and 1.13.2 placement/update formula both give `UP=true`.

Put a 0.6-wide player at y=0 with center x=z=-0.3, so its initial X and Z maxima are both 0.

Request movement `(dx,dy,dz)=(0.3,0,0.24)`, with sneaking and on-ground false.

The shared resolver processes Y, then X, then Z.

During X, the player only touches the wall's z=0 boundary, so 1.12.2 does not clip X.

After X, its box overlaps 1.12.2's wall in X; the positive Z request meets the wall at z=0 and clips to zero.


## Current evidence and remaining work

Source decisions, implementation coverage and runtime parity are separate. Source paths are relative to `decompiled_minecraft/`; immutable Git evidence can be read with `git show <revision>:<path>`.

```json
{
  "implementation": {
    "status": "open",
    "changes": [],
    "hooks": [],
    "limits": [
      "Complete current Change/catalog inventory inspected; no dedicated operation implements F-WALL-THREE-ARM-CORNER-GAPS — three-arm wall shape leaves a reachable collision corner; later broadly related hooks do not establish this claimed delta."
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
      "missing": "Later applicability gate: exact introduction of campaign-F-WALL-THREE-ARM-CORNER-GAPS is unknown within (1.12.2, 1.13.2]",
      "next_action": "Preserve any exact historical verdict for its bounded assertion; verify intervening releases separately before assigning a new implementation range. Formatting alone requires no renewed acceptance."
    },
    {
      "missing": "Current implementation of campaign-F-WALL-THREE-ARM-CORNER-GAPS",
      "next_action": "After expert scope review and independent source acceptance, assign this specific operation to the separate implementation phase."
    },
    {
      "missing": "Exact independent source verdict/snapshot identity for F-WALL-THREE-ARM-CORNER-GAPS",
      "next_action": "Retain original source-confirmed status only; source-acceptance owner must resolve a preserved independent verdict or review this exact bounded assertion in the later campaign."
    }
  ],
  "runtime": "unverified"
}
```
