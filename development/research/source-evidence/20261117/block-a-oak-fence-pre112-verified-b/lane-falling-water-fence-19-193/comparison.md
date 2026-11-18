# 1.9 → 1.9.3: falling-water fence cue and local player current

## Roster and readiness

- Pair: exact Minecraft 1.9 → 1.9.3; Ornithe Feather mappings on both sides.
- Source roots: `decompiled_minecraft/1.9/ornithe-feather/` and `decompiled_minecraft/1.9.3/ornithe-feather/` in the primary local checkout. Both contain `.decompile-provenance.json`; these are completed manifest-backed trees, not legacy trees.
- 1.9 Feather mapping input: `feather-gen2/1.9+build.2/feather-gen2-1.9+build.2-mergedv2.jar`, SHA-256 `a4e09a92eab0fbf6ef4668e295db1949e9d52c42f145d56b6dfbf3a10c2c7c2a`; cached metadata input SHA-256 `ca32cbff5f91d52f6563a2d50a20e3f0fb1e1f76dbb6e41e53c847d2d6977371`.
- 1.9.3 Feather mapping input: `feather-gen2/1.9.3+build.2/feather-gen2-1.9.3+build.2-mergedv2.jar`, SHA-256 `fc5defa1546cf87888a11783e2b5d9b48b47c6c022f48de4e55add978ab35da3`; cached metadata input SHA-256 `353c13519171cbba1a96872aebcb8b38248d5d5963b4c4613c23337adf028985`.
- Readiness check: for every source file listed below, the file's SHA-256 equals the matching `sources` entry in that version's provenance manifest. Provenance manifests also pin the version's Minecraft client input, Feather mapping artifact, decompiler/remapper tools, and source hashes. Source trees were read only.
- Bound: only horizontal falling-water wall cue when the neighboring same-level or above-neighbor block is the legacy plain oak fence, and application of water current to a locally controlled client player. No other fence variants or intervening patch releases are covered.

## Comparison

### Falling-water wall cue with legacy plain oak fence

- 1.9 `LiquidBlock.getFlow(WorldView, BlockPos)`, lines 128–168: for a block whose `LEVEL >= 8`, iterate `Direction.Plane.HORIZONTAL`; at each direction test `isFaceSolid` on the adjacent block, then the block one above it. Either true result changes the accumulated flow with `vec3d.normalize().add(0.0, -6.0, 0.0)` and breaks. The final result is normalized.
- 1.9.3 `LiquidBlock.getFlow(WorldView, BlockPos, BlockState)`, lines 131–171: same horizontal iteration, short-circuit order, falling-level gate, downward cue and final normalization. The method now receives the current liquid state and uses it for its depth/level reads; the wall-cue predicate is unchanged.
- Both versions' `LiquidBlock.isFaceSolid` returns `material == Material.ICE ? false : super.isFaceSolid(...)` for horizontal faces after excluding the same liquid material. `Block.isFaceSolid` returns `world.getBlockState(pos).getMaterial().isSolid()`. In both versions `Material.isSolid()` defaults to true. The legacy plain oak block is registered as `fence` using `new FenceBlock(Material.WOOD, PlanksBlock.Variant.OAK.getColor())`; therefore, for this bounded case, an oak fence in either tested position is solid to this predicate even though `FenceBlock.isSolidRender` returns false. A qualifying adjacent or above-adjacent fence activates the same downward-flow cue in both releases.
- Result: no behavior difference established for this bounded fence case. This result does not cover other fence materials/variants or establish an intermediate release boundary.

### Current application on locally controlled client player

