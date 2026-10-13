# WORLD-12: moving piston collision shape

1.8.9, ornithe-feather; feather-gen2-1.8.9+build.2-mergedv2.jar, feather-gen2-1.8.9+build.2.tiny: `1.8.9/ornithe-feather/net/minecraft/block/MovingBlock.java`, `public Box getCollisionShape(World world, BlockPos pos, BlockState state) {`, lines 118-130.
1.8.9, ornithe-feather; feather-gen2-1.8.9+build.2-mergedv2.jar, feather-gen2-1.8.9+build.2.tiny: `1.8.9/ornithe-feather/net/minecraft/block/MovingBlock.java`, `public Box getCollisionShape(World world, BlockPos pos, BlockState movedState, float progress, Direction facing) {`, lines 162-197.
1.9.4, ornithe-feather; feather-gen2-1.9.4+build.2-mergedv2.jar, feather-gen2-1.9.4+build.2.tiny: `1.9.4/ornithe-feather/net/minecraft/block/MovingBlock.java`, `public Box getCollisionShape(BlockState state, World world, BlockPos pos) {`, lines 134-137.
1.9.4, ornithe-feather; feather-gen2-1.9.4+build.2-mergedv2.jar, feather-gen2-1.9.4+build.2.tiny: `1.9.4/ornithe-feather/net/minecraft/block/entity/MovingBlockEntity.java`, `public Box getShape(WorldView world, BlockPos pos) {`, lines 79-81.
1.9.4, ornithe-feather; feather-gen2-1.9.4+build.2-mergedv2.jar, feather-gen2-1.9.4+build.2.tiny: `1.9.4/ornithe-feather/net/minecraft/block/entity/MovingBlockEntity.java`, `public Box getShape(WorldView world, BlockPos pos, float progress) {`, lines 83-88.

Difference: - 1.8.9 source: `1.8.9/ornithe-feather/net/minecraft/block/MovingBlock.java` `getCollisionShape(world,pos,state)` samples `getProgress(0.0F)`, reverses it for extension, and asks for one moved-state collision box (118-130); the helper gets the moved block's collision shape and adjusts only the bound on each facing axis (162-197).

- 1.9.4 source: paired `MovingBlock.java` Its `getCollisionShape()` delegates to `MovingBlockEntity.getShape(world,pos)` (134-137).

The paired entity `getShape()` unions shapes at current `progress` and `lastProgress` (79-80), while each shape uses the moved state's outline shape translated by `getAmountExtended(progress) * facing` (83-87). `getAmountExtended()` returns `progress - 1` for extension or `1 - progress` for retraction (75-77).

Movement meaning: - Disposition: source-level moving-collision provider delta established for an advancing full-cube state.

The distinct 1.8.9 single-bound adjustment and 1.9.4 current/previous-shape union, plus collision-shape versus outline-shape provider, prevent an equivalence claim.


## Current evidence and remaining work

Source decisions, implementation coverage and runtime parity are separate. Source paths are relative to `decompiled_minecraft/`; immutable Git evidence can be read with `git show <revision>:<path>`.

