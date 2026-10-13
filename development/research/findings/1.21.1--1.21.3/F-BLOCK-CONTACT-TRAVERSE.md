# F-BLOCK-CONTACT-TRAVERSE — swept player block contact

1.21.1, mojmap: `1.21.1/mojmap/net/minecraft/world/entity/Entity.java`, move 598-727.
1.21.3, mojmap: `1.21.3/mojmap/net/minecraft/world/entity/Entity.java`, isControlledByLocalInstance 3149-3151; applyEffectsFromBlocks 736-738; applyEffectsFromBlocks 736-771.
1.21.1, mojmap: `1.21.1/mojmap/net/minecraft/world/entity/LivingEntity.java`, aiStep 2586-2681.
1.21.3, mojmap: `1.21.3/mojmap/net/minecraft/world/entity/LivingEntity.java`, aiStep 2690-2779.
1.21.1, mojmap: `1.21.1/mojmap/net/minecraft/client/player/LocalPlayer.java`, tick 191-210.
1.21.3, mojmap: `1.21.3/mojmap/net/minecraft/client/player/LocalPlayer.java`, isEffectiveAi 478-480.
1.21.1, mojmap: `1.21.1/mojmap/net/minecraft/world/entity/player/Player.java`, aiStep 513-535.
1.21.3, mojmap: `1.21.3/mojmap/net/minecraft/world/level/block/state/BlockBehaviour.java`, getEntityInsideCollisionShape 353-355.
1.21.3, mojmap: `1.21.3/mojmap/net/minecraft/world/level/BlockGetter.java`, boxTraverseBlocks 172-190.
1.21.3, mojmap: `1.21.3/mojmap/net/minecraft/world/phys/shapes/Shapes.java`, block 42-44.

Difference: For a movement whose player bounding box traverses a cobweb block's inside shape but ends with no overlap, 1.21.1's post-move scan visits only the final bounding-box cells and does not call the callback for that cobweb. 1.21.3's post-travel scan traverses cells along the old-position-to-current-position movement and can call it. The ordinary local player reaches 1.21.3's scan because `LocalPlayer.isEffectiveAi()` returns true. The unchanged cobweb callback then writes a movement multiplier which is consumed by the next `Entity.move`; this is conditional on the path geometry, callback guards and a movement component affected by that multiplier.

Movement meaning: On both sides the local player tick reaches `Player.aiStep`, `LivingEntity.aiStep`, travel and `Entity.move`. 1.21.1 invokes the inside-block callback from each eligible move and scans the post-move box when the current box's chunks are available. 1.21.3 replaces that per-move callback with a post-travel pass from `oldPosition()` to `position`; the server-or-locally-controlled guard passes for `LocalPlayer` because its `isEffectiveAi()` override returns true. 1.21.3's path checker uses the current entity box, path-traversed block positions and each state's inside-collision shape. For COBWEB, the unchanged `WebBlock` callback writes `stuckSpeedMultiplier`, which both versions consume on the next move. This finding is limited to client local-player movement; it makes no claim about server movement equivalence. Source-proven: with the stated path and shape preconditions, 1.21.1's final-box scan omits an intermediate-only cobweb callback while 1.21.3's path traversal can invoke it, and the callback's multiplier is read by the next move. It is a source-level prediction that this changes the next client-predicted movement vector when the multiplier affects a nonzero component.


## Current evidence and remaining work

Source decisions, implementation coverage and runtime parity are separate. Source paths are relative to `decompiled_minecraft/`; immutable Git evidence can be read with `git show <revision>:<path>`.

