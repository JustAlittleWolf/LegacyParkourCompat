# F-S1-OPEN-SHULKER-ESCAPE: open shulker boxes stop triggering player escape

1.15.2, mojmap: `1.15.2/mojmap/net/minecraft/client/player/LocalPlayer.java`, `checkInBlock`, lines 386–440.
1.16.5, mojmap: `1.16.5/mojmap/net/minecraft/client/player/LocalPlayer.java`, `moveTowardsClosestSpace`, lines 414–447.

Difference: For an open shulker box with its block entity present, 1.15.2's `ShulkerBoxBlock.isSuffocating` still returns true. When a sampled player column includes that block, `blocked` therefore treats the column as obstructed, regardless of the shulker's open state or the player's overlap with its current shape. 1.16.5's shulker registration defines suffocation as `blockEntity.isClosed()` (or true when the block entity is absent). An open shulker is filtered out before 1.16.5 checks collision-shape intersection, so that same sample does not count as suffocating.

Movement meaning: Shulker boxes exist in both endpoints.


## Current evidence and remaining work

Source decisions, implementation coverage and runtime parity are separate. Source paths are relative to `decompiled_minecraft/`; immutable Git evidence can be read with `git show <revision>:<path>`.

```json
{
  "implementation": {
    "status": "partial",
    "changes": [
      "src/main/java/me/wolfii/legacyparkourcompat/change/v1_15_2/SuffocationProbe.java"
    ],
    "hooks": [
      "SuffocationProbeHook"
    ],
    "limits": [
      "SuffocationProbeHook restores unconditional Shulker suffocation only when selected target is exactly V1_15_2, and scans the historical column. Exact-profile check limits resolver coverage; bounded endpoint code is present but broader applicability not proved."
    ]
  },
  "applicability": [
    "Endpoint interval (1.15.2, 1.16.5]; exact cutover remains the original evidence’s separate applicability gate."
  ],
  "sources": [
    {
      "version": "1.15.2",
      "mapping": "mojmap",
      "path": "1.15.2/mojmap/net/minecraft/client/player/LocalPlayer.java",
      "member": "checkInBlock",
      "lines": [
        [
          386,
          440
        ]
      ],
      "status": "verified-content",
      "sha256": "3a9019bd7b860e251c23fd8d0cd70b7f5b38566d34470c4e29b1014ef689ccbd",
      "detail": "Canonical source read. Matches digest in archived original."
    },
    {
      "version": "1.16.5",
      "mapping": "mojmap",
      "path": "1.16.5/mojmap/net/minecraft/client/player/LocalPlayer.java",
      "member": "moveTowardsClosestSpace",
      "lines": [
        [
          414,
          447
        ]
      ],
      "status": "verified-content",
      "sha256": "6011569e766bb1568609147be9aa14e9c08c51948e3d3a60fd066e848f6a8c2b",
      "detail": "Canonical source read. Matches digest in archived original."
    }
  ],
  "sourceReviews": [
    {
      "verdict": "accepted for finding-only handoff",
      "assertion": "Conditional open-shulker sampled-column local-player escape impulse.",
      "assertion_preserved": true,
      "detail": "Historical exact snapshot decision preserved; no final displacement, first release, or runtime claim",
      "snapshot": "d859478b9f6de63ba4a121a1f31d6dc90ad4f51e:workflows/source-campaign-2026-10-07/1.15.2--1.16.5/findings/F-S1-OPEN-SHULKER-ESCAPE.md",
      "snapshot_sha256": "11860ba106197cae687735e641db4c9ef7057ddadcddeafe5c16f012e8e657e4",
      "identity": "4a5eae32b5819418dbd59eb4a763ee24ea1f2c52:development/research/archive/legacy-workflows/coverage-review-2026-10-07/middle.md"
    }
  ],
  "openIssues": [],
  "runtime": "unverified"
}
```
