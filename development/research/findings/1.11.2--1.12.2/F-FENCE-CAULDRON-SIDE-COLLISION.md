# F-FENCE-CAULDRON-SIDE-COLLISION: fence side collision differs beside a cauldron

1.11.2, ornithe-feather; feather-gen2-1.11.2+build.2-mergedv2.jar, feather-gen2-1.11.2+build.2.tiny: `1.11.2/ornithe-feather/net/minecraft/block/FenceBlock.java`, `public boolean shouldConnectTo(WorldView world, BlockPos pos) {`, lines 124-134.
1.11.2, ornithe-feather; feather-gen2-1.11.2+build.2-mergedv2.jar, feather-gen2-1.11.2+build.2.tiny: `1.11.2/ornithe-feather/net/minecraft/block/FenceBlock.java`, `public void addCollisions(BlockState state, World world, BlockPos pos, Box shape, List<Box> collisions, @Nullable Entity entity, boolean forceShape) {`, lines 59-80.
1.12.2, ornithe-feather; feather-gen2-1.12.2+build.2-mergedv2.jar, feather-gen2-1.12.2+build.2.tiny: `1.12.2/ornithe-feather/net/minecraft/block/FenceBlock.java`, `public boolean shouldConnectTo(WorldView world, BlockPos pos, Direction dir) {`, lines 124-130.
1.12.2, ornithe-feather; feather-gen2-1.12.2+build.2-mergedv2.jar, feather-gen2-1.12.2+build.2.tiny: `1.12.2/ornithe-feather/net/minecraft/block/FenceBlock.java`, `protected static boolean shouldConnectTo(Block block) {`, lines 132-138.
1.12.2, ornithe-feather; feather-gen2-1.12.2+build.2-mergedv2.jar, feather-gen2-1.12.2+build.2.tiny: `1.12.2/ornithe-feather/net/minecraft/block/FenceBlock.java`, `public void addCollisions(BlockState state, World world, BlockPos pos, Box shape, List<Box> collisions, @Nullable Entity entity, boolean forceShape) {`, lines 59-80.
1.12.2, ornithe-feather; feather-gen2-1.12.2+build.2-mergedv2.jar, feather-gen2-1.12.2+build.2.tiny: `1.12.2/ornithe-feather/net/minecraft/block/CauldronBlock.java`, `public FaceShape getFaceShape(WorldView world, BlockState state, BlockPos pos, Direction face) {`, lines 253-259.

Difference: The paired `FenceBlock.addCollisions` methods match.

Both resolve virtual properties when `forceShape` is false, add the center post, then add each rail selected by the resolved directional properties.

The paired player collision collector in `World.getCollisions(Box)` reaches block-state `addCollisions` with `forceShape=false` through the unchanged state/block dispatch (S2.2 records the method ranges and whole-file hashes).

In 1.11.2, `FenceBlock.shouldConnectTo` accepts a non-fence neighbor only if its material is solid-blocking and its state is a cube, except pumpkins; cauldron is `Material.IRON` but `isCube` returns false, so the east property is false.

In 1.12.2, the fence asks the east neighbor for its west `FaceShape`; `CauldronBlock.getFaceShape` returns `SOLID` for horizontal faces.

Cauldron is not in `Block.isExceptionForAttachment` or the additional explicit fence exceptions, so the 1.12.2 east property is true.

With a fence at `(0,0,0)` and a cauldron at `(1,0,0)`, the fence's 1.12.2 east collision rail is x `[0.625,1]`, z `[0.375,0.625]`, y `[0,1.5]`; the 1.11.2 center post in both versions is x/z `[0.375,0.625]`, y `[0,1.5]`.

The cauldron's five collision boxes are unchanged: a base to y `0.3125` and four walls to y `1.0`.

Its outline `getShape` is full cube in both versions, but movement collision dispatch uses its five `addCollisions` boxes.

Movement meaning: Both `PlayerEntity.tick()` bodies set `noClip` to `isSpectator()`.

Thus a non-spectator creative-flight player remains subject to `Entity.move` collision clipping. `PlayerEntity.moveRelative` takes the flight branch when `abilities.flying && !isRiding()` and still reaches movement through the inherited entity movement path; flight does not itself set `noClip`.

Concrete source-level geometry: a 0.6-wide player with feet at y `1.1`, centered at x `1.5`, z `0.5`, fits within the cauldron's interior width/depth of `0.75`.

The AABB clears every cauldron collision box vertically because its minimum y `1.1` exceeds their maximum y `1.0`.

At z `0.5`, its width overlaps the fence post and east rail.


## Current evidence and remaining work

Source decisions, implementation coverage and runtime parity are separate. Source paths are relative to `decompiled_minecraft/`; immutable Git evidence can be read with `git show <revision>:<path>`.

