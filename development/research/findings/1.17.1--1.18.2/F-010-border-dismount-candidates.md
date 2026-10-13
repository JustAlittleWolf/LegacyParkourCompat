# F-010: Dismount candidates wholly beyond the world border are rejected in 1.18.2

1.17.1, mojmap: `1.17.1/mojmap/net/minecraft/world/entity/vehicle/DismountHelper.java`, `canDismountTo, findSafeDismountLocation`, lines 41–43, 74–89.
1.18.2, mojmap: `1.18.2/mojmap/net/minecraft/world/entity/vehicle/DismountHelper.java`, `canDismountTo, findSafeDismountLocation`, lines 41–49, 80–104.

Difference: For equal block-collision inputs, 1.17.1 accepts a dismount AABB when no block collision shape intersects it. 1.18.2 additionally rejects it when the AABB lies wholly outside the world-border interior along either horizontal axis. An AABB that still overlaps the interior passes 1.18.2's `isWithinBounds` predicate.

Movement meaning: The player wake path is `LivingEntity#stopSleeping` -> `BedBlock#findStandUpPosition` -> ordered safe-candidate search -> `DismountHelper#findSafeDismountLocation` -> `LivingEntity#setPos`.


## Current evidence and remaining work

Source decisions, implementation coverage and runtime parity are separate. Source paths are relative to `decompiled_minecraft/`; immutable Git evidence can be read with `git show <revision>:<path>`.

```json
{
  "implementation": {
    "status": "open",
    "changes": [],
    "hooks": [],
    "limits": [
      "Current annotated Change/interface/catalog inventory contains no operation implementing: F-010: Dismount candidates wholly beyond the world border are rejected in 1.18.2. Existing unrelated later operations do not establish coverage."
    ]
  },
  "applicability": [
    "Endpoint interval (1.17.1, 1.18.2]; exact cutover remains the original evidence’s separate applicability gate."
  ],
  "sources": [
    {
      "version": "1.17.1",
      "mapping": "mojmap",
      "path": "1.17.1/mojmap/net/minecraft/world/entity/vehicle/DismountHelper.java",
      "member": "canDismountTo, findSafeDismountLocation",
      "lines": [
        [
          41,
          43
        ],
        [
          74,
          89
        ]
      ],
      "status": "verified-content",
      "sha256": "a7e0b00886828ea8b3acacde3602d6849c7e4f33a3d33b1ccd0f27ebc1a306b5",
      "detail": "Canonical source read. Matches digest in archived original."
    },
    {
      "version": "1.18.2",
      "mapping": "mojmap",
      "path": "1.18.2/mojmap/net/minecraft/world/entity/vehicle/DismountHelper.java",
      "member": "canDismountTo, findSafeDismountLocation",
      "lines": [
        [
          41,
          49
        ],
        [
          80,
          104
        ]
      ],
      "status": "verified-content",
      "sha256": "18cde6b3f0d4297134989620b654a508fb30fe8bf07a2f44d656446ebca99997",
      "detail": "Canonical source read. Matches digest in archived original."
    }
  ],
  "sourceReviews": [
    {
      "verdict": "ACCEPT, bounded",
      "assertion": " A `DismountHelper` candidate validation checks block collisions; B also rejects candidates outside the world border (`isWithinBounds`). The player dismount path and bed/wake path can consume candidate positions and call player position setters. This supports candidate admission only; it does not establish the exact resulting pose or non-player/vehicle movement. Border admission is an environmental bound, not historical block eligibility.\n",
      "assertion_preserved": true,
      "detail": "Exact original snapshot verdict; current compact wording does not renew acceptance. Later identity-only revision, where present, remains separate.",
      "snapshot": "e531086eecc778432b40b2b9ef39499b8a7f0dba:workflows/source-campaign-2026-10-07/1.17.1--1.18.2/findings/F-010-border-dismount-candidates.md",
      "snapshot_sha256": "65407bcfbc1758f4e267362ac7cfeb88ac67abdf6f646b62452ce5ccfff757e6",
      "identity": "4a5eae32b5819418dbd59eb4a763ee24ea1f2c52:development/research/archive/legacy-workflows/source-boundary-reviews/2026-10-08-mc1171-1182-snapshot-review.md"
    }
  ],
  "openIssues": [],
  "runtime": "unverified"
}
```
