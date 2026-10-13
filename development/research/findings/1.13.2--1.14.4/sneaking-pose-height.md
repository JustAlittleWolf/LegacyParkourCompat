# Sneaking pose height

1.13.2, ornithe-feather: `1.13.2/ornithe-feather/net/minecraft/entity/living/player/PlayerEntity.java`, `updatePlayerPose`, lines 334–360.
1.14.4, ornithe-feather: `1.14.4/ornithe-feather/net/minecraft/entity/living/player/PlayerEntity.java`, `f_48397842` pose-dimension map, lines 109–117.

Difference: The sneaking collision height is 1.65 in 1.13.2 and 1.5 in 1.14.4, with width 0.6.

Movement meaning: The dimension provider supplies the player collision box and pose-fit checks; a 1.5-high opening fits the newer sneaking box while obstructing the older 1.65-high box. Pose selection and collision-aware resizing are separately recorded.


## Current evidence and remaining work

Source decisions, implementation coverage and runtime parity are separate. Source paths are relative to `decompiled_minecraft/`; immutable Git evidence can be read with `git show <revision>:<path>`.

```json
{
  "implementation": {
    "status": "partial",
    "changes": [
      "src/main/java/me/wolfii/legacyparkourcompat/change/v1_13/SneakingDimensions.java",
      "src/main/java/me/wolfii/legacyparkourcompat/change/v1_14/NativePoseDimensions.java"
    ],
    "hooks": [
      "PlayerDimensionsHook",
      "PlayerPoseHook"
    ],
    "limits": [
      "Registered PlayerPoseHook/PlayerDimensionsHook selects historical requested pose and 1.65 height then checks noBlockCollision(null, requested); V1_14 returns vanilla dimensions. Original includes entity-aware resize/query correspondence beyond this block-only historical fit; current full fidelity is not established."
    ]
  },
  "applicability": [
    "Endpoint interval (1.13.2, 1.14.4]; exact cutover remains the original evidence’s separate applicability gate."
  ],
  "sources": [
    {
      "version": "1.13.2",
      "mapping": "ornithe-feather",
      "path": "1.13.2/ornithe-feather/net/minecraft/entity/living/player/PlayerEntity.java",
      "member": "updatePlayerPose",
      "lines": [
        [
          334,
          360
        ]
      ],
      "status": "verified-content",
      "sha256": "4ed22f6c5a3c55d67eed782070ac722201df4d624adbc90779f1fd29c2876633",
      "detail": "Canonical source read. Matches digest in archived original."
    },
    {
      "version": "1.14.4",
      "mapping": "ornithe-feather",
      "path": "1.14.4/ornithe-feather/net/minecraft/entity/living/player/PlayerEntity.java",
      "member": "updatePlayerPose",
      "lines": [
        [
          343,
          370
        ],
        [
          109,
          117
        ]
      ],
      "status": "verified-content",
      "sha256": "2614deb3b50d11f6cdf54d9e308703bf49cb53b72646b05253857e9557d657df",
      "detail": "Canonical source read. Matches digest in archived original."
    }
  ],
  "sourceReviews": [
    {
      "verdict": "accepted, bounded",
      "assertion": "Fixed-profile pose selection and collision-aware resize.",
      "assertion_preserved": true,
      "detail": "Historical exact snapshot decision preserved; ",
      "snapshot": "448934e826fb41266dc79a313ef1187899d76382:workflows/source-campaign-2026-10-07/1.13.2--1.14.4/findings/F002-pose-selection-and-collision-aware-resize.md",
      "snapshot_sha256": "baa5c6b30167eaa8024d34b39186440be944115061c8f39fdf5e17555b8ec67e",
      "identity": "development/research/reconciliation/review-decisions/d138264d37d9992dbab8bc72b8929978a908d246c2585e55f86a0fe1929a69a2.md"
    }
  ],
  "openIssues": [
    {
      "missing": "1.13.2 original Feather mapped-client JAR d118ff5b9eb93fed06680466cb26c41a30647cdb2fc8744638cf9b35cf7864e5 remains unavailable; revised source equality does not prove original derived-JAR equivalence.",
      "next_action": "Retain existing Feather-r1 provenance caveat; original equivalence requires the exact preserved artifact, not regeneration during preparation."
    }
  ],
  "runtime": "unverified"
}
```
