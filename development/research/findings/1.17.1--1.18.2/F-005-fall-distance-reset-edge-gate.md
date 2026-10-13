# F-005: Long movement can reset fallDistance before later edge-backoff checks

1.17.1, mojmap: `1.17.1/mojmap/net/minecraft/world/entity/Entity.java`, `move`, lines 553–566.
1.18.2, mojmap: `1.18.2/mojmap/net/minecraft/world/entity/Entity.java`, `move`, lines 562–575.

Difference: 1.18.2 adds a fallDistance writer after edge-backoff/collision; unchanged Player gate reads it later.

Movement meaning: Player movement -> Entity#move clip/reset -> later Player#isAboveGround. 1.18.2 clip-filter closure is resolved: ClipContext#Block.FALLDAMAGE_RESETTING checks the new block tag, ClipContext#Fluid.WATER checks the water tag present in both versions, and Entity#resetFallDistance writes zero on a non-MISS hit.


## Current evidence and remaining work

Source decisions, implementation coverage and runtime parity are separate. Source paths are relative to `decompiled_minecraft/`; immutable Git evidence can be read with `git show <revision>:<path>`.

```json
{
  "implementation": {
    "status": "partial",
    "changes": [
      "src/main/java/me/wolfii/legacyparkourcompat/change/v1_18/FallDistanceReset.java"
    ],
    "hooks": [
      "PlayerFallDistanceResetHook"
    ],
    "limits": [
      "PlayerFallDistanceResetHook suppresses newer writer for selected versions >=V1_17_1 and <V1_18_2; operation is present but exact-profile range excludes older resolver-selected targets. Keep current behavior and flag applicability for later review."
    ]
  },
  "applicability": [
    "Accepted sampled first changed release 1.18.2; r1 witness rejected and r2 consumer/dependency correction accepted separately."
  ],
  "sources": [
    {
      "version": "1.17.1",
      "mapping": "mojmap",
      "path": "1.17.1/mojmap/net/minecraft/world/entity/Entity.java",
      "member": "move",
      "lines": [
        [
          553,
          566
        ]
      ],
      "status": "verified-content",
      "sha256": "ab28e1fba924771ec048140dfd293ee5a46a7dfe81f71a1a0b1aecc1927232de",
      "detail": "Canonical source read. Matches digest in archived original."
    },
    {
      "version": "1.18.2",
      "mapping": "mojmap",
      "path": "1.18.2/mojmap/net/minecraft/world/entity/Entity.java",
      "member": "move",
      "lines": [
        [
          562,
          575
        ]
      ],
      "status": "verified-content",
      "sha256": "2228fdaca5793171cbd94038306d571a6ada78ca96f5734efb4cada5b744c10a",
      "detail": "Canonical source read. Matches digest in archived original."
    }
  ],
  "sourceReviews": [
    {
      "verdict": "ACCEPT, bounded",
      "assertion": " B `Entity.move` applies edge back-off, resolves movement, and then—only if `fallDistance != 0` and resolved movement length squared is at least `1.0`—clips downward through blocks tagged `fall_damage_resetting` and water before writing the final position. A has no corresponding reset at this point. Targeted canonical jar tag inspection confirms B's block tag contains the `climbable` tag, sweet berry bush, and cobweb; these members existed in A. This is a conditional later-state writer, not a claim that every edge-gated move resets fall distance. Excluded health/fall-damage production is not being emulated.\n",
      "assertion_preserved": true,
      "detail": "Exact original snapshot verdict; current compact wording does not renew acceptance. Later identity-only revision, where present, remains separate.",
      "snapshot": "e531086eecc778432b40b2b9ef39499b8a7f0dba:workflows/source-campaign-2026-10-07/1.17.1--1.18.2/findings/F-005-fall-distance-reset-edge-gate.md",
      "snapshot_sha256": "6a0803d36f7d7f0f51ebdf9753e5f7adf3863cf8fe7418415ad55b12e6128d73",
      "identity": "4a5eae32b5819418dbd59eb4a763ee24ea1f2c52:development/research/archive/legacy-workflows/source-boundary-reviews/2026-10-08-mc1171-1182-snapshot-review.md"
    },
    {
      "verdict": "REQUEST CHANGES to witness; boundary ACCEPT",
      "assertion": "First changed release 1.18.2 accepted, but post-move WebBlock callback independently resets fallDistance and invalidates r1 consumer witness.",
      "assertion_preserved": false,
      "review_sha256": "a73dd9aaeb03a02faceb67474ca2d31be0dda2083cd14274bfed7b40b07048a0",
      "snapshot": "6702eda57a1f378576fca06778e11648cc0e5c8c",
      "identity": "4a5eae32b5819418dbd59eb4a763ee24ea1f2c52:development/research/archive/legacy-workflows/source-boundary-reviews/2026-10-09-F005-independent-review.md"
    },
    {
      "verdict": "ACCEPT, r2 witness correction",
      "assertion": "Firework/glide segment crosses reset selector, but final bbox excludes cobweb cell; post-move WebBlock writer cannot erase the reset. Original r1 REQUEST CHANGES retained.",
      "assertion_preserved": true,
      "review_sha256": "67ce567f9edc39bef7a9fef511b464ee0bf76abc7fe2a2dbb5ea1d525269e313",
      "snapshot": "96b6f7253491903c0da443d74102dcc451be1245",
      "identity": "4a5eae32b5819418dbd59eb4a763ee24ea1f2c52:development/research/archive/legacy-workflows/source-boundary-reviews/2026-10-09-F005-r2-independent-review.md"
    }
  ],
  "openIssues": [],
  "runtime": "unverified"
}
```
