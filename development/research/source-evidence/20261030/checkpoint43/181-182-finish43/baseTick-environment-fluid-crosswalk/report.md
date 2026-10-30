# 1.8.1–1.8.2 base-tick environment and fluid consumer crosswalk

## Verdict

**Close this residual crosswalk; no movement-semantic source delta found in the paired base-tick/fluid consumers.** The direct player water-state/current path, water/lava predicates, lava fall-distance response, and collision-position liquid predicate are paired below through their concrete consumers. Flowing/source-liquid tick methods produce world block states and are excluded under the pair's equal-vanilla-state assumption; they are not an unresolved player-movement consumer. This bounded report does not alone decide pair-wide coverage.

## Exact source identity

Read-only roots:

- `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.8.1\ornithe-feather`
- `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.8.2\ornithe-feather`

The sources are Feather Gen2 `1.8.1+build.2` / `1.8.2+build.2`, pipeline `lpc-decompile-v1`. Client SHA-256: `66a749ad95adcb929439155c0341309e89e437444867e108dcb36fd16493b0bd` / `5cc5b24312f3d182bf06f7e1a4da3a05ce94be6c861d5c9995c86e6d8811fbe9`. Mapping artifact SHA-256: `dac40cbd25c4d76d99d4263fd9754ab0195efd658756b6828fd06f0c4a8002f7` / `8235d85a8471d541f2bad73c2a60c6132a9432fe804b98369982fc8de847f37b`. Provenance manifest SHA-256: `f3a3baa039f0e0ade6e656dfa11a4529909a4b709ac489703c4250262cefb1ca` / `8ad11ea36f2dda39ae30d57daa53316a8c0889a2f3c0c84ee5d79b9b21c25472`.

For each paired file below I recomputed the on-disk SHA-256 and matched it to the file's own `sources` entry in its exact version manifest. Hashes are lower-case SHA-256.

