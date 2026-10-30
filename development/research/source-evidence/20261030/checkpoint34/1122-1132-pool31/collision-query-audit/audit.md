# Independent collision/query closure audit: 1.12.2 → 1.13.2

## Bound input and evidence identity

Audited `.native-source/20261030/1122-1132-pool31/collision-contracts/query-closure/collision-query-closure.md`, SHA-256 `137bf753ded9b4850fa04ea76232ad422c3354c34ad70f6d29174338f751bfc3`.

The four bound prior collision artifacts were also rehashed and match their stated identities:

| Artifact | SHA-256 |
|---|---|
| `collision-contracts/coverage.md` | `2cb4040f8e2aad534d2ca9a60380a97b8e1bd28ddf7dfed903b9daf1b24bf888` |
| `collision-contracts/water-contact-and-current-query.md` | `391d448311d3719bc42776861da02c59f34221e7fbaec3d6200ad70670cede70` |
| `collision-contracts/correction-supplement.md` | `1895e10caf739762a66323bcd901ddac25e391b3949cfc5b7518744671e72b71` |
| `collision-contracts/correction-supplement-2.md` | `22d70fddce221f362a6e41b27fd469d098d5630feeeee38338347723523c247b` |

I reopened the canonical read-only Feather Gen2 build 2 sources at `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\{1.12.2,1.13.2}\ornithe-feather`. Both `ornithe-feather.sources.sha256` files hash to the manifests recorded in the bound coverage: 1.12.2 `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da`; 1.13.2 `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`. The provenance records identify Feather Gen2 build 2 merged mappings for the exact release on each side. All 30 source files in the closure report's direct-hash table matched both their on-disk SHA-256 and their release manifest row. I additionally checked `KeyboardInput.java`, `Input.java`, and `MathHelper.java` in both releases; all six on-disk hashes match their manifest rows.

## Independently retraced source facts

- **Old clipper:** 1.12.2 `Entity.move` (`net/minecraft/entity/Entity.java:462-601`) snapshots requested deltas, gathers one `List<Box>` from the swept AABB, and clips Y, X, then Z. `Box.intersectX` (`net/minecraft/util/math/Box.java:198-215`) requires strict overlap on Y/Z and has no epsilon: with positive X gap `g=1e-8` and request `x=5e-8`, it reduces the move to `g`. The 1.12.2 world collector's loops are X outer, Z middle, Y inner (`net/minecraft/world/World.java:965-1004`); block providers append in that visit order and entity boxes are appended afterward (`:1017-1038`).
- **New query shape:** 1.13.2 `WorldView.getCollisions(entity, Box, dx, dy, dz)` (`net/minecraft/world/WorldView.java:154-164`) makes the movement query from the swept box expanded by `1e-7`, subtracting a starting box offset against movement direction. With the stated positive 1e-8 obstacle gap, the remaining query slab overlaps the obstacle; `getBlockCollisions` filters for nonempty overlap and emits the translated provider or its aggregate full-block shape (`:112-152`).
- **New traversal/order:** `BlockPos.Mutable.iterateRegionMutable` (`net/minecraft/util/math/BlockPos.java:212-239`) advances X fastest, then Y, then Z. The `WorldView` collector's sequential `map/filter` stream preserves that encounter order; aggregated unit-block providers are emitted after ordinary providers, and `World.getCollisions` concatenates entity shapes after the interface stream (`world/World.java:1754-1758`). This differs from the old loop order, but the small-offset gate below makes the exact first shape immaterial for this witness.
- **Tolerance:** 1.13.2 `VoxelShapes.calculateMaxOffset` (`net/minecraft/block/shape/VoxelShapes.java:194-206`) gets an iterator, and for each available element returns `0.0` when `abs(maxDist) < 1e-7` *before calling `next()`*. The candidate obstacle makes the stream nonempty. Therefore the query/solver arithmetic in the report is correct as a conditional source fact: the old AABB clipper allows `1e-8`, while the new helper returns zero for `x=5e-8` when any candidate shape is present. There is no claim here that the block's state/provider or local-player setup is established by this arithmetic alone.
- **Other movement branches:** I checked the enclosing `Entity.move` methods. The old step alternative reuses its candidate list and compares squared horizontal distances; the new step alternative uses the memoized candidate stream and the same style of comparison. The 1.13.2 all-zero movement guard avoids collection only when all three requested deltas are zero; it does not bypass the stated nonzero X witness. The old and new sneaking edge-reduction branches are ground+sneaking only and are excluded by the proposed witness's precondition.

