# F-05: Retracting moving piston uses separate base and head collision shapes

1.10.2, ornithe-feather; feather-gen2-1.10.2+build.2-mergedv2.jar, feather-gen2-1.10.2+build.2.tiny: `1.10.2/ornithe-feather/net/minecraft/block/MovingBlock.java`, `public Box getCollisionShape(BlockState state, World world, BlockPos pos) {`, lines 134-137.
1.10.2, ornithe-feather; feather-gen2-1.10.2+build.2-mergedv2.jar, feather-gen2-1.10.2+build.2.tiny: `1.10.2/ornithe-feather/net/minecraft/block/entity/MovingBlockEntity.java`, `public Box getShape(WorldView world, BlockPos pos) {`, lines 80-82.
1.10.2, ornithe-feather; feather-gen2-1.10.2+build.2-mergedv2.jar, feather-gen2-1.10.2+build.2.tiny: `1.10.2/ornithe-feather/net/minecraft/block/entity/MovingBlockEntity.java`, `public Box getShape(WorldView world, BlockPos pos, float progress) {`, lines 84-89.
1.11.2, ornithe-feather; feather-gen2-1.11.2+build.2-mergedv2.jar, feather-gen2-1.11.2+build.2.tiny: `1.11.2/ornithe-feather/net/minecraft/block/MovingBlock.java`, `public Box getCollisionShape(BlockState state, WorldView world, BlockPos pos) {`, lines 129-132.
1.11.2, ornithe-feather; feather-gen2-1.11.2+build.2-mergedv2.jar, feather-gen2-1.11.2+build.2.tiny: `1.11.2/ornithe-feather/net/minecraft/block/MovingBlock.java`, `public void addCollisions(BlockState state, World world, BlockPos pos, Box shape, List<Box> collisions, @Nullable Entity entity, boolean forceShape) {`, lines 135-140.
1.11.2, ornithe-feather; feather-gen2-1.11.2+build.2-mergedv2.jar, feather-gen2-1.11.2+build.2.tiny: `1.11.2/ornithe-feather/net/minecraft/block/entity/MovingBlockEntity.java`, `public Box getShape(WorldView world, BlockPos pos) {`, lines 96-98.
1.11.2, ornithe-feather; feather-gen2-1.11.2+build.2-mergedv2.jar, feather-gen2-1.11.2+build.2.tiny: `1.11.2/ornithe-feather/net/minecraft/block/entity/MovingBlockEntity.java`, `public Box getShape(WorldView world, BlockPos pos, float progress) {`, lines 100-105.

Difference: 1.10.2's `MovingBlock` does not override `addCollisions`.

Ordinary world collision dispatch therefore uses the base `Block#addCollisions`, which asks `MovingBlock#getCollisionShape`; that returns `MovingBlockEntity#getShape`, a union of the translated `movedState` shape at current and last progress.

For a retracting source, `PistonBaseBlock` constructs the moving entity with `extending=false, source=true` and `movedState` set to the piston base state.

1.11.2's ordinary world query passes `forceShape=false` to the new `MovingBlock#addCollisions` override.

The override delegates to `MovingBlockEntity#addCollisions`, which adds the extended base collision at the source and then constructs collision boxes from a piston-head state translated by progress.

The head arm uses new direction-specific short-arm boxes as the animation approaches completion.

Movement meaning: The retracting source is created by the paired piston-base path in both versions. 1.10.2 local player's ordinary `World.getCollisions` query scans the moving-block cell and receives the version-specific boxes when its query box intersects them.

The collision-box set supplied to player movement differs for a reachable retracting source during partial progress.

Which axis clipping or step candidate changes depends on player position, box, progress and nearby geometry.


## Current evidence and remaining work

Source decisions, implementation coverage and runtime parity are separate. Source paths are relative to `decompiled_minecraft/`; immutable Git evidence can be read with `git show <revision>:<path>`.

