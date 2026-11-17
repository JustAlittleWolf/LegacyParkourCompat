# Fluid player-movement dependency: 1.10.2 → 1.11

## Exact inputs

Both sides use the exact `ornithe-feather` namespace. The assigned queue gives these publication identities:

| Release | Ready marker | Source manifest |
| --- | --- | --- |
| 1.10.2 | `4c6d236e1d06ffc365452c48e73dfa2bf2414bbf4ea67a24a7010c5583906a2d` | `91b0f478acb7b6f13463c35b268a30d2806f583402631a56f54ce2eb70d1ec71` |
| 1.11 | `3c4102230dba0f9d2d07f752bbd2a19d2717375f6e31fb618c0450b7fd82bd45` | `8b3e5da58494f1dba77410960753b0bf646425a1ada491d8242cc71e6c7a59ca` |

The following source hashes were rechecked against those release manifests:

| Source file | 1.10.2 SHA-256 | 1.11 SHA-256 |
| --- | --- | --- |
| `net/minecraft/entity/Entity.java` | `05da145effa19a6ef7934cc276e89226373b67c12f4ce89a8ce2183f29039f77` | `3209feaacd1e798b521a193eeb081d92c96881a70a18254ef24a58e911a4fd97` |
| `net/minecraft/entity/living/LivingEntity.java` | `d40dd476b6b68c6ce45b4202823475deb546ecda2284da330ff6724b33815e82` | `1f2e31fa6a905ccc2a51a0338f2aaf448f2b0569f28644e8205d864e570d9b2b` |
| `net/minecraft/world/World.java` | `888ed0e9de765def87b05c4126ecdf0b10e9dd448b4543dd1cb98211e9951646` | `f64905888809d5666a63c8e403f171c13907502b8d982c5b3b8fce192360ff47` |
| `net/minecraft/block/Block.java` | `1971dbc284d511e2ed366f77bc77fd8cd07174baad3e7732e908d3daeb640c01` | `3fe8fdabe68da83fcfa01c0e6b8f5272fe81d71f6bf553506bdcef54757b0488` |
| `net/minecraft/block/LiquidBlock.java` | `b988d19e379d01d291cb014cb05e1579311c589654ce9ff4faa7f43bbba0533e` | `c91618c8445dd488dd61f4a01aed0799dae2faaf3585bc6f2d5164891e988244` |
| `net/minecraft/block/FlowingLiquidBlock.java` | `eac6b26e87f4b759bfea9adb1d1d7edcc4ad2b7c6a88677feab9fc83a7b50d79` | `6be4da4408c29a730fb33227514d8ad93f20b0adc87eda66796d069e3ae93be2` |
| `net/minecraft/block/LiquidSourceBlock.java` | `896a64f1a1170047cad30c1f65fe06e6a3a223f07f42b875c153748d1df3e3b3` | `0c82f8ec6d69b0181f8f81138e75326d0ea1fcc7ade9ea6ad12f2e3850245b13` |
| `net/minecraft/block/material/LiquidMaterial.java` | `208feeac7b775c16d7e7eb956d8f1dbf6d21e00f3f4463e6d9c197a295ea818a` | `208feeac7b775c16d7e7eb956d8f1dbf6d21e00f3f4463e6d9c197a295ea818a` |
| `net/minecraft/client/world/ClientWorld.java` | `00bc522817863345c535cd438c9be994f8ecfdeaf3e3c9c7c57d517287e8515c` | `996286a2b9a14b7396f1ff3501c0a2494af67534930ad5e9a0bbf5e88f12e01f` |

## Local-client player path and result

The pair coverage report records the client player movement entry and the typed `Entity.move` change. In these fluid-specific callers, `Entity.baseTick` invokes `checkWaterCollisions` before the inherited `LivingEntity.tick` travel section. `LivingEntity.tick` then selects water/lava jump response and calls `moveRelative`; the latter's water and lava branches are guarded by the same `isLocallyControlled() || isLogicalSideForUpdatingMovement()` condition in both releases.

