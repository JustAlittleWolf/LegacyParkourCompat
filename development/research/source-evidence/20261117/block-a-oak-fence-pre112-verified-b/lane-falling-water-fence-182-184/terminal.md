# Terminal: dry oak fence in falling-water flow

**Result:** no 1.8.2→1.8.4 behavior change found for the assigned cue or its local-player application. The inspected source bytes are identical in 1.8.2, 1.8.3 and 1.8.4 for every source file listed below. This closes only this bounded comparison; it does not establish behavior before 1.8.2 or after 1.8.4.

## Source readiness and identity

All three canonical trees are `decompiled_minecraft/<version>/ornithe-feather/` and contain `.decompile-provenance.json`. The manifests identify exact client artifacts and Feather Gen2 build.2 merged-v2 mapping artifacts. Each tree has 1,612 Java files and 1,612 manifest source records; independently recomputed SHA-256 values matched all records (zero missing, extra or mismatched Java files). The reported provenance hashes below are SHA-256 of the manifest files. These are manifested sources, not legacy trees without provenance.

| Release | Feather artifact (mapping/build) | Client SHA-256 | Feather artifact SHA-256 | Manifest SHA-256 |
|---|---|---|---|---|
| 1.8.2 | `1.8.2+build.2`, merged-v2 | `5cc5b24312f3d182bf06e7f1a4da3a05ce94be6c861d5c9995c86e6d8811fbe9` | `8235d85a8471d541f2bad73c2a60c6132a9432fe804b98369982fc8de847f37b` | `8ad11ea36f2dda39ae30d57daa53316a8c0889a2f3c0c84ee5d79b9b21c25472` |
| 1.8.3 | `1.8.3+build.2`, merged-v2 | `4da43d06c8c1605a9a3ba1bf22893245cf1b108eb5c8fed859012252370c2c06` | `e0ad2917dcc3a91586fb6f9b1bde78e55f1ece908572672885395e0d045dd9a2` | `7da5f772d7ed1433cb3da23861723ebaec7cb714ce49c4d1a5c30499fec3afed` |
| 1.8.4 | `1.8.4+build.2`, merged-v2 | `d68a3aa5271abece4ce2b7048f6caf4f58071476344d9a52fe9ab1410c5d458b` | `8ee5582ac3f6581e85ac27de5c9df9427a4bf67d0652531cf2e94eac4dde9b8c` | `b2207bf9ab2a7561bf73ac92a63cc67760ccc6fd9e2378ff1e10710ecbb03a00` |

Each manifest records `Eclipse Adoptium:25.0.3+9-LTS:Windows 11:amd64` and pipeline `lpc-decompile-v1` with one thread and Vineflower 1.12.0. Their exact source entries match the files rehashed here. All source file hashes listed next are identical on all three releases, so the complete cited class bodies were checked and intact.

## Bounded source comparison

In all three releases, `Block.java:1016–1020` registers the ordinary `fence` as `new FenceBlock(Material.WOOD, ...OAK...)`. `Material.WOOD` is constructed as a plain `Material` with flammability (`Material.java:7`), so it inherits the base `Material.isSolid()` and `Material.blocksMovement()` implementations, both returning `true` (`Material.java:60–70`).

`LiquidBlock.getFlow` (`LiquidBlock.java:129–161`) loops all four horizontal directions. For a dry neighbor (`getLiquidDepth < 0`), it reads water one block below that neighbor only if the same-height neighbor's material does **not** block movement (`:133–143`). A dry oak fence therefore prevents this lower-water gradient sample by its `WOOD` material. The check is material-based; it does not inspect fence connectivity or its movement box.

For a liquid cell whose raw `LEVEL` is at least 8 (`LiquidBlock.java:150`), the same method checks each horizontal neighbor and that neighbor's upper cell (`:151–153`). `LiquidBlock.isFaceSolid` returns false for same-liquid material and ice, but otherwise delegates to `Block.isFaceSolid` (`LiquidBlock.java:74–82`); the base implementation asks the neighbor material's `isSolid()` (`Block.java:329–331`). Thus a dry oak fence horizontally adjacent at the liquid cell's height **or one block above** supplies the falling-water wall cue, despite `FenceBlock.isCube()` and `isSolidRender()` both being false (`FenceBlock.java:113–121`). On the first such cue, flow is changed by `normalize().add(0.0, -6.0, 0.0)`, then normalized again on return (`LiquidBlock.java:153–160`). `LiquidBlock.applyMaterialDrag` adds this result to the accumulator (`:163–165`).

The current-application path is present unchanged in all three releases. The local client player ticks only when its chunk is loaded and then calls `super.tick()` (`LocalClientPlayerEntity.java:104–115`); the inherited entity base tick calls `checkWaterCollisions()` (`Entity.java:299–300`). That method queries `getShape().grown(0.0, -0.4F, 0.0).contract(0.001, 0.001, 0.001)` (`Entity.java:746–747`). `World.applyLiquidDrag` returns false for an unloaded scan area; otherwise it scans the query bounds, selects water cells passing its liquid-height test, accumulates each block's material drag, and adds `0.014` times the normalized sum to velocity only when nonzero and `entity.hasLiquidCollision()` (`World.java:1486–1526`). A player suppresses that velocity response while flying (`PlayerEntity.java:1616–1619`). A qualifying water cell must therefore intersect the local player's adjusted scan bounds; a dry fence by itself does not make the player wet or apply current. The water collision result separately updates `inWater` and clears fall distance (`Entity.java:748–759`).

No difference in the cue, gates, vector operation order, scan/application, or local-player path appears at either exact patch step 1.8.2→1.8.3 or 1.8.3→1.8.4. No finding/change is supported for this release interval.

## Verified source file SHA-256

These hashes matched the embedded source records in each release manifest; each file is byte-identical across 1.8.2, 1.8.3 and 1.8.4.

| Relative source | SHA-256 |
|---|---|
| `net/minecraft/block/LiquidBlock.java` | `1537fd6b1bca65e0f910f9a12bcd3bb27f5a86be7f13a1caa380c5e66544f1d5` |
| `net/minecraft/block/FenceBlock.java` | `8d6a803988d77fc51f364520f2e0cd5567b1d5c29a4d28d01cdcf93e6d5890d9` |
| `net/minecraft/block/Material.java` | `017713d76afe726ca243ce32cbc35c13d3f0f0e7e5d90d122f81103cc2ca1bd2` |
| `net/minecraft/block/Block.java` | `ea10f05106a3cf7189aec85236a7ecf9c7106a717f37bed583b5ea4adeffa528` |
| `net/minecraft/block/Blocks.java` | `1da2d85406ed991e8f6bc1f420babf0bd43598478f347c6a4dc9fea322ed0695` |
| `net/minecraft/entity/Entity.java` | `d4c10932cb5bb1067a5a58be4e1bb1b42bdde3b6fc893d88178cc07435a1696b` |
| `net/minecraft/entity/living/player/PlayerEntity.java` | `e66cb294fc93118148a444bbafdf4dd57cbf66a23d69b1e8892cefccc690ab88` |
| `net/minecraft/world/World.java` | `3c04f5b874fbb6692039164c882a3c47fe28c7eca864e91c9ac57798153888ee` |
| `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `1762b116e6b06d682b7daaa0fc8cce39b0ff455b3db8cf79dab03ac74f6c4053` |