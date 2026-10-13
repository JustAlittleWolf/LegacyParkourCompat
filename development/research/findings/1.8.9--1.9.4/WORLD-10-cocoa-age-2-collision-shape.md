# WORLD-10: cocoa age-2 collision shape

1.8.9, ornithe-feather; feather-gen2-1.8.9+build.2-mergedv2.jar, feather-gen2-1.8.9+build.2.tiny: `1.8.9/ornithe-feather/net/minecraft/block/CocoaBlock.java`, `public void updateShape(WorldView world, BlockPos pos) {`, lines 70-90.
1.9.4, ornithe-feather; feather-gen2-1.9.4+build.2-mergedv2.jar, feather-gen2-1.9.4+build.2.tiny: `1.9.4/ornithe-feather/net/minecraft/block/CocoaBlock.java`, `public Box getShape(BlockState state, WorldView world, BlockPos pos) {`, lines 77-90.

Difference: - Bounded geometry result: at age 0 and age 1, all four 1.8.9/1.9.4 facing boxes match.

At age 2, 1.8.9 computes an 8/16-wide cocoa body with lower Y 3/16; 1.9.4 returns the age-1 box, which is 6/16 wide with lower Y 5/16.

Both have upper Y 12/16.

The local facing-side and tangent extents differ by 2/16 on each applicable horizontal bound; no orientation is omitted.

Movement meaning: - Player reachability: paired `World.getCollisions(Entity,Box)` provider dispatch feeds the ordinary local-player `Entity.move()` path in COLL-01.

The source-state precondition is a cocoa block with age 2 in the queried collision box; neighboring state is not read by this provider.

- Disposition: direct registered collision-shape delta established for age 2 in every facing.


## Current evidence and remaining work

Source decisions, implementation coverage and runtime parity are separate. Source paths are relative to `decompiled_minecraft/`; immutable Git evidence can be read with `git show <revision>:<path>`.

```json
{
  "implementation": {
    "status": "open",
    "changes": [],
    "hooks": [],
    "limits": [
      "Complete current Change/catalog inventory inspected; no dedicated operation implements WORLD-10: cocoa age-2 collision shape; later broadly related hooks do not establish this claimed delta."
    ]
  },
  "applicability": [],
  "sources": [
    {
      "version": "1.8.9",
      "mapping": "ornithe-feather; feather-gen2-1.8.9+build.2-mergedv2.jar, feather-gen2-1.8.9+build.2.tiny",
      "path": "1.8.9/ornithe-feather/net/minecraft/block/CocoaBlock.java",
      "member": "CocoaBlock#getShape",
      "lines": null,
      "status": "different",
      "detail": "Checked complete canonical file SHA-256 d4e3c6b53fd33b3ff5e433b333511d93cbb83dbc0bdfb5690efbef734c5542e9 (matches an archived source-file digest); named declaration absent. Named declaration is absent in this checked canonical file; paired existing declaration/range is retained separately, so this is an explicit addition/removal or mapping correspondence limit, not an invented line."
    },
    {
      "version": "1.8.9",
      "mapping": "ornithe-feather; feather-gen2-1.8.9+build.2-mergedv2.jar, feather-gen2-1.8.9+build.2.tiny",
      "path": "1.8.9/ornithe-feather/net/minecraft/block/CocoaBlock.java",
      "member": "public void updateShape(WorldView world, BlockPos pos) {",
      "lines": "70-90",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 d4e3c6b53fd33b3ff5e433b333511d93cbb83dbc0bdfb5690efbef734c5542e9; matches an archived source-file digest. Checked full declaration/body; snippet SHA-256 442e5d07a0f2285fbae9fbd995556cb5109443f105299bbcf32cb9551a7ea9f0."
    },
    {
      "version": "1.9.4",
      "mapping": "ornithe-feather; feather-gen2-1.9.4+build.2-mergedv2.jar, feather-gen2-1.9.4+build.2.tiny",
      "path": "1.9.4/ornithe-feather/net/minecraft/block/CocoaBlock.java",
      "member": "public Box getShape(BlockState state, WorldView world, BlockPos pos) {",
      "lines": "77-90",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 fc7589009c1a84c6e69408fc2ff17eec46202acd03e986555e83812204dd2234; matches an archived source-file digest. Checked full declaration/body; snippet SHA-256 2de14af55a0f99c2171b668c49e22a2238c6666471bd94cf831ff03999882afe."
    },
    {
      "version": "1.9.4",
      "mapping": "ornithe-feather; feather-gen2-1.9.4+build.2-mergedv2.jar, feather-gen2-1.9.4+build.2.tiny",
      "path": "1.9.4/ornithe-feather/net/minecraft/block/CocoaBlock.java",
      "member": "CocoaBlock#updateShape",
      "lines": null,
      "status": "different",
      "detail": "Checked complete canonical file SHA-256 fc7589009c1a84c6e69408fc2ff17eec46202acd03e986555e83812204dd2234 (matches an archived source-file digest); named declaration absent. Named declaration is absent in this checked canonical file; paired existing declaration/range is retained separately, so this is an explicit addition/removal or mapping correspondence limit, not an invented line."
    }
  ],
  "sourceReviews": [],
  "openIssues": [
    {
      "missing": "Later applicability gate: exact introduction of campaign-WORLD-10-cocoa-age-2-collision-shape is unknown within (1.8.9, 1.9.4]",
      "next_action": "Preserve any exact historical verdict for its bounded assertion; verify intervening releases separately before assigning a new implementation range. Formatting alone requires no renewed acceptance."
    },
    {
      "missing": "Current implementation of campaign-WORLD-10-cocoa-age-2-collision-shape",
      "next_action": "After expert scope review and independent source acceptance, assign this specific operation to the separate implementation phase."
    },
    {
      "missing": "Exact independent source verdict/snapshot identity for WORLD-10-cocoa-age-2-collision-shape",
      "next_action": "Retain original source-confirmed status only; source-acceptance owner must resolve a preserved independent verdict or review this exact bounded assertion in the later campaign."
    }
  ],
  "runtime": "unverified"
}
```
