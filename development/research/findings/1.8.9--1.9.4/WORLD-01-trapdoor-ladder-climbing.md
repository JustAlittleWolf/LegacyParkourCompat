# WORLD-01: matching open trapdoor above a ladder is climbable

1.8.9, ornithe-feather; feather-gen2-1.8.9+build.2-mergedv2.jar, feather-gen2-1.8.9+build.2.tiny: `1.8.9/ornithe-feather/net/minecraft/entity/living/LivingEntity.java`, `public boolean isClimbing() {`, lines 794-800.
1.9.4, ornithe-feather; feather-gen2-1.9.4+build.2-mergedv2.jar, feather-gen2-1.9.4+build.2.tiny: `1.9.4/ornithe-feather/net/minecraft/entity/living/LivingEntity.java`, `public boolean isClimbing() {`, lines 905-917.

Difference: 1.8.9 returns climbing only when the block at the player's floored position is a ladder or vine, and excludes spectators. 1.9.4 keeps that spectator exclusion and adds a trapdoor branch: the block must be a `TrapdoorBlock`, its `OPEN` property must be true, the block directly below must be a ladder, and the ladder and trapdoor `FACING` values must match.

The ladder and trapdoor state properties and their registrations exist on both sides; the new rule therefore changes movement for an arrangement of existing blocks rather than assigning historical behavior to a modern-only block.

Movement meaning: `LivingEntity.moveRelative(FF)V` consults `isClimbing()` before movement and applies the climbing horizontal clamp, fall-distance reset, downward-velocity clamp, and player-sneak descent rule. 1.8.9's consumer is at lines 1150-1162; 1.9.4's corresponding consumer is at lines 1376-1393 in the same paired `LivingEntity.java` files.

The new predicate also reads the block state directly below and compares its facing.

When all 1.9.4 predicate conditions hold, 1.9.4 selects the climbing travel branch; 1.8.9 does not.

The resulting player velocity follows the existing climbing clamp branch.


## Current evidence and remaining work

Source decisions, implementation coverage and runtime parity are separate. Source paths are relative to `decompiled_minecraft/`; immutable Git evidence can be read with `git show <revision>:<path>`.

```json
{
  "implementation": {
    "status": "open",
    "changes": [],
    "hooks": [],
    "limits": [
      "Complete current Change/catalog inventory inspected; no dedicated operation implements WORLD-01: matching open trapdoor above a ladder is climbable; later broadly related hooks do not establish this claimed delta."
    ]
  },
  "applicability": [],
  "sources": [
    {
      "version": "1.8.9",
      "mapping": "ornithe-feather; feather-gen2-1.8.9+build.2-mergedv2.jar, feather-gen2-1.8.9+build.2.tiny",
      "path": "1.8.9/ornithe-feather/net/minecraft/entity/living/LivingEntity.java",
      "member": "public boolean isClimbing() {",
      "lines": "794-800",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 082831c6578e3a70fa6cea5b90bc3eefc26678259b66334470de22b90b5b0e4e; matches an archived source-file digest. Checked full declaration/body; snippet SHA-256 a92c7b828a4d362aa331f5ef4542d1c28415f9dd380274213ff4760e41ca790d."
    },
    {
      "version": "1.9.4",
      "mapping": "ornithe-feather; feather-gen2-1.9.4+build.2-mergedv2.jar, feather-gen2-1.9.4+build.2.tiny",
      "path": "1.9.4/ornithe-feather/net/minecraft/entity/living/LivingEntity.java",
      "member": "public boolean isClimbing() {",
      "lines": "905-917",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 bbb7703f18fd5da05c4e4a43a77ea644b388e63c01d34166d308ea52054be4e5; matches an archived source-file digest. Checked full declaration/body; snippet SHA-256 6a5dcdc9fd67355cbf331c5807d7e05562f3f9fc9c6fc6655393af228050ed52."
    }
  ],
  "sourceReviews": [],
  "openIssues": [
    {
      "missing": "Later applicability gate: exact introduction of campaign-WORLD-01-trapdoor-ladder-climbing is unknown within (1.8.9, 1.9.4]",
      "next_action": "Preserve any exact historical verdict for its bounded assertion; verify intervening releases separately before assigning a new implementation range. Formatting alone requires no renewed acceptance."
    },
    {
      "missing": "Current implementation of campaign-WORLD-01-trapdoor-ladder-climbing",
      "next_action": "After expert scope review and independent source acceptance, assign this specific operation to the separate implementation phase."
    },
    {
      "missing": "Exact independent source verdict/snapshot identity for WORLD-01-trapdoor-ladder-climbing",
      "next_action": "Retain original source-confirmed status only; source-acceptance owner must resolve a preserved independent verdict or review this exact bounded assertion in the later campaign."
    }
  ],
  "runtime": "unverified"
}
```
