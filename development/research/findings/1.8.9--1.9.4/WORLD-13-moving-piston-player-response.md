# WORLD-13: moving piston direct player response

1.8.9, ornithe-feather; feather-gen2-1.8.9+build.2-mergedv2.jar, feather-gen2-1.8.9+build.2.tiny: `1.8.9/ornithe-feather/net/minecraft/block/entity/MovingBlockEntity.java`, `private void moveEntities(float progress, float amount) {`, lines 80-113.
1.9.4, ornithe-feather; feather-gen2-1.9.4+build.2-mergedv2.jar, feather-gen2-1.9.4+build.2.tiny: `1.9.4/ornithe-feather/net/minecraft/block/entity/MovingBlockEntity.java`, `private void moveEntities() {`, lines 90-149.

Difference: - 1.8.9 source: `1.8.9/ornithe-feather/net/minecraft/block/entity/MovingBlockEntity.java` `moveEntities(progress,amount)` obtains the MovingBlock collision box and queries entities (80-90); for an extending moved slime block it sets the facing-axis velocity and does not call `Entity.move`, while other moved states call `entity.move(amount * facing offsets)` (93-107).

- 1.9.4 source: paired `MovingBlockEntity.java` `moveEntities()` queries the progress-union shape translated by block position (90-93), skips entities only when `getPistonMoveBehavior() == IGNORE`, sets the slime-facing velocity for slime states, then computes the entity-to-box edge gap plus `0.01` and calls `entity.move(gap * movement direction)` for both slime and non-slime states (94-146).

Movement meaning: - Disposition: direct player response differs.

For an extending piston-moving slime block and a player returned by the respective entity query, 1.8.9 assigns facing-axis velocity only; 1.9.4 assigns the same velocity and also calls `Entity.move`.

For other moved blocks, 1.8.9 passes a progress-derived amount while 1.9.4 computes the edge gap and adds `0.01` before moving.

WORLD-12 records the query-box shape difference; no final position or trajectory is claimed.


## Current evidence and remaining work

Source decisions, implementation coverage and runtime parity are separate. Source paths are relative to `decompiled_minecraft/`; immutable Git evidence can be read with `git show <revision>:<path>`.

```json
{
  "implementation": {
    "status": "open",
    "changes": [],
    "hooks": [],
    "limits": [
      "Complete current Change/catalog inventory inspected; no dedicated operation implements WORLD-13: moving piston direct player response; later broadly related hooks do not establish this claimed delta."
    ]
  },
  "applicability": [],
  "sources": [
    {
      "version": "1.8.9",
      "mapping": "ornithe-feather; feather-gen2-1.8.9+build.2-mergedv2.jar, feather-gen2-1.8.9+build.2.tiny",
      "path": "1.8.9/ornithe-feather/net/minecraft/block/entity/MovingBlockEntity.java",
      "member": "private void moveEntities(float progress, float amount) {",
      "lines": "80-113",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 05a5bfbf2556eaa621996bda365b7bab01f5b9060cf773e7b9949ebe2f39fff6; matches an archived source-file digest. Checked full declaration/body; snippet SHA-256 b5673f0d8e628285c6d34285bdfc8f81ddbc833c7bd03263d76daf30f72c65e3."
    },
    {
      "version": "1.9.4",
      "mapping": "ornithe-feather; feather-gen2-1.9.4+build.2-mergedv2.jar, feather-gen2-1.9.4+build.2.tiny",
      "path": "1.9.4/ornithe-feather/net/minecraft/block/entity/MovingBlockEntity.java",
      "member": "private void moveEntities() {",
      "lines": "90-149",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 50eb847d3ff4ff78441057f397c43e15fb894177f7c5a736a61c22a80f1de467; matches an archived source-file digest. Checked full declaration/body; snippet SHA-256 b5ab71ef71e0f56901dd90a9391878ef1964a95cc6adcfcada25528f34383c91."
    }
  ],
  "sourceReviews": [],
  "openIssues": [
    {
      "missing": "Later applicability gate: exact introduction of campaign-WORLD-13-moving-piston-player-response is unknown within (1.8.9, 1.9.4]",
      "next_action": "Preserve any exact historical verdict for its bounded assertion; verify intervening releases separately before assigning a new implementation range. Formatting alone requires no renewed acceptance."
    },
    {
      "missing": "Current implementation of campaign-WORLD-13-moving-piston-player-response",
      "next_action": "After expert scope review and independent source acceptance, assign this specific operation to the separate implementation phase."
    },
    {
      "missing": "Exact independent source verdict/snapshot identity for WORLD-13-moving-piston-player-response",
      "next_action": "Retain original source-confirmed status only; source-acceptance owner must resolve a preserved independent verdict or review this exact bounded assertion in the later campaign."
    }
  ],
  "runtime": "unverified"
}
```
