# 1.19.4–1.20.1 fluid provider and callback audit

## Source identity and bounds

Compared exact decompiled Mojmap source trees:

- 1.19.4 `decompiled_minecraft/1.19.4/mojmap`, ready-marker source-manifest SHA-256 `6286e325371e085dfee1ab7987e66d4b6d1a984a49b6ec8ea730045cd24cecb3`.
- 1.20.1 `decompiled_minecraft/1.20.1/mojmap`, ready-marker source-manifest SHA-256 `858b56764113e591c60d09bc63e4d90f9c1d67a705645f0c793e8b28f2378465`.

This audit covers built-in fluid state/spread providers and direct entity-inside or fluid-contact consumers only far enough to establish ordinary local-player movement relevance. It excludes fluid travel, sprint/glide/climbing eligibility, Honey, and Levitation. No resource manifest was pinned; tag membership and any state setup depending on resource data remain open. No concrete fluid-state setup witness is claimed.

## Provider candidate and failed cobweb witness

**1.19.4, Mojmap:** `net/minecraft/world/level/material/FlowingFluid.java`, `tick` and `getNewLiquid`, lines 405–423 and 154–187; `net/minecraft/world/level/material/WaterFluid.java`, `canConvertToSource`, drop-off, and source/flowing amount, lines 73–75, 99–101, 123–150. `FlowingFluid.tick` recalculates every non-source state using `getNewLiquid`. That method counts horizontally reachable same-fluid source neighbors, and with at least two and source conversion enabled returns a source when the support below is `Material.isSolid()` (line 174). Old `Material.WEB` sets `noCollider()` but not `nonSolid()`; `Material.Builder` defaults `solid=true`, `noCollider()` only clears `blocksMotion`, and `isSolid()` returns the independent solid field (`Material.java:71,108–120,158–185,212–213`).

**1.20.1, Mojmap:** `net/minecraft/world/level/material/FlowingFluid.java`, `tick` and `getNewLiquid`, lines 401–419 and 155–188; `net/minecraft/world/level/material/WaterFluid.java`, the corresponding unchanged lines 73–75, 99–101, 123–150; `net/minecraft/world/level/block/state/BlockBehaviour.java`, `BlockStateBase.calculateSolid` and `isSolid`, lines 428–457 and 475–477. The support test is now `BlockState.isSolid()` (`FlowingFluid.java:175`); absent a `forceSolidOn` override, `calculateSolid` returns false for an empty collision shape (`BlockBehaviour.java:428–448`).

**Checked/falsified candidate:** The apparent support semantic change suggested using cobweb as the support: old `Material.WEB.isSolid()` is true because `noCollider()` does not clear the independent `solid` field. But this is not a vanilla witness. The 1.20.1 `Blocks.COBWEB` registration explicitly calls `.forceSolidOn()` before `.noCollission()` (`Blocks.java:750–760`), and `calculateSolid()` checks `forceSolidOn` before it checks the collision shape (`BlockBehaviour.java:428–448`). Thus cobweb remains solid for `getNewLiquid` in both inspected endpoints. Bamboo sapling also receives `.forceSolidOn()` in 1.20.1 (`Blocks.java:5957–5971`), matching old `Material.BAMBOO_SAPLING.isSolid()`; its separate `blocksMotion` special case keeps the `getFlow` motion predicate false as in old `Material.BAMBOO_SAPLING.noCollider()`.

**Open semantic dependency:** The new source predicate is collision-shape-derived with explicit forced-solid overrides, while the old predicate was the material's independent `solid` flag. This audit has not exhaustively paired every built-in block's old material solidity against the newer cached `legacySolid` result. Therefore it establishes a provider code change, not a reachable vanilla state difference or an equivalence proof.

The local-player contact consumer is reachable on the ordinary tick: `LocalPlayer.tick` delegates through `Player.tick` and `LivingEntity.tick` to `Entity.tick/baseTick`; `baseTick` calls `updateInWaterStateAndDoFluidPushing` (`LocalPlayer.java:187–190` and `Entity.java:405–430` in 1.19.4; `LocalPlayer.java:189–192` and `Entity.java:409–434` in 1.20.1). That method queries water with `FluidTags.WATER` and current scale `0.014` (`Entity.java:1072–1093` / `1176–1198`). `Player.isPushedByFluid()` returns `!abilities.flying` in both versions (`Player.java:1848–1850` / `1874–1876`).

The paired current consumer is textually equivalent apart from `level` accessor calls: it computes the surface as block Y plus `FluidState.getHeight`, takes maximum fluid depth, gets `FluidState.getFlow`, scales it only when depth is below `0.4`, averages per-cell vectors, and for `Player` does not renormalize the average before scaling and adding it to delta movement (`Entity.java:2887–2948` / `3001–3062`; `FluidState.java:49–58,91–92`, unchanged file). Its local-player movement relevance is direct, but no witness is closed until a built-in provider state is found whose source/current output differs under paired exact block predicates.

**Open gates:** Continue the built-in old-material versus new-state solidity audit, including block states with and without `forceSolidOn/Off`; inspect the paired `canHoldFluid` spread gates with exact resource tags; and verify `FluidTags.WATER` membership from a pinned resource manifest before asserting contact execution. This draft claims no water-current velocity or player displacement difference and does not close all fluid-state production.

## Other compared providers and callbacks

