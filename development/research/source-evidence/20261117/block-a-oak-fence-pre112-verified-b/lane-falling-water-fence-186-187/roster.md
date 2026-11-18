# Source roster: falling-water fence and player gates

Pair: Minecraft 1.8.6 → 1.8.7
Mappings: Ornithe Feather (`feather-gen2`), each side's exact build.2 artifact
Lane scope: source-only; the two assigned falling-water operations below

## Published source inputs

- 1.8.6 client: `https://launcher.mojang.com/v1/objects/faa55e34ded35089d34fe921ea83d317fc152e93/client.jar`, SHA-256 `e597384cf964950b02650a39f73c2cbf5ce9e51d934cdfa1291530896e7cf100`
- 1.8.6 Feather mapping: `feather-gen2-1.8.6+build.2-mergedv2.jar`, SHA-256 `d1507e3253a944d59dd0b924b6495deb7732ecadb5f8bd06f7139c241639ad81`
- 1.8.7 client: `https://launcher.mojang.com/v1/objects/d546a6a092060c85f1eb1d9213ff823c558b1255/client.jar`, SHA-256 `2e45066ec194c6daec5f7dbb27822ef0b5855d5829f62392906620145689a3e0`
- 1.8.7 Feather mapping: `feather-gen2-1.8.7+build.2-mergedv2.jar`, SHA-256 `e0cd7842acda42ae23d2e9d031358c1faac1ac01a139bb29ac8034fbaa5fa27b`
- Source manifest: `decompiled_minecraft/<version>/ornithe-feather/.decompile-provenance.json`; SHA-256 1.8.6 `c96f635864596f6cbd6e34ba7ba184c22a8139b25f099cc8fbbff46a85286ea9`; 1.8.7 `6c6a5b64243ae7931ea1fa72a6c33440d5eec4070461cba80cdf51bf95dd93ef`.

Each hidden manifest records exact client, Feather mapping, version metadata, tool/runtime inputs and source-file hashes. All 1,612 source entries per side were checked against their files; mismatch count was zero. Relevant complete source files are byte-identical between releases.

## Assigned operations and source roster

1. Dry legacy oak fence as a horizontal wall cue in falling-water flow, checking same-height and upper-adjacent cells:
   - `net/minecraft/block/Block.java`: `isFaceSolid` (329–331), default solid-material predicate.
   - `net/minecraft/block/FenceBlock.java`: `addCollisions` (36–82), `updateShape` (85–112), `shouldConnectTo` (128–136); fence does not override `isFaceSolid`.
   - `net/minecraft/block/LiquidBlock.java`: `isFaceSolid` (74–82), `getFlow` (129–160), `applyMaterialDrag` (164–165).
   - `net/minecraft/block/Block.java`: vanilla oak fence registration (1017–1021), constructed with `Material.WOOD`.
   - `net/minecraft/block/material/Material.java`: `WOOD` declaration (7), `isSolid` (60–62).

2. Water current application, loaded-area and player gates, traced to the local client player:
   - `net/minecraft/world/World.java`: area-loaded overloads (179–215), `applyLiquidDrag` (1486–1527).
   - `net/minecraft/block/Block.java`: default `applyMaterialDrag` (592–594).
   - `net/minecraft/block/LiquidBlock.java`: flow and material-drag methods listed above.
   - `net/minecraft/entity/Entity.java`: tick call (300), `checkWaterCollisions` (746–759), default `hasLiquidCollision` (1753–1755).
   - `net/minecraft/entity/living/LivingEntity.java`: `checkFallDamage` water check (151–171).
   - `net/minecraft/entity/living/player/PlayerEntity.java`: `hasLiquidCollision` (1617–1619).
   - `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`: `tick` loaded-chunk gate and `super.tick()` (105–115).

Coverage is limited to these two assigned operations and their stated caller/dependency paths. It is not a full-pair movement inventory or pair freeze.