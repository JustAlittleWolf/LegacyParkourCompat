# Water-current source comparison: 1.9.4 → 1.10.2

## Bounded coverage

Compared the exact decompiled Ornithe Feather sources for (1) the water-query box and liquid-cell admission, (2) per-cell water-flow vectors and aggregate velocity update, and (3) the local client player's on-foot, non-flying tick reachability/order. This is a bounded operation comparison, not full-pair coverage or a pair freeze. No source difference affecting these operations was found.

## Evidence identity and integrity

Both release roots identify `ornithe-feather` and exact releases in `ornithe-feather.provenance.json`, `ornithe-feather.ready.json`, and `ornithe-feather.verification.json`. Mapping artifacts are Feather Gen2 build.2 for the matching release (`feather-gen2-<version>+build.2-mergedv2.jar` and `.tiny`). Actual `artifacts.sha256` hashes match the ready-marker artifact-manifest hashes: 1.9.4 manifest `9527dca544694daa3b4a7741be1a5b4802d8b6665c8a152a408a37a27a1b4d77`, mergedv2 JAR `49a38d0adfbda1749e519c29844116e9f22e895cb505633261b7f587268f4125`, Tiny `9e21708d4bc32a43ac404735ea3238465889797110204a0375bcb069a3798027`; 1.10.2 manifest `6b402f3e6d6cf2f7b3647806364ff44214c47348fefd6949fad03e2011379116`, mergedv2 JAR `18cffc56c2d8de89b50cb0328b174566236553c4aafb62afdc58a1e0ff0cadb0`, Tiny `7c4055aa9becb027462fe4f4a9af8822de4df9b70ecb80a84e38fd9e81e33af1`. Source manifests contain 1,819 files for 1.9.4 and 1,845 for 1.10.2; ready markers' manifest SHA-256 values match the actual manifest hashes. Independently recomputed SHA-256 for every manifest-listed source file: 0 missing/mismatched; manifest counts equal actual Java source-file counts. The published-source verification sidecars also report zero source differences and zero missing published files. Their one artifact difference per release is only the regenerated intermediate mapped client jar; both sidecars report no raw input artifact differences and matching published source trees.

Relevant source file hashes (SHA-256):

- 1.9.4 `net/minecraft/world/World.java`: `2fe063e0ec224eed9fc7d01b8f788c32035eed5fee5a9d98ea5e82296236e05a`
- 1.10.2 `net/minecraft/world/World.java`: `888ed0e9de765def87b05c4126ecdf0b10e9dd448b4543dd1cb98211e9951646`
- 1.9.4 `net/minecraft/entity/Entity.java`: `bcd7fa2206bf8d7271102f6da7fe2771f96dbe22ec79dab9f2101a3e29c17ef0`
- 1.10.2 `net/minecraft/entity/Entity.java`: `05da145effa19a6ef7934cc276e89226373b67c12f4ce89a8ce2183f29039f77`
- `net/minecraft/block/LiquidBlock.java` both releases: `b988d19e379d01d291cb014cb05e1579311c589654ce9ff4faa7f43bbba0533e`
- 1.9.4 `net/minecraft/entity/living/LivingEntity.java`: `bbb7703f18fd5da05c4e4a43a77ea644b388e63c01d34166d308ea52054be4e5`
- 1.10.2 `net/minecraft/entity/living/LivingEntity.java`: `d40dd476b6b68c6ce45b4202823475deb546ecda2284da330ff6724b33815e82`
- 1.9.4 `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`: `8aaf711948b7602c2e6c015a37e36ed06073d39727d999d80480b4910b704f5d`
- 1.10.2 `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`: `a9637065f21ad67464eb5c204c74ebf228c3bb0da8a96ddf4ae73c0490fed443`
- 1.9.4 `net/minecraft/client/world/ClientWorld.java`: `8bbda6e31e1424bf65b89a0c35110f213841695766870cf526bf6e707f67a74b`
- 1.10.2 `net/minecraft/client/world/ClientWorld.java`: `00bc522817863345c535cd438c9be994f8ecfdeaf3e3c9c7c57d517287e8515c`
- 1.9.4 `net/minecraft/entity/living/player/PlayerEntity.java`: `d658a0d95452d12bb7e347bfd802240eeecaf7f938e10dcd43e2640434387f85`
- 1.10.2 `net/minecraft/entity/living/player/PlayerEntity.java`: `a055b84b98d98e828e177cad9bba47a1334ee9bf3ecd4331792979106235a302`

## Operation comparison

### Caller box, loaded-area gate, and states

