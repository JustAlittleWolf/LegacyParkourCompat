# 1.8.5 → 1.8.8: falling-water fence cue and local-player path

**Result:** no source difference found in the assigned slice. In both endpoints, a legacy plain oak fence is read as a solid face by the falling-liquid flow cue when it is in a horizontal neighboring voxel or the voxel above that neighbor. The local player reaches the same liquid-drag operation under the same gates. This endpoint result does not establish 1.8.6 or 1.8.7 behavior or an introduction boundary.

## Ready source identities

Both trees were already available at `decompiled_minecraft/<release>/ornithe-feather/`; no source setup or generation was performed. Each `.decompile-provenance.json` declares the same `lpc-decompile-v1` pipeline and runtime (Eclipse Adoptium 25.0.3+9-LTS, Windows 11, amd64), with exact-version Feather build.2 and Mojang client inputs:

| Release | Feather input SHA-256 | Client input SHA-256 | Provenance manifest SHA-256 |
|---|---|---|---|
| 1.8.5 | `18ec5e1a8f4cdfc3a886a7e24fe6a8594f04f51f6669bb0619d727f27cdd887d` (`1.8.5+build.2-mergedv2.jar`) | `2d825073de0da4383c0525e81d181ec81f92d17daa2f0b81faff3850492e461d` | `986d25dfd11927abed51c92643e5beda48237da875fbcdf1a48a930456ea99ce` |
| 1.8.8 | `934d6112c5c1fa2d1801dbe78aee37ffbcec7ec03e1ed3a62c73de3c7925437f` (`1.8.8+build.2-mergedv2.jar`) | `9481ed51d7fc4be54ec38c509f84124fbac5d9fea238bbde5b2e6c4f753b1ac8` | `588f83dc64ce0bbd0f11880d2da8e59d4d097d43225025a2a74a2b26cf909ac1` |

For each release, the SHA-256 of every cited `.java` source below was recomputed and matched the `sources` entry in that release's manifest. The corresponding source hashes are identical across these two endpoints.

## Compared behavior

- `net/minecraft/block/LiquidBlock.java`, `getFlow` (line 129; falling-flow branch lines 150–158): horizontal liquid-depth contributions are accumulated first. When the current liquid block state's `LEVEL >= 8`, each horizontal neighbor is checked at that voxel and one voxel above it with `isFaceSolid`; the first matching face normalizes the current vector, adds `(0, -6, 0)`, then the method normalizes once more. `applyMaterialDrag` returns the incoming velocity plus this flow (lines 164–166). Both releases have the same file SHA-256: `1537fd6b1bca65e0f910f9a12bcd3bb27f5a86be7f13a1caa380c5e66544f1d5`.
- `net/minecraft/block/Block.java`, `isFaceSolid` (329–331), reads the neighboring state's block material and calls `Material.isSolid()`. The oak fence registration (line 1019) constructs `FenceBlock(Material.WOOD, PlanksBlock.Variant.OAK.getColor())`. `Material.WOOD` is an ordinary `Material`; `isSolid()` defaults to `true` (Material.java lines 7, 60–62). Thus the fence is a wall cue even though the fence collision shape is not a full cube. These sources are identical between endpoints; hashes: Block `ea10f05106a3cf7189aec85236a7ecf9c7106a717f37bed583b5ea4adeffa528`, Material `017713d76afe726ca243ce32cbc35c13d3f0f0e7e5d90d122f81103cc2ca1bd2`.
- `net/minecraft/entity/Entity.java`, `baseTick` calls `checkWaterCollisions` (lines 249–300); that checks a box grown down by `0.4F` and contracted by `0.001` (746–762). `World.applyLiquidDrag` returns false if the expanded cell area is not loaded (1486–1494), selects blocks by exact material identity and liquid-height overlap (1500–1512), and adds normalized flow scaled by `0.014` only when the vector is nonzero and `entity.hasLiquidCollision()` is true (1518–1525).
- The local-player tick is gated on its position's chunk being loaded, then calls `super.tick()` (`client/entity/living/player/LocalClientPlayerEntity.java`, 105–109); `PlayerEntity.tick` and `LivingEntity.tick` continue through their superclass tick chain to `Entity.tick/baseTick`. `PlayerEntity.hasLiquidCollision()` returns `!abilities.flying` (PlayerEntity.java 1617–1619). Thus in this path the world-area load gate can suppress the water check, while flying suppresses flow-velocity application; the flow cue itself has the `LEVEL >= 8` gate above. The relevant Entity, LivingEntity, PlayerEntity, LocalClientPlayerEntity and World source files have matching manifest hashes and identical contents in 1.8.5 and 1.8.8.

## Source references and checked SHA-256

Paths are relative to `decompiled_minecraft/<release>/ornithe-feather/`. Each listed hash is the verified SHA-256 for both compared releases.

| Relative source | SHA-256 |
|---|---|
| `net/minecraft/block/LiquidBlock.java` | `1537fd6b1bca65e0f910f9a12bcd3bb27f5a86be7f13a1caa380c5e66544f1d5` |
| `net/minecraft/block/Block.java` | `ea10f05106a3cf7189aec85236a7ecf9c7106a717f37bed583b5ea4adeffa528` |
| `net/minecraft/block/FenceBlock.java` | `8d6a803988d77fc51f364520f2e0cd5567b1d5c29a4d28d01cdcf93e6d5890d9` |
| `net/minecraft/block/material/Material.java` | `017713d76afe726ca243ce32cbc35c13d3f0f0e7e5d90d122f81103cc2ca1bd2` |
| `net/minecraft/block/Blocks.java` | `1da2d85406ed991e8f6bc1f420babf0bd43598478f347c6a4dc9fea322ed0695` |
| `net/minecraft/entity/Entity.java` | `d4c10932cb5bb1067a5a58be4e1bb1b42bdde3b6fc893d88178cc07435a1696b` |
| `net/minecraft/entity/living/LivingEntity.java` | `082831c6578e3a70fa6cea5b90bc3eefc26678259b66334470de22b90b5b0e4e` |
| `net/minecraft/entity/living/player/PlayerEntity.java` | `e66cb294fc93118148a444bbafdf4dd57cbf66a23d69b1e8892cefccc690ab88` |
| `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `1762b116e6b06d682b7daaa0fc8cce39b0ff455b3db8cf79dab03ac74f6c4053` |
| `net/minecraft/world/World.java` | `3c04f5b874fbb6692039164c882a3c47fe28c7eca864e91c9ac57798153888ee` |

No source difference in the bounded pair slice; no finding drafted. Intermediate releases and the broader movement tick/inventory are unexamined.