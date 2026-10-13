# F-08: The 1.11.2 farmland collapse raises intersecting players to the dirt top

1.10.2, ornithe-feather; feather-gen2-1.10.2+build.2-mergedv2.jar, feather-gen2-1.10.2+build.2.tiny: `1.10.2/ornithe-feather/net/minecraft/block/FarmlandBlock.java`, `public Box getShape(BlockState state, WorldView world, BlockPos pos) {`, lines 32-34.
1.10.2, ornithe-feather; feather-gen2-1.10.2+build.2-mergedv2.jar, feather-gen2-1.10.2+build.2.tiny: `1.10.2/ornithe-feather/net/minecraft/block/FarmlandBlock.java`, `public void onFallenOn(World world, BlockPos pos, Entity entity, float fallDistance) {`, lines 61-71.
1.11.2, ornithe-feather; feather-gen2-1.11.2+build.2-mergedv2.jar, feather-gen2-1.11.2+build.2.tiny: `1.11.2/ornithe-feather/net/minecraft/block/FarmlandBlock.java`, `public Box getShape(BlockState state, WorldView world, BlockPos pos) {`, lines 30-32.
1.11.2, ornithe-feather; feather-gen2-1.11.2+build.2-mergedv2.jar, feather-gen2-1.11.2+build.2.tiny: `1.11.2/ornithe-feather/net/minecraft/block/FarmlandBlock.java`, `private void setDirt(World world, BlockPos pos) {`, lines 71-79.
1.11.2, ornithe-feather; feather-gen2-1.11.2+build.2-mergedv2.jar, feather-gen2-1.11.2+build.2.tiny: `1.11.2/ornithe-feather/net/minecraft/block/FarmlandBlock.java`, `public void onFallenOn(World world, BlockPos pos, Entity entity, float fallDistance) {`, lines 59-69.

Difference: Both endpoints register farmland as block ID 60 and invoke `FarmlandBlock.onFallenOn` when `Entity.move` lands with positive accumulated fall distance.

The collapse guard is the same: server world, `random.nextFloat() < fallDistance - 0.5F`, a `LivingEntity`, a player or mob-griefing permission, and `width * width * height > 0.512F`.

For a normal player with fall distance `2.0F`, the random comparison is always true because `nextFloat()` is below `1.0F`.

1.10.2 replaces the farmland block with dirt and then follows the base fall callback. 1.11.2 performs the same block replacement in `setDirt`, obtains the new dirt block's full collision box, queries non-spectator entities intersecting that box, and calls `setPosition(entity.x, box.maxY, entity.z)` on each. 1.10.2 player landing at the center of the farmland block has its feet at the farmland collision top, `posY + 0.9375`; its box intersects the new dirt box up to `posY + 1.0`, so 1.11.2 includes that player and writes its y coordinate to `posY + 1.0`.

Movement meaning: The local/server player movement path clips downward movement, sets `onGround`, reads the landed block at `floor(y - 0.2F)`, and calls `checkFallDamage`.

With positive fall distance, `checkFallDamage` dispatches to the landed block's `onFallenOn`. 1.10.2 player landing on registered farmland reaches the unchanged collapse guard. 1.11.2's dirt-box query includes intersecting non-spectator players and directly updates their position; `Entity.setPosition` rebuilds the bounding box from that new y coordinate.

The source proves an immediate server-side player y-position write to the new dirt top in 1.11.2 for the stated conditions; 1.10.2 makes no corresponding position write.

This finding covers only that direct movement response.

