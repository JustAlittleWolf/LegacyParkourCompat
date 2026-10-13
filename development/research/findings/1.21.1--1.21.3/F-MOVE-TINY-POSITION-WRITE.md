# F-MOVE-TINY-POSITION-WRITE — tiny free movement updates position only in 1.21.3

1.21.1, mojmap: `1.21.1/mojmap/net/minecraft/world/entity/Entity.java`, move 598-727; move 598-649; setPos 384-386; setPos 388-395.
1.21.3, mojmap: `1.21.3/mojmap/net/minecraft/world/entity/Entity.java`, move 619-701; move 619-652; setPos 395-397.
1.21.1, mojmap: `1.21.1/mojmap/net/minecraft/world/entity/LivingEntity.java`, aiStep 2586-2681.
1.21.3, mojmap: `1.21.3/mojmap/net/minecraft/world/entity/LivingEntity.java`, aiStep 2690-2779.
1.21.1, mojmap: `1.21.1/mojmap/net/minecraft/client/player/LocalPlayer.java`, tick 191-210.
1.21.3, mojmap: `1.21.3/mojmap/net/minecraft/client/player/LocalPlayer.java`, tick 189-212.
1.21.1, mojmap: `1.21.1/mojmap/net/minecraft/world/entity/player/Player.java`, aiStep 513-535.
1.21.3, mojmap: `1.21.3/mojmap/net/minecraft/world/entity/player/Player.java`, aiStep 543-556.

Difference: For a nonzero movement result with squared length at or below `1.0E-7`, 1.21.1 does not write the entity's new position. If the post-edge-backoff request is collision-free, 1.21.3's expression has a zero difference between requested and actual squared lengths, so its second condition is true and it writes the position even when the actual squared length is at or below `1.0E-7`. More generally, 1.21.3 also writes when the difference `requested.lengthSqr() - actual.lengthSqr()` is below `1.0E-7`, including some small collision-shortened movements. The literal threshold and operation order above are preserved.

Movement meaning: Both ordinary local-player tick paths reach `LivingEntity.travel` and then `Entity.move`; the requested vector is passed through stuck-speed handling, edge backoff and `collide` before the compared condition. `setPos` writes the position and recomputes the bounding box on both versions. The concrete sufficient precondition is a nonzero, unobstructed post-backoff movement whose squared length is at most `1.0E-7`: 1.21.1 skips `setPos`, 1.21.3 calls it. No external input, attribute, effect, equipment, resource or block-provider dependency is needed to establish this bounded branch difference. Source-proven: under the sufficient precondition, 1.21.3 writes the small actual displacement and rebuilds the bounding box; 1.21.1 does not perform that write. This can change the stored position and subsequent position-based queries.


## Current evidence and remaining work

Source decisions, implementation coverage and runtime parity are separate. Source paths are relative to `decompiled_minecraft/`; immutable Git evidence can be read with `git show <revision>:<path>`.