- 1.9 `Entity.tick` (lines 357–358) calls `checkWaterCollisions()`; `Entity.checkWaterCollisions()` (lines 845–860) calls `World.applyLiquidDrag(...)` with the entity box grown downward by `0.4F` then contracted by `0.001`, unless mounted on a boat. `World.applyLiquidDrag` (lines 1554–1595) accumulates liquid-block flow in x/y/z loop order. After scanning, if the accumulated vector has length greater than zero and `entity.hasLiquidCollision()` is true, it normalizes the vector and adds `vec3d.x * 0.014`, `vec3d.y * 0.014`, `vec3d.z * 0.014` to the corresponding velocity components.
- 1.9.3 has the same tick call (Entity lines 360–361), collision-box transforms (Entity lines 849–864), scan ordering and application (World lines 1560–1601), gate, normalization and positive component-wise `0.014` additions. Only source line positions and liquid-state argument passing differ.
- `PlayerEntity.hasLiquidCollision()` (1.9 lines 1710–1712; 1.9.3 lines 1717–1719) is `!this.abilities.flying` in both releases. `LocalClientPlayerEntity.isLocallyControlled()` (1.9 lines 443–444; 1.9.3 lines 447–448) returns true in both, but that predicate is not consulted by `World.applyLiquidDrag`; the current impulse gate is nonzero summed flow plus `hasLiquidCollision()`. Thus a locally controlled player receives the flow when not flying; a flying player does not. No sign inversion appears in this application path.
- Result: no application-gate or impulse-direction difference established for this bounded player path.

## Exact source roster and verified per-file SHA-256

Hashes below are the exact source-file byte hashes, checked against each tree's provenance `sources` map.

| Version | Relative source file | SHA-256 |
|---|---|---|
| 1.9 | `net/minecraft/block/Block.java` | `91c053e11d6bedaac292b6be2f5670e5a5c4522f175c1e227163eeb5499780b2` |
| 1.9 | `net/minecraft/block/FenceBlock.java` | `e1874cad83f73cbe7749fc08439df6b90c67bac95bdfe27b48e62b2a9bcb4761` |
| 1.9 | `net/minecraft/block/LiquidBlock.java` | `7e76c5ebb1a59d36be3517d09e5df60a0eb206ff5ace5ce889eac9152b53c6e8` |
| 1.9 | `net/minecraft/block/material/Material.java` | `f198b08007c0acbe4e2737f7e484a9c85ec183220daef03b954cc90d0af95d19` |
| 1.9 | `net/minecraft/world/World.java` | `d1e23db2e20c86eb37b964a0df6838f1422ade7b0c2fff9c869ff8345979aa98` |
| 1.9 | `net/minecraft/entity/Entity.java` | `74d3ac34d7e6355c8fdcdd777dcd95ba9ef4626718c8772c1b58d0e28419e42a` |
| 1.9 | `net/minecraft/entity/living/LivingEntity.java` | `5b5936656aacc64a4d75db7b370985f9456ac96076331da047bd246f76a705fb` |
| 1.9 | `net/minecraft/entity/living/player/PlayerEntity.java` | `edbb46236d1e42630e600e4afcb434eb6e9adecbfc4ac7e15710a5dea50010b1` |
| 1.9 | `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `04d6196e7e17595c0aed59b28bc1730f8be5ea3eccdb1f971e9e2492dfadfe06` |
| 1.9.3 | `net/minecraft/block/Block.java` | `e62ece80c6a7e7121346f65f8fdfd9b148c29441a27de9f568bba9afe1d84fe6` |
| 1.9.3 | `net/minecraft/block/FenceBlock.java` | `d9375bba3b41ae408bdf65d69b25aecad522a6d0b45b38d30f77d12b343a4909` |
| 1.9.3 | `net/minecraft/block/LiquidBlock.java` | `b988d19e379d01d291cb014cb05e1579311c589654ce9ff4faa7f43bbba0533e` |
| 1.9.3 | `net/minecraft/block/material/Material.java` | `f198b08007c0acbe4e2737f7e484a9c85ec183220daef03b954cc90d0af95d19` |
| 1.9.3 | `net/minecraft/world/World.java` | `2fe063e0ec224eed9fc7d01b8f788c32035eed5fee5a9d98ea5e82296236e05a` |
| 1.9.3 | `net/minecraft/entity/Entity.java` | `bcd7fa2206bf8d7271102f6da7fe2771f96dbd22ec79dab9f2101a3e29c17ef0` |
| 1.9.3 | `net/minecraft/entity/living/LivingEntity.java` | `bbb7703f18fd5da05c4e4a43a77ea644b388e63c01d34166d308ea52054be4e5` |
| 1.9.3 | `net/minecraft/entity/living/player/PlayerEntity.java` | `ef96c8a00443f724eadad05a6a4dcc8a8a2e23cd4c3390b9e0f1f78c9ff4d1af` |
| 1.9.3 | `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `8aaf711948b7602c2e6c015a37e36ed06073d39727d999d80480b4910b704f5d` |