# 1.10 → 1.10.1: falling-water fence wall cue

**Bounded result:** no difference detected for this operation in this endpoint pair. This is a source coverage note, not a claim about intermediate patches or other fence variants.

## Exact source identity and readiness

Canonical sources were already available at `decompiled_minecraft/1.10/ornithe-feather/` and `decompiled_minecraft/1.10.1/ornithe-feather/`; no generation or setup was run. Each tree has `.decompile-provenance.json`.

| Release | Mapping artifact | Artifact SHA-256 | Provenance file SHA-256 |
| --- | --- | --- | --- |
| 1.10 | Ornithe Feather `1.10+build.2` | `ad7cf6744d81d0260c9d97ddfe8250b2fdbd54fe332d4d00266b3545d8ddf8dd` | `df266e2c3009dd1a873c1665c30fe388078ed1c6227668e10cde87409a18c6da` |
| 1.10.1 | Ornithe Feather `1.10.1+build.2` | `6e8ba44832b7b77d913962462bde55084005d5de6389232453afc0bc0339ea37` | `a9a50497930848bf0ddec732b6d424e947f643a8fa6290e01425ac2bbfce219f` |

The provenance inputs identify exact client JARs: 1.10 `ba038efbc6d9e4a046927a7658413d0276895739`, SHA-256 `c00f8170ebf4fd834f552246743a64885a6dffb3dd1e3a3f3dba9676b7a0af35`; 1.10.1 `44b389fff90324c4ca18796d4428a7b8ec6c2eb0`, SHA-256 `338b205f20c22b9c676d9d3227a5e6c5e190f30ad84d7caad035150b3e05ba72`. Each cited source file below matched its manifest SHA-256. The flow, fence, block, material, entity, living-entity and local-player files also have identical SHA-256s across the pair; `World.java` differs as a whole, but the examined `applyLiquidDrag` method text is identical.

## Compared operation

In both versions, `net/minecraft/block/LiquidBlock.java`:

- `isFaceSolid` (lines 82–90) returns false for the same liquid, true for `UP`, false for ice, and otherwise delegates to the block’s `isFaceSolid`.
- `getFlow` (lines 131–171) computes horizontal flow from neighboring liquid depths. Only at source level `LEVEL >= 8` (line 160) it checks each horizontal adjacent voxel and that voxel one block above (lines 161–163). The tested face is the horizontal direction. If either queried face is solid, it normalizes the current vector, adds vertical `-6.0` (line 164), breaks, and normalizes again before returning (line 171).
- `applyMaterialDrag` (lines 174–176) returns the incoming aggregate velocity plus that flow vector.

The vanilla dry oak fence is registered in `net/minecraft/block/Block.java` (lines 922–930) as `FenceBlock(Material.WOOD, PlanksBlock.Variant.OAK.getColor())`. `FenceBlock` is not a cube (`FenceBlock.java` lines 105–113), but that does not gate this cue: its inherited `Block.isFaceSolid` returns `world.getBlockState(pos).getMaterial().isSolid()` (`Block.java` lines 332–334), and `Material.WOOD.isSolid()` is true (`material/Material.java` lines 9, 63–65). For this exact oak fence, either a horizontal adjacent fence block or a fence one block above the adjacent position therefore triggers the falling-liquid downward cue. This does not assert that other fence materials or variants behave alike.

## Client player application and gates

The local client player calls `super.tick()` only when its chunk is loaded (`client/entity/living/player/LocalClientPlayerEntity.java` lines 151–155). Its superclass chain reaches `Entity.baseTick`; there `tickSprintingEffect()` is followed by `checkWaterCollisions()` (`entity/Entity.java` lines 365–366). The collision method clears `inWater` for a boat mount; otherwise it passes `getShape().grown(0.0, -0.4F, 0.0).contract(0.001)`, `Material.WATER` and the entity to `World.applyLiquidDrag` (`Entity.java` lines 862–877).

`World.applyLiquidDrag` returns false if its bounds are not area-loaded (lines 1564–1573). It scans the floored/ceiled bounds, accepts matching water material only when the entity bounds reach that liquid surface (lines 1579–1591), and accumulates each block’s material drag. If the aggregate flow is nonzero and `entity.hasLiquidCollision()` is true, it normalizes the aggregate and adds `0.014` times each component to velocity X/Y/Z (lines 1596–1603). `Entity.hasLiquidCollision()` returns true by default (lines 2009–2011). There is no client-only conditional in this shared collision/current path; the local client player reaches it through its ordinary tick path. This note describes that client application path and does not propose server-side historical movement.

## Evidence files and SHA-256

| Source file, relative to mapping root | SHA-256 in both trees |
| --- | --- |
| `net/minecraft/block/LiquidBlock.java` | `b988d19e379d01d291cb014cb05e1579311c589654ce9ff4faa7f43bbba0533e` |
| `net/minecraft/block/FlowingLiquidBlock.java` | `eac6b26e87f4b759bfea9adb1d1d7edcc4ad2b7c6a88677feab9fc83a7b50d79` |
| `net/minecraft/block/FenceBlock.java` | `d9375bba3b41ae408bdf65d69b25aecad522a6d0b45b38d30f77d12b343a4909` |
| `net/minecraft/block/Block.java` | `1971dbc284d511e2ed366f77bc77fd8cd07174baad3e7732e908d3daeb640c01` |
| `net/minecraft/block/material/Material.java` | `5d682e9ea5a30540e62a68879fbdabd2dc1fb79781a8350c61370c1166654a7d` |
| `net/minecraft/entity/Entity.java` | `05da145effa19a6ef7934cc276e89226373b67c12f4ce89a8ce2183f29039f77` |
| `net/minecraft/entity/living/LivingEntity.java` | `d40dd476b6b68c6ce45b4202823475deb546ecda2284da330ff6724b33815e82` |
| `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `a9637065f21ad67464eb5c204c74ebf228c3bb0da8a96ddf4ae73c0490fed443` |

`net/minecraft/world/World.java`: 1.10 `827fea19d95ddc0a2847859f0c288bf0b6d6dc3692feb30b52b84c60fa19d877`; 1.10.1 `41fd1edc24b39a5b9a979d15d54fbd3ebf9818651628109f232fcc24c7e28d0a`. The `applyLiquidDrag` method at lines 1564–1605 is identical in the compared sources.