```json
{
  "implementation": {
    "status": "open",
    "changes": [],
    "hooks": [],
    "limits": [
      "No catalog Change implements the operation stated in this record. The current 104 annotated Changes/catalog were checked; unrelated hooks do not close this operation. Registration establishes static correspondence only; no runtime parity or renewed applicability acceptance."
    ]
  },
  "applicability": [],
  "sources": [
    {
      "version": "1.21.1",
      "mapping": "mojmap",
      "path": "1.21.1/mojmap/net/minecraft/world/entity/Entity.java",
      "member": "move",
      "lines": "598-727",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 b81905c7879e2cc5c5063a41d865c4164ad919f156306705be791d1017b99850; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 140c47931cccc8fc9e4c22d7603e2d714d1a953a146f51ea7397d95c955536ec *1.21.1/client_mappings.txt. Canonical source-manifest SHA-256 900f956e00f6fc1300bb3d689ea49df2b1a57bcaa54617344ef456d95d47cb48 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.1",
      "mapping": "mojmap",
      "path": "1.21.1/mojmap/net/minecraft/world/entity/Entity.java",
      "member": "move",
      "lines": "598-649",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 b81905c7879e2cc5c5063a41d865c4164ad919f156306705be791d1017b99850; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 140c47931cccc8fc9e4c22d7603e2d714d1a953a146f51ea7397d95c955536ec *1.21.1/client_mappings.txt. Canonical source-manifest SHA-256 900f956e00f6fc1300bb3d689ea49df2b1a57bcaa54617344ef456d95d47cb48 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.3",
      "mapping": "mojmap",
      "path": "1.21.3/mojmap/net/minecraft/world/entity/Entity.java",
      "member": "move",
      "lines": "619-701",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 a93719c302a0381a972af75ea360465e2e3551708dd07c34d4d40b7e5173c2b9; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: e92a9cb01c233075b51a6233d0c22f56dcda3fcab46438d3f7a1781d104dcd41 *1.21.3/client_mappings.txt. Canonical source-manifest SHA-256 d673bb5464853e3a2a92ed9b1ffe1789d4e62c097bb884f67c336fcbb3b178ce matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.3",
      "mapping": "mojmap",
      "path": "1.21.3/mojmap/net/minecraft/world/entity/Entity.java",
      "member": "move",
      "lines": "619-652",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 a93719c302a0381a972af75ea360465e2e3551708dd07c34d4d40b7e5173c2b9; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: e92a9cb01c233075b51a6233d0c22f56dcda3fcab46438d3f7a1781d104dcd41 *1.21.3/client_mappings.txt. Canonical source-manifest SHA-256 d673bb5464853e3a2a92ed9b1ffe1789d4e62c097bb884f67c336fcbb3b178ce matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.1",
      "mapping": "mojmap",
      "path": "1.21.1/mojmap/net/minecraft/world/entity/Entity.java",
      "member": "setPos",
      "lines": "384-386",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 b81905c7879e2cc5c5063a41d865c4164ad919f156306705be791d1017b99850; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 140c47931cccc8fc9e4c22d7603e2d714d1a953a146f51ea7397d95c955536ec *1.21.1/client_mappings.txt. Canonical source-manifest SHA-256 900f956e00f6fc1300bb3d689ea49df2b1a57bcaa54617344ef456d95d47cb48 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.3",
      "mapping": "mojmap",
      "path": "1.21.3/mojmap/net/minecraft/world/entity/Entity.java",
      "member": "setPos",
      "lines": "395-397",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 a93719c302a0381a972af75ea360465e2e3551708dd07c34d4d40b7e5173c2b9; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: e92a9cb01c233075b51a6233d0c22f56dcda3fcab46438d3f7a1781d104dcd41 *1.21.3/client_mappings.txt. Canonical source-manifest SHA-256 d673bb5464853e3a2a92ed9b1ffe1789d4e62c097bb884f67c336fcbb3b178ce matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.1",
      "mapping": "mojmap",
      "path": "1.21.1/mojmap/net/minecraft/world/entity/Entity.java",
      "member": "setPos",
      "lines": "388-395",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 b81905c7879e2cc5c5063a41d865c4164ad919f156306705be791d1017b99850; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 140c47931cccc8fc9e4c22d7603e2d714d1a953a146f51ea7397d95c955536ec *1.21.1/client_mappings.txt. Canonical source-manifest SHA-256 900f956e00f6fc1300bb3d689ea49df2b1a57bcaa54617344ef456d95d47cb48 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.1",
      "mapping": "mojmap",
      "path": "1.21.1/mojmap/net/minecraft/client/player/LocalPlayer.java",
      "member": "tick",
      "lines": "191-210",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 c555e68ac3c63ab9b4f9a9e31933e263b96350a2bc599d11a0de5928bc24b583; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 140c47931cccc8fc9e4c22d7603e2d714d1a953a146f51ea7397d95c955536ec *1.21.1/client_mappings.txt. Canonical source-manifest SHA-256 900f956e00f6fc1300bb3d689ea49df2b1a57bcaa54617344ef456d95d47cb48 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.1",
      "mapping": "mojmap",
      "path": "1.21.1/mojmap/net/minecraft/world/entity/player/Player.java",
      "member": "aiStep",
      "lines": "513-535",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 ed32b88c3c7c8418b83db41823520f2b6b0b49a98610306ef26e9681dc925c71; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 140c47931cccc8fc9e4c22d7603e2d714d1a953a146f51ea7397d95c955536ec *1.21.1/client_mappings.txt. Canonical source-manifest SHA-256 900f956e00f6fc1300bb3d689ea49df2b1a57bcaa54617344ef456d95d47cb48 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.1",
      "mapping": "mojmap",
      "path": "1.21.1/mojmap/net/minecraft/world/entity/LivingEntity.java",
      "member": "aiStep",
      "lines": "2586-2681",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 324a3eee8496caab57cfaf5101ef576f1ae3c60c40e3857e96f35f3af9a3a0d8; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 140c47931cccc8fc9e4c22d7603e2d714d1a953a146f51ea7397d95c955536ec *1.21.1/client_mappings.txt. Canonical source-manifest SHA-256 900f956e00f6fc1300bb3d689ea49df2b1a57bcaa54617344ef456d95d47cb48 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.3",
      "mapping": "mojmap",
      "path": "1.21.3/mojmap/net/minecraft/client/player/LocalPlayer.java",
      "member": "tick",
      "lines": "189-212",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 fbd40f1f47adfa66dda9b15188e5dce82af3e8e8d7c3dd0543e602a354ad3fe0; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: e92a9cb01c233075b51a6233d0c22f56dcda3fcab46438d3f7a1781d104dcd41 *1.21.3/client_mappings.txt. Canonical source-manifest SHA-256 d673bb5464853e3a2a92ed9b1ffe1789d4e62c097bb884f67c336fcbb3b178ce matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.3",
      "mapping": "mojmap",
      "path": "1.21.3/mojmap/net/minecraft/world/entity/player/Player.java",
      "member": "aiStep",
      "lines": "543-556",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 a803203e92aa4729d5f5c9b16085b6a43ce51d9907d309eb96736e9c7c1340de; archived source-hash equivalence not established. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: e92a9cb01c233075b51a6233d0c22f56dcda3fcab46438d3f7a1781d104dcd41 *1.21.3/client_mappings.txt. Canonical source-manifest SHA-256 d673bb5464853e3a2a92ed9b1ffe1789d4e62c097bb884f67c336fcbb3b178ce matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.3",
      "mapping": "mojmap",
      "path": "1.21.3/mojmap/net/minecraft/world/entity/LivingEntity.java",
      "member": "aiStep",
      "lines": "2690-2779",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 087390495f4fdfd14b9e12230e7aea4fdc50913bb4d1d91119e72892881cfc52; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: e92a9cb01c233075b51a6233d0c22f56dcda3fcab46438d3f7a1781d104dcd41 *1.21.3/client_mappings.txt. Canonical source-manifest SHA-256 d673bb5464853e3a2a92ed9b1ffe1789d4e62c097bb884f67c336fcbb3b178ce matches archived publication; cited file hash is present in that manifest."
    }
  ],
  "sourceReviews": [],
  "openIssues": [],
  "runtime": "unverified"
}
```
