# WORLD-15: piston-head arm collision boxes

1.8.9, ornithe-feather; feather-gen2-1.8.9+build.2-mergedv2.jar, feather-gen2-1.8.9+build.2.tiny: `1.8.9/ornithe-feather/net/minecraft/block/PistonHeadBlock.java`, `public void addCollisions(World world, BlockPos pos, BlockState state, Box shape, List<Box> collisions, Entity entity) {`, lines 87-93.
1.8.9, ornithe-feather; feather-gen2-1.8.9+build.2-mergedv2.jar, feather-gen2-1.8.9+build.2.tiny: `1.8.9/ornithe-feather/net/minecraft/block/PistonHeadBlock.java`, `private void setArmShapeForCollisions(BlockState state) {`, lines 95-120.
1.9.4, ornithe-feather; feather-gen2-1.9.4+build.2-mergedv2.jar, feather-gen2-1.9.4+build.2.tiny: `1.9.4/ornithe-feather/net/minecraft/block/PistonHeadBlock.java`, `public void addCollisions(BlockState state, World world, BlockPos pos, Box shape, List<Box> collisions, @Nullable Entity entity) {`, lines 65-68.

Difference: - 1.8.9 source: `1.8.9/ornithe-feather/net/minecraft/block/PistonHeadBlock.java` `addCollisions()` selects the face shape, dispatches it, selects the arm shape, dispatches it, then restores full mutable bounds (87-93); the six arm boxes are selected in `setArmShapeForCollisions()` (95-120).

- 1.9.4 source: paired `PistonHeadBlock.java` `addCollisions()` adds the state face shape plus one direction-indexed arm constant (65-85; constants 25-36).

Movement meaning: - Disposition: paired head-face boxes match, while each direction's connecting-arm collision box differs.

The states and adjacent piston base are not folded into this provider comparison; no player clipping outcome is claimed.


## Current evidence and remaining work

Source decisions, implementation coverage and runtime parity are separate. Source paths are relative to `decompiled_minecraft/`; immutable Git evidence can be read with `git show <revision>:<path>`.

```json
{
  "implementation": {
    "status": "open",
    "changes": [],
    "hooks": [],
    "limits": [
      "Complete current Change/catalog inventory inspected; no dedicated operation implements WORLD-15: piston-head arm collision boxes; later broadly related hooks do not establish this claimed delta."
    ]
  },
  "applicability": [],
  "sources": [
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
      "version": "1.8.9",
      "mapping": "ornithe-feather; feather-gen2-1.8.9+build.2-mergedv2.jar, feather-gen2-1.8.9+build.2.tiny",
      "path": "1.8.9/ornithe-feather/net/minecraft/block/PistonHeadBlock.java",
      "member": "private void setArmShapeForCollisions(BlockState state) {",
      "lines": "95-120",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 c5515744ce27521195ec802e2bd89ae5327448302542436576bcacf19f245fa4; matches an archived source-file digest. Checked full declaration/body; snippet SHA-256 d77ff543941f0858ea7cbd856e99f792a19936e78fe5dfdb2e28a897ae4a7fa2."
    },
    {
      "version": "1.9.4",
      "mapping": "ornithe-feather; feather-gen2-1.9.4+build.2-mergedv2.jar, feather-gen2-1.9.4+build.2.tiny",
      "path": "1.9.4/ornithe-feather/net/minecraft/block/PistonHeadBlock.java",
      "member": "public void addCollisions(BlockState state, World world, BlockPos pos, Box shape, List<Box> collisions, @Nullable Entity entity) {",
      "lines": "65-68",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 9e8bd56219d3a00e57082481d6e17a2c9383e994dac6cecac2534918f4aeb579; matches an archived source-file digest. Checked full declaration/body; snippet SHA-256 26fc89bf2242b9969191d8c4f0538fc0648e368ad43af8b401647eea3a6d28d2."
    },
    {
      "version": "1.9.4",
      "mapping": "ornithe-feather; feather-gen2-1.9.4+build.2-mergedv2.jar, feather-gen2-1.9.4+build.2.tiny",
      "path": "1.9.4/ornithe-feather/net/minecraft/block/PistonHeadBlock.java",
      "member": "PistonHeadBlock#setArmShapeForCollisions",
      "lines": null,
      "status": "different",
      "detail": "Checked complete canonical file SHA-256 9e8bd56219d3a00e57082481d6e17a2c9383e994dac6cecac2534918f4aeb579 (matches an archived source-file digest); named declaration absent. Named declaration is absent in this checked canonical file; paired existing declaration/range is retained separately, so this is an explicit addition/removal or mapping correspondence limit, not an invented line."
    }
  ],
  "sourceReviews": [],
  "openIssues": [
    {
      "missing": "Later applicability gate: exact introduction of campaign-WORLD-15-piston-head-arm-collision is unknown within (1.8.9, 1.9.4]",
      "next_action": "Preserve any exact historical verdict for its bounded assertion; verify intervening releases separately before assigning a new implementation range. Formatting alone requires no renewed acceptance."
    },
    {
      "missing": "Current implementation of campaign-WORLD-15-piston-head-arm-collision",
      "next_action": "After expert scope review and independent source acceptance, assign this specific operation to the separate implementation phase."
    },
    {
      "missing": "Exact independent source verdict/snapshot identity for WORLD-15-piston-head-arm-collision",
      "next_action": "Retain original source-confirmed status only; source-acceptance owner must resolve a preserved independent verdict or review this exact bounded assertion in the later campaign."
    }
  ],
  "runtime": "unverified"
}
```