```json
{
  "implementation": {
    "status": "open",
    "changes": [],
    "hooks": [],
    "limits": [
      "Complete current Change/catalog inventory inspected; no dedicated operation implements F-05: Retracting moving piston uses separate base and head collision shapes; later broadly related hooks do not establish this claimed delta."
    ]
  },
  "applicability": [],
  "sources": [
    {
      "version": "1.10.2",
      "mapping": "ornithe-feather; feather-gen2-1.10.2+build.2-mergedv2.jar, feather-gen2-1.10.2+build.2.tiny",
      "path": "1.10.2/ornithe-feather/net/minecraft/block/MovingBlock.java",
      "member": "public Box getCollisionShape(BlockState state, World world, BlockPos pos) {",
      "lines": "134-137",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 4f84cdf7c0d88c51ca065299c8755341d01b126d62c282c31fc0fa46bde1daf6; matches an archived source-file digest. Checked full declaration/body; snippet SHA-256 863d94e142b2de2f2805167ad33a1f6190bbf48fdf366eb352801155bec3536c."
    },
    {
      "version": "1.10.2",
      "mapping": "ornithe-feather; feather-gen2-1.10.2+build.2-mergedv2.jar, feather-gen2-1.10.2+build.2.tiny",
      "path": "1.10.2/ornithe-feather/net/minecraft/block/MovingBlock.java",
      "member": "MovingBlock#addCollisions",
      "lines": null,
      "status": "different",
      "detail": "Checked complete canonical file SHA-256 4f84cdf7c0d88c51ca065299c8755341d01b126d62c282c31fc0fa46bde1daf6 (matches an archived source-file digest); named declaration absent. Named declaration is absent in this checked canonical file; paired existing declaration/range is retained separately, so this is an explicit addition/removal or mapping correspondence limit, not an invented line."
    },
    {
      "version": "1.10.2",
      "mapping": "ornithe-feather; feather-gen2-1.10.2+build.2-mergedv2.jar, feather-gen2-1.10.2+build.2.tiny",
      "path": "1.10.2/ornithe-feather/net/minecraft/block/entity/MovingBlockEntity.java",
      "member": "public Box getShape(WorldView world, BlockPos pos) {",
      "lines": "80-82",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 720e1305c494a3a316b21beef823158c435826f50383778a5bd00ad0e4ff6ac1; matches an archived source-file digest. Checked full declaration/body; snippet SHA-256 e80ef096c9af88f75b6b12646f488370d75278ba01e36596cfbb83c68fc2e826."
    },
    {
      "version": "1.10.2",
      "mapping": "ornithe-feather; feather-gen2-1.10.2+build.2-mergedv2.jar, feather-gen2-1.10.2+build.2.tiny",
      "path": "1.10.2/ornithe-feather/net/minecraft/block/entity/MovingBlockEntity.java",
      "member": "public Box getShape(WorldView world, BlockPos pos, float progress) {",
      "lines": "84-89",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 720e1305c494a3a316b21beef823158c435826f50383778a5bd00ad0e4ff6ac1; matches an archived source-file digest. Checked full declaration/body; snippet SHA-256 4ad9228157d3c6552a4b74ce386b5100e1fc65eabe55e8457326b3759b6cdf38."
    },
    {
      "version": "1.10.2",
      "mapping": "ornithe-feather; feather-gen2-1.10.2+build.2-mergedv2.jar, feather-gen2-1.10.2+build.2.tiny",
      "path": "1.10.2/ornithe-feather/net/minecraft/block/entity/MovingBlockEntity.java",
      "member": "MovingBlockEntity#addCollisions",
      "lines": null,
      "status": "different",
      "detail": "Checked complete canonical file SHA-256 720e1305c494a3a316b21beef823158c435826f50383778a5bd00ad0e4ff6ac1 (matches an archived source-file digest); named declaration absent. Named declaration is absent in this checked canonical file; paired existing declaration/range is retained separately, so this is an explicit addition/removal or mapping correspondence limit, not an invented line."
    },
    {
      "version": "1.11.2",
      "mapping": "ornithe-feather; feather-gen2-1.11.2+build.2-mergedv2.jar, feather-gen2-1.11.2+build.2.tiny",
      "path": "1.11.2/ornithe-feather/net/minecraft/block/MovingBlock.java",
      "member": "public Box getCollisionShape(BlockState state, WorldView world, BlockPos pos) {",
      "lines": "129-132",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 f2f99070cad0b64d8ecede16b44e0d0383e11c5b4e0c787edeaa2b8fc5c8af18; matches an archived source-file digest. Checked full declaration/body; snippet SHA-256 aff0e1977a087d32af5034773556bdd143c709a1f65c12169d2c4a0d4e8aa34d."
    },
    {
      "version": "1.11.2",
      "mapping": "ornithe-feather; feather-gen2-1.11.2+build.2-mergedv2.jar, feather-gen2-1.11.2+build.2.tiny",
      "path": "1.11.2/ornithe-feather/net/minecraft/block/MovingBlock.java",
      "member": "public void addCollisions(BlockState state, World world, BlockPos pos, Box shape, List<Box> collisions, @Nullable Entity entity, boolean forceShape) {",
      "lines": "135-140",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 f2f99070cad0b64d8ecede16b44e0d0383e11c5b4e0c787edeaa2b8fc5c8af18; matches an archived source-file digest. Checked full declaration/body; snippet SHA-256 f1c8bb8e808d5e11dc66e35e6ac12a0b027aabd189624ef669d17ac248a16eda."
    },
    {
      "version": "1.11.2",
      "mapping": "ornithe-feather; feather-gen2-1.11.2+build.2-mergedv2.jar, feather-gen2-1.11.2+build.2.tiny",
      "path": "1.11.2/ornithe-feather/net/minecraft/block/entity/MovingBlockEntity.java",
      "member": "public Box getShape(WorldView world, BlockPos pos) {",
      "lines": "96-98",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 a55ee14227fe6ac0932283cba0414e946ba8e2637f543b3a3fe13d4e45b7b16e; matches an archived source-file digest. Checked full declaration/body; snippet SHA-256 e80ef096c9af88f75b6b12646f488370d75278ba01e36596cfbb83c68fc2e826."
    },
    {
      "version": "1.11.2",
      "mapping": "ornithe-feather; feather-gen2-1.11.2+build.2-mergedv2.jar, feather-gen2-1.11.2+build.2.tiny",
      "path": "1.11.2/ornithe-feather/net/minecraft/block/entity/MovingBlockEntity.java",
      "member": "public Box getShape(WorldView world, BlockPos pos, float progress) {",
      "lines": "100-105",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 a55ee14227fe6ac0932283cba0414e946ba8e2637f543b3a3fe13d4e45b7b16e; matches an archived source-file digest. Checked full declaration/body; snippet SHA-256 e8c62c0b37c24f67951c52c130a22ac9fc8f1425928086ee97e13ab6053aa1f2."
    },
    {
      "version": "1.11.2",
      "mapping": "ornithe-feather; feather-gen2-1.11.2+build.2-mergedv2.jar, feather-gen2-1.11.2+build.2.tiny",
      "path": "1.11.2/ornithe-feather/net/minecraft/block/entity/MovingBlockEntity.java",
      "member": "public void addCollisions(World world, BlockPos pos, Box shape, List<Box> collisions, @Nullable Entity entity) {",
      "lines": "318-346",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 a55ee14227fe6ac0932283cba0414e946ba8e2637f543b3a3fe13d4e45b7b16e; matches an archived source-file digest. Checked full declaration/body; snippet SHA-256 09cef9c347578bc13d18e525ac7e88e263f184ef56c01f5c80157c88fab37fcf."
    }
  ],
  "sourceReviews": [],
  "openIssues": [
    {
      "missing": "Later applicability gate: exact introduction of campaign-F-05-retracting-piston-collision-shapes is unknown within (1.10.2, 1.11.2]",
      "next_action": "Preserve any exact historical verdict for its bounded assertion; verify intervening releases separately before assigning a new implementation range. Formatting alone requires no renewed acceptance."
    },
    {
      "missing": "Current implementation of campaign-F-05-retracting-piston-collision-shapes",
      "next_action": "After expert scope review and independent source acceptance, assign this specific operation to the separate implementation phase."
    },
    {
      "missing": "Exact independent source verdict/snapshot identity for F-05-retracting-piston-collision-shapes",
      "next_action": "Retain original source-confirmed status only; source-acceptance owner must resolve a preserved independent verdict or review this exact bounded assertion in the later campaign."
    }
  ],
  "runtime": "unverified"
}
```