```json
{
  "implementation": {
    "status": "open",
    "changes": [],
    "hooks": [],
    "limits": [
      "Complete current Change/catalog inventory inspected; no dedicated operation implements F-FENCE-CAULDRON-SIDE-COLLISION: fence side collision differs beside a cauldron; later broadly related hooks do not establish this claimed delta."
    ]
  },
  "applicability": [],
  "sources": [
    {
      "version": "1.11.2",
      "mapping": "ornithe-feather; feather-gen2-1.11.2+build.2-mergedv2.jar, feather-gen2-1.11.2+build.2.tiny",
      "path": "1.11.2/ornithe-feather/net/minecraft/block/FenceBlock.java",
      "member": "public boolean shouldConnectTo(WorldView world, BlockPos pos) {",
      "lines": "124-134",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 856cb92d4613289747a34a6bc85f306ca9d44b7057a45c79f2020d13025d1cf7; matches an archived source-file digest. Checked full declaration/body; snippet SHA-256 91d4f3c3f40f27116da22184f2a36fc5a9a603408598edbca9764f5a86597ff0."
    },
    {
      "version": "1.11.2",
      "mapping": "ornithe-feather; feather-gen2-1.11.2+build.2-mergedv2.jar, feather-gen2-1.11.2+build.2.tiny",
      "path": "1.11.2/ornithe-feather/net/minecraft/block/FenceBlock.java",
      "member": "public void addCollisions(BlockState state, World world, BlockPos pos, Box shape, List<Box> collisions, @Nullable Entity entity, boolean forceShape) {",
      "lines": "59-80",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 856cb92d4613289747a34a6bc85f306ca9d44b7057a45c79f2020d13025d1cf7; matches an archived source-file digest. Checked full declaration/body; snippet SHA-256 b43668b59ddc239f60e4cd1eb4d2e426ee7810875f3702cdca33e2bd45fd5381."
    },
    {
      "version": "1.11.2",
      "mapping": "ornithe-feather; feather-gen2-1.11.2+build.2-mergedv2.jar, feather-gen2-1.11.2+build.2.tiny",
      "path": "1.11.2/ornithe-feather/net/minecraft/block/CauldronBlock.java",
      "member": "CauldronBlock#getFaceShape",
      "lines": null,
      "status": "different",
      "detail": "Checked complete canonical file SHA-256 8b95c69febce0002140c2f960cfa191737dd51dabf5ab99f7e74416dd382c0e7 (matches an archived source-file digest); named declaration absent. Named declaration is absent in this checked canonical file; paired existing declaration/range is retained separately, so this is an explicit addition/removal or mapping correspondence limit, not an invented line."
    },
    {
      "version": "1.12.2",
      "mapping": "ornithe-feather; feather-gen2-1.12.2+build.2-mergedv2.jar, feather-gen2-1.12.2+build.2.tiny",
      "path": "1.12.2/ornithe-feather/net/minecraft/block/FenceBlock.java",
      "member": "public boolean shouldConnectTo(WorldView world, BlockPos pos, Direction dir) {",
      "lines": "124-130",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 633ad3b20250bd010a28d0b1362b24c81a5508be8a81a9d826953a3c9c4a703e; matches an archived source-file digest. Checked full declaration/body; snippet SHA-256 edeb2bddd8ccc03b76df9c41adf8a21c22c7f97b778ae889d73830f9ac767011."
    },
    {
      "version": "1.12.2",
      "mapping": "ornithe-feather; feather-gen2-1.12.2+build.2-mergedv2.jar, feather-gen2-1.12.2+build.2.tiny",
      "path": "1.12.2/ornithe-feather/net/minecraft/block/FenceBlock.java",
      "member": "protected static boolean shouldConnectTo(Block block) {",
      "lines": "132-138",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 633ad3b20250bd010a28d0b1362b24c81a5508be8a81a9d826953a3c9c4a703e; matches an archived source-file digest. Checked full declaration/body; snippet SHA-256 42410dcfb2349b5b123e4d18391836323c29ebc09e22def9f1b40a765b508f39."
    },
    {
      "version": "1.12.2",
      "mapping": "ornithe-feather; feather-gen2-1.12.2+build.2-mergedv2.jar, feather-gen2-1.12.2+build.2.tiny",
      "path": "1.12.2/ornithe-feather/net/minecraft/block/FenceBlock.java",
      "member": "public void addCollisions(BlockState state, World world, BlockPos pos, Box shape, List<Box> collisions, @Nullable Entity entity, boolean forceShape) {",
      "lines": "59-80",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 633ad3b20250bd010a28d0b1362b24c81a5508be8a81a9d826953a3c9c4a703e; matches an archived source-file digest. Checked full declaration/body; snippet SHA-256 b43668b59ddc239f60e4cd1eb4d2e426ee7810875f3702cdca33e2bd45fd5381."
    },
    {
      "version": "1.12.2",
      "mapping": "ornithe-feather; feather-gen2-1.12.2+build.2-mergedv2.jar, feather-gen2-1.12.2+build.2.tiny",
      "path": "1.12.2/ornithe-feather/net/minecraft/block/CauldronBlock.java",
      "member": "public FaceShape getFaceShape(WorldView world, BlockState state, BlockPos pos, Direction face) {",
      "lines": "253-259",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 0aff4a0de147e0c677f4b931cd9a881154666bb288f128c9d8041b70c38d63f9; matches an archived source-file digest. Checked full declaration/body; snippet SHA-256 e53a6bdd68d234e395a472070accb82e82a6e2c6322534398a7d2513f114b25c."
    }
  ],
  "sourceReviews": [],
  "openIssues": [
    {
      "missing": "Later applicability gate: exact introduction of campaign-F-FENCE-CAULDRON-SIDE-COLLISION is unknown within (1.11.2, 1.12.2]",
      "next_action": "Preserve any exact historical verdict for its bounded assertion; verify intervening releases separately before assigning a new implementation range. Formatting alone requires no renewed acceptance."
    },
    {
      "missing": "Current implementation of campaign-F-FENCE-CAULDRON-SIDE-COLLISION",
      "next_action": "After expert scope review and independent source acceptance, assign this specific operation to the separate implementation phase."
    },
    {
      "missing": "Exact independent source verdict/snapshot identity for F-FENCE-CAULDRON-SIDE-COLLISION",
      "next_action": "Retain original source-confirmed status only; source-acceptance owner must resolve a preserved independent verdict or review this exact bounded assertion in the later campaign."
    }
  ],
  "runtime": "unverified"
}
```
