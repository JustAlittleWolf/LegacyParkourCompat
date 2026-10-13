# F-FARMLAND-PUSH-DISTANCE: block conversion moves intersecting players by different distances

1.12.2, ornithe-feather; feather-gen2-1.12.2+build.2-mergedv2.jar, feather-gen2-1.12.2+build.2.tiny: `1.12.2/ornithe-feather/net/minecraft/block/FarmlandBlock.java`, `public Box getShape(BlockState state, WorldView world, BlockPos pos) {`, lines 31-33.
1.12.2, ornithe-feather; feather-gen2-1.12.2+build.2-mergedv2.jar, feather-gen2-1.12.2+build.2.tiny: `1.12.2/ornithe-feather/net/minecraft/block/FarmlandBlock.java`, `setDirt(world, pos);`, lines 52-52.
1.12.2, ornithe-feather; feather-gen2-1.12.2+build.2-mergedv2.jar, feather-gen2-1.12.2+build.2.tiny: `1.12.2/ornithe-feather/net/minecraft/block/FarmlandBlock.java`, `setDirt(world, pos);`, lines 66-66.
1.12.2, ornithe-feather; feather-gen2-1.12.2+build.2-mergedv2.jar, feather-gen2-1.12.2+build.2.tiny: `1.12.2/ornithe-feather/net/minecraft/block/FarmlandBlock.java`, `protected static void setDirt(World world, BlockPos pos) {`, lines 72-80.
1.13.2, ornithe-feather; feather-gen2-1.13.2+build.2-mergedv2.jar, feather-gen2-1.13.2+build.2.tiny: `1.13.2/ornithe-feather/net/minecraft/block/FarmlandBlock.java`, `public VoxelShape getShape(BlockState state, BlockView world, BlockPos pos) {`, lines 57-59.
1.13.2, ornithe-feather; feather-gen2-1.13.2+build.2-mergedv2.jar, feather-gen2-1.13.2+build.2.tiny: `1.13.2/ornithe-feather/net/minecraft/block/FarmlandBlock.java`, `setDirt(state, world, pos);`, lines 69-69.
1.13.2, ornithe-feather; feather-gen2-1.13.2+build.2-mergedv2.jar, feather-gen2-1.13.2+build.2.tiny: `1.13.2/ornithe-feather/net/minecraft/block/FarmlandBlock.java`, `setDirt(state, world, pos);`, lines 76-76.
1.13.2, ornithe-feather; feather-gen2-1.13.2+build.2-mergedv2.jar, feather-gen2-1.13.2+build.2.tiny: `1.13.2/ornithe-feather/net/minecraft/block/FarmlandBlock.java`, `setDirt(world.getBlockState(pos), world, pos);`, lines 91-91.

Difference: - Behavior boundary: server-side Farmland `onFallenOn` conversion from farmland to dirt and the resulting direct `Entity.teleport` vertical position write for entities intersecting the newly occupied upper block volume.

Damage calculation after the callback is excluded.

- Preconditions and concrete conditional witness: an authoritative server, farmland at block position `(0,64,0)`, player P1 lands on it with positive fall distance and `nextFloat() < fallDistance - 0.5F`, and P1 satisfies `width*width*height > 0.512F`; the PlayerEntity branch bypasses `mobGriefing`.

Player P2's Box intersects the top 1/16 layer, e.g.

X/Z `[0.2,0.8]`, minY `64.95`, and a positive height.

Conditional on 1.12.2 and 1.13.2 returning that same P2 from their corresponding `world.getEntities` query, the formulas differ:

  - 1.12.2: `min(0.0625, 65.0 - 64.95) + 0.001 = 0.051` blocks upward.

  - 1.13.2: for the new-volume voxel ending at Y=65, `calculateMaxDistance` returns `65.0 - (64.95 + 1.0) = -0.95`; the helper teleports by `1.0 - 0.95 = 0.05` blocks upward.

  - Result: under this paired-query and shape precondition, P2's direct vertical position write differs by `0.001` blocks.

The candidate query bounds on 1.12.2 and 1.13.2 are the same top layer; entity candidate enumeration remains conditional on the already recorded CHUNK-ENTITY-ENUMERATION slice.