| Paired role source | 1.8.1 SHA-256 | 1.8.2 SHA-256 |
|---|---|---|
| `net/minecraft/entity/Entity.java` | `aa359621cf9348e5ce4bcb9e28b67f8a5c75d7e5a3d554c8d93471c79540a55c` | `d4c10932cb5bb1067a5a58be4e1bb1b42bdde3b6fc893d88178cc07435a1696b` |
| `net/minecraft/world/World.java` | `f0873207c5c7a793189597470392fa0184513b3c5a682a6f8795e1c9ffde9f71` | `3c04f5b874fbb6692039164c882a3c47fe28c7eca864e91c9ac57798153888ee` |
| `net/minecraft/block/Block.java` | `4f645cb1f25248ba16649d9d1ab17b3dbe4c7316191deb982f641beff437e67c` | `ea10f05106a3cf7189aec85236a7ecf9c7106a717f37bed583b5ea4adeffa528` |
| `net/minecraft/block/LiquidBlock.java` | `36d345c4b0985902ed265602e391982cdc13bc447906e219d3efe568aed4676a` | `1537fd6b1bca65e0f910f9a12bcd3bb27f5a86be7f13a1caa380c5e66544f1d5` |
| `net/minecraft/block/FlowingLiquidBlock.java` | `d52c2ca1f9d145960da1342f2b74458a4327e0803d0d85a07c715d3079e57f8c` | `16b30954bbb5bc9b40072ad19304a7efe8ac2483ee1a4baeda777fb4145daaf0` |
| `net/minecraft/block/LiquidSourceBlock.java` | `e4ba29eb92a7c777fa294c2af278d5ea6b440681b39785a38dc9d30918995e39` | `2b263d3dad30022c81c53d7682791447e4c8a22fa22c01f4e3c1ce84f8c49568` |
| `net/minecraft/util/math/Direction.java` | `8d076a0c15e6bdcc8c96dede16aa85c1545512176bc67a072ed920ae0d1513e7` | `ede4dc29795263853c048e569f24e74407255767c5e02349a90895ac5b08e544` |
| `net/minecraft/util/math/Direction__Plane.java` → nested `Direction.Plane` in `Direction.java` | `4ce0c6f0b0dcb41b7cb05f85c68763b0bf1504e660ec6f9c47a64e5eae3dd01d` | enclosing `Direction.java` above |
| `net/minecraft/entity/living/LivingEntity.java` | `fbfb0f0821a2d6b9d1d665387b2c5423fc0c1d8f5f36f7b5e1258b26d90c4c4f` | `082831c6578e3a70fa6cea5b90bc3eefc26678259b66334470de22b90b5b0e4e` |
| `net/minecraft/entity/living/player/PlayerEntity.java` | `6c24523140f31d588214da9c9ce15ac7999446e1b647fc64194e695ebbd30d8e` | `e66cb294fc93118148a444bbafdf4dd57cbf66a23d69b1e8892cefccc690ab88` |
| `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `42a92a74e5c64992572760196a2052273d96bc061cd9d1120045ba03532f1e2c` | `1762b116e6b06d682b7daaa0fc8cce39b0ff455b3db8cf79dab03ac74f6c4053` |

## Paired role/member crosswalk

Line references are source navigation. Equivalent local-variable names and generated nested-class paths are joined by role, not treated as behavior changes.

| Tick role / member | 1.8.1 → 1.8.2 member and route | Paired result |
|---|---|---|
| Client tick dispatch | `Minecraft.tick` calls `world.tickEntities()` before `world.tick()` (1599/1615 → 1634/1650); `World.tickEntities` visits the local player through `tickEntity`; `LocalClientPlayerEntity.tick` gates its superclass tick on the player chunk being loaded and then emits movement state (111–120 → 105–114). | Same eligible client-player phase order and loaded-player gate as the hash-verified tick inventory. |
| Entity base tick entry | `Entity.tick` calls `baseTick` (249–250 → 245–246). `Entity.baseTick` (253–337 → 249–333) keeps the same ordering: snapshot prior movement fields; server-only portal timer; sprint particles; water collision/current update; client fire-timer reset or server fire countdown; lava test and lava response/fall-distance halving; void handling; server fire flag; `firstTick=false`. | Full paired environment body checked. Gates, constants, casts, and state-write order match. No base-tick delta. |
| Living/player base tick | `LivingEntity.baseTick` calls `super.baseTick` before living-specific state work (179 onward in both); underwater/breath/damage logic follows and is not player locomotion. `LivingEntity.tick`/movement branch reads `isInWater` and `isInLava`; `PlayerEntity.tick` is in the same inherited chain. | Water/lava locomotion consumers were separately rechecked below. Breath, oxygen, damage and exhaustion production are excluded; no movement effect remains open in this route. |
| Water contact cache | `Entity.checkWaterCollisions` (750–764 → 746–760) calls `World.applyLiquidDrag` using `shape.grown(0,-0.4F,0).contract(0.001,0.001,0.001)`. A hit optionally splashes, clears fall distance, sets `inWater`, and clears fire timer; a miss clears `inWater`. | Same query box, call order, gates and writes. This cached `inWater` value is the direct gate consumed by living/player water movement. |
| Water current/loaded-region scan | `World.applyLiquidDrag` (1468–1509 → 1486–1527) uses identical floor bounds (`min`, `floor(max+1)`), `isAreaLoaded(..., true)` early return, half-open XYZ scan order, material equality, liquid-surface threshold, and per-block `applyMaterialDrag` accumulation. It normalizes only a nonzero sum and when `entity.hasLiquidCollision()`, then adds each component times `0.014`. `Block.applyMaterialDrag` is identity; `LiquidBlock.applyMaterialDrag` adds `getFlow`. | No delta in scan, float/double operations, loop order, early return, or velocity writes. |
| Flow direction and depth roles | `LiquidBlock.getHeightLoss` (42–48 → 41–47), `getLiquidState`/`getLiquidDepth` (50–57 → 49–56), `isFaceSolid` (75–83 → 74–82), and `getFlow`/`applyMaterialDrag` (130–167 → 129–166) preserve constants, neighbor coordinates, state tests, ordered vector additions, normalization and falling-liquid side-wall check. Old `Direction__Plane.HORIZONTAL.get()` returns `[NORTH,EAST,SOUTH,WEST]` (15); new nested `Direction.Plane.HORIZONTAL.get()` returns the identical array (315). | The path rename from flattened helper to nested enum preserves iteration order and contents. No liquid-current difference. |
| Player current admission | `Entity.hasLiquidCollision()` returns true; `PlayerEntity.hasLiquidCollision()` returns `!abilities.flying` (1615–1617 → 1617–1619). | Same player flight gate controls current addition. |
| Water/lava travel consumer | `LivingEntity.moveRelative` (1121 onward → 1119 onward) consumes the cached `isInWater` / `isInLava` predicates with the same local-control, player-flight, friction, input and current/velocity math. `LivingEntity.mobTick` keeps the same `jumpInWater`, `jumpInLava`, otherwise grounded-jump selection (1435–1445 → 1433–1443); the paired `jumpInWater` and `jumpInLava` members (1113–1118 → 1111 onward) preserve their velocity updates. | Source bodies and enclosing gates match for the fluid-related branches. No delta in player movement response. |
| Lava and water surface predicates | `Entity.isSubmergedIn` (829–842 → 825–838) samples eye position and one block state; computes `getHeightLoss(metadata)-0.11111111F`; compares strictly; and preserves the Player special case. `Entity.isInLava` (844–846 → 840–842) calls `World.containsMaterial` over `shape.grown(-0.1F,-0.4F,-0.1F)`. `World.containsMaterial` (1511–1531 → 1529–1549) uses identical half-open scanned bounds and material equality. | Same queries and gates. `isSubmergedIn` callers in the player path are mining/stat accounting or living breath state; no omitted local-player travel consumer was found. |
| Lava fall response | The lava branch in `Entity.baseTick` invokes `takeLavaDamage()` and then halves `fallDistance`. The method `takeLavaDamage` (343–347 → 339–343) has the same immunity check, `takeDamage(LAVA,4.0F)`, and `setOnFireFor(15)` order. | The fall-distance write is movement state and was directly checked: unchanged. Damage/fire arithmetic is outside scope. |
| Position validity / liquid query | `Entity.doesNotCollide` (371–373 → 367–369) combines `World.getCollisions` with `!World.containsLiquid(Box)`. `World.containsLiquid(Box)` (1420–1441 → 1438–1459) scans inclusive `floor(min..max)` bounds and checks liquid material; its paired loop is equivalent. | Paired direct placement/collision predicate; no delta. `containsLiquid(Box,Material)` (1533–1563 → 1551–1581) has the same per-material level-height test; checked but its local callers are boat/fishing-bobber entity physics, excluded for this player-only path. |
| Dynamic liquid-state producers | `FlowingLiquidBlock.tick` and `LiquidSourceBlock` spread/schedule methods exist in both; they write vanilla world block states. | State production is not replayed as historical server physics. For this comparison the vanilla block states/raw inputs are equal; these methods are therefore not an open movement-consumer dependency. Their inputs are not used to infer a resource/tag blocker. |

## Scope and exclusions

`Entity.baseTick` still calls water checks on the client; those checks are not incorrectly classified as server-only. The client water-current response, flying suppression, cached-water movement gates, lava predicate and movement fall-distance write above are in scope and paired. The server-only parts of portal advancement, fire damage and block-state production remain excluded. Player breath/health/food production, visual splash/fire particles, movement statistics and underwater mining-speed checks do not alter the local player movement result under the project exclusions.

Equal vanilla block-state inputs are the comparison assumption. 1.8.x predates block tags; no block-tag absence gate is used. No resource or generic producer-proof flag is a remaining dependency for this named fluid consumer.

## Checks

Static source review only. No product/wiki/history/backlog material, source generation, tests, builds, or game launches were used. The original source trees were not modified.