1.9.4 `Entity.checkWaterCollisions` at `Entity.java:849–862` and 1.10.2 at `Entity.java:862–875` pass the same `getShape().grown(0.0, -0.4F, 0.0).contract(0.001)` box, `Material.WATER`, and entity to `World.applyLiquidDrag`. Boat exclusion, `inWater` updates, splash, fall-distance reset, and fire reset are unchanged.

`World.applyLiquidDrag` is at 1.9.4 `World.java:1560–1601` and 1.10.2 `World.java:1570–1611`. Both floor each minimum and ceil each maximum, then iterate each axis with an exclusive upper bound. Both reject the query when `isAreaLoaded(i,k,m,j,l,n,true)` is false. The loaded-area predicate is the same at `World.java:216–237` on both sides: vertical range must intersect `[0,256)`, chunk coordinates are shifted by 4, and every chunk coordinate from shifted min through shifted max is checked inclusively with `allowEmpty=true`. `ClientWorld.isChunkLoadedAt` is likewise the same at `ClientWorld.java:104–106`: `allowEmpty || !chunk.isEmpty()`. Thus empty client chunks are admitted for this query; `getBlockState` then returns air outside world height and otherwise reads the chunk state (`World.java:621–628` in 1.9.4; `625–632` in 1.10.2). No admission/state-guard difference found.

The per-cell material gate is the same: `blockState.getMaterial() == material`, with caller material `WATER`; cell top is `p + 1 - LiquidBlock.getHeightLoss(LEVEL)` and the cell contributes only when the query's ceiled upper Y `l >= d`. `LiquidBlock.getLiquidState` also requires the state's material to equal the liquid instance material (`LiquidBlock.java:57–64` both versions), so ordinary water source and flowing blocks use the same water-material path; no source-versus-flowing branch was added or removed.

### Per-cell flow and aggregate acceleration

`LiquidBlock.getFlow` and `applyMaterialDrag` are byte-identical across releases (`LiquidBlock.java:131–177`; verified same whole-file SHA above). Per cell, it sums horizontal-neighbor liquid-depth differences; for a non-liquid, non-motion-blocking neighbor it also samples the block below and uses `j - (i - 8)`. For level `>= 8`, a solid side face at the cell or above changes the vector to its normalized value plus `(0,-6,0)`. It releases the pooled position and normalizes the per-cell vector once before returning it. `applyMaterialDrag` adds that normalized per-cell vector to the running whole-box vector. This preserves direction iteration, state reads, casts and normalization order exactly.

`World.applyLiquidDrag` aggregates in the same nested X/Y/Z cell order. If aggregate length is positive and `entity.hasLiquidCollision()`, both versions normalize the aggregate once and add `0.014` times each component to velocity after scanning all cells. The only expression-level rewrite observed is 1.9.4's local `double e = 0.014; ... * e` versus 1.10.2's retained `double e = 0.014; ... * 0.014` for all three components. Both literals are the same double constant, with no cast or changed operation order; this is not a movement semantic difference. Player gating remains `PlayerEntity.hasLiquidCollision() == !abilities.flying` (`PlayerEntity.java:1716–1719` in 1.9.4; `1735–1738` in 1.10.2). On-foot, non-flying local players pass this acceleration gate; flying players do not.

### Local client tick reachability and position order

`LocalClientPlayerEntity.tick` is the same at 1.9.4 `LocalClientPlayerEntity.java:143–157` and 1.10.2 `:152–166`: when the player's X/Z chunk is loaded, it calls `super.tick()` before network movement reporting; no new on-foot/flying exclusion gates the superclass tick. `LivingEntity.tick` calls `super.tick()` before its AI/jump/travel phase (`1.9.4 LivingEntity.java:1489–1491`; `1.10.2 :1525–1527`). Inherited `Entity.baseTick` calls `checkWaterCollisions` before travel (`1.9.4 Entity.java:360–362`; `1.10.2 :365–367`), so the water operation is reached in the client tick. The local living-entity travel phase remains after that check and applies input/travel (`1.9.4 LivingEntity.java:1705–1712`; `1.10.2 :1741–1748`). Additionally, the same `LivingEntity.checkFallDamage` path rechecks water after a move when `!isInWater()` (`1.9.4 LivingEntity.java:183–200`; `1.10.2 :185–202`), with no pair difference. No position-order or reachability change found for ordinary on-foot, non-flying water-current behavior.

## Disposition

Coverage result: compared slices are unchanged in movement meaning, with only the harmless equivalent `0.014` expression rewrite in aggregate velocity addition. No independent difference/finding for this bounded water-current assignment. Broader liquid/body/eye-swimming behavior and complete 1.9.4↔1.10.2 movement-pair coverage remain outside this assignment.