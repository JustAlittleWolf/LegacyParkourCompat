# F-004: Auto-jump collision candidates omit the old world-border shape

1.17.1, mojmap: `1.17.1/mojmap/net/minecraft/client/player/LocalPlayer.java`, `updateAutoJump`, lines 956–959.
1.18.2, mojmap: `1.18.2/mojmap/net/minecraft/client/player/LocalPlayer.java`, `updateAutoJump`, lines 952–953.
1.17.1, mojmap: `1.17.1/mojmap/net/minecraft/world/level/CollisionSpliterator.java`, `getChunk`, lines 103–116.
1.18.2, mojmap: `1.18.2/mojmap/net/minecraft/world/level/CollisionGetter.java`, `getCollisions`, lines 67–80.

Difference: 1.17.1 auto-jump query may include border shape; 1.18.2 query omits it, with border collision handled elsewhere.

Movement meaning: LocalPlayer#aiStep -> updateAutoJump -> getCollisions; general solver border path open.


## Current evidence and remaining work

Source decisions, implementation coverage and runtime parity are separate. Source paths are relative to `decompiled_minecraft/`; immutable Git evidence can be read with `git show <revision>:<path>`.

```json
{
  "implementation": {
    "status": "open",
    "changes": [],
    "hooks": [],
    "limits": [
      "Current annotated Change/interface/catalog inventory contains no operation implementing: F-004: Auto-jump collision candidates omit the old world-border shape. Existing unrelated later operations do not establish coverage."
    ]
  },
  "applicability": [
    "Endpoint interval (1.17.1, 1.18.2]; exact cutover remains the original evidence’s separate applicability gate."
  ],
  "sources": [
    {
      "version": "1.17.1",
      "mapping": "mojmap",
      "path": "1.17.1/mojmap/net/minecraft/client/player/LocalPlayer.java",
      "member": "updateAutoJump",
      "lines": [
        [
          956,
          959
        ]
      ],
      "status": "verified-content",
      "sha256": "c9a91cb6cb57806bc8d22e5bfe2d97daaf21d5d2a48f34a6e2c53164c61c5812",
      "detail": "Canonical source read. Matches digest in referenced archived run."
    },
    {
      "version": "1.18.2",
      "mapping": "mojmap",
      "path": "1.18.2/mojmap/net/minecraft/client/player/LocalPlayer.java",
      "member": "updateAutoJump",
      "lines": [
        [
          952,
          953
        ]
      ],
      "status": "verified-content",
      "sha256": "99c2d18bcd23243afb8f95c5bafb21fb0be7ea04aacbb14fcf7be7ced2c9c095",
      "detail": "Canonical source read. Matches digest in referenced archived run."
    },
    {
      "version": "1.17.1",
      "mapping": "mojmap",
      "path": "1.17.1/mojmap/net/minecraft/world/level/CollisionSpliterator.java",
      "member": "getChunk",
      "lines": [
        [
          103,
          116
        ]
      ],
      "status": "verified-content",
      "sha256": "c4f8158bd6778159946ebf16c22a6c12f5a4bccb40e2f1215f4fc930bb218bd0",
      "detail": "Canonical source read. Matches digest in referenced archived run."
    },
    {
      "version": "1.18.2",
      "mapping": "mojmap",
      "path": "1.18.2/mojmap/net/minecraft/world/level/CollisionGetter.java",
      "member": "getCollisions",
      "lines": [
        [
          67,
          80
        ]
      ],
      "status": "verified-content",
      "sha256": "ed62e6b800ec057c1269d3cc4e2fb7c0cc2037370223fac99f41371e000c6b5c",
      "detail": "Canonical source read. Matches digest in referenced archived run."
    }
  ],
  "sourceReviews": [
    {
      "verdict": "ACCEPT, bounded",
      "assertion": " A's collision candidate traversal admits/tests the world-border shape in the auto-jump query path; B's shared collision query handles the border through its query-box/border logic. This supports the reported candidate-set distinction for the stated setup. Whether that candidate intersects the auto-jump query at a qualifying height, passes all predicates, and changes the timer remains unresolved. The world border is environmental state, not a historical block; this is not old-map block eligibility.\n",
      "assertion_preserved": true,
      "detail": "Exact original snapshot verdict; current compact wording does not renew acceptance. Later identity-only revision, where present, remains separate.",
      "snapshot": "e531086eecc778432b40b2b9ef39499b8a7f0dba:workflows/source-campaign-2026-10-07/1.17.1--1.18.2/findings/F-004-autojump-border-shape.md",
      "snapshot_sha256": "6a9b9d60301b6670fc4b3e6279bf2f9399d1bb714de9137a79738004935063d2",
      "identity": "4a5eae32b5819418dbd59eb4a763ee24ea1f2c52:development/research/archive/legacy-workflows/source-boundary-reviews/2026-10-08-mc1171-1182-snapshot-review.md"
    }
  ],
  "openIssues": [
    {
      "missing": "First qualifying obstacle geometry/filter/timer consequence remains open despite accepted candidate order/admission operation.",
      "next_action": "Preserve bounded historical acceptance; later close only the named auto-jump dependency before a trajectory claim."
    }
  ],
  "runtime": "unverified"
}
```