```json
{
  "implementation": {
    "status": "partial",
    "changes": [
      "src/main/java/me/wolfii/legacyparkourcompat/change/v1_18_2/InsideBlockContact.java",
      "src/main/java/me/wolfii/legacyparkourcompat/change/v1_21/InsideBlockContact.java"
    ],
    "hooks": [
      "InsideBlockContactHook"
    ],
    "limits": [
      "InsideBlockContactHook provides old inset values; it does not implement swept block-cell traversal or entire new callback segment paths. Registration establishes static correspondence only; no runtime parity or renewed applicability acceptance."
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
      "detail": "Canonical file SHA-256 b81905c7879e2cc5c5063a41d865c4164ad919f156306705be791d1017b99850; archived source-hash equivalence not established. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 140c47931cccc8fc9e4c22d7603e2d714d1a953a146f51ea7397d95c955536ec *1.21.1/client_mappings.txt. Canonical source-manifest SHA-256 900f956e00f6fc1300bb3d689ea49df2b1a57bcaa54617344ef456d95d47cb48 matches archived publication; cited file hash is present in that manifest."
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
      "path": "1.21.3/mojmap/net/minecraft/world/entity/LivingEntity.java",
      "member": "aiStep",
      "lines": "2690-2779",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 087390495f4fdfd14b9e12230e7aea4fdc50913bb4d1d91119e72892881cfc52; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: e92a9cb01c233075b51a6233d0c22f56dcda3fcab46438d3f7a1781d104dcd41 *1.21.3/client_mappings.txt. Canonical source-manifest SHA-256 d673bb5464853e3a2a92ed9b1ffe1789d4e62c097bb884f67c336fcbb3b178ce matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.3",
      "mapping": "mojmap",
      "path": "1.21.3/mojmap/net/minecraft/world/entity/Entity.java",
      "member": "isControlledByLocalInstance",
      "lines": "3149-3151",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 a93719c302a0381a972af75ea360465e2e3551708dd07c34d4d40b7e5173c2b9; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: e92a9cb01c233075b51a6233d0c22f56dcda3fcab46438d3f7a1781d104dcd41 *1.21.3/client_mappings.txt. Canonical source-manifest SHA-256 d673bb5464853e3a2a92ed9b1ffe1789d4e62c097bb884f67c336fcbb3b178ce matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.3",
      "mapping": "mojmap",
      "path": "1.21.3/mojmap/net/minecraft/client/player/LocalPlayer.java",
      "member": "isEffectiveAi",
      "lines": "478-480",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 fbd40f1f47adfa66dda9b15188e5dce82af3e8e8d7c3dd0543e602a354ad3fe0; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: e92a9cb01c233075b51a6233d0c22f56dcda3fcab46438d3f7a1781d104dcd41 *1.21.3/client_mappings.txt. Canonical source-manifest SHA-256 d673bb5464853e3a2a92ed9b1ffe1789d4e62c097bb884f67c336fcbb3b178ce matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.3",
      "mapping": "mojmap",
      "path": "1.21.3/mojmap/net/minecraft/world/entity/Entity.java",
      "member": "applyEffectsFromBlocks",
      "lines": "736-738",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 a93719c302a0381a972af75ea360465e2e3551708dd07c34d4d40b7e5173c2b9; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: e92a9cb01c233075b51a6233d0c22f56dcda3fcab46438d3f7a1781d104dcd41 *1.21.3/client_mappings.txt. Canonical source-manifest SHA-256 d673bb5464853e3a2a92ed9b1ffe1789d4e62c097bb884f67c336fcbb3b178ce matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.3",
      "mapping": "mojmap",
      "path": "1.21.3/mojmap/net/minecraft/world/entity/Entity.java",
      "member": "applyEffectsFromBlocks",
      "lines": "736-771",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 a93719c302a0381a972af75ea360465e2e3551708dd07c34d4d40b7e5173c2b9; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: e92a9cb01c233075b51a6233d0c22f56dcda3fcab46438d3f7a1781d104dcd41 *1.21.3/client_mappings.txt. Canonical source-manifest SHA-256 d673bb5464853e3a2a92ed9b1ffe1789d4e62c097bb884f67c336fcbb3b178ce matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.3",
      "mapping": "mojmap",
      "path": "1.21.3/mojmap/net/minecraft/world/level/BlockGetter.java",
      "member": "boxTraverseBlocks",
      "lines": "172-190",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 5d6fde6fef7928603ff97f4a4015efbb48442f9e583f1d0cfe368c5168e01c39; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: e92a9cb01c233075b51a6233d0c22f56dcda3fcab46438d3f7a1781d104dcd41 *1.21.3/client_mappings.txt. Canonical source-manifest SHA-256 d673bb5464853e3a2a92ed9b1ffe1789d4e62c097bb884f67c336fcbb3b178ce matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.3",
      "mapping": "mojmap",
      "path": "1.21.3/mojmap/net/minecraft/world/level/block/state/BlockBehaviour.java",
      "member": "getEntityInsideCollisionShape",
      "lines": "353-355",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 4d92417129eb6def084a67d249d2ff8c7a30a4767fa46e63a045667ea70d5133; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: e92a9cb01c233075b51a6233d0c22f56dcda3fcab46438d3f7a1781d104dcd41 *1.21.3/client_mappings.txt. Canonical source-manifest SHA-256 d673bb5464853e3a2a92ed9b1ffe1789d4e62c097bb884f67c336fcbb3b178ce matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.3",
      "mapping": "mojmap",
      "path": "1.21.3/mojmap/net/minecraft/world/phys/shapes/Shapes.java",
      "member": "block",
      "lines": "42-44",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 ab6c69c27c07c3cfa82e85906888036c34a46188e1bde5663e980dea3f6c2a75; archived source-hash equivalence not established. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: e92a9cb01c233075b51a6233d0c22f56dcda3fcab46438d3f7a1781d104dcd41 *1.21.3/client_mappings.txt. Canonical source-manifest SHA-256 d673bb5464853e3a2a92ed9b1ffe1789d4e62c097bb884f67c336fcbb3b178ce matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.1",
      "mapping": "mojmap",
      "path": "1.21.1/mojmap/net/minecraft/world/level/block/WebBlock.java",
      "member": "entityInside",
      "lines": "26-33",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 ece9f8840481748d2d1542990b442cc9a97d670255d693b85f528fa618f48414; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 140c47931cccc8fc9e4c22d7603e2d714d1a953a146f51ea7397d95c955536ec *1.21.1/client_mappings.txt. Canonical source-manifest SHA-256 900f956e00f6fc1300bb3d689ea49df2b1a57bcaa54617344ef456d95d47cb48 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.3",
      "mapping": "mojmap",
      "path": "1.21.3/mojmap/net/minecraft/world/level/block/WebBlock.java",
      "member": "entityInside",
      "lines": "26-33",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 ece9f8840481748d2d1542990b442cc9a97d670255d693b85f528fa618f48414; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: e92a9cb01c233075b51a6233d0c22f56dcda3fcab46438d3f7a1781d104dcd41 *1.21.3/client_mappings.txt. Canonical source-manifest SHA-256 d673bb5464853e3a2a92ed9b1ffe1789d4e62c097bb884f67c336fcbb3b178ce matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.1",
      "mapping": "mojmap",
      "path": "1.21.1/mojmap/net/minecraft/world/level/block/Blocks.java",
      "member": "class declaration / field initialization",
      "lines": "765-770",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 538ac50c164484ca4c2367a2a6c07cf2faedcac0c4d5117d8817d36c2d1bde6d; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: 140c47931cccc8fc9e4c22d7603e2d714d1a953a146f51ea7397d95c955536ec *1.21.1/client_mappings.txt. Canonical source-manifest SHA-256 900f956e00f6fc1300bb3d689ea49df2b1a57bcaa54617344ef456d95d47cb48 matches archived publication; cited file hash is present in that manifest."
    },
    {
      "version": "1.21.3",
      "mapping": "mojmap",
      "path": "1.21.3/mojmap/net/minecraft/world/level/block/Blocks.java",
      "member": "class declaration / field initialization",
      "lines": "701-706",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 28a66da36ce98800226ec6d8e5fb4904fc7b137322a31f6d7ec822450374e62a; matches an explicit archived source hash. Cited canonical range read; file-specific member/range correspondence checked separately from publication-directory presence. Official exact client-mapping manifest binding: e92a9cb01c233075b51a6233d0c22f56dcda3fcab46438d3f7a1781d104dcd41 *1.21.3/client_mappings.txt. Canonical source-manifest SHA-256 d673bb5464853e3a2a92ed9b1ffe1789d4e62c097bb884f67c336fcbb3b178ce matches archived publication; cited file hash is present in that manifest."
    }
  ],
  "sourceReviews": [],
  "openIssues": [],
  "runtime": "unverified"
}
```
