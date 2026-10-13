# F-FARMLAND-PLAYER-RELOCATION: farmland conversion relocates a landed player differently

1.11.2, ornithe-feather; feather-gen2-1.11.2+build.2-mergedv2.jar, feather-gen2-1.11.2+build.2.tiny: `1.11.2/ornithe-feather/net/minecraft/block/FarmlandBlock.java`, `public Box getShape(BlockState state, WorldView world, BlockPos pos) {`, lines 30-32.
1.11.2, ornithe-feather; feather-gen2-1.11.2+build.2-mergedv2.jar, feather-gen2-1.11.2+build.2.tiny: `1.11.2/ornithe-feather/net/minecraft/block/FarmlandBlock.java`, `private void setDirt(World world, BlockPos pos) {`, lines 71-79.
1.11.2, ornithe-feather; feather-gen2-1.11.2+build.2-mergedv2.jar, feather-gen2-1.11.2+build.2.tiny: `1.11.2/ornithe-feather/net/minecraft/block/FarmlandBlock.java`, `public void onFallenOn(World world, BlockPos pos, Entity entity, float fallDistance) {`, lines 59-69.
1.12.2, ornithe-feather; feather-gen2-1.12.2+build.2-mergedv2.jar, feather-gen2-1.12.2+build.2.tiny: `1.12.2/ornithe-feather/net/minecraft/block/FarmlandBlock.java`, `public Box getShape(BlockState state, WorldView world, BlockPos pos) {`, lines 31-33.
1.12.2, ornithe-feather; feather-gen2-1.12.2+build.2-mergedv2.jar, feather-gen2-1.12.2+build.2.tiny: `1.12.2/ornithe-feather/net/minecraft/block/FarmlandBlock.java`, `setDirt(world, pos);`, lines 52-52.
1.12.2, ornithe-feather; feather-gen2-1.12.2+build.2-mergedv2.jar, feather-gen2-1.12.2+build.2.tiny: `1.12.2/ornithe-feather/net/minecraft/block/FarmlandBlock.java`, `setDirt(world, pos);`, lines 66-66.
1.12.2, ornithe-feather; feather-gen2-1.12.2+build.2-mergedv2.jar, feather-gen2-1.12.2+build.2.tiny: `1.12.2/ornithe-feather/net/minecraft/block/FarmlandBlock.java`, `protected static void setDirt(World world, BlockPos pos) {`, lines 72-80.

Difference: Both versions route a landed entity through `Entity.move` to `checkFallDamage`; with `landed=true` and positive `fallDistance`, the selected support block receives `onFallenOn`.

For farmland, a server-side callback with `fallDistance > 1.5F` passes the random condition for every `nextFloat()` result, and a standing player passes the remaining checks: it is a `LivingEntity`, is a `PlayerEntity`, and its `0.6 x 0.6 x 1.8` volume is `0.648`, greater than `0.512`.

The helper first converts the farmland block to dirt; that block-state transition remains vanilla.

In 1.11.2, `setDirt` queries the dirt block's full collision box and calls `entity.setPosition(entity.x, box.maxY, entity.z)` for returned entities.

Dirt inherits `Block.getShape`, whose `FULL_BLOCK_SHAPE` reaches `pos.y + 1.0`.

In 1.12.2, `setDirt` queries only `SHAPE_ABOVE`, the top `0.0625` of the former farmland block, and computes `d = min(box.maxY - box.minY, box.maxY - entity.getShape().minY)` before calling `entity.teleport(entity.x, entity.y + d + 0.001, entity.z)`.

For a non-spectating standing player whose feet are at the farmland surface (`pos.y + 0.9375`), both entity queries include the player's box. 1.11.2 writes player Y as `pos.y + 1.0`; 1.12.2 computes `d = 0.0625` and writes Y as `pos.y + 1.001`. 1.12.2 also enters the inherited teleport path: it sets `teleported`, resets current/previous/last position through `setPositionAndAngles`, and calls `World.tickEntity(this,false)`.

With `requireLoaded=false`, that call skips `entity.tick()` while running the remaining world/chunk update path. 1.11.2's `setPosition` changes current coordinates and bounding box without those teleport updates.

Movement meaning: The player movement path is `PlayerEntity` (inheriting `Entity.move`) -> landing support `BlockState` selection -> `Entity.checkFallDamage` -> `FarmlandBlock.onFallenOn` -> `FarmlandBlock.setDirt` -> `World.getEntities(null,box)` -> player position update.


## Current evidence and remaining work

Source decisions, implementation coverage and runtime parity are separate. Source paths are relative to `decompiled_minecraft/`; immutable Git evidence can be read with `git show <revision>:<path>`.