Movement meaning: - State producer -> consumer: P1's landing/fallDistance -> Farmland `onFallenOn` guard and block-state replacement -> newly occupied collision layer -> world entity query -> direct P2 teleport -> later player position/collision movement.

- Scope: this is player movement response to a block-state transition, not the damage outcome or non-player physics.


## Current evidence and remaining work

Source decisions, implementation coverage and runtime parity are separate. Source paths are relative to `decompiled_minecraft/`; immutable Git evidence can be read with `git show <revision>:<path>`.

```json
{
  "implementation": {
    "status": "open",
    "changes": [],
    "hooks": [],
    "limits": [
      "Complete current Change/catalog inventory inspected; no dedicated operation implements F-FARMLAND-PUSH-DISTANCE: block conversion moves intersecting players by different distances; later broadly related hooks do not establish this claimed delta."
    ]
  },
  "applicability": [],
  "sources": [
    {
      "version": "1.12.2",
      "mapping": "ornithe-feather; feather-gen2-1.12.2+build.2-mergedv2.jar, feather-gen2-1.12.2+build.2.tiny",
      "path": "1.12.2/ornithe-feather/net/minecraft/block/FarmlandBlock.java",
      "member": "public Box getShape(BlockState state, WorldView world, BlockPos pos) {",
      "lines": "31-33",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 d84c75dae139181b5d58cfa34f3cb77c5b2d4bda63d6ad9c3b52a47350bd0b98; matches an archived source-file digest. Checked full declaration/body; snippet SHA-256 9203892aa07036ac9d7306823feaac3919c21f4f5d69ea95c189d07076650e7d."
    },
    {
      "version": "1.12.2",
      "mapping": "ornithe-feather; feather-gen2-1.12.2+build.2-mergedv2.jar, feather-gen2-1.12.2+build.2.tiny",
      "path": "1.12.2/ornithe-feather/net/minecraft/block/FarmlandBlock.java",
      "member": "FarmlandBlock#getCollisionShape",
      "lines": null,
      "status": "different",
      "detail": "Checked complete canonical file SHA-256 d84c75dae139181b5d58cfa34f3cb77c5b2d4bda63d6ad9c3b52a47350bd0b98 (matches an archived source-file digest); named declaration absent. Named declaration is absent in this checked canonical file; paired existing declaration/range is retained separately, so this is an explicit addition/removal or mapping correspondence limit, not an invented line."
    },
    {
      "version": "1.12.2",
      "mapping": "ornithe-feather; feather-gen2-1.12.2+build.2-mergedv2.jar, feather-gen2-1.12.2+build.2.tiny",
      "path": "1.12.2/ornithe-feather/net/minecraft/block/FarmlandBlock.java",
      "member": "setDirt(world, pos);",
      "lines": "52-52",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 d84c75dae139181b5d58cfa34f3cb77c5b2d4bda63d6ad9c3b52a47350bd0b98; matches an archived source-file digest. Checked full declaration/body; snippet SHA-256 5fb6a4b46ecba631c8bce51f57227d44608f8bf615ca3c9f600985be3d11fe50."
    },
    {
      "version": "1.12.2",
      "mapping": "ornithe-feather; feather-gen2-1.12.2+build.2-mergedv2.jar, feather-gen2-1.12.2+build.2.tiny",
      "path": "1.12.2/ornithe-feather/net/minecraft/block/FarmlandBlock.java",
      "member": "setDirt(world, pos);",
      "lines": "66-66",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 d84c75dae139181b5d58cfa34f3cb77c5b2d4bda63d6ad9c3b52a47350bd0b98; matches an archived source-file digest. Checked full declaration/body; snippet SHA-256 820e27fee4aa096a3873139a52cb13e54ca3504432598848317ba590af26a530."
    },
    {
      "version": "1.12.2",
      "mapping": "ornithe-feather; feather-gen2-1.12.2+build.2-mergedv2.jar, feather-gen2-1.12.2+build.2.tiny",
      "path": "1.12.2/ornithe-feather/net/minecraft/block/FarmlandBlock.java",
      "member": "protected static void setDirt(World world, BlockPos pos) {",
      "lines": "72-80",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 d84c75dae139181b5d58cfa34f3cb77c5b2d4bda63d6ad9c3b52a47350bd0b98; matches an archived source-file digest. Checked full declaration/body; snippet SHA-256 318704e1d39b9e2d01d028a21fe34c92d7aff1ce7233f681edd4d188537a5784."
    },
    {
      "version": "1.12.2",
      "mapping": "ornithe-feather; feather-gen2-1.12.2+build.2-mergedv2.jar, feather-gen2-1.12.2+build.2.tiny",
      "path": "1.12.2/ornithe-feather/net/minecraft/block/FarmlandBlock.java",
      "member": "setDirt(world, pos);",
      "lines": "101-101",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 d84c75dae139181b5d58cfa34f3cb77c5b2d4bda63d6ad9c3b52a47350bd0b98; matches an archived source-file digest. Checked full declaration/body; snippet SHA-256 820e27fee4aa096a3873139a52cb13e54ca3504432598848317ba590af26a530."
    },
    {
      "version": "1.12.2",
      "mapping": "ornithe-feather; feather-gen2-1.12.2+build.2-mergedv2.jar, feather-gen2-1.12.2+build.2.tiny",
      "path": "1.12.2/ornithe-feather/net/minecraft/block/FarmlandBlock.java",
      "member": "setDirt(world, pos);",
      "lines": "109-109",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 d84c75dae139181b5d58cfa34f3cb77c5b2d4bda63d6ad9c3b52a47350bd0b98; matches an archived source-file digest. Checked full declaration/body; snippet SHA-256 820e27fee4aa096a3873139a52cb13e54ca3504432598848317ba590af26a530."
    },
    {
      "version": "1.12.2",
      "mapping": "ornithe-feather; feather-gen2-1.12.2+build.2-mergedv2.jar, feather-gen2-1.12.2+build.2.tiny",
      "path": "1.12.2/ornithe-feather/net/minecraft/block/FarmlandBlock.java",
      "member": "public void onFallenOn(World world, BlockPos pos, Entity entity, float fallDistance) {",
      "lines": "60-70",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 d84c75dae139181b5d58cfa34f3cb77c5b2d4bda63d6ad9c3b52a47350bd0b98; matches an archived source-file digest. Checked full declaration/body; snippet SHA-256 8678f035148ca6184b0e2b8966e94cdd6dfb3a04e3f97a3a1172eeb125e21c9b."
    },
    {
      "version": "1.13.2",
      "mapping": "ornithe-feather; feather-gen2-1.13.2+build.2-mergedv2.jar, feather-gen2-1.13.2+build.2.tiny",
      "path": "1.13.2/ornithe-feather/net/minecraft/block/FarmlandBlock.java",
      "member": "public VoxelShape getShape(BlockState state, BlockView world, BlockPos pos) {",
      "lines": "57-59",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 581dae15cc29abe90f9cf20232a3cd1b7ab6c868808bc5e4f05816952b61eef9; matches an archived source-file digest. Checked full declaration/body; snippet SHA-256 e1b2a3dcc30b620fb6b57d6b116d91b75826ec9ccec312c2d06df0dc5655ca2b."
    },
    {
      "version": "1.13.2",
      "mapping": "ornithe-feather; feather-gen2-1.13.2+build.2-mergedv2.jar, feather-gen2-1.13.2+build.2.tiny",
      "path": "1.13.2/ornithe-feather/net/minecraft/block/FarmlandBlock.java",
      "member": "FarmlandBlock#getCollisionShape",
      "lines": null,
      "status": "different",
      "detail": "Checked complete canonical file SHA-256 581dae15cc29abe90f9cf20232a3cd1b7ab6c868808bc5e4f05816952b61eef9 (matches an archived source-file digest); named declaration absent. Named declaration is absent in this checked canonical file; paired existing declaration/range is retained separately, so this is an explicit addition/removal or mapping correspondence limit, not an invented line."
    },
    {
      "version": "1.13.2",
      "mapping": "ornithe-feather; feather-gen2-1.13.2+build.2-mergedv2.jar, feather-gen2-1.13.2+build.2.tiny",
      "path": "1.13.2/ornithe-feather/net/minecraft/block/FarmlandBlock.java",
      "member": "setDirt(state, world, pos);",
      "lines": "69-69",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 581dae15cc29abe90f9cf20232a3cd1b7ab6c868808bc5e4f05816952b61eef9; matches an archived source-file digest. Checked full declaration/body; snippet SHA-256 38a43d1f07a7ad7d03ce1b2d1a5f3d4665ef633a412a0f267621f180be463d94."
    },
    {
      "version": "1.13.2",
      "mapping": "ornithe-feather; feather-gen2-1.13.2+build.2-mergedv2.jar, feather-gen2-1.13.2+build.2.tiny",
      "path": "1.13.2/ornithe-feather/net/minecraft/block/FarmlandBlock.java",
      "member": "setDirt(state, world, pos);",
      "lines": "76-76",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 581dae15cc29abe90f9cf20232a3cd1b7ab6c868808bc5e4f05816952b61eef9; matches an archived source-file digest. Checked full declaration/body; snippet SHA-256 6cd509bbb6f04d6ca474af4d4bc80060c012b8a235598ca295d81211091e06a5."
    },
    {
      "version": "1.13.2",
      "mapping": "ornithe-feather; feather-gen2-1.13.2+build.2-mergedv2.jar, feather-gen2-1.13.2+build.2.tiny",
      "path": "1.13.2/ornithe-feather/net/minecraft/block/FarmlandBlock.java",
      "member": "setDirt(world.getBlockState(pos), world, pos);",
      "lines": "91-91",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 581dae15cc29abe90f9cf20232a3cd1b7ab6c868808bc5e4f05816952b61eef9; matches an archived source-file digest. Checked full declaration/body; snippet SHA-256 c1c62f05c40e048dd853de6d7ed8831b500d7fdae5c7dbdf3780ba0a1877f19b."
    },
    {
      "version": "1.13.2",
      "mapping": "ornithe-feather; feather-gen2-1.13.2+build.2-mergedv2.jar, feather-gen2-1.13.2+build.2.tiny",
      "path": "1.13.2/ornithe-feather/net/minecraft/block/FarmlandBlock.java",
      "member": "public static void setDirt(BlockState state, World world, BlockPos pos) {",
      "lines": "97-99",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 581dae15cc29abe90f9cf20232a3cd1b7ab6c868808bc5e4f05816952b61eef9; matches an archived source-file digest. Checked full declaration/body; snippet SHA-256 4f782961e479e6107acbdf7a6896786e2b0796fae96ee77c8f97aaeb7ae48d24."
    },
    {
      "version": "1.13.2",
      "mapping": "ornithe-feather; feather-gen2-1.13.2+build.2-mergedv2.jar, feather-gen2-1.13.2+build.2.tiny",
      "path": "1.13.2/ornithe-feather/net/minecraft/block/FarmlandBlock.java",
      "member": "public void onFallenOn(World world, BlockPos pos, Entity entity, float fallDistance) {",
      "lines": "85-95",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 581dae15cc29abe90f9cf20232a3cd1b7ab6c868808bc5e4f05816952b61eef9; matches an archived source-file digest. Checked full declaration/body; snippet SHA-256 993235cb5b34eefdf3ebb601d1ba73b7e478b82ddbcc7d36cdfc2174998b78fb."
    }
  ],
  "sourceReviews": [],
  "openIssues": [
    {
      "missing": "Later applicability gate: exact introduction of campaign-F-FARMLAND-PUSH-DISTANCE is unknown within (1.12.2, 1.13.2]",
      "next_action": "Preserve any exact historical verdict for its bounded assertion; verify intervening releases separately before assigning a new implementation range. Formatting alone requires no renewed acceptance."
    },
    {
      "missing": "Current implementation of campaign-F-FARMLAND-PUSH-DISTANCE",
      "next_action": "After expert scope review and independent source acceptance, assign this specific operation to the separate implementation phase."
    },
    {
      "missing": "Exact independent source verdict/snapshot identity for F-FARMLAND-PUSH-DISTANCE",
      "next_action": "Retain original source-confirmed status only; source-acceptance owner must resolve a preserved independent verdict or review this exact bounded assertion in the later campaign."
    }
  ],
  "runtime": "unverified"
}
```
