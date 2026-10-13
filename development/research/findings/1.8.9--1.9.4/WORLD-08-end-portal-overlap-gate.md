# WORLD-08: End portal overlap gate

1.8.9, ornithe-feather; feather-gen2-1.8.9+build.2-mergedv2.jar, feather-gen2-1.8.9+build.2.tiny: `1.8.9/ornithe-feather/net/minecraft/block/EndPortalBlock.java`, `public void onEntityCollision(World world, BlockPos pos, BlockState state, Entity entity) {`, lines 61-65.
1.9.4, ornithe-feather; feather-gen2-1.9.4+build.2-mergedv2.jar, feather-gen2-1.9.4+build.2.tiny: `1.9.4/ornithe-feather/net/minecraft/block/EndPortalBlock.java`, `public void onEntityCollision(World world, BlockPos pos, BlockState state, Entity entity) {`, lines 63-71.

Difference: - 1.8.9 source: 1.8.9/ornithe-feather/net/minecraft/block/EndPortalBlock.java, onEntityCollision lines 61-64 On the server it calls entity.changeDimension(1) whenever vehicle == null && rider == null.

- 1.9.4 source: 1.9.4/ornithe-feather/net/minecraft/block/EndPortalBlock.java, onEntityCollision lines 63-71 It additionally requires !isRiding(), !hasPassengers(), canUsePortals(), and intersection between the entity shape and the block state's translated shape.

Movement meaning: - Scope: End Portal block player callback eligibility predicate.

Records the direct dimension-transition call condition only; destination selection and post-transfer positioning are not compared.

- Reachability: Entity.checkBlockCollisions() dispatches callbacks for every overlapped block cell, independent of collision-box intersection; local players reach this through Entity.move as recorded in COLL-01.

Therefore 1.9.4's 0.75-high shape test is distinct from 1.8.9's block-cell callback guard.


## Current evidence and remaining work

Source decisions, implementation coverage and runtime parity are separate. Source paths are relative to `decompiled_minecraft/`; immutable Git evidence can be read with `git show <revision>:<path>`.

```json
{
  "implementation": {
    "status": "open",
    "changes": [],
    "hooks": [],
    "limits": [
      "Complete current Change/catalog inventory inspected; no dedicated operation implements WORLD-08: End portal overlap gate; later broadly related hooks do not establish this claimed delta."
    ]
  },
  "applicability": [],
  "sources": [
    {
      "version": "1.8.9",
      "mapping": "ornithe-feather; feather-gen2-1.8.9+build.2-mergedv2.jar, feather-gen2-1.8.9+build.2.tiny",
      "path": "1.8.9/ornithe-feather/net/minecraft/block/EndPortalBlock.java",
      "member": "public void onEntityCollision(World world, BlockPos pos, BlockState state, Entity entity) {",
      "lines": "61-65",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 1cb419f44592e68244e06b34799c6354a2bcc3d08bd849e0709fca36b54e1e40; matches an archived source-file digest. Checked full declaration/body; snippet SHA-256 c34c1951b5a2ab5291234628a471b05dbf35be95b5a363728693cf959ebc70a9."
    },
    {
      "version": "1.9.4",
      "mapping": "ornithe-feather; feather-gen2-1.9.4+build.2-mergedv2.jar, feather-gen2-1.9.4+build.2.tiny",
      "path": "1.9.4/ornithe-feather/net/minecraft/block/EndPortalBlock.java",
      "member": "public void onEntityCollision(World world, BlockPos pos, BlockState state, Entity entity) {",
      "lines": "63-71",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 7afd1729a13ee3e14e191a6fb2ce41b847a94fc01fac526a4121183074da8650; matches an archived source-file digest. Checked full declaration/body; snippet SHA-256 d8506ea94325f93fcc4925a7d3a1e06fb35118aa329e291464a7e8253f459bad."
    }
  ],
  "sourceReviews": [],
  "openIssues": [
    {
      "missing": "Later applicability gate: exact introduction of campaign-WORLD-08-end-portal-overlap-gate is unknown within (1.8.9, 1.9.4]",
      "next_action": "Preserve any exact historical verdict for its bounded assertion; verify intervening releases separately before assigning a new implementation range. Formatting alone requires no renewed acceptance."
    },
    {
      "missing": "Current implementation of campaign-WORLD-08-end-portal-overlap-gate",
      "next_action": "After expert scope review and independent source acceptance, assign this specific operation to the separate implementation phase."
    },
    {
      "missing": "Exact independent source verdict/snapshot identity for WORLD-08-end-portal-overlap-gate",
      "next_action": "Retain original source-confirmed status only; source-acceptance owner must resolve a preserved independent verdict or review this exact bounded assertion in the later campaign."
    }
  ],
  "runtime": "unverified"
}
```
