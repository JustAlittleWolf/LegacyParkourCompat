# F-009: Border overlap changes one-axis player collision

1.17.1, mojmap: `1.17.1/mojmap/net/minecraft/world/entity/Entity.java`, `applyPistonMovementRestriction, collide`, lines 749–758, 803–816.
1.18.2, mojmap: `1.18.2/mojmap/net/minecraft/world/entity/Entity.java`, `collide, collideBoundingBox`, lines 777–801, 803–817.

Difference: For a player already overlapping the east outside-border region, with no block or entity obstacles, request a one-axis move north across the north boundary. 1.17.1 sees the current-box overlap and omits the entire border shape; its scalar collision path has no border candidate and returns the requested movement. 1.18.2's near-border guard passes for the swept box, includes the outside-border shape, and clips the northward movement at the north boundary. The VoxelShape collision kernel and axis arithmetic match across versions. This changes the returned movement vector and resulting player position. 1.17.1 reachable setup is a world-border size or center change that places the border across a stationary player; `WorldBorder#setSize` and `#setCenter` replace/update the extent without relocating entities (1.17.1 lines 97-125; 1.18.2 lines 109-136). The collision precondition then persists into the player's next ordinary movement.

Movement meaning: The path is the normal non-zero `Entity#move` collision call used by Player.


## Current evidence and remaining work

Source decisions, implementation coverage and runtime parity are separate. Source paths are relative to `decompiled_minecraft/`; immutable Git evidence can be read with `git show <revision>:<path>`.

```json
{
  "implementation": {
    "status": "open",
    "changes": [],
    "hooks": [],
    "limits": [
      "Current annotated Change/interface/catalog inventory contains no operation implementing: F-009: Border overlap changes one-axis player collision. Existing unrelated later operations do not establish coverage."
    ]
  },
  "applicability": [
    "Endpoint interval (1.17.1, 1.18.2]; exact cutover remains the original evidence’s separate applicability gate."
  ],
  "sources": [
    {
      "version": "1.17.1",
      "mapping": "mojmap",
      "path": "1.17.1/mojmap/net/minecraft/world/entity/Entity.java",
      "member": "applyPistonMovementRestriction, collide",
      "lines": [
        [
          749,
          758
        ],
        [
          803,
          816
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
      "member": "collide, collideBoundingBox",
      "lines": [
        [
          777,
          801
        ],
        [
          803,
          817
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
      "assertion": " In A, `Entity.collide` omits the border collision shape when the current box overlaps the border; B's swept collision query can include that shape when the queried region is near it. A player whose position is established before border size/center changes supplies a reachable overlapping-box state, and an orthogonal attempted move is a valid player input. The accepted claim is collision-candidate inclusion and axis-resolution input; no claim is made that every such move changes the final pose. Border configuration is not a block-state mechanic.\n",
      "assertion_preserved": true,
      "detail": "Exact original snapshot verdict; current compact wording does not renew acceptance. Later identity-only revision, where present, remains separate.",
      "snapshot": "e531086eecc778432b40b2b9ef39499b8a7f0dba:workflows/source-campaign-2026-10-07/1.17.1--1.18.2/findings/F-009-border-overlap-axis-collision.md",
      "snapshot_sha256": "c54aa0c02915c6ba7853a5dec58f0a8d360f27d65510501f0d746254703c1c21",
      "identity": "4a5eae32b5819418dbd59eb4a763ee24ea1f2c52:development/research/archive/legacy-workflows/source-boundary-reviews/2026-10-08-mc1171-1182-snapshot-review.md"
    }
  ],
  "openIssues": [],
  "runtime": "unverified"
}
```