```json
{
  "implementation": {
    "status": "open",
    "changes": [],
    "hooks": [],
    "limits": [
      "Complete current Change/catalog inventory inspected; no dedicated operation implements WORLD-12: moving piston collision shape; later broadly related hooks do not establish this claimed delta."
    ]
  },
  "applicability": [],
  "sources": [
    {
      "version": "1.8.9",
      "mapping": "ornithe-feather; feather-gen2-1.8.9+build.2-mergedv2.jar, feather-gen2-1.8.9+build.2.tiny",
      "path": "1.8.9/ornithe-feather/net/minecraft/block/MovingBlock.java",
      "member": "public Box getCollisionShape(World world, BlockPos pos, BlockState state) {",
      "lines": "118-130",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 700010b071738738f04e0508050590d8d625f01115fc4886fef6e262f85c6e85; matches an archived source-file digest. Checked full declaration/body; snippet SHA-256 d62f52cf6c81e5e3fae89b2f6d1bfa6dfba2bd5cf2e394c9d230fd273b6dd08d."
    },
    {
      "version": "1.8.9",
      "mapping": "ornithe-feather; feather-gen2-1.8.9+build.2-mergedv2.jar, feather-gen2-1.8.9+build.2.tiny",
      "path": "1.8.9/ornithe-feather/net/minecraft/block/MovingBlock.java",
      "member": "public Box getCollisionShape(World world, BlockPos pos, BlockState movedState, float progress, Direction facing) {",
      "lines": "162-197",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 700010b071738738f04e0508050590d8d625f01115fc4886fef6e262f85c6e85; matches an archived source-file digest. Checked full declaration/body; snippet SHA-256 64f2dafbb71b251539c90c62c0f6ff8d18d5a407b97809bbd487e4280632f6d3."
    },
    {
      "version": "1.8.9",
      "mapping": "ornithe-feather; feather-gen2-1.8.9+build.2-mergedv2.jar, feather-gen2-1.8.9+build.2.tiny",
      "path": "1.8.9/ornithe-feather/net/minecraft/block/entity/MovingBlockEntity.java",
      "member": "MovingBlockEntity#getShape",
      "lines": null,
      "status": "different",
      "detail": "Checked complete canonical file SHA-256 05a5bfbf2556eaa621996bda365b7bab01f5b9060cf773e7b9949ebe2f39fff6 (not bound to a source-file digest in this record); named declaration absent. Named declaration is absent in this checked canonical file; paired existing declaration/range is retained separately, so this is an explicit addition/removal or mapping correspondence limit, not an invented line."
    },
    {
      "version": "1.9.4",
      "mapping": "ornithe-feather; feather-gen2-1.9.4+build.2-mergedv2.jar, feather-gen2-1.9.4+build.2.tiny",
      "path": "1.9.4/ornithe-feather/net/minecraft/block/MovingBlock.java",
      "member": "public Box getCollisionShape(BlockState state, World world, BlockPos pos) {",
      "lines": "134-137",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 4f84cdf7c0d88c51ca065299c8755341d01b126d62c282c31fc0fa46bde1daf6; matches an archived source-file digest. Checked full declaration/body; snippet SHA-256 863d94e142b2de2f2805167ad33a1f6190bbf48fdf366eb352801155bec3536c."
    },
    {
      "version": "1.9.4",
      "mapping": "ornithe-feather; feather-gen2-1.9.4+build.2-mergedv2.jar, feather-gen2-1.9.4+build.2.tiny",
      "path": "1.9.4/ornithe-feather/net/minecraft/block/entity/MovingBlockEntity.java",
      "member": "public Box getShape(WorldView world, BlockPos pos) {",
      "lines": "79-81",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 50eb847d3ff4ff78441057f397c43e15fb894177f7c5a736a61c22a80f1de467; matches an archived source-file digest. Checked full declaration/body; snippet SHA-256 e80ef096c9af88f75b6b12646f488370d75278ba01e36596cfbb83c68fc2e826."
    },
    {
      "version": "1.9.4",
      "mapping": "ornithe-feather; feather-gen2-1.9.4+build.2-mergedv2.jar, feather-gen2-1.9.4+build.2.tiny",
      "path": "1.9.4/ornithe-feather/net/minecraft/block/entity/MovingBlockEntity.java",
      "member": "public Box getShape(WorldView world, BlockPos pos, float progress) {",
      "lines": "83-88",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 50eb847d3ff4ff78441057f397c43e15fb894177f7c5a736a61c22a80f1de467; matches an archived source-file digest. Checked full declaration/body; snippet SHA-256 4ad9228157d3c6552a4b74ce386b5100e1fc65eabe55e8457326b3759b6cdf38."
    }
  ],
  "sourceReviews": [],
  "openIssues": [
    {
      "missing": "Later applicability gate: exact introduction of campaign-WORLD-12-moving-piston-collision-shape is unknown within (1.8.9, 1.9.4]",
      "next_action": "Preserve any exact historical verdict for its bounded assertion; verify intervening releases separately before assigning a new implementation range. Formatting alone requires no renewed acceptance."
    },
    {
      "missing": "Current implementation of campaign-WORLD-12-moving-piston-collision-shape",
      "next_action": "After expert scope review and independent source acceptance, assign this specific operation to the separate implementation phase."
    },
    {
      "missing": "Exact independent source verdict/snapshot identity for WORLD-12-moving-piston-collision-shape",
      "next_action": "Retain original source-confirmed status only; source-acceptance owner must resolve a preserved independent verdict or review this exact bounded assertion in the later campaign."
    }
  ],
  "runtime": "unverified"
}
```