```json
{
  "implementation": {
    "status": "partial",
    "changes": [
      "src/main/java/me/wolfii/legacyparkourcompat/change/v1_10_1/FarmlandConversion.java"
    ],
    "hooks": [
      "FarmlandEntityPushHook"
    ],
    "limits": [
      "FarmlandConversion only suppresses native push-up for profiles resolving V1_10_1. It does not implement 1.11.2 setPosition-to-dirt-top or the 1.12.2 teleport(+d+0.001) distinction. Existing class/catalog retained; the claimed relocation revision remains open."
    ]
  },
  "applicability": [],
  "sources": [
    {
      "version": "1.11.2",
      "mapping": "ornithe-feather; feather-gen2-1.11.2+build.2-mergedv2.jar, feather-gen2-1.11.2+build.2.tiny",
      "path": "1.11.2/ornithe-feather/net/minecraft/block/FarmlandBlock.java",
      "member": "public Box getShape(BlockState state, WorldView world, BlockPos pos) {",
      "lines": "30-32",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 09e260528fd78b4bbdbc302726c7ae937fbc49f17b674c4a7ad802e0f7b6f5e8; matches an archived source-file digest. Checked full declaration/body; snippet SHA-256 9203892aa07036ac9d7306823feaac3919c21f4f5d69ea95c189d07076650e7d."
    },
    {
      "version": "1.11.2",
      "mapping": "ornithe-feather; feather-gen2-1.11.2+build.2-mergedv2.jar, feather-gen2-1.11.2+build.2.tiny",
      "path": "1.11.2/ornithe-feather/net/minecraft/block/FarmlandBlock.java",
      "member": "FarmlandBlock#getCollisionShape",
      "lines": null,
      "status": "different",
      "detail": "Checked complete canonical file SHA-256 09e260528fd78b4bbdbc302726c7ae937fbc49f17b674c4a7ad802e0f7b6f5e8 (matches an archived source-file digest); named declaration absent. Named declaration is absent in this checked canonical file; paired existing declaration/range is retained separately, so this is an explicit addition/removal or mapping correspondence limit, not an invented line."
    },
    {
      "version": "1.11.2",
      "mapping": "ornithe-feather; feather-gen2-1.11.2+build.2-mergedv2.jar, feather-gen2-1.11.2+build.2.tiny",
      "path": "1.11.2/ornithe-feather/net/minecraft/block/FarmlandBlock.java",
      "member": "private void setDirt(World world, BlockPos pos) {",
      "lines": "71-79",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 09e260528fd78b4bbdbc302726c7ae937fbc49f17b674c4a7ad802e0f7b6f5e8; matches an archived source-file digest. Checked full declaration/body; snippet SHA-256 e997f1c5b22acd6641e1f91a7b8903a53aeb36701a83c0fcfadccfd0871914ee."
    },
    {
      "version": "1.11.2",
      "mapping": "ornithe-feather; feather-gen2-1.11.2+build.2-mergedv2.jar, feather-gen2-1.11.2+build.2.tiny",
      "path": "1.11.2/ornithe-feather/net/minecraft/block/FarmlandBlock.java",
      "member": "public void onFallenOn(World world, BlockPos pos, Entity entity, float fallDistance) {",
      "lines": "59-69",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 09e260528fd78b4bbdbc302726c7ae937fbc49f17b674c4a7ad802e0f7b6f5e8; matches an archived source-file digest. Checked full declaration/body; snippet SHA-256 4b4e064b370f1c6decd2abd4729b331cbea4961cba7c6594c1281e31b961b19d."
    },
    {
      "version": "1.12.2",
      "mapping": "ornithe-feather; feather-gen2-1.12.2+build.2-mergedv2.jar, feather-gen2-1.12.2+build.2.tiny",
      "path": "1.12.2/ornithe-feather/net/minecraft/block/FarmlandBlock.java",
      "member": "public Box getShape(BlockState state, WorldView world, BlockPos pos) {",
      "lines": "31-33",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 d84c75dae139181b5d58cfa34f3cb77c5b2d4bda63d6ad9c3b52a47350bd0b98; matches an archived source-file digest. Checked full declaration/body; snippet SHA-256 9203892aa07036ac9d7306823feaac3919c21f4f5d69ea95c189d07076650e7d."
    },
    {
      "version": "1.12.2",
      "mapping": "ornithe-feather; feather-gen2-1.12.2+build.2-mergedv2.jar, feather-gen2-1.12.2+build.2.tiny",
      "path": "1.12.2/ornithe-feather/net/minecraft/block/FarmlandBlock.java",
      "member": "FarmlandBlock#getCollisionShape",
      "lines": null,
      "status": "different",
      "detail": "Checked complete canonical file SHA-256 d84c75dae139181b5d58cfa34f3cb77c5b2d4bda63d6ad9c3b52a47350bd0b98 (matches an archived source-file digest); named declaration absent. Named declaration is absent in this checked canonical file; paired existing declaration/range is retained separately, so this is an explicit addition/removal or mapping correspondence limit, not an invented line."
    },
    {
      "version": "1.12.2",
      "mapping": "ornithe-feather; feather-gen2-1.12.2+build.2-mergedv2.jar, feather-gen2-1.12.2+build.2.tiny",
      "path": "1.12.2/ornithe-feather/net/minecraft/block/FarmlandBlock.java",
      "member": "setDirt(world, pos);",
      "lines": "52-52",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 d84c75dae139181b5d58cfa34f3cb77c5b2d4bda63d6ad9c3b52a47350bd0b98; matches an archived source-file digest. Checked full declaration/body; snippet SHA-256 5fb6a4b46ecba631c8bce51f57227d44608f8bf615ca3c9f600985be3d11fe50."
    },
    {
      "version": "1.12.2",
      "mapping": "ornithe-feather; feather-gen2-1.12.2+build.2-mergedv2.jar, feather-gen2-1.12.2+build.2.tiny",
      "path": "1.12.2/ornithe-feather/net/minecraft/block/FarmlandBlock.java",
      "member": "setDirt(world, pos);",
      "lines": "66-66",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 d84c75dae139181b5d58cfa34f3cb77c5b2d4bda63d6ad9c3b52a47350bd0b98; matches an archived source-file digest. Checked full declaration/body; snippet SHA-256 820e27fee4aa096a3873139a52cb13e54ca3504432598848317ba590af26a530."
    },
    {
      "version": "1.12.2",
      "mapping": "ornithe-feather; feather-gen2-1.12.2+build.2-mergedv2.jar, feather-gen2-1.12.2+build.2.tiny",
      "path": "1.12.2/ornithe-feather/net/minecraft/block/FarmlandBlock.java",
      "member": "protected static void setDirt(World world, BlockPos pos) {",
      "lines": "72-80",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 d84c75dae139181b5d58cfa34f3cb77c5b2d4bda63d6ad9c3b52a47350bd0b98; matches an archived source-file digest. Checked full declaration/body; snippet SHA-256 318704e1d39b9e2d01d028a21fe34c92d7aff1ce7233f681edd4d188537a5784."
    },
    {
      "version": "1.12.2",
      "mapping": "ornithe-feather; feather-gen2-1.12.2+build.2-mergedv2.jar, feather-gen2-1.12.2+build.2.tiny",
      "path": "1.12.2/ornithe-feather/net/minecraft/block/FarmlandBlock.java",
      "member": "setDirt(world, pos);",
      "lines": "101-101",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 d84c75dae139181b5d58cfa34f3cb77c5b2d4bda63d6ad9c3b52a47350bd0b98; matches an archived source-file digest. Checked full declaration/body; snippet SHA-256 820e27fee4aa096a3873139a52cb13e54ca3504432598848317ba590af26a530."
    },
    {
      "version": "1.12.2",
      "mapping": "ornithe-feather; feather-gen2-1.12.2+build.2-mergedv2.jar, feather-gen2-1.12.2+build.2.tiny",
      "path": "1.12.2/ornithe-feather/net/minecraft/block/FarmlandBlock.java",
      "member": "setDirt(world, pos);",
      "lines": "109-109",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 d84c75dae139181b5d58cfa34f3cb77c5b2d4bda63d6ad9c3b52a47350bd0b98; matches an archived source-file digest. Checked full declaration/body; snippet SHA-256 820e27fee4aa096a3873139a52cb13e54ca3504432598848317ba590af26a530."
    },
    {
      "version": "1.12.2",
      "mapping": "ornithe-feather; feather-gen2-1.12.2+build.2-mergedv2.jar, feather-gen2-1.12.2+build.2.tiny",
      "path": "1.12.2/ornithe-feather/net/minecraft/block/FarmlandBlock.java",
      "member": "public void onFallenOn(World world, BlockPos pos, Entity entity, float fallDistance) {",
      "lines": "60-70",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 d84c75dae139181b5d58cfa34f3cb77c5b2d4bda63d6ad9c3b52a47350bd0b98; matches an archived source-file digest. Checked full declaration/body; snippet SHA-256 8678f035148ca6184b0e2b8966e94cdd6dfb3a04e3f97a3a1172eeb125e21c9b."
    }
  ],
  "sourceReviews": [],
  "openIssues": [
    {
      "missing": "Later applicability gate: exact introduction of campaign-F-FARMLAND-PLAYER-RELOCATION is unknown within (1.11.2, 1.12.2]",
      "next_action": "Preserve any exact historical verdict for its bounded assertion; verify intervening releases separately before assigning a new implementation range. Formatting alone requires no renewed acceptance."
    },
    {
      "missing": "Exact independent source verdict/snapshot identity for F-FARMLAND-PLAYER-RELOCATION",
      "next_action": "Retain original source-confirmed status only; source-acceptance owner must resolve a preserved independent verdict or review this exact bounded assertion in the later campaign."
    }
  ],
  "runtime": "unverified"
}
```
