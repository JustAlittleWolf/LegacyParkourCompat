# 1.9.1 to 1.9.2 source lane roster

## Frozen inputs

- Releases: Minecraft 1.9.1 and 1.9.2.
- Mapping family: Ornithe Feather, `ornithe-feather`; each manifest pins Feather Gen2 build.2 (`1.9.1+build.2` and `1.9.2+build.2`).
- Canonical source roots: `decompiled_minecraft/1.9.1/ornithe-feather/` and `decompiled_minecraft/1.9.2/ornithe-feather/`.
- Each root has `.decompile-provenance.json`; manifests identify the exact release, mapping artifact, client artifact, metadata input, decompiler pipeline/tool/runtime, and per-source SHA-256 values. The release metadata and client/mapping inputs are distinct per side. Relevant source files below were checked against each manifest's source hash and intact method bodies.
- Mapping artifact SHA-256: 1.9.1 `6d5768353bf2ff2756d38537312548a8ac91d570f130d23c23e9412d2edd97b3`; 1.9.2 `e2b98ececbff8fa0bd0c89283bbb32b7f9f9dcd4b89fa3f137666fa3ac0b6859`.
- Client artifact SHA-256: 1.9.1 `56ad7e53f6f4ad393844e274aa2190692b61d2ad9f6b3d12b33a8b7332fd51f9`; 1.9.2 `ef87eb71250df1b871deab8d3ef10aac3290b9e78667ad548fde4d0e14af0d65`.
- This roster is limited to the two assigned operations; it does not assert complete pair coverage.

## Source integrity and paired comparison

These per-source SHA-256 values match the corresponding manifest in both releases and are byte-identical across the pair:

| Relative source | SHA-256 |
| --- | --- |
| `net/minecraft/block/Block.java` | `91c053e11d6bedaac292b6be2f5670e5a5c4522f175c1e227163eeb5499780b2` |
| `net/minecraft/block/FenceBlock.java` | `e1874cad83f73cbe7749fc08439df6b90c67bac95bdfe27b48e62b2a9bcb4761` |
| `net/minecraft/block/LiquidBlock.java` | `7e76c5ebb1a59d36be3517d09e5df60a0eb206ff5ace5ce889eac9152b53c6e8` |
| `net/minecraft/block/material/Material.java` | `f198b08007c0acbe4e2737f7e484a9c85ec183220daef03b954cc90d0af95d19` |
| `net/minecraft/world/World.java` | `cdda70c97f0e4d6f6c454c8f6d3c0d47f0e0ae612e75062d8fe93ddd3963e6b0` |
| `net/minecraft/entity/Entity.java` | `74d3ac34d7e6355c8fdcdd777dcd95ba9ef4626718c8772c1b58d0e28419e42a` |
| `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `de9ad48bcfb2705ef7a9a63e9401c1742aead183e1e9c2d13f8fe40cca0139eb` |

`net/minecraft/entity/living/player/PlayerEntity.java` is manifest-verified on both sides but differs only at its unrelated `DataVersion` constant (1.9.1 uses 175; 1.9.2 uses 176). The bounded `hasLiquidCollision()` method is unchanged: it returns `!this.abilities.flying` in both. No assigned movement difference was found.

## Operation 1: falling-water fence wall cue

- 1.9.1 and 1.9.2, `net/minecraft/block/LiquidBlock.java`, `getFlow(WorldView, BlockPos)`, lines 128–168; `net/minecraft/block/Block.java`, `isFaceSolid(WorldView, BlockPos, Direction)`, lines 305–307; `net/minecraft/block/FenceBlock.java`, `getShape`/non-solid-render methods, lines 78–111; `net/minecraft/block/Block.java`, oak fence registration, lines 875–880; `net/minecraft/block/material/Material.java`, `WOOD` and `isSolid()`, lines 9 and 62–64.
- Difference: none across these exact releases. For a liquid state with `LEVEL >= 8`, `LiquidBlock.getFlow` checks each horizontal neighbor and its upper-adjacent cell using `isFaceSolid`. The base predicate checks `material.isSolid()` and does not inspect the fence collision/outline shape or the passed face. The plain oak fence is registered with `Material.WOOD`; wood is solid, so that fence can trigger the cue in either checked cell. On a trigger, the flow vector is normalized, receives `(0,-6,0)`, and is normalized again on return. This describes the source behavior in both versions, not a release delta.

## Operation 2: current velocity, loaded-area and player gates

- 1.9.1 and 1.9.2, `net/minecraft/entity/Entity.java`, tick call to `checkWaterCollisions()`, lines 357–359; `checkWaterCollisions()`, lines 845–857; `net/minecraft/world/World.java`, `applyLiquidDrag(Box, Material, Entity)`, lines 1554–1595; `net/minecraft/block/LiquidBlock.java`, `applyMaterialDrag`, lines 170–173; `net/minecraft/entity/living/player/PlayerEntity.java`, `hasLiquidCollision()`, lines 1709–1712; `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`, `isLocal()`, lines 302–305; `net/minecraft/entity/Entity.java`, `isLogicalSideForUpdatingMovement()`, lines 2237–2239.
- Difference: none across these exact releases. Each entity tick invokes the water-collision check. A boat mount skips the water path; otherwise the entity bounds are expanded downward by `-0.4F` and contracted by `0.001` before querying water. `World.applyLiquidDrag` returns `false` without scanning when the covered area is not loaded (`allowEmpty=true`); in a loaded area it accumulates each intersecting liquid's material-drag vector. A nonzero vector is normalized and adds `0.014` times each component to velocity only when `hasLiquidCollision()` is true. `PlayerEntity` disables this current response while flying. `LocalClientPlayerEntity.isLocal()` returns true, and Entity's logical-movement-side predicate uses that player flag when selecting movement ownership; that predicate does not gate the liquid-current application. These gates and operation order are identical in both versions.

## Disposition

Both bounded slices are examined, dependencies needed to interpret their gates are closed, and neither contains a 1.9.1-to-1.9.2 movement delta. No finding draft is warranted for this lane assignment.