## Reachability verdict for `SELF X=5e-8`

**Unestablished as a vanilla local-player movement witness; do not treat it as an accepted player-movement finding yet.** The closure report supplies a legal numeric input to the public `Entity.move` contract but does not construct it from the local player's caller.

I traced the ordinary airborne, non-fall-flying player path on both sides. `LivingEntity.mobTick` first zeros each velocity component with absolute value below `0.003` (`1.12.2:1781-1812`; `1.13.2:1852-1888`), then invokes local `serverTickAi`, dampens movement inputs, and calls `moveRelative` (`1.12.2:1820-1847`; `1.13.2:1896-1926`). The local player's `mobTick` calls `input.tick()` before `super.mobTick()` (`LocalClientPlayerEntity.java:698, 812` on 1.12.2; `:702, 837` on 1.13.2); the client constructs `KeyboardInput` for it (`Minecraft.java:1953/1991` and `:1444/1480`). `serverTickAi` copies the input axes (`LocalClientPlayerEntity.java:626-640` on both sides). `KeyboardInput.tick` builds the axes from pressed keys as `-1/0/1` and only applies a `0.3` scale while sneaking. In `LivingEntity.moveRelative`, the ordinary air branch calls `updateVelocity` and then `move(MoverType.SELF, velocityX, velocityY, velocityZ)` (`1.12.2:1487-1514`; `1.13.2:1537-1575`). `Entity.updateVelocity` computes X from `sideways*cos(yaw) - forward*sin(yaw)` (`1.12.2:1049-1067`; `1.13.2:1057-1075`). Both `MathHelper` implementations use a 65,536-entry sine table indexed by truncating `angle * 10430.378F`; the value is discrete, not a continuous trigonometric function.

Consequently, sub-0.003 *residual* velocity cannot reach that ordinary move call, and the suggested “near-cardinal yaw” argument alone does not construct a 5e-8 result: keyboard input is discrete and the lookup is quantized. A diagonal cancellation may produce zero or a table-step difference, but the report gives no yaw/input/arithmetic construction yielding the claimed nonzero value. I did not find or establish a fall-flying or other vanilla local-player caller that constructs a nonzero X delta below `1e-7` after the mob-tick clamp. Such a caller remains an open prerequisite; I am not asserting that every specialized route is impossible.

Until a constructive caller is supplied and traced through these gates, the source arithmetic remains conditional on a move argument outside the demonstrated ordinary local-player path. Its player-reachable consequence is therefore open. No runtime, mock, gameplay, test, or build evidence was used.

## Provider inventory and closure gaps retained

