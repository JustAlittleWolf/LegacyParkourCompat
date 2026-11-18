# Independent re-review r1

Verdict: ACCEPT, with the stated bounded scope. The corrected allow-empty and player-override claims match the frozen exact Ornithe Feather source for 1.11 and 1.11.1. No difference is established for the assigned on-foot, nonflying, nonfalling ordinary water-current operation.

## Bound claims

- Corrected comparison: comparison.corrected-r1.md SHA-256 d459314cc822244b1915fa4b834d4b612ca1f18c5328cb89d2ae6ad437501331.
- Original comparison: comparison.md SHA-256 e0b265e7f2e94c5807309411674614b3671ad2b6e1febd60983b1f25cb808fd3.
- Author source roster: source-roster.json SHA-256 70b519af1fd367d6b033f409246cade964b5f7e2ab84093f4feffc4d6e7c7211.
- Frozen independent roster: independent-source-roster.json SHA-256 d33a77c0a3416cc42d82495157506e960b8dc0e1b09a702aac76130d50744a8c.
- Prior audit verdict: audit-verdict.json SHA-256 017cceef395c392fe330d8e16a180ad6f4bb08df064642d1df71d48f0df23832 (preserved unchanged).

## Source bindings and review

- 1.11/net/minecraft/client/world/ClientWorld.java SHA-256 996286a2b9a14b7396f1ff3501c0a2494af67534930ad5e9a0bbf5e88f12e01f.
- 1.11/net/minecraft/world/World.java SHA-256 f64905888809d5666a63c8e403f171c13907502b8d982c5b3b8fce192360ff47.
- 1.11/net/minecraft/entity/living/player/PlayerEntity.java SHA-256 a0d787681de5caecda03fea1536dac0c07e6ca5cfbfe3b5fbb700cbe77c86fb6.
- 1.11.1/net/minecraft/client/world/ClientWorld.java SHA-256 996286a2b9a14b7396f1ff3501c0a2494af67534930ad5e9a0bbf5e88f12e01f.
- 1.11.1/net/minecraft/world/World.java SHA-256 27cfaa5ff45c2d88c492fc5dae3ea4a2bb4536fd64bff8498a9a7d0cb82efb58.
- 1.11.1/net/minecraft/entity/living/player/PlayerEntity.java SHA-256 8b37fd286b2dac1b343bd708b80284b43bb31c61bafc63bdcf6620ba3a59065c.

World.isChunkLoaded(BlockPos) delegates with allowEmpty=true; World.isAreaLoaded(..., allowEmpty) retains the vertical overlap gate maxY >= 0 && minY < 256 before horizontal chunk checks. World.applyLiquidDrag passes true; the client override returns allowEmpty || !chunk.isEmpty(), so empty client chunks do not fail either chunk predicate, while the vertical gate may still return false.
PlayerEntity.hasLiquidCollision() returns !abilities.flying and overrides Entity unconditional default. The corrected comparison accurately uses the player override for the bounded nonflying case.

The scope phrase on a loaded client chunk is treated as an assumed scenario, not as a gate guaranteed by isChunkLoaded: allowEmpty=true short-circuits before the chunk emptiness check. The corrected body accurately states this client implication.

Encoding: UTF-8, no BOM, LF line endings.
