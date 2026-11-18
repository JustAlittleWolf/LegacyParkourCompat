# Independent source audit verdict: 1.8.5 -> 1.8.8

**Audited candidate:** `lane-falling-water-fence-185-188/comparison.md`

**Candidate SHA-256:** `52ac1e0f5c115e6d4d88cc005e5e7d57cfb4fa5c6d24be90de1a71d6395704d2`

**Verdict: AGREE for the bounded slice.** I independently traced the frozen roster against the exact 1.8.5 and 1.8.8 Feather sources. The cited player, fluid, fence and world source files have identical byte hashes across these endpoints, and each hash matches that side's provenance `sources` entry. The result establishes no difference for this assigned endpoint pair slice only. It does not establish behavior in 1.8.6 or 1.8.7, the patch introduction point, full-pair movement coverage or runtime parity.

## Fence cue

`net/minecraft/block/LiquidBlock.java`, `getFlow` (129–161), loops over horizontal directions. For each neighbor it first applies depth-derived flow contributions. When the current liquid state's `LEVEL >= 8`, it tests the horizontal neighbor voxel, then that voxel one block above, using `isFaceSolid`; on the first true result it normalizes the current vector, adds `(0, -6, 0)`, and breaks, then normalizes the result again. `Block.isFaceSolid(WorldView, BlockPos, Direction)` (329–331) reads the block at the requested position and returns that block material's `isSolid()`; the face argument is unused. Thus same-height and upper horizontal neighbors are both cues.

`Block.java` registers ordinary `fence` as `new FenceBlock(Material.WOOD, PlanksBlock.Variant.OAK.getColor())` (1016–1020). `Material.WOOD` is a plain `Material`; inherited `Material.isSolid()` returns true (60–62). Therefore a dry ordinary oak fence provides this liquid-current cue at either checked neighbor position even though its collision geometry is not a full cube. This path is a material solidity predicate, not the fence `isCube()` or collision connection predicate. The `FenceBlock` collision and neighbor-connection methods were also checked; their results do not gate `LiquidBlock.getFlow`'s `isFaceSolid` test.

## Local-player current and gates

`LocalClientPlayerEntity.tick` (105–115) only enters `super.tick()` when `world.isChunkLoaded(new BlockPos(x, 0.0, z))` succeeds. `ClientPlayerEntity` adds no `tick` override; the player tick chain reaches `PlayerEntity.tick` (183 onward), `LivingEntity.tick` (1259 onward), then `Entity.tick` (245) and `Entity.baseTick` (249 onward). `baseTick` calls `checkWaterCollisions`; that method passes `getShape().grown(0.0, -0.4F, 0.0).contract(0.001, 0.001, 0.001)` to `World.applyLiquidDrag` with `Material.WATER` (746–760).

`World.applyLiquidDrag` (1486–1526) floors each contracted box min and `max + 1.0` coordinate, then returns false immediately unless `isAreaLoaded(i,k,m,j,l,n,true)`. For loaded cells it loops x, y, z in that order, accepts exact material identity (`block.getMaterial() == material`) and the source's `l >= p + 1 - LiquidBlock.getHeightLoss(level)` condition, and accumulates each block's `applyMaterialDrag`. It only applies velocity if the aggregate vector has nonzero length and `entity.hasLiquidCollision()` succeeds; it normalizes the aggregate and adds components scaled by `0.014` to the existing velocity. `PlayerEntity.hasLiquidCollision()` returns `!abilities.flying` (1617–1619), so flying suppresses the velocity write while the liquid overlap result may still set `inWater`. `LiquidBlock.applyMaterialDrag` adds `getFlow` to the accumulated vector (164–166). These call order, geometric gates, loaded checks, predicates and velocity writes match in both endpoints.

## Exact evidence identity

Both source roots are `decompiled_minecraft/<release>/ornithe-feather/`, produced by the `lpc-decompile-v1` pinned pipeline under Windows 11 amd64 / Eclipse Adoptium 25.0.3+9. Mapping inputs are exact Feather Gen2 build.2 mergedv2 artifacts. Provenance manifest hashes and pinned input identities:

| Release | Provenance SHA-256 | Client jar SHA-256 | Feather input SHA-256 | Mojang version metadata SHA-256 |
|---|---|---|---|
| 1.8.5 | `986d25dfd11927abed51c92643e5beda48237da875fbcdf1a48a930456ea99ce` | `2d825073de0da4383c0525e81d181ec81f92d17daa2f0b81faff3850492e461d` | `18ec5e1a8f4cdfc3a886a7e24fe6a8594f04f51f6669bb0619d727f27cdd887d` | `4fe7e25babf0c16244675812332c1403f0a30f8dac03b9e7c9cfd40ba39c7597` |
| 1.8.8 | `588f83dc64ce0bbd0f11880d2da8e59d4d097d43225025a2a74a2b26cf909ac1` | `9481ed51d7fc4be54ec38c509f84124fbac5d9fea238bbde5b2e6c4f753b1ac8` | `934d6112c5c1fa2d1801dbe78aee37ffbcec7ec03e1ed3a62c73de3c7925437f` | `7f002c1dca74cc51ce995e7fdb465ab4984c940e77a1b1c4f2b09bed8702e558` |

Verified per-release `.java` source SHA-256 values (same on both endpoints and matching each provenance manifest):

| Relative source | SHA-256 |
|---|---|
| `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `1762b116e6b06d682b7daaa0fc8cce39b0ff455b3db8cf79dab03ac74f6c4053` |
| `net/minecraft/client/entity/living/player/ClientPlayerEntity.java` | `b322144fcc965c9bf5f6ff18882d17f6f0e1cdaf0e545b1c7663c662aceb029b` |
| `net/minecraft/entity/living/player/PlayerEntity.java` | `e66cb294fc93118148a444bbafdf4dd57cbf66a23d69b1e8892cefccc690ab88` |
| `net/minecraft/entity/living/LivingEntity.java` | `082831c6578e3a70fa6cea5b90bc3eefc26678259b66334470de22b90b5b0e4e` |
| `net/minecraft/entity/Entity.java` | `d4c10932cb5bb1067a5a58be4e1bb1b42bdde3b6fc893d88178cc07435a1696b` |
| `net/minecraft/block/FenceBlock.java` | `8d6a803988d77fc51f364520f2e0cd5567b1d5c29a4d28d01cdcf93e6d5890d9` |
| `net/minecraft/block/Block.java` | `ea10f05106a3cf7189aec85236a7ecf9c7106a717f37bed583b5ea4adeffa528` |
| `net/minecraft/block/LiquidBlock.java` | `1537fd6b1bca65e0f910f9a12bcd3bb27f5a86be7f13a1caa380c5e66544f1d5` |
| `net/minecraft/block/material/Material.java` | `017713d76afe726ca243ce32cbc35c13d3f0f0e7e5d90d122f81103cc2ca1bd2` |
| `net/minecraft/block/Blocks.java` | `1da2d85406ed991e8f6bc1f420babf0bd43598478f347c6a4dc9fea322ed0695` |
| `net/minecraft/world/World.java` | `3c04f5b874fbb6692039164c882a3c47fe28c7eca864e91c9ac57798153888ee` |
| `net/minecraft/world/WorldView.java` | `212c5386769dce8f12c06cf8fe9fed6f931bda8cd519239a20bc894a4c1f2fd8` |

Checked the bounded slices only. No product source, wiki inventory, backlog, build, test, runtime or network operation was used.