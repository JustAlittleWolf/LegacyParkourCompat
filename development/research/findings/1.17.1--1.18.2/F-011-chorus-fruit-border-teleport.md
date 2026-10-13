# F-011: Chorus-fruit random teleport rejects a candidate already overlapping the border shape in 1.18.2

1.17.1, mojmap: `1.17.1/mojmap/net/minecraft/world/entity/LivingEntity.java`, `isVisuallySwimming`, lines 3020–3070.
1.18.2, mojmap: `1.18.2/mojmap/net/minecraft/world/entity/LivingEntity.java`, `randomTeleport`, lines 3020–3064.

Difference: For a block-supported candidate whose player AABB is otherwise free of block/entity collisions and contains no liquid, choose a border and candidate box that overlaps the outside-of-floor/ceiling border collision shape by more than `1.0E-7`, while the entity center still passes 1.18.2's `isInsideCloseToBorder` proximity guard. 1.17.1's `worldBorderCheck` sees that the deflated AABB already intersects the outside shape (`isOutsideBorder`), so it does not yield that shape; block/entity checks can therefore accept the candidate. 1.18.2 supplies the border shape and `noCollision` rejects the same candidate. For example, with the maximum border at X=10.5 (collision shape begins beyond ceil(X)=11), a width-0.6 player centered at X=10.9 overlaps the border shape while remaining within 1.18.2's reported two-block proximity margin. Chorus fruit tries this method for a player on the server, so a differing first candidate can stop 1.17.1's loop while 1.18.2 continues to a later candidate, or make success/failure differ.

Movement meaning: The direct player destination acceptance is in movement scope.


## Current evidence and remaining work

Source decisions, implementation coverage and runtime parity are separate. Source paths are relative to `decompiled_minecraft/`; immutable Git evidence can be read with `git show <revision>:<path>`.

```json
{
  "implementation": {
    "status": "open",
    "changes": [],
    "hooks": [],
    "limits": [
      "Current annotated Change/interface/catalog inventory contains no operation implementing: F-011: Chorus-fruit random teleport rejects a candidate already overlapping the border shape in 1.18.2. Existing unrelated later operations do not establish coverage."
    ]
  },
  "applicability": [
    "Endpoint interval (1.17.1, 1.18.2]; exact cutover remains the original evidence’s separate applicability gate."
  ],
  "sources": [
    {
      "version": "1.17.1",
      "mapping": "mojmap",
      "path": "1.17.1/mojmap/net/minecraft/world/entity/LivingEntity.java",
      "member": "isVisuallySwimming",
      "lines": [
        [
          3020,
          3070
        ]
      ],
      "status": "verified-content",
      "sha256": "33fd081aadb2b6fdc9ebf487db6da5b38c54f4b8676572790ee2203690d15e6f",
      "detail": "Canonical source read. Matches digest in archived original."
    },
    {
      "version": "1.18.2",
      "mapping": "mojmap",
      "path": "1.18.2/mojmap/net/minecraft/world/entity/LivingEntity.java",
      "member": "randomTeleport",
      "lines": [
        [
          3020,
          3064
        ]
      ],
      "status": "verified-content",
      "sha256": "db4168d531caf18f22e3fefd073365e776da4075ce01452bb9f7671d9b458782",
      "detail": "Canonical source read. Matches digest in archived original."
    }
  ],
  "sourceReviews": [
    {
      "verdict": "ACCEPT, bounded",
      "assertion": " A's collision traversal skips the world-border shape in the identified teleport validation path; B's shared candidate collision query includes the border check. `ChorusFruitItem` generates candidate positions and the server `LivingEntity.randomTeleport` path validates them with `noCollision`, so the reported source-level acceptance difference is reachable. Candidate choice, RNG/food-state production, and final teleport outcome are not accepted. Excluded food/combat production remains out of scope; this report accepts only the player pose-validation consumer conditionally associated with the item path. The border is not a block mechanic.\n",
      "assertion_preserved": true,
      "detail": "Exact original snapshot verdict; current compact wording does not renew acceptance. Later identity-only revision, where present, remains separate.",
      "snapshot": "e531086eecc778432b40b2b9ef39499b8a7f0dba:workflows/source-campaign-2026-10-07/1.17.1--1.18.2/findings/F-011-chorus-fruit-border-teleport.md",
      "snapshot_sha256": "decbf5a6a4f8963efb8d19824c30d96ebeac25c22598e8622858e3fa6bf153e3",
      "identity": "4a5eae32b5819418dbd59eb4a763ee24ea1f2c52:development/research/archive/legacy-workflows/source-boundary-reviews/2026-10-08-mc1171-1182-snapshot-review.md"
    }
  ],
  "openIssues": [],
  "runtime": "unverified"
}
```