The adjacent fall-damage call remains excluded from the campaign's damage simulation scope.


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
      "FarmlandConversion suppresses player push-up at the farmland conversion context, matching the pre-1.11 absence of relocation at the response operation level. Current context/mixins are client-only; ordinary historical collapse/position relocation in the finding is server-triggered. This static class/catalog correspondence does not establish a reachable client response or later 1.11/1.12 relocation behavior."
    ]
  },
  "applicability": [],
  "sources": [
    {
      "version": "1.10.2",
      "mapping": "ornithe-feather; feather-gen2-1.10.2+build.2-mergedv2.jar, feather-gen2-1.10.2+build.2.tiny",
      "path": "1.10.2/ornithe-feather/net/minecraft/block/FarmlandBlock.java",
      "member": "public Box getShape(BlockState state, WorldView world, BlockPos pos) {",
      "lines": "32-34",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 63d9048ebed65b890c370a1da6e79733904f67d953aff9fd8c0316f97f9d4e7f; matches an archived source-file digest. Checked full declaration/body; snippet SHA-256 9203892aa07036ac9d7306823feaac3919c21f4f5d69ea95c189d07076650e7d."
    },
    {
      "version": "1.10.2",
      "mapping": "ornithe-feather; feather-gen2-1.10.2+build.2-mergedv2.jar, feather-gen2-1.10.2+build.2.tiny",
      "path": "1.10.2/ornithe-feather/net/minecraft/block/FarmlandBlock.java",
      "member": "FarmlandBlock#getCollisionShape",
      "lines": null,
      "status": "different",
      "detail": "Checked complete canonical file SHA-256 63d9048ebed65b890c370a1da6e79733904f67d953aff9fd8c0316f97f9d4e7f (matches an archived source-file digest); named declaration absent. Named declaration is absent in this checked canonical file; paired existing declaration/range is retained separately, so this is an explicit addition/removal or mapping correspondence limit, not an invented line."
    },
    {
      "version": "1.10.2",
      "mapping": "ornithe-feather; feather-gen2-1.10.2+build.2-mergedv2.jar, feather-gen2-1.10.2+build.2.tiny",
      "path": "1.10.2/ornithe-feather/net/minecraft/block/FarmlandBlock.java",
      "member": "FarmlandBlock#setDirt",
      "lines": null,
      "status": "different",
      "detail": "Checked complete canonical file SHA-256 63d9048ebed65b890c370a1da6e79733904f67d953aff9fd8c0316f97f9d4e7f (matches an archived source-file digest); named declaration absent. Named declaration is absent in this checked canonical file; paired existing declaration/range is retained separately, so this is an explicit addition/removal or mapping correspondence limit, not an invented line."
    },
    {
      "version": "1.10.2",
      "mapping": "ornithe-feather; feather-gen2-1.10.2+build.2-mergedv2.jar, feather-gen2-1.10.2+build.2.tiny",
      "path": "1.10.2/ornithe-feather/net/minecraft/block/FarmlandBlock.java",
      "member": "public void onFallenOn(World world, BlockPos pos, Entity entity, float fallDistance) {",
      "lines": "61-71",
      "status": "verified-content",
      "detail": "Canonical file SHA-256 63d9048ebed65b890c370a1da6e79733904f67d953aff9fd8c0316f97f9d4e7f; matches an archived source-file digest. Checked full declaration/body; snippet SHA-256 c89a508abe171f6eeec366cf2c61d46a99fe93fe3628c4a16abb48c029db3c58."
    },
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
    }
  ],
  "sourceReviews": [],
  "openIssues": [
    {
      "missing": "Later applicability gate: exact introduction of campaign-F-08-farmland-landing-player-position is unknown within (1.10.2, 1.11.2]",
      "next_action": "Preserve any exact historical verdict for its bounded assertion; verify intervening releases separately before assigning a new implementation range. Formatting alone requires no renewed acceptance."
    },
    {
      "missing": "Exact independent source verdict/snapshot identity for F-08-farmland-landing-player-position",
      "next_action": "Retain original source-confirmed status only; source-acceptance owner must resolve a preserved independent verdict or review this exact bounded assertion in the later campaign."
    },
    {
      "missing": "Exact independent source verdict/snapshot identity for F004",
      "next_action": "Retain original source-confirmed status only; source-acceptance owner must resolve a preserved independent verdict or review this exact bounded assertion in the later campaign."
    }
  ],
  "runtime": "unverified"
}
```