No independent water/lava travel formula or fluid-contact movement-response delta was found in this slice. The fluid branches' gates, arithmetic, and operation order match; 1.11 tags their `move` calls with `MoverType.SELF`, which is part of the separate `Entity.move`/mover-type candidate in the pair report.

- `Entity.checkWaterCollisions` (1.10.2 lines 862–884; 1.11 lines 884–906) applies the same bounds, water material and boat gate. On water contact it resets `fallDistance`, sets `inWater`, and clears fire. The expression changes from `onFireTimer = 0` to `extinguish()`; both `Entity.extinguish` bodies assign `onFireTimer = 0`, so this changes no movement state.
- `World.applyLiquidDrag` (1.10.2 lines 1570–1612; 1.11 lines 1592–1634) has the same area-loaded gate, floor/ceil bounds, x/y/z iteration order, per-block `applyMaterialDrag` call, vector normalization, and `0.014` velocity addition when the accumulated current is nonzero and `entity.hasLiquidCollision()` is true. `World.isAreaLoaded` and its client `isChunkLoadedAt` implementation are unchanged in behavior.
- `LiquidBlock.getFlow` (both lines 131–170) reads `LEVEL` (0–15), horizontal and below neighbor liquid depths, movement-blocking material, and face solidity in the same order. Its falling-liquid branch applies the same downward `-6.0` contribution before normalization when `LEVEL >= 8`. `LiquidBlock.applyMaterialDrag` (both line 175) adds that flow vector. Default `Block.applyMaterialDrag` returns the existing vector unchanged in both releases.
- `LiquidBlock.getHeightLoss` (1.10.2 line 49; 1.11 line 50) retains the same `state >= 8` reset and `(state + 1) / 9.0F` arithmetic. `Entity.isSubmergedIn` and `Entity.isInLava` retain their exact state/material/box predicates (1.10.2 lines 938–960; 1.11 lines 960–982). `isSubmergedIn` is used by the breathing path, not the water/lava travel branch; damage/health effects remain excluded.
- `LivingEntity.jumpInWater` and `jumpInLava` add `0.04F` to vertical velocity in both versions (1.10.2 lines 1320–1328; 1.11 lines 1374–1382). The water travel branch keeps the same depth-strider cap/interpolation, `0.8F` vertical damping and `0.02` gravity; the lava branch keeps the same `0.02F` input acceleration, `0.5` damping and `0.02` gravity (1.10.2 `moveRelative` lines 1332–1510; 1.11 lines 1386–1556). Both retain the horizontal-collision `canMove` check and `0.3F` vertical response.
- `World.getBlockState`, `World.containsMaterial`, and the liquid collision shape are unchanged for these callers. `LiquidBlock.getCollisionShape` returns `EMPTY_BLOCK_SHAPE` in both versions; liquid travel uses the material predicates/current calculation rather than a solid collision box. Neither compared `LiquidBlock` declares a waterlogged property.

## Other fluid-source changes and boundaries

- `LiquidBlock.getTickRate` changes lava's dimension predicate from `dimension.hasNoSky()` to `dimension.hasCeiling()`; the water rate remains `5`. `FlowingLiquidBlock.tick` changes a neighbor update call from `updateNeighbors(pos, this)` to `updateNeighbors(pos, this, false)`. These are fluid block-state update/scheduling paths, not player travel formulas. `FlowingLiquidBlock.onAdded` calls `World.scheduleTick`; the base `World.scheduleTick` methods are empty, and `ClientWorld` does not override them or run scheduled block ticks in its `tickChunks` path. Historical server fluid-state production is outside this client-movement assignment; the movement predicates consume the current vanilla `BlockState`.
- `LiquidBlock.getHeight`/`getHeightY` are added in 1.11, but the exact source call sites are boat movement, fishing-bobber movement and water rendering. Boat physics and rendering are excluded; no local player travel caller was found.
- The remaining `move(MoverType.SELF, …)` change is not accepted here as a fluid-specific effect. Its movement-resolver gates, including clipping and player applicability, remain bound to the separate exact-pair candidate and audit.

This report closes only the water/lava movement and contact-response dependency slice. It does not accept the separate mover-type candidate, infer historical fluid state production, close all provider/effect/response gaps, or freeze the exact pair.
