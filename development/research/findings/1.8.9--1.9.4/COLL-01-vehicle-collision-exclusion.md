# COLL-01: Mounted player collision query includes its boat in 1.8.9

1.8.9, ornithe-feather; feather-gen2-1.8.9+build.2-mergedv2.jar, feather-gen2-1.8.9+build.2.tiny: `1.8.9/ornithe-feather/net/minecraft/world/World.java`, `public List<Box> getCollisions(Entity entity, Box shape) {`, lines 891-945.
1.8.9, ornithe-feather; feather-gen2-1.8.9+build.2-mergedv2.jar, feather-gen2-1.8.9+build.2.tiny: `1.8.9/ornithe-feather/net/minecraft/entity/Entity.java`, `public void move(double dx, double dy, double dz) {`, lines 371-636.
1.9.4, ornithe-feather; feather-gen2-1.9.4+build.2-mergedv2.jar, feather-gen2-1.9.4+build.2.tiny: `1.9.4/ornithe-feather/net/minecraft/world/World.java`, `public List<Box> getCollisions(@Nullable Entity entity, Box shape) {`, lines 899-961.
1.9.4, ornithe-feather; feather-gen2-1.9.4+build.2-mergedv2.jar, feather-gen2-1.9.4+build.2.tiny: `1.9.4/ornithe-feather/net/minecraft/world/World.java`, `public boolean getCollisions(Box shape) {`, lines 1018-1055.
1.9.4, ornithe-feather; feather-gen2-1.9.4+build.2-mergedv2.jar, feather-gen2-1.9.4+build.2.tiny: `1.9.4/ornithe-feather/net/minecraft/entity/Entity.java`, `public void move(double dx, double dy, double dz) {`, lines 441-720.

Difference: 1.8.9's comparisons use the whole collision-query result list where the loop needs to test a candidate entity.

The mapped bytecode confirms the decompiled source expression.

Since the player rider and its boat are not the result-list object, the predicate admits the boat candidate; when its boat shape intersects the player's collision query box, 1.8.9 adds that shape.

The player's base opposing-shape callback returns null, so the boat's own collision shape is the relevant added shape.

1.9.4 checks each candidate's root vehicle. 1.8.9 mounted player and its boat resolve to the same root vehicle, so 1.9.4 skips the boat candidate before reading either collision shape.

Both versions otherwise query the same entity candidates for this bounded comparison.

Movement meaning: The local passenger reaches `LivingEntity#moveRelative(FF)V` through `World#tickEntities()` -> `Entity#rideTick()` -> local player tick -> `LivingEntity#mobTick()`.

The travel method calls `Entity#move(DDD)V`, which calls `World#getCollisions(Entity,Box)`. 1.8.9 boat exposes its current box as a collision shape in both versions.


## Current evidence and remaining work

Source decisions, implementation coverage and runtime parity are separate. Source paths are relative to `decompiled_minecraft/`; immutable Git evidence can be read with `git show <revision>:<path>`.

```json
{
  "implementation": {
    "status": "open",
    "changes": [],
    "hooks": [],
    "limits": [
      "Complete current Change/catalog inventory inspected; no dedicated operation implements COLL-01: Mounted player collision query includes its boat in 1.8.9; later broadly related hooks do not establish this claimed delta."
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
      "path": "1.8.9/ornithe-feather/net/minecraft/entity/Entity.java",
      "member": "public void move(double dx, double dy, double dz) {",
      "lines": "371-636",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 d4c10932cb5bb1067a5a58be4e1bb1b42bdde3b6fc893d88178cc07435a1696b; matches an archived source-file digest. Checked full declaration/body; snippet SHA-256 31760c0cdec903263c45fabe12483f9c7507c7f7e5104a01930f23bcf66f5a1c."
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
      "path": "1.9.4/ornithe-feather/net/minecraft/entity/Entity.java",
      "member": "public void move(double dx, double dy, double dz) {",
      "lines": "441-720",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 bcd7fa2206bf8d7271102f6da7fe2771f96dbe22ec79dab9f2101a3e29c17ef0; matches an archived source-file digest. Checked full declaration/body; snippet SHA-256 68a47d7966cc01b5736876a485940f6a9128213e67714d21989594514be467a4."
    }
  ],
  "sourceReviews": [],
  "openIssues": [
    {
      "missing": "Later applicability gate: exact introduction of campaign-COLL-01-vehicle-collision-exclusion is unknown within (1.8.9, 1.9.4]",
      "next_action": "Preserve any exact historical verdict for its bounded assertion; verify intervening releases separately before assigning a new implementation range. Formatting alone requires no renewed acceptance."
    },
    {
      "missing": "Current implementation of campaign-COLL-01-vehicle-collision-exclusion",
      "next_action": "After expert scope review and independent source acceptance, assign this specific operation to the separate implementation phase."
    },
    {
      "missing": "Exact independent source verdict/snapshot identity for COLL-01-vehicle-collision-exclusion",
      "next_action": "Retain original source-confirmed status only; source-acceptance owner must resolve a preserved independent verdict or review this exact bounded assertion in the later campaign."
    }
  ],
  "runtime": "unverified"
}
```
