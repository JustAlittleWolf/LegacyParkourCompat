# F-FENCE-FROSTED-ICE-CONNECTION — 1.13.2 fences omit the Frosted Ice arm

1.12.2, ornithe-feather; feather-gen2-1.12.2+build.2-mergedv2.jar, feather-gen2-1.12.2+build.2.tiny: `1.12.2/ornithe-feather/net/minecraft/block/FenceBlock.java`, `public boolean shouldConnectTo(WorldView world, BlockPos pos, Direction dir) {`, lines 124-130.
1.12.2, ornithe-feather; feather-gen2-1.12.2+build.2-mergedv2.jar, feather-gen2-1.12.2+build.2.tiny: `1.12.2/ornithe-feather/net/minecraft/block/FenceBlock.java`, `protected static boolean shouldConnectTo(Block block) {`, lines 132-138.
1.13.2, ornithe-feather; feather-gen2-1.13.2+build.2-mergedv2.jar, feather-gen2-1.13.2+build.2.tiny: `1.13.2/ornithe-feather/net/minecraft/block/FenceBlock.java`, `public boolean shouldConnectTo(BlockState state, FaceShape shape) {`, lines 46-50.
1.13.2, ornithe-feather; feather-gen2-1.13.2+build.2-mergedv2.jar, feather-gen2-1.13.2+build.2.tiny: `1.13.2/ornithe-feather/net/minecraft/block/FenceBlock.java`, `public static boolean shouldConnectTo(Block block) {`, lines 52-61.

Difference: For a solid-face neighbor, 1.12.2's fence predicate rejects the neighbor only when it appears in `Block.isExceptionForAttachment` or the fence's additional exception list.

Neither list contains `Blocks.FROSTED_ICE`, so an east-adjacent Frosted Ice block with its default `FaceShape.SOLID` sets EAST=true. 1.13.2 adds `Blocks.FROSTED_ICE` to `FenceBlock.shouldConnectTo(Block)`, so the same solid face sets EAST=false. 1.13.2's placement and horizontal `updateShape` both use that predicate; 1.12.2 resolves the connection from neighbors during collision collection.

Frosted Ice existed in both versions under the same `frosted_ice` registry key.

It inherits the full-block face and collision shapes.

In 1.13.2, `Block.Properties.hasCollision` defaults true and the Frosted Ice registration does not disable it.

This finding therefore concerns an old block present in both eras.

Movement meaning: Place a fence at (0,0,0) and Frosted Ice at (1,0,0), leaving the remaining fence neighbors open.

On the paired movement path, 1.12.2 resolves EAST=true and appends its east collision arm X[10,16] × Z[6,10] × Y[0,24] in sixteenth-block coordinates. 1.13.2 resolves EAST=false and returns only the center post; the 1.13.2 fence geometry and inherited pane-provider route are bounded in `FENCE-COLLISION-GEOMETRY`.

For an isolated source-derived player query, set a 0.6-wide player box to X[1.0,1.6], Z[0.2,0.8], Y[1.0001,2.8001], with `onGround=false`, not sneaking, and request `dx=-0.1`, `dy=dz=0`.

The player is just above the Frosted Ice top, so the Frosted Ice full cube does not overlap vertically. 1.12.2's east arm extends to x=1.0 and up to y=1.5, so the negative X movement is clipped at zero.


## Current evidence and remaining work

Source decisions, implementation coverage and runtime parity are separate. Source paths are relative to `decompiled_minecraft/`; immutable Git evidence can be read with `git show <revision>:<path>`.

```json
{
  "implementation": {
    "status": "open",
    "changes": [],
    "hooks": [],
    "limits": [
      "Complete current Change/catalog inventory inspected; no dedicated operation implements F-FENCE-FROSTED-ICE-CONNECTION — 1.13.2 fences omit the Frosted Ice arm; later broadly related hooks do not establish this claimed delta."
    ]
  },
  "applicability": [],
  "sources": [
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
      "version": "1.13.2",
      "mapping": "ornithe-feather; feather-gen2-1.13.2+build.2-mergedv2.jar, feather-gen2-1.13.2+build.2.tiny",
      "path": "1.13.2/ornithe-feather/net/minecraft/block/FenceBlock.java",
      "member": "public boolean shouldConnectTo(BlockState state, FaceShape shape) {",
      "lines": "46-50",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 abefbedea10306abf602d4e379a4793b415ca7635231752cd9f18a80aced5fc2; matches an archived source-file digest. Checked full declaration/body; snippet SHA-256 568c34287390a4b14511d3282f8043ce19a7a1e0aa880bfdcc12ea24e91c0456."
    },
    {
      "version": "1.13.2",
      "mapping": "ornithe-feather; feather-gen2-1.13.2+build.2-mergedv2.jar, feather-gen2-1.13.2+build.2.tiny",
      "path": "1.13.2/ornithe-feather/net/minecraft/block/FenceBlock.java",
      "member": "public static boolean shouldConnectTo(Block block) {",
      "lines": "52-61",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 abefbedea10306abf602d4e379a4793b415ca7635231752cd9f18a80aced5fc2; matches an archived source-file digest. Checked full declaration/body; snippet SHA-256 10cec7aa934196e04e4d0c5dced43af1fdbad78c4b1d45b2da01f4b190d998d7."
    }
  ],
  "sourceReviews": [],
  "openIssues": [
    {
      "missing": "Later applicability gate: exact introduction of campaign-F-FENCE-FROSTED-ICE-CONNECTION is unknown within (1.12.2, 1.13.2]",
      "next_action": "Preserve any exact historical verdict for its bounded assertion; verify intervening releases separately before assigning a new implementation range. Formatting alone requires no renewed acceptance."
    },
    {
      "missing": "Current implementation of campaign-F-FENCE-FROSTED-ICE-CONNECTION",
      "next_action": "After expert scope review and independent source acceptance, assign this specific operation to the separate implementation phase."
    },
    {
      "missing": "Exact independent source verdict/snapshot identity for F-FENCE-FROSTED-ICE-CONNECTION",
      "next_action": "Retain original source-confirmed status only; source-acceptance owner must resolve a preserved independent verdict or review this exact bounded assertion in the later campaign."
    }
  ],
  "runtime": "unverified"
}
```
