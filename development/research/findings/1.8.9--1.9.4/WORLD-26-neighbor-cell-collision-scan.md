# WORLD-26: neighbor-cell collision scan and protruding piston-head arms

1.8.9, ornithe-feather; feather-gen2-1.8.9+build.2-mergedv2.jar, feather-gen2-1.8.9+build.2.tiny: `1.8.9/ornithe-feather/net/minecraft/world/World.java`, `public List<Box> getCollisions(Entity entity, Box shape) {`, lines 891-945.
1.8.9, ornithe-feather; feather-gen2-1.8.9+build.2-mergedv2.jar, feather-gen2-1.8.9+build.2.tiny: `1.8.9/ornithe-feather/net/minecraft/block/PistonHeadBlock.java`, `public void addCollisions(World world, BlockPos pos, BlockState state, Box shape, List<Box> collisions, Entity entity) {`, lines 87-93.
1.9.4, ornithe-feather; feather-gen2-1.9.4+build.2-mergedv2.jar, feather-gen2-1.9.4+build.2.tiny: `1.9.4/ornithe-feather/net/minecraft/world/World.java`, `public List<Box> getCollisions(@Nullable Entity entity, Box shape) {`, lines 899-961.
1.9.4, ornithe-feather; feather-gen2-1.9.4+build.2-mergedv2.jar, feather-gen2-1.9.4+build.2.tiny: `1.9.4/ornithe-feather/net/minecraft/world/World.java`, `public boolean getCollisions(Box shape) {`, lines 1018-1055.
1.9.4, ornithe-feather; feather-gen2-1.9.4+build.2-mergedv2.jar, feather-gen2-1.9.4+build.2.tiny: `1.9.4/ornithe-feather/net/minecraft/block/PistonHeadBlock.java`, `public void addCollisions(BlockState state, World world, BlockPos pos, Box shape, List<Box> collisions, @Nullable Entity entity) {`, lines 65-68.

Difference: - Source hashes: 1.8.9 `World.java` , `Block.java` , `PistonHeadBlock.java` ; 1.9.4 `World.java` , `Block.java` , `PistonHeadBlock.java` .

Movement meaning: - Disposition: this is a conditional source-level clipped-displacement difference for the specified local move and collision context: 1.8.9 retains `dx=0.2`, 1.9.4 clips to `dx=0.05`.

Full candidate enumeration, other block/entity collisions, world-border and loaded-chunk interactions, and step-candidate outcomes remain dependencies of the broad COLL-01 slice.


## Current evidence and remaining work

Source decisions, implementation coverage and runtime parity are separate. Source paths are relative to `decompiled_minecraft/`; immutable Git evidence can be read with `git show <revision>:<path>`.

```json
{
  "implementation": {
    "status": "open",
    "changes": [],
    "hooks": [],
    "limits": [
      "Complete current Change/catalog inventory inspected; no dedicated operation implements WORLD-26: neighbor-cell collision scan and protruding piston-head arms; later broadly related hooks do not establish this claimed delta."
    ]
  },
  "applicability": [],
  "sources": [
    {
      "version": "1.8.9",
      "mapping": "ornithe-feather; feather-gen2-1.8.9+build.2-mergedv2.jar, feather-gen2-1.8.9+build.2.tiny",
      "path": "1.8.9/ornithe-feather/net/minecraft/world/World.java",
      "member": "public List<Box> getCollisions(Entity entity, Box shape) {",
      "lines": "891-945",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 3c04f5b874fbb6692039164c882a3c47fe28c7eca864e91c9ac57798153888ee; matches an archived source-file digest. Checked full declaration/body; snippet SHA-256 8ae43bedf64e6e4210a136fb79f24d22faf40361b274ac5ddf673118ba11e933."
    },
    {
      "version": "1.8.9",
      "mapping": "ornithe-feather; feather-gen2-1.8.9+build.2-mergedv2.jar, feather-gen2-1.8.9+build.2.tiny",
      "path": "1.8.9/ornithe-feather/net/minecraft/block/PistonHeadBlock.java",
      "member": "public void addCollisions(World world, BlockPos pos, BlockState state, Box shape, List<Box> collisions, Entity entity) {",
      "lines": "87-93",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 c5515744ce27521195ec802e2bd89ae5327448302542436576bcacf19f245fa4; matches an archived source-file digest. Checked full declaration/body; snippet SHA-256 cdd31554fe2923a1caf2bd0fa8870447e42fe40cce30fc86006f2e3be57f4f66."
    },
    {
      "version": "1.9.4",
      "mapping": "ornithe-feather; feather-gen2-1.9.4+build.2-mergedv2.jar, feather-gen2-1.9.4+build.2.tiny",
      "path": "1.9.4/ornithe-feather/net/minecraft/world/World.java",
      "member": "public List<Box> getCollisions(@Nullable Entity entity, Box shape) {",
      "lines": "899-961",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 2fe063e0ec224eed9fc7d01b8f788c32035eed5fee5a9d98ea5e82296236e05a; matches an archived source-file digest. Checked full declaration/body; snippet SHA-256 ae6f3ad90dc33b0833a6ebf1dc4546177d3e62e7385df02eb81c84bf8f8ae19c."
    },
    {
      "version": "1.9.4",
      "mapping": "ornithe-feather; feather-gen2-1.9.4+build.2-mergedv2.jar, feather-gen2-1.9.4+build.2.tiny",
      "path": "1.9.4/ornithe-feather/net/minecraft/world/World.java",
      "member": "public boolean getCollisions(Box shape) {",
      "lines": "1018-1055",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 2fe063e0ec224eed9fc7d01b8f788c32035eed5fee5a9d98ea5e82296236e05a; matches an archived source-file digest. Checked full declaration/body; snippet SHA-256 5f77e96e406206299a40031c3746876541420608d9c79cb03d1cb7196b565565."
    },
    {
      "version": "1.9.4",
      "mapping": "ornithe-feather; feather-gen2-1.9.4+build.2-mergedv2.jar, feather-gen2-1.9.4+build.2.tiny",
      "path": "1.9.4/ornithe-feather/net/minecraft/block/PistonHeadBlock.java",
      "member": "public void addCollisions(BlockState state, World world, BlockPos pos, Box shape, List<Box> collisions, @Nullable Entity entity) {",
      "lines": "65-68",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 9e8bd56219d3a00e57082481d6e17a2c9383e994dac6cecac2534918f4aeb579; matches an archived source-file digest. Checked full declaration/body; snippet SHA-256 26fc89bf2242b9969191d8c4f0538fc0648e368ad43af8b401647eea3a6d28d2."
    }
  ],
  "sourceReviews": [],
  "openIssues": [
    {
      "missing": "Later applicability gate: exact introduction of campaign-WORLD-26-neighbor-cell-collision-scan is unknown within (1.8.9, 1.9.4]",
      "next_action": "Preserve any exact historical verdict for its bounded assertion; verify intervening releases separately before assigning a new implementation range. Formatting alone requires no renewed acceptance."
    },
    {
      "missing": "Current implementation of campaign-WORLD-26-neighbor-cell-collision-scan",
      "next_action": "After expert scope review and independent source acceptance, assign this specific operation to the separate implementation phase."
    },
    {
      "missing": "Exact independent source verdict/snapshot identity for WORLD-26-neighbor-cell-collision-scan",
      "next_action": "Retain original source-confirmed status only; source-acceptance owner must resolve a preserved independent verdict or review this exact bounded assertion in the later campaign."
    }
  ],
  "runtime": "unverified"
}
```