The closure report's signature-only inventory remains an explicit open dependency; this audit did not duplicate block-geometry analysis. The following 1.12.2 `getShape` / `getCollisionShape` declarations and corresponding registered-state cases were not individually compared: `AbstractPressurePlateBlock`, `AbstractRailBlock`, `AirBlock`, `AnvilBlock`, `BannerBlock`, `BedBlock`, `BeetrootsBlock`, `Block`, `BrewingStandBlock`, `ButtonBlock`, `CactusBlock`, `CakeBlock`, `CarpetBlock`, `CarrotsBlock`, `CauldronBlock`, `ChestBlock`, `ChorusPlantBlock`, `CobwebBlock`, `CocoaBlock`, `DaylightDetectorBlock`, `DeadBushBlock`, `DiodeBlock`, `DoorBlock`, `DoublePlantBlock`, `DragonEggBlock`, `EnchantingTableBlock`, `EnderChestBlock`, `EndGatewayBlock`, `EndPortalBlock`, `EndPortalFrameBlock`, `EndRodBlock`, `FarmlandBlock`, `FenceBlock`, `FenceGateBlock`, `FireBlock`, `FlowerBlock`, `FlowerPotBlock`, `GrassPathBlock`, `HopperBlock`, `LadderBlock`, `LeverBlock`, `LilyPadBlock`, `LiquidBlock`, `MovingBlock`, `MushroomPlantBlock`, `NetherWartBlock`, `PaneBlock`, `PistonBaseBlock`, `PistonHeadBlock`, `PlantBlock`, `PortalBlock`, `PotatoesBlock`, `RedstoneWireBlock`, `SaplingBlock`, `ShulkerBoxBlock`, `ShulkerBoxBlockEntity`, `SignBlock`, `SkullBlock`, `SlabBlock`, `SnowLayerBlock`, `SoulSandBlock`, `StateDefinition`, `StemBlock`, `StructureVoidBlock`, `SugarCaneBlock`, `TallPlantBlock`, `TorchBlock`, `TrapdoorBlock`, `TripwireBlock`, `TripwireHookBlock`, `VineBlock`, `WallBlock`, `WallSignBlock`, `WheatBlock`.

The following old custom `addCollisions` declarations were not body-compared beyond the base hook: `BrewingStandBlock`, `CauldronBlock`, `ChorusPlantBlock`, `EndPortalBlock`, `EndPortalFrameBlock`, `FenceBlock`, `HopperBlock`, `LilyPadBlock`, `MovingBlock`, `PaneBlock`, `PistonBaseBlock`, `PistonHeadBlock`, `StairsBlock`, `WallBlock`. The old/new `Blocks` and provider registration entries for these declarations were not compared individually. Moving-fence geometry remains excluded by assignment; `MovingBlockEntity` is outside player-movement scope. This leaves existing-block geometry, state-dependent provider registration, world-border edge cases, and the complete pair inventory / independent coverage audit open. No full-pair freeze is claimed.

## Direct source hashes added for reachability audit

All eight files below also matched their exact release-manifest row:

| Release | Relative source path | SHA-256 |
|---|---|---|
| 1.12.2 | `net/minecraft/client/entity/living/player/KeyboardInput.java` | `7be11425906be051c83e275f359816546e4677b16d212156380e8d2e9258654a` |
| 1.12.2 | `net/minecraft/client/entity/living/player/Input.java` | `9e704cfe7fdc55c4eab78670e60cf392ba6817adbd5e7451d3a86f11da8bf50e` |
| 1.12.2 | `net/minecraft/util/math/MathHelper.java` | `579e05d110460f444bcac86729141545095cb5932c0f8d34c9a4dcfcd9a35e80` |
| 1.12.2 | `net/minecraft/client/Minecraft.java` | `8af833a5d03a6f6516a7eff0ab463a96bfec64aac31fc80656f37d193b1c1310` |
| 1.13.2 | `net/minecraft/client/entity/living/player/KeyboardInput.java` | `7be11425906be051c83e275f359816546e4677b16d212156380e8d2e9258654a` |
| 1.13.2 | `net/minecraft/client/entity/living/player/Input.java` | `9e704cfe7fdc55c4eab78670e60cf392ba6817adbd5e7451d3a86f11da8bf50e` |
| 1.13.2 | `net/minecraft/util/math/MathHelper.java` | `2211b0cb3da7d8789a4a4df96509b95e6b1be8b222be2f530e7bd0695573bd69` |
| 1.13.2 | `net/minecraft/client/Minecraft.java` | `b1d35473cd12bdf38e87f9888b4ed6f620614cbe914cf7e7a40c601d4e284875` |