- `FlowingFluid.getFlow` changes `getMaterial().blocksMotion()` to `BlockState.blocksMotion()` (1.19.4 `FlowingFluid.java:53–98`; 1.20.1 `:54–99`). The same method changes its falling-fluid ice exception from `Material.ICE` to `block instanceof IceBlock` (`:104–113` / `:105–115`). These predicates are not involved in the falsified cobweb source-conversion candidate. The inspected built-in ice and frosted ice are `IceBlock`/`FrostedIceBlock extends IceBlock` in both endpoints (`Blocks.java:1249–1258,2831–2841` in 1.19.4 and `:2204–2214,4936–4946` in 1.20.1); broader modded/material-class equivalence is outside vanilla-state scope.
- The same `FlowingFluid` source diff changes `canHoldFluid` from material-based replaceability to explicit portal/structure-void exclusions and `BlockState.blocksMotion()` (1.19.4 `:377–397`; 1.20.1 `:378–389`). It consults `BlockTags.SIGNS`; no resource manifest is pinned, so tag membership and spread/setup claims through this branch remain open. `getSpread`, `canPassThroughWall`, and the side/down spread gate are otherwise paired; no complete scheduled spread path is claimed here.
- `FluidState.java` is byte-identical at both endpoints (manifest SHA-256 `0d6d1d24de58f056163acc3ae8976d2c7b3394aca2ae37fa43bc560c9d33b7c`). `WaterFluid.java` is byte-identical (SHA-256 `3e10320356b11d415d8e4f3ea2b7fbb27e5bd8abdc45d524afb18b834e613e5f`).
- `LiquidBlock.entityInside` is absent in both; its only observed diff is the loot-context type on `getDrops` (`LiquidBlock.java:96–99`; source hashes differ). `BubbleColumnBlock.entityInside` is identical at both endpoints (lines 40–70, source SHA-256 `34f7c5400f1b8db997ac18333b7e57497b1667274779d4d410f4177b922cb452`). It calls `onAboveBubbleCol` for an entity overlapping a bubble column whose block above is air, else `onInsideBubbleColumn`; both entity response formulas are unchanged (`Entity.java:2138–2160` / `2245–2267`). The common `Entity.checkInsideBlocks` callback loop remains the route to `BlockState.entityInside` (`Entity.java:896–925` / `965–994`).
- Shared `FlowingFluid.getNewLiquid` also changes the support test used for lava. No separate lava witness is closed: gamerule, lava tag/current-query membership and lava amount/drop-off contact conditions are outside this water-only witness.

## Paired source hashes

SHA-256 values below match the exact pinned source manifests.

| Source | 1.19.4 | 1.20.1 |
|---|---|---|
| `net/minecraft/world/entity/Entity.java` | `3667fee610cbc5f58012e3a8fb8d6c4849f649fb7fe5300595158112d8b4b58b` | `94b9c3656715de2d61fa9a02ccef164261e50eb13d6090a6f07e58fe9c5b0759` |
| `net/minecraft/world/entity/player/Player.java` | `5e4436afccb361156f8184e7a5cfd91d5b12dcfe8f5937b4ac23dd3edda737a2` | `873d82c6f5471d06812929e471d970fd973bfbe21638e4e348a6fcde45637dac` |
| `net/minecraft/client/player/LocalPlayer.java` | `8e7da18f42d09fbb994f522c2b0e65fcb2bb83cabb21024360299d44d9674c58` | `69a2d043d2c0595bd364d625a5445e4934d9e07fef020cd79fafb42f59952ac2` |
| `net/minecraft/world/level/material/FlowingFluid.java` | `d84ceb3c0b83da25fb68599e16cc2298106e745a9ef0c9f32f616d097a8ec56c` | `ba1dff6ccb28b62760c74d66832d8fdbe7a1cdc37b9c5a0ff476bd200215c904` |
| `net/minecraft/world/level/material/FluidState.java` | `0d6d1d24de58f056163acc3ae8976d2c7b3394aca2ae37fa43bc560c9d33b7c` | `0d6d1d24de58f056163acc3ae8976d2c7b3394aca2ae37fa43bc560c9d33b7c` |
| `net/minecraft/world/level/material/WaterFluid.java` | `3e10320356b11d415d8e4f3ea2b7fbb27e5bd8abdc45d524afb18b834e613e5f` | `3e10320356b11d415d8e4f3ea2b7fbb27e5bd8abdc45d524afb18b834e613e5f` |
| `net/minecraft/world/level/material/Material.java` / `net/minecraft/world/level/block/state/BlockBehaviour.java` | `Material.java`: `842c7ee4723993c6ee3bf4732343f3225bb05aac5ee80c919c34c3ba1a027431`; `BlockBehaviour.java`: `517aa4bd9875e9f7b3f5fba472a84e82afd1e11e35d3c83dc2677d8bbeb4997d` | `BlockBehaviour.java`: `5f37987191165379c222c412949a0c80a904bf78a453035b1f9d86c6454510fa` (`Material` class removed) |
| `net/minecraft/world/level/block/Blocks.java` | `a4f2df87c2cae5a3827a818f39f7ad123ee5b18905ead9947efe654421c30ff5` | `1e7095d1fbfdc8198191f40037997feeca2ec993cc54dd53f2636375cae66b51` |
| `net/minecraft/world/level/block/LiquidBlock.java` | `c4dd00de721b3edb45b47832c2bf68e22ee4d065b984f179a206a33eeda61f02` | `691765d865bdc4c384d7bd4482bbf041def46df4c419b08c975652b37a4353e9` |
| `net/minecraft/world/level/block/BubbleColumnBlock.java` | `34f7c5400f1b8db997ac18333b7e57497b1667274779d4d410f4177b922cb452` | `34f7c5400f1b8db997ac18333b7e57497b1667274779d4d410f4177b922cb452` |

No exact intermediate release was inspected; any change boundary within 1.19.4–1.20.1 remains unknown. Pair coverage outside these provider/callback slices remains open.
