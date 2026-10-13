# WORLD-14: extended piston-base collision shape

1.8.9, ornithe-feather; feather-gen2-1.8.9+build.2-mergedv2.jar, feather-gen2-1.8.9+build.2.tiny: `1.8.9/ornithe-feather/net/minecraft/block/PistonBaseBlock.java`, `public Box getCollisionShape(World world, BlockPos pos, BlockState state) {`, lines 217-220.
1.8.9, ornithe-feather; feather-gen2-1.8.9+build.2-mergedv2.jar, feather-gen2-1.8.9+build.2.tiny: `1.8.9/ornithe-feather/net/minecraft/block/PistonBaseBlock.java`, `public void addCollisions(World world, BlockPos pos, BlockState state, Box shape, List<Box> collisions, Entity entity) {`, lines 211-214.
1.9.4, ornithe-feather; feather-gen2-1.9.4+build.2-mergedv2.jar, feather-gen2-1.9.4+build.2.tiny: `1.9.4/ornithe-feather/net/minecraft/block/PistonBaseBlock.java`, `public void addCollisions(BlockState state, World world, BlockPos pos, Box shape, List<Box> collisions, @Nullable Entity entity) {`, lines 76-78.

Difference: - 1.8.9 source: `1.8.9/ornithe-feather/net/minecraft/block/PistonBaseBlock.java` `updateShape()` reads the live block state, and for an extended piston sets the bound 0.25 short of the facing side; otherwise it sets a full cube (174-203). `getCollisionShape()` calls that resolver (216-219).

Its `addCollisions()` then dispatches through the collision method (211-214).

- 1.9.4 source: paired `PistonBaseBlock.java` no `getShape()`/`getCollisionShape()` override exists. 1.9.4 `addCollisions()` passes `state.getShape(world,pos)` to collision assembly (76-78), which reaches the inherited full-cube provider. 1.9.4 `Block.java` full-cube provider and registration are under

Movement meaning: - Disposition: source-level collision-provider delta established for every extended facing.

World list composition, adjacent piston-head geometry and final player clipping remain separate open dependencies.


## Current evidence and remaining work

Source decisions, implementation coverage and runtime parity are separate. Source paths are relative to `decompiled_minecraft/`; immutable Git evidence can be read with `git show <revision>:<path>`.

```json
{
  "implementation": {
    "status": "open",
    "changes": [],
    "hooks": [],
    "limits": [
      "Complete current Change/catalog inventory inspected; no dedicated operation implements WORLD-14: extended piston-base collision shape; later broadly related hooks do not establish this claimed delta."
    ]
  },
  "applicability": [],
  "sources": [
    {
      "version": "1.8.9",
      "mapping": "ornithe-feather; feather-gen2-1.8.9+build.2-mergedv2.jar, feather-gen2-1.8.9+build.2.tiny",
      "path": "1.8.9/ornithe-feather/net/minecraft/block/PistonBaseBlock.java",
      "member": "public Box getCollisionShape(World world, BlockPos pos, BlockState state) {",
      "lines": "217-220",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 3c96698a674714446f9fd0d6cc1c4d5eb72937d9a2f396516ddb2461c3ea4929; matches an archived source-file digest. Checked full declaration/body; snippet SHA-256 35bc77eb151c9a91f28074f4715cb07aa79bd934594f4ba28805c9787e3d291c."
    },
    {
      "version": "1.8.9",
      "mapping": "ornithe-feather; feather-gen2-1.8.9+build.2-mergedv2.jar, feather-gen2-1.8.9+build.2.tiny",
      "path": "1.8.9/ornithe-feather/net/minecraft/block/PistonBaseBlock.java",
      "member": "public void addCollisions(World world, BlockPos pos, BlockState state, Box shape, List<Box> collisions, Entity entity) {",
      "lines": "211-214",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 3c96698a674714446f9fd0d6cc1c4d5eb72937d9a2f396516ddb2461c3ea4929; matches an archived source-file digest. Checked full declaration/body; snippet SHA-256 46cfd742f3fd1b441fe80cc66076de9dcebadb50af39977724314ff7d326022d."
    },
    {
      "version": "1.9.4",
      "mapping": "ornithe-feather; feather-gen2-1.9.4+build.2-mergedv2.jar, feather-gen2-1.9.4+build.2.tiny",
      "path": "1.9.4/ornithe-feather/net/minecraft/block/PistonBaseBlock.java",
      "member": "PistonBaseBlock#getCollisionShape",
      "lines": null,
      "status": "different",
      "detail": "Checked complete canonical file SHA-256 4ef15129e660397ba3a531c8ff4a4810393973ea56417d8830f4445d14035e36 (matches an archived source-file digest); named declaration absent. Named declaration is absent in this checked canonical file; paired existing declaration/range is retained separately, so this is an explicit addition/removal or mapping correspondence limit, not an invented line."
    },
    {
      "version": "1.9.4",
      "mapping": "ornithe-feather; feather-gen2-1.9.4+build.2-mergedv2.jar, feather-gen2-1.9.4+build.2.tiny",
      "path": "1.9.4/ornithe-feather/net/minecraft/block/PistonBaseBlock.java",
      "member": "public void addCollisions(BlockState state, World world, BlockPos pos, Box shape, List<Box> collisions, @Nullable Entity entity) {",
      "lines": "76-78",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 4ef15129e660397ba3a531c8ff4a4810393973ea56417d8830f4445d14035e36; matches an archived source-file digest. Checked full declaration/body; snippet SHA-256 803617a352ce7976815422496d79e604399d8aeac910693ac431214fcab6c101."
    }
  ],
  "sourceReviews": [],
  "openIssues": [
    {
      "missing": "Later applicability gate: exact introduction of campaign-WORLD-14-extended-piston-base-shape is unknown within (1.8.9, 1.9.4]",
      "next_action": "Preserve any exact historical verdict for its bounded assertion; verify intervening releases separately before assigning a new implementation range. Formatting alone requires no renewed acceptance."
    },
    {
      "missing": "Current implementation of campaign-WORLD-14-extended-piston-base-shape",
      "next_action": "After expert scope review and independent source acceptance, assign this specific operation to the separate implementation phase."
    },
    {
      "missing": "Exact independent source verdict/snapshot identity for WORLD-14-extended-piston-base-shape",
      "next_action": "Retain original source-confirmed status only; source-acceptance owner must resolve a preserved independent verdict or review this exact bounded assertion in the later campaign."
    }
  ],
  "runtime": "unverified"
}
```
