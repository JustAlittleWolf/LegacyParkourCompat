

1.19.4, mojmap: `1.19.4/mojmap/net/minecraft/world/entity/Entity.java`, move 585-599.
1.20.1, mojmap: `1.20.1/mojmap/net/minecraft/world/entity/Entity.java`, move 629-643; setOnGround 553-585.
1.20.1, mojmap: `1.20.1/mojmap/net/minecraft/world/entity/player/Player.java`, tick 227-231; tick 228-232.
1.20.1, mojmap: `1.20.1/mojmap/net/minecraft/world/level/CollisionGetter.java`, findSupportingBlock 95-110.

Difference: 1.19.4 derives the movement-factor block position from the entity's center X/Z and bounding-box minimum Y minus 0.5000001. 1.20.1 updates mainSupportingBlockPos after grounded collision using a thin AABB below the entity, then getBlockPosBelowThatAffectsMyMovement uses getOnPos(0.500001F), which takes the stored supporting block's X/Z when present. If no support position is stored, 1.20.1 instead floors position.x, position.y - 0.500001F, and position.z; 1.19.4 still floors position.x, boundingBox.minY - 0.5000001, and position.z. The changed spectator tick path in S1-06 calls setOnGround(false), which clears mainSupportingBlockPos before no-physics movement. Thus the block position feeding jump and speed factor reads can differ due to a selected support block, the empty-support fallback offset, or a state-clear path; the factor formulas themselves retain the same current-block then supporting-block fallback order.

Movement meaning: Entity.move performs the support update after collision on a downward contact. In 1.20.1, Player#tick also reaches setOnGround(false) on the spectator branch; checkSupportingBlock(false, null) clears a previously recorded support position, and the following empty-support lookup uses 1.20.1's position-based fallback. On later movement math, getBlockJumpFactor contributes to the player's ground-jump power, while getBlockSpeedFactor contributes to speed and post-move damping. CollisionGetter#findSupportingBlock enumerates blocks through BlockCollisions under the support AABB. S4-02 must finish the shape/query path and S5-01 must inventory block registrations and factors before naming concrete blocks or terrain outcomes. The source confirms that 1.20.1 can provide a different block-position input to player movement-factor reads when its selected supporting block differs from 1.19.4's center-column position. This finding does not assert a particular block layout, factor value, or observed trajectory; those concrete consequences depend on shape and block-property closure.


## Current evidence and remaining work

Source decisions, implementation coverage and runtime parity are separate. Source paths are relative to `decompiled_minecraft/`; immutable Git evidence can be read with `git show <revision>:<path>`.

```json
{
  "implementation": {
    "status": "partial",
    "changes": [
      "src/main/java/me/wolfii/legacyparkourcompat/change/v1_15_2/GroundFrictionSamplePoint.java"
    ],
    "hooks": [
      "GroundFrictionBlockHook"
    ],
    "limits": [
      "GroundFrictionBlockHook reused at V1_19_4 returns old sampling position. Supporting block speed/jump-factor consumers are distinct and not all represented. Registration establishes static correspondence only; no runtime parity or renewed applicability acceptance."
    ]
  },
  "applicability": [],
  "sources": [
    {
      "version": "1.19.4",
      "mapping": "mojmap",
      "path": "1.19.4/mojmap/net/minecraft/world/entity/Entity.java",
      "member": "move",
      "lines": "585-599",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 3667fee610cbc5f58012e3a8fb8d6c4849f649fb7fe5300595158112d8b4b58b; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 5ef270b938f89cfc77371e0deee9f9044c41d9a373fa11dcb1fd1923272c4606 *1.19.4/client_mappings.txt. Canonical source-manifest SHA-256 6286e325371e085dfee1ab7987e66d4b6d1a984a49b6ec8ea730045cd24cecb3 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.20.1",
      "mapping": "mojmap",
      "path": "1.20.1/mojmap/net/minecraft/world/entity/Entity.java",
      "member": "move",
      "lines": "629-643",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 94b9c3656715de2d61fa9a02ccef164261e50eb13d6090a6f07e58fe9c5b0759; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 67bfc33ef0c7103f002d6ac134f8eddd3d67e2ce45d266e83816d1a2e2c9b01d *1.20.1/client_mappings.txt. Canonical source-manifest SHA-256 858b56764113e591c60d09bc63e4d90f9c1d67a705645f0c793e8b28f2378465 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.20.1",
      "mapping": "mojmap",
      "path": "1.20.1/mojmap/net/minecraft/world/level/CollisionGetter.java",
      "member": "findSupportingBlock",
      "lines": "95-110",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 8e4863afe40705497b36d0a58d8ee11e5103f4e72cb642aa63cc57f57649a130; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 67bfc33ef0c7103f002d6ac134f8eddd3d67e2ce45d266e83816d1a2e2c9b01d *1.20.1/client_mappings.txt. Canonical source-manifest SHA-256 858b56764113e591c60d09bc63e4d90f9c1d67a705645f0c793e8b28f2378465 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.20.1",
      "mapping": "mojmap",
      "path": "1.20.1/mojmap/net/minecraft/world/entity/player/Player.java",
      "member": "tick",
      "lines": "227-231",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 873d82c6f5471d06812929e471d970fd973bfbe21638e4e348a6fcde45637dac; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 67bfc33ef0c7103f002d6ac134f8eddd3d67e2ce45d266e83816d1a2e2c9b01d *1.20.1/client_mappings.txt. Canonical source-manifest SHA-256 858b56764113e591c60d09bc63e4d90f9c1d67a705645f0c793e8b28f2378465 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.20.1",
      "mapping": "mojmap",
      "path": "1.20.1/mojmap/net/minecraft/world/entity/player/Player.java",
      "member": "tick",
      "lines": "228-232",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 873d82c6f5471d06812929e471d970fd973bfbe21638e4e348a6fcde45637dac; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 67bfc33ef0c7103f002d6ac134f8eddd3d67e2ce45d266e83816d1a2e2c9b01d *1.20.1/client_mappings.txt. Canonical source-manifest SHA-256 858b56764113e591c60d09bc63e4d90f9c1d67a705645f0c793e8b28f2378465 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.20.1",
      "mapping": "mojmap",
      "path": "1.20.1/mojmap/net/minecraft/world/entity/Entity.java",
      "member": "setOnGround",
      "lines": "553-585",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 94b9c3656715de2d61fa9a02ccef164261e50eb13d6090a6f07e58fe9c5b0759; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 67bfc33ef0c7103f002d6ac134f8eddd3d67e2ce45d266e83816d1a2e2c9b01d *1.20.1/client_mappings.txt. Canonical source-manifest SHA-256 858b56764113e591c60d09bc63e4d90f9c1d67a705645f0c793e8b28f2378465 matches archived publication; cited file hash is present in that manifest."
    }
  ],
  "sourceReviews": [],
  "openIssues": [],
  "runtime": "unverified"
}
```
